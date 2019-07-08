package cn.lyx.dao.Imply;

import cn.lyx.dao.DaoOne;
import cn.lyx.daomain.Route;
import cn.lyx.daomain.RouteImg;
import cn.lyx.daomain.Seller;
import cn.lyx.utils.JdbcUtils;
import com.sun.org.apache.bcel.internal.generic.Select;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class OneImply implements DaoOne {
    JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
    @Override
    public Route findOne(int rid) {
        String string="select * from tab_route where rid=?";
        Route route = jdbcTemplate.queryForObject(string, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }

    @Override
    public List<RouteImg> findImage(int rid) {
        String string="select * from tab_route_img where rid=?";
        List<RouteImg> query = jdbcTemplate.query(string, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return query;
    }

    @Override
    public Seller finSeller(int sid) {
        String string="select * from tab_seller where sid=?";
        Seller seller = jdbcTemplate.queryForObject(string, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
        return seller;
    }
}
