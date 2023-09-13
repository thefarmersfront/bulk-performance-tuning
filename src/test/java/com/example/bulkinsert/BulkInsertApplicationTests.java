package com.example.bulkinsert;

import com.example.bulkinsert.dtos.Member;
import com.example.bulkinsert.entities.MemberEntity;
import com.example.bulkinsert.repositories.MemberJpaRepository;
import com.example.bulkinsert.services.JdbcMemberService;
import com.example.bulkinsert.services.JpaMemberService;
import com.example.bulkinsert.utils.MemberMaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.shaded.com.google.common.collect.Lists;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class BulkInsertApplicationTests {
    @Autowired
    private JpaMemberService jpaMemberService;

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private JdbcMemberService jdbcMemberService;

    @DisplayName("jpa bulk insert 테스트")
    @Test
    void jpaTest() {
        // insert 대상 데이터 생성
        List<Member> insertMemberList = MemberMaker.makeMemberWithArticle();
        List<List<Member>> partitionMemberList = Lists.partition(insertMemberList, 1000);

        // 1000개씩 나눠서 insert
        partitionMemberList.forEach(members -> {
            jpaMemberService.createMember(members);
        });

        // 저장된 데이터 조회
        List<MemberEntity> savedMembers = memberJpaRepository.findAllByIdWithArticle();
        //insert 대상 데이터 <-> 실제 저장된 데이터 비교
        equalCheck(insertMemberList, savedMembers);
    }

    @DisplayName("jdbc bulk insert 테스트")
    @Test
    void jdbcTest() {
        // insert 대상 데이터 생성
        List<Member> insertMemberList = MemberMaker.makeMemberWithArticle();
        List<List<Member>> partitionMemberList = Lists.partition(insertMemberList, 1000);

        // 1000개씩 나눠서 insert
        partitionMemberList.forEach(members -> {
            jdbcMemberService.createMember(members);
        });

        // 저장된 데이터 조회
        List<MemberEntity> savedMembers = memberJpaRepository.findAllByIdWithArticle();
        //insert 대상 데이터 <-> 실제 저장된 데이터 비교
        equalCheck(insertMemberList, savedMembers);
    }

    /**
     * insert 대상 데이터 <-> 실제 저장된 데이터 비교
     */
    void equalCheck(List<Member> insertTargetMembers, List<MemberEntity> savedMembers) {
        for (int i = 0; i < insertTargetMembers.size(); i++) {
            var insertTargetMember = insertTargetMembers.get(i);
            var savedMember = savedMembers.get(i);
            assertThat(insertTargetMember.getName()).isEqualTo(savedMember.getName());
            assertThat(insertTargetMember.getAge()).isEqualTo(savedMember.getAge());
            assertThat(insertTargetMember.getArticleList()).isNotEmpty();
            assertThat(insertTargetMember.getArticleList().size()).isEqualTo(savedMember.getArticleEntityList().size());

            var insertTargetArticles = insertTargetMember.getArticleList();
            var savedArticles = savedMember.getArticleEntityList();

            for (int j = 0; j < insertTargetArticles.size(); j++) {
                var insertTargetArticle = insertTargetArticles.get(j);
                var savedArticle = savedArticles.get(j);

                assertThat(insertTargetArticle.getName()).isEqualTo(savedArticle.getName());
                assertThat(insertTargetArticle.getLikeCount()).isEqualTo(savedArticle.getLikeCount());
            }
        }
    }
}
