package com.cst438.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;


import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;


@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
public class StudentController {

	@Autowired
	StudentRepository studentRepository;
	
	@PostMapping("/student/add")
	@Transactional
	public Student addStudent(@RequestBody Student student) {
		
		//  Get name and email from request body
		String name = student.getName();
		String email = student.getEmail();
		
		Student foundStudent = studentRepository.findByEmail(email);
		
		if(foundStudent == null) {
			student = new Student();
			student.setName(name);
			student.setEmail(email);
			student.setStatusCode(0);
			
			studentRepository.save(student);
			
			return student;
		}else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student Already Exists");
		}
		
	}
	
	@PostMapping("/student/place_hold")
	@Transactional
	public Student placeHold(@RequestBody Student student) {
		
		String hold = "HOLD";
				
		// Get email from request body
		String email = student.getEmail();
		
		// Search for student by email
		Student foundStudent = studentRepository.findByEmail(email);
		
		//  If student is found, place hold
		if(foundStudent != null) {
			
			foundStudent.setStatus(hold);
			foundStudent.setStatusCode(1);
			
			studentRepository.save(foundStudent);
			
			return foundStudent;
			
			
		}else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student Doesn't Exists");
		}
		
	}
		
	@PostMapping("/student/release_hold")
	@Transactional
	public Student releaseHold(@RequestBody Student student) {
				
		// Get email from request body
		String email = student.getEmail();
		
		// Search for student by email
		Student foundStudent = studentRepository.findByEmail(email);
		
		// If student found, release hold
		if(foundStudent != null) {
			
			foundStudent.setStatus(null);
			foundStudent.setStatusCode(0);
			
			studentRepository.save(foundStudent);
			
			return foundStudent;
			
			
		}else {
			throw  new ResponseStatusException( HttpStatus.BAD_REQUEST, "Student Doesn't Exists");
		}
		
	}
	
	
	
	
}
