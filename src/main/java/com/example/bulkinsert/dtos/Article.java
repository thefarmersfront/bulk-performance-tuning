package com.example.bulkinsert.dtos;

import lombok.Getter;

@Getter
public class Article {
    private String name;
    private Long likeCount;
    private Long memberId;

    public Article(String name, Long likeCount) {
        this.name = name;
        this.likeCount = likeCount;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
}
