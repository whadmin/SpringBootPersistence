package com.springboot.dao;

import java.sql.Connection;  
import java.sql.PreparedStatement;  
import java.sql.ResultSet;  
import java.sql.SQLException;  
import java.sql.Statement;  
import java.util.List;  
  


import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;  
import org.springframework.jdbc.core.PreparedStatementCreator;  
import org.springframework.jdbc.core.RowMapper;  
import org.springframework.jdbc.support.GeneratedKeyHolder;  
import org.springframework.jdbc.support.KeyHolder;  
import org.springframework.stereotype.Repository;  
import org.springframework.transaction.annotation.Transactional;

import com.springboot.entiy.User;


  
@Repository  
public class UserRepository {  
    @Autowired
    @Qualifier("druidJdbcTemplate")
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    @Qualifier("primaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate1;  
    
    @Autowired
    @Qualifier("secondaryJdbcTemplate")
    private JdbcTemplate jdbcTemplate2;  
  
    @Transactional(value = "druidTransactionManager")  
    public List<User> findAll() {  
        return jdbcTemplate.query("select * from users", new UserRowMapper());  
    }  
  
    @Transactional(value = "druidTransactionManager")
    public User findUserById(int id) {  
        return jdbcTemplate.queryForObject("select * from users where id=?", new Object[] { id }, new UserRowMapper());  
    }  
  
    public User create(final User user) {  
        final String sql = "insert into users(username,password) values(?,?)";  
  
        KeyHolder holder = new GeneratedKeyHolder();  
  
        jdbcTemplate.update(new PreparedStatementCreator() {  
  
            @Override  
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {  
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
                ps.setString(1, user.getUserName());  
                ps.setString(2, user.getPassword());  
                return ps;  
            }  
        }, holder);  
  
        int newUserId = holder.getKey().intValue();  
        user.setId(newUserId);  
        return user;  
    }  
  
    public void delete(final Integer id) {  
        final String sql = "delete from users where id=?";  
        jdbcTemplate.update(sql, new Object[] { id }, new int[] { java.sql.Types.INTEGER });  
    }  
  
    public void update(final User user) {  
        jdbcTemplate.update("update users set username=?,password=? where id=?",  
                new Object[] { user.getUserName(), user.getPassword(), user.getId() });  
    }
 
}  
  
class UserRowMapper implements RowMapper<User> {  
  
    @Override  
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {  
        User user = new User();  
        user.setId(rs.getInt("id"));  
        user.setUserName(rs.getString("username"));  
        user.setPassword(rs.getString("password"));  
        return user;  
    }  
}  
