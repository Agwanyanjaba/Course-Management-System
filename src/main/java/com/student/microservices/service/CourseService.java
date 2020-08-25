package com.student.microservices.service;

import com.student.microservices.model.Courses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.Map;

@Component
public class CourseService {
    @Autowired
    private JdbcTemplate transactionJDBCTemplate;
    private static final Logger LOGGER = LogManager.getLogger(CourseService.class); //log service data
    //courseID,title, department, level,lastUpdated
    public Map<String, Object> transferFunds(Courses courses) {
        String sqlQuery = "INSERT INTO courses (courseID,title,department,level,lastUpdated) values(?,?,?,?,?) ";
        KeyHolder transactionHolder = new GeneratedKeyHolder();
        Map<String, Object> transactionResponse = null;

        try {
            transactionJDBCTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlQuery.toString(), Statement.RETURN_GENERATED_KEYS);

                    //Set values passed from the API
                    ps.setString(1, courses.getCourseID());
                    ps.setString(2, courses.getTitle());
                    ps.setString(3, courses.getDepartment());
                    ps.setString(4, courses.getLevel());
                    ps.setTimestamp(5, new Timestamp(new java.util.Date().getTime()));


                    return ps;
                }
            }, transactionHolder);
            transactionResponse = transactionHolder.getKeys();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        LOGGER.info(transactionResponse);
        return transactionResponse;
    }

    //get all Courses
    public List<Courses> getCourses() {
        String sqlQuery = "SELECT * FROM  courses order by lastUpdated DESC";
        return transactionJDBCTemplate.query(sqlQuery, (rs, rowNum) -> {
            Courses courses = new Courses();
            courses.setCourseID(rs.getString("courseID"));
            courses.setTitle(rs.getString("title"));
            courses.setDepartment(rs.getString("department"));
            courses.setLevel(rs.getString("level"));
            courses.setLastUpdated(rs.getString("lastUpdated"));

            return courses;
        });
    }

    //Service to get course  based on course ID

    public List<Courses> getCourseDetails(String courseID) {
        String accountQuery = "SELECT * FROM courses WHERE courseID = ? order by lastUpdated DESC";
        return transactionJDBCTemplate.query(accountQuery, (rs, rowNum) -> {
                    Courses userCourses = new Courses();
                    userCourses.setCourseID(rs.getString("courseID"));
                    userCourses.setTitle(rs.getString("title"));
                    userCourses.setDepartment(rs.getString("department"));
                    userCourses.setLevel(rs.getString("level"));
                    userCourses.setLastUpdated(rs.getString("lastUpdated"));

                    return userCourses; //return list of all transaction for the user
                }, courseID
        );
    }
}
