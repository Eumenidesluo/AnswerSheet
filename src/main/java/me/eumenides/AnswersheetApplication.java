package me.eumenides;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@MapperScan("me.eumenides.dao")
@SpringBootApplication
@EnableRedisHttpSession
public class AnswersheetApplication {
	public static void main(String[] args) {
		SpringApplication.run(AnswersheetApplication.class, args);

	}
}
