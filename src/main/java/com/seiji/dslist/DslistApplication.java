package com.seiji.dslist;

import com.seiji.dslist.user.application.dto.RegisterDTO;
import com.seiji.dslist.user.application.services.AuthService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.seiji.dslist.user.enums.Role.ADMIN;

@SpringBootApplication
public class DslistApplication {

	public static void main(String[] args) {
		SpringApplication.run(DslistApplication.class, args);
	}
}
