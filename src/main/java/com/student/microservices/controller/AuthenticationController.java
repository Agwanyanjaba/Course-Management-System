package com.student.microservices.controller;

import com.student.microservices.model.Authentication;
import com.student.microservices.viewModel.UserView;
import com.student.microservices.service.AuthenticationService;
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
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/v1/auth/")


public class AuthenticationController {

    @Bean
    public WebMvcConfigurer corsConfigurerAuth() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/modules").allowedOrigins("http://127.0.0.1:5500");
            }
        };
    }

    @Autowired
    AuthenticationService authenticationService;

    //API definition
    @RequestMapping(value = "signin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchLogins(@RequestParam String username,@RequestParam String password) {
        Long queryStartTime = System.currentTimeMillis();
        try {
            List<Authentication> listLogin;
            listLogin = authenticationService.getLoginCredentials(username, password);
            if(listLogin.isEmpty()){
                Date date = new Date();
                RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Processing Time");
                HashMap<String, Object> loginMap = new HashMap<>();
                loginMap.put("Metadata", restMetaData.toString());
                loginMap.put("Headers", "Login Data");
                loginMap.put("Data", "Failed");
                loginMap.put("StatusCode", "404");
                loginMap.put("Message", "Wrong Credentials. Check and Try again");

                System.out.println(listLogin);
                return new ResponseEntity<>(loginMap, HttpStatus.OK);
            }
            else {
                Date date = new Date();

                RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Processing Time");
                HashMap<String, Object> loginMap = new HashMap<>();
                loginMap.put("Metadata", restMetaData.toString());
                loginMap.put("Headers", "Login Data");
                loginMap.put("Data", "success");
                loginMap.put("Status Code", "200");

                System.out.println(listLogin);
                return new ResponseEntity<>(loginMap, HttpStatus.OK);
            }
        } catch (Exception e) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Some error occured");
            HashMap<String, Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data", e.getMessage());

            return new ResponseEntity<>(loginError, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of login data");
        }
    }

    //Student signin
    //API definition
    @RequestMapping(value = "studentsignin", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchStudentLogins(@RequestParam String username,@RequestParam String password) {
        Long queryStartTime = System.currentTimeMillis();
        try {
            List<Authentication> listLogin;
            listLogin = authenticationService.getStudentCredentials(username, password);
            if(listLogin.isEmpty()){
                Date date = new Date();
                RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Processing Time");
                HashMap<String, Object> loginMap = new HashMap<>();
                loginMap.put("Metadata", restMetaData.toString());
                loginMap.put("Headers", "Login Data");
                loginMap.put("Data", "Failed");
                loginMap.put("StatusCode", "404");
                loginMap.put("Message", "Wrong Credentials. Check and Try again");

                System.out.println(listLogin);
                return new ResponseEntity<>(loginMap, HttpStatus.OK);
            }
            else {
                Date date = new Date();

                RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Processing Time");
                HashMap<String, Object> loginMap = new HashMap<>();
                loginMap.put("Metadata", restMetaData.toString());
                loginMap.put("Headers", "Login Data");
                loginMap.put("Data", "success");
                loginMap.put("Status Code", "200");

                System.out.println(listLogin);
                return new ResponseEntity<>(loginMap, HttpStatus.OK);
            }
        } catch (Exception e) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Some error occured");
            HashMap<String, Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data", e.getMessage());

            return new ResponseEntity<>(loginError, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of login data");
        }
    }

    //Create user API definition
    @CrossOrigin
    @RequestMapping(value = "adminsignup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<HashMap<String, Object>> createUser(@RequestBody UserView userView) {
        System.out.println("Pushed to db==========>"+userView.getAdminID());
        Long queryStartTime = System.currentTimeMillis();
        try {

            String mapResponse;
            mapResponse = authenticationService.createUserCredentials(userView);

            if (mapResponse == null) {
                System.out.println(userView.getAdminID() + " not Created");
                Date date = new Date();
                RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Unexpected Error occured");
                HashMap<String, Object> loginMap = new HashMap<>();
                loginMap.put("Metadata", restMetaData.toString());
                loginMap.put("Data", mapResponse);
                return new ResponseEntity<>(loginMap, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Date date = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Processing Time");
            HashMap<String, Object> loginMap = new HashMap<>();
            loginMap.put("Metadata", restMetaData.toString());
            loginMap.put("Headers", "Login Data");
            loginMap.put("Data", mapResponse);

            return new ResponseEntity<>(loginMap, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Some error occured");
            HashMap<String, Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data", e.getMessage());

            return new ResponseEntity<>(loginError, HttpStatus.INTERNAL_SERVER_ERROR);

        }


    }
}
