package in.clouthink.daas.es.repository.page;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public interface PageableSearchRequest {

    int getPage();

    int getSize();

    String getSortBy();

    Sort.Direction getSortDirection();

    Pageable resolvePageable();

}
