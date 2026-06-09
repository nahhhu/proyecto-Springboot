package com.techlab.spring;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class  TechLabSpringApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String dbPassword = dotenv.get("PASSWORD");
        String cloudinaryUrl = dotenv.get("CLOUDINARY_URL");
        if (dbPassword != null) {
            System.setProperty("PASSWORD", dbPassword);
        } else {
            System.out.println("La clave de .env no está.");
        }
        if(cloudinaryUrl != null){
            System.setProperty("CLOUDINARY_URL", cloudinaryUrl);
        }else{
            System.out.println("La URL de cloudinary no está.");
        }

        SpringApplication.run(TechLabSpringApplication.class, args);
    }

}
