package com.example.bulkinsert.services;

import com.example.bulkinsert.dtos.Article;
import com.example.bulkinsert.dtos.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JdbcMemberService {
    private final JdbcTemplate jdbcTemplate;

    /**
     * jdbc를 이용한 insert
     */
    @Transactional
    public void createMember(List<Member> memberList) {
        // member 먼저 bulk insert
        jdbcTemplate.batchUpdate(
                "INSERT INTO member (age, name) VALUES(?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Member insertMember = memberList.get(i);
                        ps.setInt(1, insertMember.getAge());
                        ps.setString(2, insertMember.getName());
                    }

                    @Override
                    public int getBatchSize() {
                        return memberList.size();
                    }
                }
        );

        // 실행된 bulk insert의 첫번째 Primary key 조회
        Long firstRowInsertPK = jdbcTemplate.queryForObject("SELECT last_insert_id()", Long.class);

        // 조회한 첫번째 PK로 각 member별 PK 맵핑
        for (int i = 0; i < memberList.size(); i++) {
            Member setIdTargetMember = memberList.get(i);
            setIdTargetMember.setId(i + firstRowInsertPK);
        }

        // insert할 member 연관 Article 생성 (위에서 맵핑한 member의 PK를 기반으로 FK를 알고 있는 article 객첸)
        List<Article> articleList = memberList.stream()
                .flatMap(member -> member.getArticleListWithFK().stream())
                .collect(Collectors.toList());


        // FK를 가진상태로 article bulk insert
        jdbcTemplate.batchUpdate(
                "INSERT INTO article (name, like_count, member_id) VALUES(?, ?, ?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Article insertArticle = articleList.get(i);
                        ps.setString(1, insertArticle.getName());
                        ps.setLong(2, insertArticle.getLikeCount());
                        ps.setLong(3, insertArticle.getMemberId());
                    }

                    @Override
                    public int getBatchSize() {
                        return articleList.size();
                    }
                }
        );
    }
}
