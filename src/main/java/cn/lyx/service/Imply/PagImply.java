package cn.lyx.service.Imply;

import cn.lyx.dao.DaoPag;
import cn.lyx.daomain.Category;
import cn.lyx.service.PagService;
import cn.lyx.utils.JdbcUtils;
import cn.lyx.utils.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class PagImply implements PagService {
   private DaoPag daoPag=new cn.lyx.dao.Imply.PagImply();
    @Override
    public List<Category> finall() {
        Jedis jedis = JedisUtil.getJedis();
        Set<Tuple> pag = jedis.zrangeWithScores("pag", 0, -1);
        List<Category> all=null;
        if(pag==null||pag.size()==0) {
          all  = daoPag.findAll();
            for (int n = 0; n < all.size(); n++) {
                jedis.zadd("pag", all.get(n).getCid(), all.get(n).getCname());
            }
        }else{
          all=new ArrayList<Category>();
            for (Tuple tuple : pag) {
                Category category=new Category();
                category.setCid((int)tuple.getScore());
                category.setCname(tuple.getElement());
                all.add(category);
            }
        }
        return all;
    }
}
