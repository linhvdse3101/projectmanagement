package com.management.project.commons;

import lombok.Data;

@Data
public class PagingRequestDto {
    private int page = 0;
    private int pageSize = 10;
    private String sortBy = "";
    private String sortDirection = "ASC";
    private String query = "";

    public PagingRequestDto(){};

    public PagingRequestDto(int page, int pageSize, String sortBy, String sortDirection) {
        this.page = page - 1;
        this.pageSize = pageSize;
        this.sortBy = sortBy;
        this.sortDirection = sortDirection;
    }
}
