package com.student.microservices.controller;

import com.student.microservices.model.Modules;
import com.student.microservices.service.ModulesService;
import com.student.microservices.utils.RestMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://127.0.0.1:5500")
@RequestMapping("/v1/modules")

public class ModulesController {

    @Bean
    public WebMvcConfigurer corsConfigurerModules() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/v1/modules").allowedOrigins("http://www.wybosoftbank.com:8080");
            }
        };
    }

    private Date date = new Date();
    private Long queryStartTime = System.currentTimeMillis();
    private RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Students response");
    private HashMap<String, Object> response = new HashMap();

    @Autowired
    private ModulesService modulesService;


    //POST Module API
    @RequestMapping(value = "addmodule", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> commitTransaction(@RequestBody Modules modules) {
        Long queryStartTime = System.currentTimeMillis();

        try {
            Map<String, Object> mapResponse = new HashMap<>();
            mapResponse = modulesService.postModule(modules);

            Date date = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, date, "Students response");

            HashMap<String, Object> response = new HashMap();
            response.put("MetaData", restMetaData.toString());
            response.put("Headers", "Modules API. Modules Data");
            response.put("Data", mapResponse);
            response.put("StatusCOde", "200");

            //LOGGER.info(mapTransactionResponse);

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
            System.out.println("Success fetching of module data");
        }

    }

    //All transactions API
    @Autowired

    //actual API for getting all modules
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> fetchModules() {

        try {
            List<Modules> listTransactions = new ArrayList<>();
            listTransactions = modulesService.getModules();
            response.put("MetaData", restMetaData.toString());
            response.put("Headers", "Modules API. Get all Modules Data");
            response.put("Data", listTransactions);

            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("MetaData", restMetaData);
            response.put("Error", e.getMessage());
            response.put("Data", null);
            return new ResponseEntity<HashMap<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of module data");
        }
    }

    //get all modules based on student id
    //API definition
    @RequestMapping(value = "student", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HashMap<String, Object>> getCustTransaction(@RequestParam String modulesID) {
        try {
            //StringUtils.isBlank()
            List<Modules> modulesList = new ArrayList<>();
            modulesList = modulesService.getModuleDetails(modulesID);
            Date date = new Date();

            HashMap<String, Object> loginMap = new HashMap<>();
            loginMap.put("Metadata", restMetaData.toString());
            loginMap.put("Headers", "Login Data");
            loginMap.put("Data", modulesList);

            System.out.println(modulesList);
            return new ResponseEntity<HashMap<String, Object>>(loginMap, HttpStatus.OK);
        } catch (Exception customerException) {
            Date errorDate = new Date();
            RestMetaData restMetaData = new RestMetaData(System.currentTimeMillis() - queryStartTime, errorDate, "Some error occured");
            HashMap<String, Object> loginError = new HashMap<>();
            loginError.put("MetaData", restMetaData.toString());
            loginError.put("Data", customerException.getMessage());

            return new ResponseEntity<HashMap<String, Object>>(loginError, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            //timing goes here
            System.out.println("Success fetching of data");
        }
    }

}

