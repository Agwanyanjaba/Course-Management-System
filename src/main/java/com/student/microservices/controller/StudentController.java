package com.student.microservices.controller;

import com.student.microservices.model.Students;
import com.student.microservices.service.StudentService;
import com.student.microservices.utils.RestMetaData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/v1/students")
public class StudentController {

    @Bean
    public WebMvcConfigurer corsConfigurerStudents() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/students").allowedOrigins("http://localhost:8080");
            }
        };
    }

    @Autowired
    StudentService studentService;
    private Date date = new Date();
    private Long queryStartTime = System.currentTimeMillis();
    private RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Students response");
    private HashMap<String, Object> response = new HashMap();
    private static final Logger LOGGER = LogManager.getLogger(StudentController.class);

    //API to get all students
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchStudents() {

        try {
            List<Students> listStudents = new ArrayList<>();
            listStudents = studentService.getAllStudentsList();
            response.put("MetaData", restMetaData.toString());
            response.put("Headers", "Students API. Get all Students Data");
            response.put("Data", listStudents);

            LOGGER.info(listStudents);
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("MetaData", restMetaData);
            response.put("Error", e.getMessage());
            response.put("Data", null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing
            System.out.println("Success fetching of student data");
        }
    }

    //Get Student details per user API based on Student ID
    //API definition
    @RequestMapping(value = "details", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchStudentDetails(@RequestParam String studentID) {
        try {
            //StringUtils.isBlank()
            List<Students> userAccount;
            userAccount = studentService.getStudentDetails(studentID);
            Date date = new Date();

            HashMap<String, Object> loginMap = new HashMap<>();
            loginMap.put("Metadata", restMetaData.toString());
            loginMap.put("Headers", "Student Data");
            loginMap.put("Data", userAccount);

            System.out.println(userAccount);
            return new ResponseEntity<>(loginMap, HttpStatus.OK);
        } catch (Exception customerException) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Some error occured");
            HashMap<String, Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data", customerException.getMessage());

            return new ResponseEntity<>(loginError, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of Customer Students Details");
        }
    }

}

