package in.clouthink.daas.es.test.common;

import in.clouthink.daas.es.test.Es6TestApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Es6TestApplication.class)
@ActiveProfiles(profiles = "suites")
public abstract class AbstractTest {

    public void setUp() {

    }

}
