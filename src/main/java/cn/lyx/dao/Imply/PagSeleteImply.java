package cn.lyx.dao.Imply;

import cn.lyx.dao.DaoPagSelect;
import cn.lyx.daomain.Route;
import cn.lyx.utils.JdbcUtils;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitorUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class PagSeleteImply implements DaoPagSelect {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public int findAll(int cid, String rname) {
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if(cid != 0){
            sb.append( " and cid = ? ");

            params.add(cid);
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sql = sb.toString();
        return jdbcTemplate.queryForObject(sql,Integer.class,params.toArray());
    }

    @Override
    public List<Route> getPagSize(int cid, int nowPag, int pigSize, String rname) {
        String sql = " select * from tab_route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);

        List params = new ArrayList();
        if(cid != 0){
            sb.append( " and cid = ? ");
            params.add(cid);
        }
        if(rname != null && rname.length() > 0){
            sb.append(" and rname like ? ");
            params.add("%"+rname+"%");
        }
        sb.append(" limit ? , ? ");
        sql = sb.toString();
        params.add(nowPag);
        params.add(8);
        return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Route>(Route.class),params.toArray());
    }
}
