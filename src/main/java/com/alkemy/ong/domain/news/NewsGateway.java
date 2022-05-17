package com.alkemy.ong.domain.news;


import com.alkemy.ong.domain.utils.PageModel;

public interface NewsGateway {

    News getDetails(Long id);
    News findById(Long id);
    News save(News news);
    void delete(Long id);
    PageModel findByPage(int pageNumber, int size);
    News update(News news, Long id);
}
