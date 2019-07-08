package cn.lyx.dao.Imply;

import cn.lyx.dao.DaoFavorite;
import cn.lyx.daomain.Route;
import cn.lyx.utils.JdbcUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;


public class FavoriteImply implements DaoFavorite {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JdbcUtils.getDataSource());

    @Override
    public List<Integer> findFavorite(int uid) {
        String string = "select rid from tab_favorite where uid=?";
        List<Integer> query = jdbcTemplate.queryForList(string, Integer.class, uid);
        return query;
    }

    @Override
    public List<Route> findList(Integer[] arr, int i, int pagSize) {
        List list=new ArrayList();
        String string = "select * from tab_route where 1=1 and rid in ";
        StringBuilder stringBuilder = new StringBuilder(string);
        stringBuilder.append(" ( ");
        for (int j = 0; j < arr.length; j++) {
            int k=arr[j];
            list.add(k);
            if(j<arr.length-1) {
                stringBuilder.append(" ? , ");
            }else {
                stringBuilder.append(" ? ) ");
            }
        }
        stringBuilder.append(" limit ? , ? ");
        string = stringBuilder.toString();
        list.add(i);
        list.add(pagSize);
        List<Route> query = jdbcTemplate.query(string, new BeanPropertyRowMapper<Route>(Route.class), list.toArray());
        return query;
    }

    @Override
    public List<Integer> findRank() {
        String s="SELECT DISTINCT rid FROM tab_favorite ";
        List<Integer> integers = jdbcTemplate.queryForList(s, Integer.class);
        return integers;
    }

}
