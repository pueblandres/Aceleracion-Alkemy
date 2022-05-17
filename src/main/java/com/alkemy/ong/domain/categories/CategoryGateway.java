package com.alkemy.ong.domain.categories;

import com.alkemy.ong.domain.utils.PageModel;

import java.util.List;

public interface CategoryGateway {

    List<Category> findAll();

    Category findById(Long id);

    Category save(Category category);

    void delete(Long id);
    Category update(Long id, Category category);

    PageModel findByPage(int pageNumber, int size);
}
