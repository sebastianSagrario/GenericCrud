package com.crud.crudEjemplo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CrudEjemploApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudEjemploApplication.class, args);
                System.out.println(new BCryptPasswordEncoder().encode("123456"));
	}

        
}
