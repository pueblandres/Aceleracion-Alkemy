package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.NewsRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.news.News;
import com.alkemy.ong.domain.news.NewsGateway;
import com.alkemy.ong.domain.utils.PageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import static java.util.stream.Collectors.toList;

@Component
public class DefaultNewsGateway implements NewsGateway {

    private final NewsRepository newsRepository;
    public DefaultNewsGateway(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    public News findById(Long id) {
        NewsEntity news = newsRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("ID"));
        return toModel(news);
    }

    @Override
    public News save(News news) {
        NewsEntity newsEntity = this.toEntity(news);
        return this.toModel(newsRepository.save(newsEntity));
    }

    @Override
    public News getDetails(Long id) {
        return this.findById(id);
    }

    @Override
    public void delete(Long id) {
        this.findById(id);
        newsRepository.deleteById(id);
    }

    @Override
    public PageModel findByPage(int pageNumber, int size) {
        Page<NewsEntity> page = newsRepository.findAll(PageRequest.of(pageNumber, size));
        return PageModel.builder()
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .isFirst(page.isFirst())
                .content(page.getContent()
                        .stream()
                        .map(this::toModel)
                        .collect(toList()))
                .build();
    }

    @Override
    public News update(News news, Long id) {
        NewsEntity entity = newsRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("news's ID"));
        refreshValues(entity,news);
        return toModel(newsRepository.save(entity));
    }

    private void refreshValues(NewsEntity entity, News news) {
        entity.setName(news.getName());
        entity.setContent(news.getContent());
        entity.setImage(news.getImage());
    }

    private NewsEntity toEntity(News news){
        return NewsEntity.builder()
                .id(news.getId())
                .name(news.getName())
                .content(news.getContent())
                .image(news.getImage())
                .categoryId(news.getCategoryId())
                .type(news.getType())
                .build();
    }

    private News toModel(NewsEntity newsEntity){
        return News.builder()
                .id(newsEntity.getId())
                .name(newsEntity.getName())
                .content(newsEntity.getContent())
                .image(newsEntity.getImage())
                .categoryId(newsEntity.getCategoryId())
                .type(newsEntity.getType())
                .build();
    }
}
