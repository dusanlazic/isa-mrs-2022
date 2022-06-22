package com.team4.isamrs;

import com.team4.isamrs.service.*;
import com.team4.isamrs.util.StorageConfig;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(StorageConfig.class)
@EnableScheduling
@EnableAsync
@EnableCaching
public class ProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjectApplication.class, args);
	}

	@Bean
	CommandLineRunner init(PhotoService photoService, AccountService accountService,
						   TestDataSupplierService testDataSupplierService, LoyaltyProgramService loyaltyProgramService,
						   GlobalSettingsService globalSettingsService) {
		return args -> {
			photoService.init();
			accountService.initializeRoles();
			loyaltyProgramService.init();
			globalSettingsService.init();
			testDataSupplierService.injectTestData();
		};
	}
}
