package com.example.bulkinsert.repositories;

import com.example.bulkinsert.entities.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    @Query("SELECT DISTINCT m FROM MemberEntity m JOIN FETCH m.articleEntityList  ORDER BY m.id ASC")
    List<MemberEntity> findAllByIdWithArticle();
}
