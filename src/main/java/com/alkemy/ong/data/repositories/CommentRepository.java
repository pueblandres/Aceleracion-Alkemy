package com.alkemy.ong.data.repositories;

import com.alkemy.ong.data.entities.CommentEntity;
import com.alkemy.ong.data.entities.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findAllByNewsId(NewsEntity entity);
}
