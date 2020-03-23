package com.shumile.spring_jdbc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Slf4j
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    /**
     * 添加用户
     */
    public void addUser() {
        User user = User.builder().
                id(1L).name("舒米君").age(18).build();
        jdbcTemplate.update
                ("insert user values(?,?,?)",
                        new Object[]{user.getId(), user.getName(), user.getAge()}
                );
        log.info("user :{} added",user);
    }
    /**
     * 获取总数
     *
     * @return
     */
    private Long getCount() {

        return jdbcTemplate
                .queryForObject("select count(1) from user", Long.class);
    }

    /**
     * 采用RowMapper的方式，获取结果对象。
     */
    public void listData() {
        log.info("Count:{}", getCount());
        //采用RowMapper的方式，获取结果对象。
        List<User> userList2 = jdbcTemplate.query("select * from user", new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException {
                return User.builder()
                        .id(resultSet.getLong(1)
                        ).name(resultSet.getString("name")
                        ).age(resultSet.getInt("age"))
                        .build();
            }
        });
        userList2.forEach(user -> log.info("User2:{}", user));
    }
}
