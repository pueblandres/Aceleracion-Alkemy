package com.alkemy.ong.domain.news;

import com.alkemy.ong.domain.utils.PageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class NewsService  {
    private final NewsGateway newsGateway;
    @Value("${spring.application.pageSize}")
    int pageSize;
    public NewsService(NewsGateway newsGateway){
        this.newsGateway = newsGateway;
    }
    public News getDetails(Long id) {
        return newsGateway.getDetails(id);
    }
    public News save(News news) {
        news.setType("news");
        return newsGateway.save(news);
    }
    public void delete(Long id) {
        newsGateway.delete(id);
    }
    public PageModel findByPage(int pageNumber) {
        return newsGateway.findByPage(pageNumber, pageSize);
    }

    public News update(News news, Long id) {
        return newsGateway.update(news,id);
    }
}
