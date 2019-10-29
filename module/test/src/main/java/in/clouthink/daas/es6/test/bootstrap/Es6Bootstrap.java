package in.clouthink.daas.es6.test.bootstrap;

import in.clouthink.daas.es6.support.Es6Properties;
import in.clouthink.daas.es6.test.repository.HttpRequestEventRepository;
import in.clouthink.daas.es6.test.repository.impl.HttpRequestEventRepositoryImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Es6Bootstrap implements InitializingBean {

    @Autowired
    private Es6Properties es6Properties;

    @Autowired
    private HttpRequestEventRepositoryImpl httpRequestEventRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (es6Properties.isAutoCreateIndex()) {
            httpRequestEventRepository.createIndex();
            httpRequestEventRepository.createType();
        }
    }

}
