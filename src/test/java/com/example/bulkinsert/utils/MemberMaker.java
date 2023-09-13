package com.example.bulkinsert.utils;

import com.example.bulkinsert.dtos.Article;
import com.example.bulkinsert.dtos.Member;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.List;

public class MemberMaker {
    private static final Long MEMBER_COUNT = 10000L;

    /**
     * 테스트용 member 데이터 생성 (article 포함)
     */
    public static List<Member> makeMemberWithArticle() {
        var memberList = new ArrayList<Member>();

        for (int i = 0; i < MEMBER_COUNT; i++) {

            var articleList = new ArrayList<Article>();
            for (int j = 0; j < RandomUtils.nextInt(1, 5); j++) {
                articleList.add(
                        new Article(
                                RandomStringUtils.randomAlphabetic(6),
                                RandomUtils.nextLong(1,500)
                        )
                );
            }

            memberList.add(
                    new Member(
                            RandomStringUtils.randomAlphabetic(6),
                            RandomUtils.nextInt(1,500),
                            articleList
                    )
            );

        }

        return memberList;
    }
}
