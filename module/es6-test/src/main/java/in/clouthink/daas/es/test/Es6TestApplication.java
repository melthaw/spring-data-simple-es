package in.clouthink.daas.es.test;

import in.clouthink.daas.es.support.EsProperties;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(EsProperties.class)
public class Es6TestApplication {

    @Autowired
    private EsProperties esProperties;

    @Bean(destroyMethod = "close")
    @ConditionalOnMissingBean(RestHighLevelClient.class)
    public RestHighLevelClient client() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(esProperties.getHost(),
                                     esProperties.getPort(),
                                     esProperties.getProto())));
    }

    public static void main(String[] args) {
        SpringApplication.run(new Object[]{Es6TestApplication.class}, args);
    }

}
