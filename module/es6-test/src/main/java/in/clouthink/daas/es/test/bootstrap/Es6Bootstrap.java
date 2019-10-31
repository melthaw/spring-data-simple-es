package in.clouthink.daas.es.test.bootstrap;

import in.clouthink.daas.es.test.repository.impl.HttpRequestEventRepositoryImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Es6Bootstrap implements InitializingBean {

    @Autowired
    private HttpRequestEventRepositoryImpl httpRequestEventRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        httpRequestEventRepository.createIndex();
        httpRequestEventRepository.createType();
    }

}
