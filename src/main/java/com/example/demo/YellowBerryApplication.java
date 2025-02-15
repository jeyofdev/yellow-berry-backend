package com.example.demo;

import com.example.demo.auth_user.AuthUser;
import com.example.demo.config.DatabaseConfig;
import com.example.demo.util.DatabaseInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class YellowBerryApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(YellowBerryApplication.class, args);
		DatabaseConfig dbConfig = context.getBean(DatabaseConfig.class);

		// Check if database exist
		DatabaseInitializer.initializeDatabase(
				"jdbc:postgresql://localhost:5432/postgres",
				dbConfig.getDbUser(),
				dbConfig.getDbPassword(),
				dbConfig.getDbName()
		);
	}

}
