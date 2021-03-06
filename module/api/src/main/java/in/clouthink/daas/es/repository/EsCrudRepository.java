package in.clouthink.daas.es.repository;

import in.clouthink.daas.es.model.MutableIdentityProvider;
import in.clouthink.daas.es.repository.page.PageableSearchRequest;
import org.springframework.data.domain.Page;

public interface EsCrudRepository<T extends MutableIdentityProvider<String>, R extends PageableSearchRequest> {

    T getOne(String id);

    Page<T> getAll(PageableSearchRequest pageableSearchRequest);

    Page<T> getAll(PageableSearchRequest pageableSearchRequest, Fields fields);

    Page<T> search(R searchRequest);

    Page<T> search(R searchRequest, Fields fields);

    String save(T record);

    void saveBulk(T... records);

    void update(String id, T record);

    void delete(String id);

}
