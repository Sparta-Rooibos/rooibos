package com.sparta.rooibos.slcak;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SlcakApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SlcakApplication.class, args);
	}

}
