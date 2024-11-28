package com.management.project.commons;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PagingProvider {
    /**
     * Tạo Pageable từ PagingRequestDto.
     *
     * @param pagingRequestDto thông tin phân trang và sắp xếp
     * @return Pageable
     */
    public static Pageable toPageable(PagingRequestDto pagingRequestDto) {
        Sort sort = Sort.by(pagingRequestDto.getSortBy());
        if ("DESC".equalsIgnoreCase(pagingRequestDto.getSortDirection())) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }

        return PageRequest.of(pagingRequestDto.getPage(), pagingRequestDto.getPageSize(), sort);
    }

    /**
     * Tạo Pageable với tuỳ chỉnh sẵn.
     *
     * @param page       số trang
     * @param pageSize   kích thước trang
     * @param sortBy     cột sắp xếp
     * @param direction  hướng sắp xếp (ASC/DESC)
     * @return Pageable
     */
    public static Pageable customPageable(int page, int pageSize, String sortBy, String direction) {
        Sort sort = Sort.by(sortBy);
        if ("DESC".equalsIgnoreCase(direction)) {
            sort = sort.descending();
        } else {
            sort = sort.ascending();
        }
        return PageRequest.of(page, pageSize, sort);
    }

    public static int calculateOffset(int page, int pageSize) {
        return page * pageSize; // Offset = page number * page size
    }

}
