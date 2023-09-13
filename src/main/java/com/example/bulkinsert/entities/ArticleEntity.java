package com.example.bulkinsert.entities;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "article")
@Entity
public class ArticleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long likeCount;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    /**
     * constructor
     */
    public ArticleEntity(String name, Long likeCount, MemberEntity memberEntity) {
        this.name = name;
        this.likeCount = likeCount;
        this.memberEntity = memberEntity;
    }
}
