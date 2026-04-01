package kr.co.ictedu.geoulA_admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class GeoulAAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(GeoulAAdminApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer crossConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				System.out.println("Test==================");
				registry.addMapping("/**")
				.allowedOrigins("http://192.168.0.54:3001/" , "http://localhost:3001/")
				.allowedHeaders("*")
				.allowedMethods("*").maxAge(3600);
			}
		};
	}

}
