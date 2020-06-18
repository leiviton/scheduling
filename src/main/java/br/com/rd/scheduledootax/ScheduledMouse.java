package br.com.rd.scheduledootax;

import java.awt.AWTException;
import java.awt.Robot;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMouse {

	private static final Logger log = LoggerFactory.getLogger(ScheduledMouse.class);

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 120000)
	public void reportCurrentTime() throws AWTException {
		System.setProperty("java.awt.headless", "false");
		log.info("Movendo mouse {}", dateFormat.format(new Date()));
		Robot robot = new Robot();
		robot.mouseMove(300, 300);
	}
}
