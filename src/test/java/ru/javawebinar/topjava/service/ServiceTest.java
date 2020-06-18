package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

import java.util.concurrent.TimeUnit;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
abstract public class ServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceTest.class);
    private static final StringBuilder result = new StringBuilder();

    @Rule
    public Stopwatch stopwatch = new Stopwatch() {

        @Override
        protected void finished(long nanos, Description description) {
            String res = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            result.append(res);
        }
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @AfterClass
    public static void getTestStat() {
        LOG.info("\n---------------------------------" +
                "\nTest                     Time, ms" +
                "\n---------------------------------" +
                result +
                "\n---------------------------------\n");
//        result.delete(0, result.length());
        result.setLength(0);
    }

}