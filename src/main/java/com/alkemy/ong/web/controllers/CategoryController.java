package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.categories.Category;
import com.alkemy.ong.domain.categories.CategoryService;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.utils.PageModel;
import com.alkemy.ong.web.controllers.utils.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import static java.util.stream.Collectors.toList;


@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController (CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @Operation(summary = "Get all category names",
            description = "Get all category names separated by pages of 10")
    @ApiResponses(
            value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "Categories found",
                        content = @Content(
                                mediaType = "JSON Value",
                                schema = @Schema(implementation = CategoryBasicDTO.class)
                        )
                ),
                @ApiResponse(
                        responseCode = "400",
                        description = "Bad Request",
                        content = @Content(
                                examples = @ExampleObject(
                                        name = "400",
                                        summary = "Client Error 400",
                                        description = "Bad Request, site not correct",
                                        value = ("Bad request, URL not exist")
                                )
                        ))
            }
    )
    @GetMapping
    private ResponseEntity<PageResponse<CategoryBasicDTO>> getAllCategoryBasicByPage(@RequestParam("page") int pageNumber){

        PageModel<Category> page = categoryService.findByPage(pageNumber);
        String path = "/categories";
        PageResponse response = PageResponse.builder()
                .content(page.getContent()
                        .stream()
                        .map(this::toDTOBasic)
                        .collect(toList()))
                .build();
        response.setResponse(path,pageNumber,page.getTotalPages(),page.isFirst(),page.isLast());
        return ResponseEntity.ok().body(response);
    }

    @Operation(summary = "Get category by ID",
            description = "Get category by ID with all details")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success, category found",
                            content = @Content(
                                    mediaType = "JSON Value",
                                    schema = @Schema(implementation = CategoryDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category not found",
                            content = @Content(
                                    mediaType = "JSON Value",
                                    schema = @Schema(implementation = ResourceNotFoundException.class),
                                    examples = @ExampleObject(
                                            name = "404",
                                            summary = "Client Error 404",
                                            description = "Bad Request, ID is not found",
                                            value = ("Error, ID is not found.")
                                    )
                            ))
            }
    )
    @GetMapping("/{id}")
    private ResponseEntity<CategoryDTO> getCategoryByID(@PathVariable Long id){
        Category category = categoryService.findById(id);
        CategoryDTO categoryDTO = toDTO(category);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @Operation(summary = "Create category",
            description = "Create category with all details")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Category created"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                            examples = @ExampleObject(
                                    name = "400",
                                    summary = "Client Error 400",
                                    description = "The request of the save category is no correct",
                                    value = ("Bad request, category not saved")
                            )
                    ))}
    )
    @PostMapping
    private  ResponseEntity<CategoryDTO> save(@Valid @RequestBody CategoryDTO categoryDTO){
        Category category = toModel(categoryDTO);
        Category categorySaved = categoryService.save(category);
        CategoryDTO result = toDTO(categorySaved);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Delete category by ID",
            description = "Soft delete by ID of the specific category")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No content, category deleted"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category not found",
                            content = @Content(
                                    mediaType = "JSON Value",
                                    schema = @Schema(implementation = ResourceNotFoundException.class),
                                    examples = @ExampleObject(
                                    name = "404",
                                    summary = "Client Error 404",
                                    description = "The request id is not found",
                                    value = ("Error, ID is not found.")
                            )
                            ))
            }
    )
    @DeleteMapping("/{id}")
    private ResponseEntity<Void> delete(@PathVariable Long id){
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();}

    @Operation(summary = "Update category by ID",
            description = "Update all details of the specific category ")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success, category updated"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Category not found",
                            content = @Content(
                                    mediaType = "JSON Value",
                                    schema = @Schema(implementation = ResourceNotFoundException.class),
                                    examples = @ExampleObject(
                                    name = "404",
                                    summary = "Client Error 400",
                                    description = "The request id is not found",
                                    value = ("Error, ID is not found.")
                            )
                            ))
            }
    )
    @PutMapping("/{id}")
    private ResponseEntity<CategoryDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryDTO categoryDTO){
        Category categoryUpdated = categoryService.update(id, toModel(categoryDTO));
        return  ResponseEntity.ok().body(toDTO(categoryUpdated));

    }

    @Data
    @Builder
    private static class CategoryDTO {
        @Schema(example = "1", description = "ID of the category")
        private Long id;
        @Schema(example = "Health", description = "Name of the category (Without numbers)")
        @NotEmpty(message = "The field must be a name")
        @Pattern(regexp = "[A-Za-z]*$")
        private String name;
        @Schema(example = "Health is responsible for keeping the population healthy", description = "Brief description of the category")
        private String description;
        @Schema(example = "/health", description = "URL of the category image")
        private String image;
    }

    @Data
    @Builder
    private static class CategoryBasicDTO {
        @Schema(example = "1", description = "ID of the category")
        private Long id;
        @Schema(example = "Health", description = "Name of the category (Without numbers)")
        @NotEmpty(message = "The field must be a name")
        @Pattern(regexp = "[A-Za-z]*$")
        private String name;
    }

    private CategoryBasicDTO toDTOBasic(Category category) {
        return CategoryBasicDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    private CategoryDTO toDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .image(category.getImage())
                .build();
    }

    private Category toModel(CategoryDTO categoryDTO){
        return Category.builder()
                .name(categoryDTO.getName())
                .description(categoryDTO.getDescription())
                .image(categoryDTO.getImage())
                .build();
    }
}
