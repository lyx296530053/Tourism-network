package cn.lyx.dao.Imply;

import cn.lyx.dao.DaoCollect;
import cn.lyx.utils.JdbcUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class CollectImply implements DaoCollect {
   private JdbcTemplate jdbcTemplate=new JdbcTemplate(JdbcUtils.getDataSource());
    @Override
    public boolean selcctFavoriter(int uid, int rid) {
       String string="select count(*) from tab_favorite where uid=? and rid=?";
        Integer integer = jdbcTemplate.queryForObject(string, Integer.class, uid, rid);
       if(integer!=0){
           return true;
       }else {
           return false;
       }
    }

    @Override
    public int selectCount(int rid) {
        String string="select count(*) from tab_favorite where rid=?";
        Integer integer = jdbcTemplate.queryForObject(string, Integer.class, rid);
        if(integer!=0){
           return integer;
        }else {
            return 0;
        }
    }

    @Override
    public void addFavoiter(int uid, int rid) {
        boolean b = selcctFavoriter(uid, rid);
        if(b){
            return;
        }else {
            String string = "insert into tab_favorite values(?,?)";
            jdbcTemplate.update(string,uid,rid);
        }
    }

    @Override
    public void removeFavorite(int uid, int rid) {
        String string="delete from tab_favorite where uid=? and rid=?";
        jdbcTemplate.update(string,uid,rid);
    }
}
