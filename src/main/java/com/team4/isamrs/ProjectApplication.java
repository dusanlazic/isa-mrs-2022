package com.team4.isamrs;

import com.team4.isamrs.service.AccountService;
import com.team4.isamrs.service.PhotoService;
import com.team4.isamrs.service.TestDataSupplierService;
import com.team4.isamrs.util.StorageConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PhotoService photoService, AccountService accountService,
						   TestDataSupplierService testDataSupplierService) {
		return (args) -> {
			photoService.init();
			accountService.initializeRoles();
			accountService.createTestAccount();
			testDataSupplierService.injectTestData();
		};
	}
}
