package com.alkemy.ong.domain.comments;

import java.util.List;

public interface CommentGateway {

    List<Comment> findAll();

    Comment create(Comment comment);

    Comment update(Long id, Comment comment);

    void delete(Long id);

    List<Comment> findAllByNewsId(Long id);
}
