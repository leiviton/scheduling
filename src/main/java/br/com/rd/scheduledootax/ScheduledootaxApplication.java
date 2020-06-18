package br.com.rd.scheduledootax;

import java.awt.AWTException;
import java.sql.SQLException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ScheduledootaxApplication {

	public static void main(String[] args) throws AWTException, SQLException {
		SpringApplication.run(ScheduledootaxApplication.class, args);
	}

}
