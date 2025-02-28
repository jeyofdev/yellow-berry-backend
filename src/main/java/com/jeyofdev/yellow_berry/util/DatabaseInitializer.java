package com.jeyofdev.yellow_berry.util;

import com.github.javafaker.Faker;
import com.jeyofdev.yellow_berry.domain.brand.*;
import com.jeyofdev.yellow_berry.domain.brand.dto.SaveBrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private final BrandService brandService;
    private final BrandController brandController;
    private final Faker faker = new Faker();

    /*public static void initializeDatabase(String jdbcUrl, String user, String password, String dbName) {
        String createDbQuery = MessageFormat.format("CREATE DATABASE {0}", dbName);
        try (Connection connection = DriverManager.getConnection(jdbcUrl, user, password);
             Statement statement = connection.createStatement()) {

            // If the database does not exist, create it
            statement.executeUpdate(createDbQuery);
            System.out.println("Database created successfully!");
        } catch (SQLException e) {
            if (e.getSQLState().equals("42P04")) { // Code error for "Database already exists"
                System.out.println("The database already exists.");
            } else {
                System.err.println(MessageFormat.format("Error creating database : {0}", e.getMessage()));
                throw new RuntimeException(e);
            }
        }
    }*/

    @Override
    public void run(String... args) throws Exception {
        this.createDatas();
    }

    private void createDatas() throws IOException {
        System.out.println("Database initialization started...");
        this.createBrand();
    }

    private void createBrand() {
        if (brandRepository.count() == 0) {
            IntStream.range(0, 10).forEach(i -> {
                SaveBrandDTO saveBrandDTO = new SaveBrandDTO(faker.company().name());
                brandController.saveBrand(saveBrandDTO);
            });
        }
    }
}
