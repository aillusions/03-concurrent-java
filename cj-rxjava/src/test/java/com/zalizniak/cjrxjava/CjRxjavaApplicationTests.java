package com.zalizniak.cjrxjava;

import io.reactivex.Flowable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CjRxjavaApplicationTests {

    @Test
    public void contextLoads() {

        Flowable.just("Hello world").subscribe(System.out::println);
    }

}
