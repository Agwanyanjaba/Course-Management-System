package com.student.microservices.service;

import com.student.microservices.model.Authentication;
import com.student.microservices.viewModel.UserView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.util.*;

@Component
public class AuthenticationService {
    @Autowired
    private JdbcTemplate loginTemplate;

    //Admin auth service
    //String username;
    public List<Authentication> getLoginCredentials(String username,String password) {
        // select * from administrators where adminID='Admin1' and password='123456';
        String loginQuery = "SELECT * FROM administrators WHERE adminID = ? and password = ?";
        return loginTemplate.query(loginQuery, (rs, rowNum) -> {
                    Authentication authentication = new Authentication();
                    authentication.setPassword(rs.getString("password"));
                    authentication.setUsername(rs.getString("adminID"));

                    System.out.println(authentication);
                    return authentication;

                }, username,password
        );
    }

    //Admin auth service
    public List<Authentication> getStudentCredentials(String username,String password) {
        username="BSCS-ST002";
        password="1234";
        System.out.println("Username"+username);
        System.out.println("Pass"+password);

        String loginQuery = "SELECT * FROM students WHERE studentID = ? and password = ?";
        return loginTemplate.query(loginQuery, (rs, rowNum) -> {
                    Authentication authentication = new Authentication();
                    authentication.setPassword(rs.getString("password"));
                    authentication.setUsername(rs.getString("studentID"));

                    System.out.println("=========>"+ authentication);
                    return authentication;

                }, username,password

        );
    }

    public String createUserCredentials(UserView userView) {
        String sqlQuery = "insert into administrators (adminID,firstName,lastName,password) values(?,?,?,?)";
        KeyHolder userHolder = new GeneratedKeyHolder();
        String queryResponse = null;

        try {
            int insertion = loginTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlQuery.toString(), Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, userView.getAdminID());
                    ps.setString(2, userView.getFirstName());
                    ps.setString(3, userView.getLastName());
                    ps.setString(4, userView.getPassword());
                    return ps;

                }
            }, userHolder);
            if (insertion == 1) {
                queryResponse = "success";
                System.out.print("=======>" + queryResponse);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return queryResponse;
    }

}


