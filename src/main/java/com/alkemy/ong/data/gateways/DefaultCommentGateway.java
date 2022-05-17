package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.CommentEntity;
import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.entities.UserEntity;
import com.alkemy.ong.data.repositories.CommentRepository;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.data.repositories.UserRepository;
import com.alkemy.ong.domain.comments.Comment;
import com.alkemy.ong.domain.comments.CommentGateway;
import com.alkemy.ong.domain.exceptions.BadRequestException;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultCommentGateway implements CommentGateway {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;

    public DefaultCommentGateway(CommentRepository commentRepository, UserRepository userRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.newsRepository = newsRepository;
    }

    public Comment create(Comment comment) {
        CommentEntity commentEntity = toEntity(comment);
        return toModel(commentRepository.save(commentEntity));
    }

    public List<Comment> findAll() {
        return commentRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(p -> toModel(p))
                .collect(toList());
    }

    public void delete(Long id) {
        commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Id"));
        commentRepository.deleteById(id);
    }

    public Comment update(Long id, Comment comment) {
        CommentEntity commentEntity = commentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found Id"));
        commentEntity.setBody(comment.getBody());
        commentEntity.setUserId(getUserEntity(comment.getUserId()));
        commentEntity.setNewsId(getNewsEntity(comment.getNewsId()));
        return toModel(commentRepository.save(commentEntity));
    }

    private UserEntity getUserEntity(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(
                        () -> new BadRequestException(HttpStatus.BAD_REQUEST, "User with id: " + userId + " not found.")
                );
    }

    private NewsEntity getNewsEntity(Long newsId) {
        return newsRepository.findById(newsId)
                .orElseThrow(
          () -> new ResourceNotFoundException("News with id: " + newsId)
                );
    }

    @Override
    public List<Comment> findAllByNewsId(Long id) {
        return commentRepository.findAllByNewsId(this.getNewsEntity(id))
                .stream()
                .map(this::toModel)
                .collect(toList());
    }

    private CommentEntity toEntity(Comment comment) {
        return CommentEntity.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .userId(getUserEntity(comment.getUserId()))
                .newsId(getNewsEntity(comment.getNewsId()))
                .build();
    }

    private Comment toModel(CommentEntity entity) {
        return Comment.builder()
                .id(entity.getId())
                .body(entity.getBody())
                .userId(entity.getUserId().getId())
                .newsId(entity.getNewsId().getId())
                .build();
    }
}
