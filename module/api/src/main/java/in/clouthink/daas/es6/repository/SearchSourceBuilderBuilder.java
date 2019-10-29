package in.clouthink.daas.es6.repository;

import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;

public class SearchSourceBuilderBuilder {

    public static SearchSourceBuilder build(Pageable pageable) {
        return build(pageable, Fields.empty());
    }

    public static SearchSourceBuilder build(Pageable pageable, Fields fields) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.from(pageable.getOffset());
        searchSourceBuilder.size(pageable.getPageSize());

        if (pageable.getSort() != null) {
            Iterator<Sort.Order> sortOrderIterator = pageable.getSort().iterator();
            Sort.Order sortOrder;
            while (sortOrderIterator.hasNext()) {
                sortOrder = sortOrderIterator.next();
                searchSourceBuilder.sort(sortOrder.getProperty(),
                                         sortOrder.getDirection() == Sort.Direction.ASC ?
                                         SortOrder.ASC :
                                         SortOrder.DESC);
            }
        }

        if (fields != null && !fields.isEmpty()) {
            searchSourceBuilder.fetchSource(fields.getIncludedFields(), fields.getExcludedFields());
        }

        return searchSourceBuilder;
    }

}
