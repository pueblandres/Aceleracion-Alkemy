package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.CategoryEntity;
import com.alkemy.ong.data.entities.NewsEntity;
import com.alkemy.ong.data.repositories.CategoryRepository;
import com.alkemy.ong.domain.categories.Category;
import com.alkemy.ong.domain.categories.CategoryGateway;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.utils.PageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.*;

@Component
public class DefaultCategoryGateway implements CategoryGateway {

    private final CategoryRepository categoryRepository;
    
    public DefaultCategoryGateway(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll().stream().map(this::toModel).collect(toList());
    }

    @Override
    public Category findById(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID"));
        return toModel(categoryEntity);
    }

    @Override
    public Category save(Category category) {
        CategoryEntity categoryEntity = toEntity(category);
        return toModel(categoryRepository.save(categoryEntity));
    }

    @Override
    public void delete(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID"));
        categoryEntity.setIsDeleted(true);
        categoryRepository.save(categoryEntity);}

    public Category update(Long id, Category category) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID"));
        categoryEntity.setName(category.getName());
        categoryEntity.setDescription(category.getDescription());
        categoryEntity.setImage(category.getImage());
        return toModel(categoryRepository.save(categoryEntity));
    }

    @Override
    public PageModel findByPage(int pageNumber, int size) {
        Page<CategoryEntity> page = categoryRepository.findAll(PageRequest.of(pageNumber, size));
        return PageModel.builder()
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .isFirst(page.isFirst())
                .content(page.getContent()
                        .stream()
                        .map(this::toModel)
                        .collect(toList()))
                .build();    }

    private Category toModel(CategoryEntity categoryEntity) {
        return Category.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .image(categoryEntity.getImage())
                .build();

    }

    private CategoryEntity toEntity(Category category) {
        return CategoryEntity.builder()
                .name(category.getName())
                .description(category.getDescription())
                .image(category.getImage())
                .isDeleted(false)
                .build();
    }


}
