package com.management.project.responses.commons;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content = new ArrayList<>();
    private int page = 1;
    private int pageSize = 10;
    private int totalElements;
    private int totalPages;

    @Builder
    public PageResponse(List<T> content, int page, int pageSize, int totalElements) {
        this.content = content;
        this.page = page;
        this.pageSize = pageSize;
        this.totalElements = totalElements;
        this.totalPages = totalElements % pageSize == 0 ? (int) (totalElements / pageSize) : (int) (totalElements / pageSize) + 1;
    }
}
