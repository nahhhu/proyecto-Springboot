package com.techlab.spring;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechLabSpringApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String dbPassword = dotenv.get("PASSWORD");
        if (dbPassword != null){
            System.setProperty("PASSWORD",dbPassword);
        }else {
            System.out.println("La clave de .env no esta.");
        }
        SpringApplication.run(TechLabSpringApplication.class, args);
    }

}
