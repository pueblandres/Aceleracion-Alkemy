package com.alkemy.ong.domain.categories;

import com.alkemy.ong.domain.utils.PageModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryGateway categoryGateway;

    public CategoryService(CategoryGateway categoryGateway){
        this.categoryGateway = categoryGateway;
    }

    public List<Category> findAll(){
        return categoryGateway.findAll();
    }

    public Category findById(Long id){return categoryGateway.findById(id);}

    public Category save(Category category){return categoryGateway.save(category);}

    public void delete (Long id){categoryGateway.delete(id);}
    public Category update(Long id, Category category){return categoryGateway.update(id, category);}

    @Value("${spring.application.pageSize}")
    int pageSize;

    public PageModel findByPage(int pageNumber) {
        return categoryGateway.findByPage(pageNumber, pageSize);
}

}
