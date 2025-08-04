package com.mweb.jobportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JobportalApplication {

	public static void main(String[] args) {

		System.setProperty("spring.servlet.multipart.max-file-size", "5MB");
		System.setProperty("spring.servlet.multipart.max-request-size", "10MB");


		System.setProperty("org.apache.tomcat.util.http.fileupload.FileUploadBase.fileCountMax", "20");

		SpringApplication.run(JobportalApplication.class, args);
	}
}

