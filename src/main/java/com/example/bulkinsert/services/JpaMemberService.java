package com.example.bulkinsert.services;

import com.example.bulkinsert.dtos.Member;
import com.example.bulkinsert.entities.ArticleEntity;
import com.example.bulkinsert.entities.MemberEntity;
import com.example.bulkinsert.repositories.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JpaMemberService {
    private final MemberJpaRepository memberJpaRepository;

    /**
     * jpa를 이용한 insert
     */
    @Transactional
    public void createMember(List<Member> memberList) {
        var memberEntityList = memberList.stream()
                .map(member -> {
                    var memberEntity = new MemberEntity(
                            member.getName(),
                            member.getAge()
                    );

                    member.getArticleList().forEach(article -> {
                        var articleEntity = new ArticleEntity(
                                article.getName(),
                                article.getLikeCount(),
                                memberEntity
                        );
                        memberEntity.addArticleEntity(articleEntity);
                    });

                    return memberEntity;
                })
                .collect(Collectors.toList());

        memberJpaRepository.saveAll(memberEntityList);
    }
}
