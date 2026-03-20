package com.huawei.apartment;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 公寓管理系统启动类
 *
 * @author Huawei
 * @since 2026-03-18
 */
@SpringBootApplication
@MapperScan("com.huawei.apartment.mapper")
public class ApartmentManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApartmentManagementApplication.class, args);
        System.out.println("========================================");
        System.out.println("公寓管理系统启动成功!");
        System.out.println("========================================");
    }
}
