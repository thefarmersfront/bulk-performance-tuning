package com.example.bulkinsert.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@Entity
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST)
    private List<ArticleEntity> articleEntityList;

    /**
     * constructor
     */
    public MemberEntity(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.articleEntityList = new ArrayList<>();
    }

    /**
     * article 추가
     */
    public void addArticleEntity(ArticleEntity article) {
        this.articleEntityList.add(article);
    }
}
