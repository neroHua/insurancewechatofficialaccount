package com.yuexing.insurancewechatofficalaccount.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class InsurancewechatofficalaccountwechatApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsurancewechatofficalaccountwechatApplication.class, args);
    }

}
