package in.clouthink.daas.es.repository.metadata;

import in.clouthink.daas.es.annotation.GenerationType;

public class IndexMetadata {

    private String indexName;

    private GenerationType idGenerationStrategy;

    public IndexMetadata(String indexName,
                         GenerationType idGenerationStrategy) {
        this.indexName = indexName;
        this.idGenerationStrategy = idGenerationStrategy;
    }

    public String getIndexName() {
        return indexName;
    }

    public GenerationType getIdGenerationStrategy() {
        return idGenerationStrategy;
    }

    public boolean isAutoGenerateId() {
        return idGenerationStrategy == GenerationType.AUTO;
    }

    public boolean isGenerateUuid() {
        return idGenerationStrategy == GenerationType.UUID;
    }

}
