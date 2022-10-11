package com.forex.conversion;

import com.forex.conversion.entity.Currency;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@ComponentScan(basePackages = {"com.forex.conversion"}, excludeFilters = {})
@EnableJpaRepositories(basePackages = {"com.forex.conversion.repository", "com.forex.conversion.dao"})
@EnableCaching
public class ForexApplication {

    public static void main(String[] args) {
        SpringApplication.run(ForexApplication.class, args);
        System.out.println("Started...");
    }

}
