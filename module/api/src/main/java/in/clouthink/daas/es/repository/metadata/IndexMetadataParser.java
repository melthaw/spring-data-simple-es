package in.clouthink.daas.es.repository.metadata;

import in.clouthink.daas.es.annotation.Id;
import in.clouthink.daas.es.annotation.Index;
import in.clouthink.daas.es.exception.DuplicatedIdAnnotationException;
import in.clouthink.daas.es.exception.IdAnnotationRequiredException;
import in.clouthink.daas.es.exception.IndexAnnotationRequiredException;
import in.clouthink.daas.es.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.List;

public class IndexMetadataParser {

    /**
     * @param entityClazz the class of entity
     * @return IndexMetadata
     */
    public static IndexMetadata parseIndexMetadata(Class entityClazz) {
        if (entityClazz == null) {
            throw new IllegalArgumentException("The class must not be null");
        }

        Index indexAnno = (Index) entityClazz.getAnnotation(Index.class);
        if (indexAnno == null) {
            throw new IndexAnnotationRequiredException(entityClazz.getSimpleName());
        }

        List<Field> foundFields = ClassUtils.getFieldsListWithAnnotation(entityClazz, Id.class);

        if (foundFields.isEmpty()) {
            throw new IdAnnotationRequiredException();
        }
        if (foundFields.size() > 1) {
            throw new DuplicatedIdAnnotationException();
        }

        Field idField = foundFields.get(0);
        Id idAnno = idField.getAnnotation(Id.class);

        return new IndexMetadata(indexAnno.value(), idAnno.strategy());
    }


}
