package com.elijah.permissionhelper;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

@SpringBootTest
class PermissionhelperApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(PermissionhelperApplicationTests.class);
    @Autowired
    EhrNewbornDao ehrNewbornDao;


    @Test
    void contextLoads() {
    }

    @Test
    public void testSelectOne() throws ExecutionException, InterruptedException {
        FutureTask futureTask1 = new FutureTask(() -> {
            PermissionHelper.doPermissionCheck("centerid","bb717701-10de-47ee-b49e-693261c6df22");
            logger.info("frist time invoke:{}",ehrNewbornDao.selectOne().toString());
            logger.info("second time invoke:{}",ehrNewbornDao.selectOne().toString());
            return null;
        });
        FutureTask<Map> futureTask2 = new FutureTask(() -> ehrNewbornDao.selectOne());
        new Thread(futureTask1).start();
        new Thread(futureTask2).start();
        Object res1 = futureTask1.get();
        Object res2 = futureTask2.get();
        logger.info("res1:{}\nres2:{}", res1, res2);
    }
}
