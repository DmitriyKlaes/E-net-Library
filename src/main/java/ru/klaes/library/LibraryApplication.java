package ru.klaes.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class LibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryApplication.class, args);

//		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
//		UserRepository userRepository = context.getBean(UserRepository.class);
//		PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
//		saveUser(userRepository, "admin", passwordEncoder);
//		saveUser(userRepository, "reader", passwordEncoder);
	}

//	private static void saveUser(ReaderAuthDataRepository repository, String login, PasswordEncoder passwordEncoder) {
//		ReaderAuthData readerUser = new ReaderAuthData();
//		Role role = new Role();
//		Reader reader = new Reader();
//		reader.setName(login);
//		readerUser.setLogin(login);
//		readerUser.setPassword(passwordEncoder.encode(login));
//		readerUser.setReader(reader);
//		role.setName(login);
//		readerUser.addRole(role);
//		repository.save(readerUser);
//	}

}
