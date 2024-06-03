package com.health;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.json.GsonBuilderUtils;

import java.time.LocalDate;

@SpringBootApplication
public class HealthCareApplication {

	public static void main(String[] args) {SpringApplication.run(HealthCareApplication.class, args);}

}
