package com.alkemy.ong.domain.comments;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentGateway commentGateway;

    public CommentService(CommentGateway commentGateway) {
        this.commentGateway = commentGateway;
    }

    public List<Comment> findAll() {
        return commentGateway.findAll();
    }

    public Comment create(Comment comment) {
        return commentGateway.create(comment);
    }

    public Comment update(Long id, Comment comment) {
        return commentGateway.update(id, comment);
    }

    public void delete(Long id) {
        commentGateway.delete(id);
    }

    public List<Comment> findAllByNewsId(Long id) {
        return commentGateway.findAllByNewsId(id);
    }
}
