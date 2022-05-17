package com.alkemy.ong.web.controllers.utils;

import lombok.Builder;
import lombok.Data;

import java.util.List;


@Data
@Builder
public class PageResponse<T> {

    private List<T> content;
    private String nextPage;
    private String previousPage;

    public void setResponse(String path, int pageNumber, int totalPages, boolean isFirst, boolean isLast){
        if(pageNumber >= totalPages){
            throw new IllegalArgumentException("Incorrect index");
        }
        this.nextPage = isLast
                ? ""
                : path + "?page=" + (pageNumber + 1) ;
        this.previousPage = isFirst
                ? ""
                : path + "?page=" + (pageNumber - 1) ;
    }
}
