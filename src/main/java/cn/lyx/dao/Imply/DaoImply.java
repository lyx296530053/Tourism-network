package cn.lyx.dao.Imply;

import cn.lyx.dao.DaoUesr;
import cn.lyx.daomain.User;
import cn.lyx.utils.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class DaoImply implements DaoUesr {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public User checkForm(String username) {
        User user = null;

        try {
            String string = "select * from tab_user where  username=?";
            user = jdbcTemplate.queryForObject(string, new BeanPropertyRowMapper<User>(User.class),username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void save(User user) {
        String sql = "insert into tab_user(username,password,name,telephone,email,status,code,birthday,sex) values(?,?,?,?,?,?,?,?,?)";
        //2.执行sql

        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode(),
                user.getBirthday(),
                user.getSex()
        );
    }

    @Override
    public User checkCode(String str) {
        User user = null;
        try {
            String string = "SELECT * FROM  tab_user WHERE code=?";
            user = jdbcTemplate.queryForObject(string, new BeanPropertyRowMapper<User>(User.class), str);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public void setStauds(User user,String str) {
        String string = "update tab_user set status=? where code=?";
        jdbcTemplate.update(string,"Y",str);
    }

    @Override
    public User login(String username, String password) {
        User user = null;
        try {
            String string="select * from tab_user where username=?&&password=?";
            user = jdbcTemplate.queryForObject(string, new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUser(User user) {
        User user1 = null;
        try {
            String string = "select * from tab_user where username=?";
            user1 = jdbcTemplate.queryForObject(string, new BeanPropertyRowMapper<User>(User.class), user.getUsername());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user1;
    }
}
