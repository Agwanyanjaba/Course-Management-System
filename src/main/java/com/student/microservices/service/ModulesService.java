package com.student.microservices.service;

import com.student.microservices.model.Modules;
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
public class ModulesService {
    @Autowired
    private JdbcTemplate modulesJDBCTemplate;

    public Map<String, Object> postModule(Modules modules) {
        String sqlQuery = "INSERT INTO modules (moduleID,moduleName,courseID,lastUpdated) values(?,?,?,?) ";
        KeyHolder modulesHolder = new GeneratedKeyHolder();
        Map<String, Object> moduleResponse = null;

        try {
            modulesJDBCTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlQuery.toString(), Statement.RETURN_GENERATED_KEYS);

                    //Set values passed from the API
                    ps.setString(1, modules.getModuleID());
                    ps.setString(2, modules.getModuleName());
                    ps.setString(3,modules.getCourseID());
                    ps.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));

                    return ps;
                }
            }, modulesHolder);
            moduleResponse = modulesHolder.getKeys();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return moduleResponse;
    }

    //get all Modules
    //moduleID,moduleName, courseID, lastUpdated
    public List<Modules> getModules() {
        String sqlQuery = "SELECT * FROM  modules order by lastUpdated DESC";
        return modulesJDBCTemplate.query(sqlQuery, (rs, rowNum) -> {
            Modules modules = new Modules();
            modules.setModuleID(rs.getString("moduleID"));
            modules.setModuleName(rs.getString("moduleName"));
            modules.setCourseID(rs.getString("courseID"));
            modules.setLastUpdated(rs.getString("lastUpdated"));

            return modules;
        });
    }

    //Service to get transactions  based on customer ID
    //Pass customerID
    public List<Modules> getModuleDetails(String moduleID) {
        String moduleQuery = "SELECT * FROM modules WHERE moduleID = ? order by lastUpdated DESC";
        return modulesJDBCTemplate.query(moduleQuery, (rs, rowNum) -> {
                    Modules modules = new Modules();
                    modules.setModuleID(rs.getString("moduleID"));
                    modules.setModuleName(rs.getString("moduleName"));
                    modules.setCourseID(rs.getString("courseID"));
                    modules.setLastUpdated(rs.getString("lastUpdated"));

                    return modules; //return list of all modules
                }, moduleID
        );
    }
}
