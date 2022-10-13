package com.house.xxl;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@MapperScan("com.house.xxl.dao")
public class ServenHouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServenHouseApplication.class, args);
    }

}
