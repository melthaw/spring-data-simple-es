package in.clouthink.daas.es6.repository.impl;

import in.clouthink.daas.es6.annotation.Id;
import in.clouthink.daas.es6.annotation.Ip;
import in.clouthink.daas.es6.annotation.Keyword;
import in.clouthink.daas.es6.exception.Catching;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

/**
 * The Utilities for ES
 */
public class XContentBuilderBuilder {

    /**
     * @param entityClazz the class of entity
     * @return XContentBuilder
     */
    public static XContentBuilder buildFrom(Class entityClazz) {
        if (entityClazz == null) {
            throw new IllegalArgumentException("The class must not be null");
        }

        return Catching.around(() -> {
            XContentBuilder builder = XContentFactory.jsonBuilder();
            builder.startObject();
            {
                builder.field("dynamic", true);
                builder.startObject("properties");
                {
                    for (PropertyDescriptor item : BeanUtils.getPropertyDescriptors(entityClazz)) {
                        if ("class".equalsIgnoreCase(item.getName())) {
                            continue;
                        }

                        Field matchedField = ReflectionUtils.findField(entityClazz,
                                                                       (field) -> item.getName()
                                                                                      .equals(field.getName()));

                        if (matchedField != null) {
                            if (matchedField.isAnnotationPresent(Id.class)) {
                                continue;
                            }
                        }

                        builder.startObject(item.getName());
                        {

                            boolean processed = false;
                            if (matchedField != null) {
                                if (matchedField.isAnnotationPresent(Ip.class)) {
                                    builder.field("type", "ip");
                                    processed = true;
                                } else if (matchedField.isAnnotationPresent(Keyword.class)) {
                                    builder.field("type", "keyword");
                                    processed = true;
                                }
                            }

                            if (!processed) {
                                if (String.class.equals(item.getPropertyType())) {
                                    builder.field("type", "text");
                                } else {
                                    builder.field("type", item.getPropertyType().getSimpleName().toLowerCase());
                                }
                            }
                        }
                        builder.endObject();
                    }
                }
                builder.endObject();
            }
            builder.endObject();
            return builder;
        });
    }

}
