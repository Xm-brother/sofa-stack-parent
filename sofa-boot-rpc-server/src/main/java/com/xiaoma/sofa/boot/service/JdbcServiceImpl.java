package com.xiaoma.sofa.boot.service;

import com.xiaoma.sofa.boot.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class JdbcServiceImpl implements JdbcService {
    @Resource
    private JdbcTemplate jdbcTemplate;

    public List<User> getUserList(){
        String sql = "SELECT * FROM user";
        List<User> userList  = jdbcTemplate.query(sql, new RowMapper<User>() {
            User user = null;
            @Override
            public User mapRow(ResultSet rs, int i) throws SQLException {
                user = new User();
                user.setId(Integer.parseInt(rs.getString("id")));
                user.setName(rs.getString("name"));
                user.setAge(rs.getString("age"));
                user.setSex(rs.getString("sex"));
                return user;
            }
        });
        return userList;
    }
}
