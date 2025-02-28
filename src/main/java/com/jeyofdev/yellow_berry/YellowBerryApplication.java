package com.jeyofdev.yellow_berry;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class YellowBerryApplication {

    public static void main(String[] args) {
        /*ApplicationContext context = SpringApplication.run(YellowBerryApplication.class, args);
        DatabaseConfig dbConfig = context.getBean(DatabaseConfig.class);

        // Check if database exist
        DatabaseInitializer.initializeDatabase(
                "jdbc:postgresql://localhost:5432/postgres",
                dbConfig.getDbUser(),
                dbConfig.getDbPassword(),
                dbConfig.getDbName()
        );*/

        SpringApplication.run(YellowBerryApplication.class, args);

    }

}
