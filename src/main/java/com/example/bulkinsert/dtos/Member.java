package com.example.bulkinsert.dtos;

import lombok.Getter;

import java.util.List;

@Getter
public class Member {
    private Long id;
    private String name;
    private Integer age;
    private List<Article> articleList;

    public Member(String name, Integer age, List<Article> articleList) {
        this.name = name;
        this.age = age;
        this.articleList = articleList;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * member의 id를 FK로 기잔 상태로 반환
     */
    public List<Article> getArticleListWithFK() {
        articleList.forEach(article -> {
            article.setMemberId(id);
        });

        return articleList;
    }
}
