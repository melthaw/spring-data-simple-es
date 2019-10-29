package in.clouthink.daas.es6.repository.page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

public class DefaultPageableSearchRequest implements PageableSearchRequest {

    private int page = 0;

    private int size = 20;

    private String sortBy;

    private Sort.Direction sortDirection = Sort.Direction.ASC;

    @Override
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    @Override
    public Sort.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Sort.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public Pageable resolvePageable() {
        if (page < 0) {
            page = 0;
        }
        if (size < 1) {
            size = 20;
        }

        if (!StringUtils.isEmpty(sortBy)) {
            return new PageRequest(page, size, new Sort(new Sort.Order(sortDirection, sortBy)));
        }

        return new PageRequest(page, size);
    }

}
