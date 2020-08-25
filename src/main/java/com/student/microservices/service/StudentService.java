package com.student.microservices.service;

import com.student.microservices.model.Students;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StudentService {

    //Declare account objects
    private Students studentAccount = new Students();

    @Autowired
    private JdbcTemplate myJdbcTemplate;
    @Autowired

    private static final Logger LOGGER = LogManager.getLogger(StudentService.class);

    //Service to get all students
    //studentID,firstName, lastName, password, department, address, email, courseID
    public List<Students> getAllStudentsList() {
        String sqlQuery = "SELECT * FROM students";
        return myJdbcTemplate.query(sqlQuery, (rs, rowNum) -> {
            Students studentList = new Students();
            studentList.setStudentID(rs.getString("studentID"));
            studentList.setFirstName(rs.getString("firstName"));
            studentList.setLastName(rs.getString("lastName"));
            studentList.setPassword(rs.getString("password"));
            studentList.setDepartment(rs.getString("department"));
            studentList.setAddress(rs.getString("address"));
            studentList.setEmail(rs.getString("email"));
            studentList.setCourseID(rs.getString("courseID"));

            return studentList;
        });
    }

    //Service to get Students based on Student ID
    //Pass customerID
    public List<Students> getStudentDetails(String studentID) {
        String accountQuery = "SELECT * FROM students WHERE studentID = ?";
        return myJdbcTemplate.query(accountQuery, (rs, rowNum) -> {
            //studentAccount
            studentAccount.setStudentID(rs.getString("studentID"));
            studentAccount.setFirstName(rs.getString("firstName"));
            studentAccount.setLastName(rs.getString("lastName"));
            studentAccount.setPassword(rs.getString("password"));
            studentAccount.setDepartment(rs.getString("department"));
            studentAccount.setAddress(rs.getString("address"));
            studentAccount.setEmail(rs.getString("email"));
            studentAccount.setCourseID(rs.getString("courseID"));

                    System.out.println(studentAccount);
                    LOGGER.info("Returned Auth info" + studentAccount);
                    LOGGER.debug("Returned Auth info" + studentAccount);
                    LOGGER.error("Returned Auth info" + studentAccount);
                    return studentAccount;
                }, studentID
        );
    }

//    //Service to update student after transaction is successful
//    //Pass customerID
//
//    public int updateCustomer(Students accountUpdate) {
//        System.out.println("Students Obj" + accountUpdate.toString());
//        String accountQuery = "UPDATE customers SET balance =? WHERE cid = ?";
//        int updatedCustomers = 0;
//
//        try {
//            updatedCustomers = myJdbcTemplate.update(accountQuery, accountUpdate.getBalance(), accountUpdate.getCID());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return updatedCustomers;
//    }
//
//    public int registerCustomer(Students accountUpdate) {
//        accountUpdate.setPID(bCryptHashing.enrcyptPassword(accountUpdate.getPID()));
//        System.out.println("Students Obj" + accountUpdate.toString());
//        String accountQuery = "UPDATE customers SET pid=?,FirstName=?,LastName=?,msisdn=?,imei=?,token =?,status =? WHERE cid = ?";
//        int updatedCustomers = 0;
//
//        try {
//            //updatedCustomers = myJdbcTemplate.update(accountQuery,accountUpdate.getBalance(),accountUpdate.getCID());
//            updatedCustomers = myJdbcTemplate.update(
//                    accountQuery, accountUpdate.getPID(),
//                    accountUpdate.getFirstName(),
//                    accountUpdate.getLastName(),
//                    accountUpdate.getMSISDN(),
//                    accountUpdate.getIMEI(),
//                    accountUpdate.getTOKEN(),
//                    1,
//                    accountUpdate.getCID()
//            );
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return updatedCustomers;
//    }

}
