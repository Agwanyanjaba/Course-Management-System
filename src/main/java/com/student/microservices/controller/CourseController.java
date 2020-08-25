package com.student.microservices.controller;

import com.student.microservices.model.Courses;
import com.student.microservices.service.CourseService;
import com.student.microservices.utils.RestMetaData;
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
@CrossOrigin(origins = "http://localhost:9000")
@RequestMapping("/v1/courses")

public class CourseController {

    //Enable cors for request from the backend UI:
    @Bean
    public WebMvcConfigurer corsConfigurerTransactions() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/transactions").allowedOrigins("http://www.wybosoftbank.com:8080");

            }
        };
    }

    private Date date = new Date();
    private Long queryStartTime = System.currentTimeMillis();
    private RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Students response");
    private HashMap<String, Object> response = new HashMap();

    @Autowired
    private CourseService courseService;
 ;

    //POST course API
    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> commitCourse(@RequestBody Courses courses) {
        Long queryStartTime = System.currentTimeMillis();

        try {
            Map<String, Object> mapResponse = new HashMap<>();
            mapResponse = courseService.transferFunds(courses);

            Date date = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Students response");

            HashMap<String, Object> response = new HashMap();
            response.put("MetaData", restMetaData.toString());
            response.put("Headers", "Customers API. Get all customers Data");
            response.put("Data", mapResponse);

            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Unexpected Error Occurred");
            System.out.println(e.getMessage());
            HashMap<String, Object> response = new HashMap();
            response.put("MetaData", restMetaData);
            response.put("Error", e.getMessage());
            response.put("Data", null);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of data");
        }

    }

    //All transactions API
    @Autowired

    //Actual API for getting all courses
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchAllCourses() {

        try {
            List<Courses> listCourses = new ArrayList<>();
            listCourses = courseService.getCourses();
            response.put("MetaData", restMetaData.toString());
            response.put("Headers", "Courses API. Get all courses Data");
            response.put("Data", listCourses);

            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("MetaData", restMetaData);
            response.put("Error", e.getMessage());
            response.put("Data", null);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of data");
        }
    }

    //get all courses based on course ID
    //API definition
    @RequestMapping(value = "course", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> getCourses(@RequestParam String courseID) {
        try {

            List<Courses> usertransactions = new ArrayList<>();
            usertransactions = courseService.getCourseDetails(courseID);
            Date date = new Date();

            HashMap<String, Object> courseMap = new HashMap<>();
            courseMap.put("Metadata", restMetaData.toString());
            courseMap.put("Headers", "Course Data");
            courseMap.put("Data", usertransactions);

            System.out.println(usertransactions);
            return new ResponseEntity<HashMap<String, Object>>(courseMap, HttpStatus.OK);
        } catch (Exception customerException) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Some error occured");
            HashMap<String, Object> courseError = new HashMap<>();
            courseError.put("MetaData", restMetaData.toString());
            courseError.put("Data", customerException.getMessage());

            return new ResponseEntity<HashMap<String, Object>>(courseError, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of data");
        }
    }

}

