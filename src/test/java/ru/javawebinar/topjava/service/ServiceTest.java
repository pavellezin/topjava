package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
abstract public class ServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(MealServiceTest.class);
    private String testStatus;
    private static StringBuilder result = new StringBuilder();

    @Rule
    public final TestRule watchman = new TestWatcher() {
        long start;

        @Override
        public Statement apply(Statement base, Description description) {
            return super.apply(base, description);
        }

        @Override
        protected void succeeded(Description description) {
            testStatus = "SUCCESSFUL";
        }

        @Override
        protected void failed(Throwable e, Description description) {
            testStatus = "FAILED";
        }

        @Override
        protected void skipped(AssumptionViolatedException e, Description description) {
            testStatus = "DISABLED";
        }

        @Override
        protected void starting(Description description) {
            start = System.currentTimeMillis();
        }

        @Override
        protected void finished(Description description) {
            String res = String.format("\n%-15s", testStatus)
                    + String.format(" %-25s %7d", description.getMethodName(), (System.currentTimeMillis() - start));
            result.append(res);
        }
    };

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @AfterClass
    public static void getTestStat() {
        LOG.info("\n-------------------------------------------------" +
                "\nStatus          Test                     Time, ms" +
                "\n-------------------------------------------------" +
                result +
                "\n-------------------------------------------------\n");
        ;
    }

}
