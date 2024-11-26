package com.management.project.appconfigs;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class LiquibaseConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        try {
            liquibase.setChangeLog("classpath:changelog/db.changelog-master.xml");
            liquibase.setDataSource(dataSource);
            liquibase.setShouldRun(true);
        }catch (Exception e){
            liquibase.setShouldRun(false);
            e.getStackTrace();
        }

        return liquibase;
    }
}
