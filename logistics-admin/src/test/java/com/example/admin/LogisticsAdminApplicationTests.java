package com.example.admin;

import com.example.system.exception.OperationException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@SpringBootTest
class LogisticsAdminApplicationTests {

    private final String[] map = new String[10];

    @Test
    void contextLoads(){

        SimpleDateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = null;//今天
        try {
            date = new Date(dayFormat.parse(dayFormat.format(new Date())).getTime());
        } catch (ParseException e) {
            throw new OperationException(e.getMessage());
        }
        System.out.println(date.getTime());

    }

}

