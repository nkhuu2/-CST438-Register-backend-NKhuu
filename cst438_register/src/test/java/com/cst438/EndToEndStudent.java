package com.cst438;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;


import com.cst438.domain.StudentRepository;
import com.cst438.domain.Student;


@SpringBootTest
public class EndToEndStudent {
	
	public static final String URL = "http://localhost:3000";
	
	public static final String TEST_USER_NAME = "test_name";
	
	public static final String TEST_USER_EMAIL = "test2@csumb.edu";

	public static final int SLEEP_DURATION = 1000; // 1 second.
	
	@Autowired
	StudentRepository studentRepository; 
	
	
	@Test
	public void addStudentTest() throws Exception {
		
		Student x = null;
		do {
			x = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (x != null)
				studentRepository.delete(x);
		} while (x != null);
		
		
		SafariOptions safariOptions = new SafariOptions();

        WebDriver driver = new SafariDriver(safariOptions);
        
        try {
        	WebElement we;
        	
        	driver.get(URL);
        	Thread.sleep(SLEEP_DURATION);
        	
        	we = driver.findElement(By.id("studentMenu"));
        	we.click();
        	Thread.sleep(SLEEP_DURATION);
        	
        	we = driver.findElement(By.id("enrollStudent"));
        	we.click();
        	Thread.sleep(SLEEP_DURATION);
        	
        	we = driver.findElement(By.name("name"));
        	we.sendKeys(TEST_USER_NAME);
        	
        	
        	we = driver.findElement(By.name("email"));
        	we.sendKeys(TEST_USER_EMAIL);
        	
        	we = driver.findElement(By.id("addStudent"));
        	we.click();
        	Thread.sleep(SLEEP_DURATION);
        	
        	
        	Student student = studentRepository.findByEmail(TEST_USER_EMAIL);
			assertNotNull(student, "Student was not added");
        	
        } catch (Exception ex) {
        	ex.printStackTrace();
        	throw ex;
        }finally {
        	
        	driver.quit();
        }
		
	}

}
