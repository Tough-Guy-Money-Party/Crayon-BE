package com.yoyomo.domain.shared.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PageUtil {
    private static final int PAGE_SIZE = 10;
    private static final String SORT_BY = "createdAt";

    public static Pageable makePageObject(int pageNum) {
        Sort sort = Sort.by(SORT_BY).ascending();
        return PageRequest.of(pageNum, PAGE_SIZE, sort);
    }
}
