package cn.lyx.dao.Imply;

import cn.lyx.dao.DaoPag;
import cn.lyx.daomain.Category;
import cn.lyx.daomain.PageBean;
import cn.lyx.utils.JdbcUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class PagImply implements DaoPag {
JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
    List<Category> query=null;
    @Override
    public List<Category> findAll() {
        try {
            String string = "select * from tab_category";
          query = jdbcTemplate.query(string, new BeanPropertyRowMapper<Category>(Category.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return query;
    }

}
