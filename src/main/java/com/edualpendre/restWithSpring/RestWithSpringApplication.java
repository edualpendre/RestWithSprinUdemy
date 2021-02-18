package com.edualpendre.restWithSpring;

import com.edualpendre.restWithSpring.config.FileStorageConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties({FileStorageConfig.class})
@EnableAutoConfiguration
@ComponentScan
public class RestWithSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestWithSpringApplication.class, args);

//		Se for criar usuario novo, criar um metodo com o código abaixo
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		String result = bCryptPasswordEncoder.encode("admin123");
//		System.out.println("My hash " + result);
	}

}
