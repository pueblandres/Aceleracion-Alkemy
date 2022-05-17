package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.testimonials.Testimonial;
import com.alkemy.ong.domain.testimonials.TestimonialService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {

    private final TestimonialService testimonialService;

    @Autowired
    public TestimonialController(TestimonialService testimonialService) {
        this.testimonialService = testimonialService;
    }

    @Operation(
            summary = "Get all testimonials",
            description = "Get testimonials, 10 for page"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Testimonials found",
                            content = @Content(mediaType = "JSON Value",
                                    schema = @Schema(implementation = TestimonialDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "400",
                                            summary = "Bad request",
                                            description = "Bad request",
                                            value = "Incorrect index"
                                    )
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<PageResponse<TestimonialDTO>> getTestimonialPage(@RequestParam("page") int pageNumber) {
        PageModel<Testimonial> page = testimonialService.findByPage(pageNumber);
        String path = "/testimonials";
        PageResponse response = PageResponse.builder()
                .content(page.getContent()
                        .stream()
                        .map(this::toDto)
                        .collect(toList()))
                .build();
        response.setResponse(path, pageNumber, page.getTotalPages(), page.isFirst(), page.isLast());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Operation(
            summary = "Create a testimonial",
            description = "Create a testimonial in table"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Testimonial created"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Bad request",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "400",
                                            summary = "Bad request",
                                            description = "Body request is incompleted",
                                            value = "Testimonial not saved"
                                    )
                            )
                    )
            }
    )
    @PostMapping
    public ResponseEntity<TestimonialDTO> save(@Valid @RequestBody TestimonialDTO dto) {
        Testimonial testimonial = testimonialService.save(toDomain(dto));
        return new ResponseEntity<>(toDto(testimonial), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update testimonial",
            description = "Update testimonial, for testimonial id"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Updated testimonial successfully"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Bad request",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "404",
                                            summary = "Not Found",
                                            description = "Testimonial's Id, not found",
                                            value = "Testimonial don't update"
                                    )
                            )
                    )
            }
    )
    @PutMapping("/{id}")
    public ResponseEntity<TestimonialDTO> update(@PathVariable Long id, @RequestBody TestimonialDTO dto) {
        Testimonial testimonial = testimonialService.update(id, toDomain(dto));
        return new ResponseEntity<>(toDto(testimonial), HttpStatus.OK);
    }

    @Operation(
            summary = "Deleted testimonial",
            description = "Deleted testimonial, deleted logical"
    )
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "No content"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "404",
                                            summary = "Not Found",
                                            description = "Testimonial's Id, not found",
                                            value = "Testimonial don't deleted"
                                    )
                            )
                    )
            }
    )
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        testimonialService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private Testimonial toDomain(TestimonialDTO dto) {
        return Testimonial.builder()
                .id(dto.getId())
                .name(dto.getName())
                .image(dto.getImage())
                .content(dto.getContent())
                .build();
    }

    private TestimonialDTO toDto(Testimonial testimonial) {
        return TestimonialDTO.builder()
                .id(testimonial.getId())
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .build();
    }

    @Builder
    @Data
    public static class TestimonialDTO {
        @Schema(example = "1", description = "ID of testimonial")
        private Long id;
        @Schema(example = "Personal growth", description = "Name of testimonial")
        @NotBlank(message = "The name field not empty")
        private String name;
        @NotBlank(message = "The content field not empty")
        private String content;
        @Schema(example = "https://cdn.pixabay.com/photo/2022/01/22/16/54/book-6957870_960_720.jpg", description = "URL of testimonial image")
        private String image;
    }

}
