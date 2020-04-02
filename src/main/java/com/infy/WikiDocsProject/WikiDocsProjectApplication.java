package com.infy.WikiDocsProject;

import com.infy.WikiDocsProject.Repository.RoleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@PropertySource(value={"classpath:messages.properties"})
public class WikiDocsProjectApplication {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(WikiDocsProjectApplication.class, args);
	}


	@Bean
	ApplicationRunner init() {
		return args -> {
		};
	}

	private void initRoles() {
		userRepository.deleteAll();
		roleRepository.deleteAll();

		Role userRole = Role.builder().id("USER").role("USER").build();
		Role adminRole = Role.builder().id("ADMIN").role("ADMIN").build();

		roleRepository.insert(userRole);
		roleRepository.insert(adminRole);
	}

}
