package cn.lyx.service.Imply;

import cn.lyx.dao.DaoCollect;
import cn.lyx.dao.DaoFavorite;
import cn.lyx.dao.Imply.CollectImply;
import cn.lyx.daomain.PageBean;
import cn.lyx.daomain.Route;
import cn.lyx.service.FavoriteService;

import java.util.ArrayList;
import java.util.List;

public class FavoriteImply implements FavoriteService {
    private DaoFavorite daoFavorite = new cn.lyx.dao.Imply.FavoriteImply();
    private DaoCollect daoCollect = new CollectImply();

    @Override
    public PageBean<Route> findFavorite(int uid, int nowPag, int pagSize) {
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(nowPag);
        pageBean.setPageSize(pagSize);
        List<Integer> all = daoFavorite.findFavorite(uid);
        Integer[] integers = all.toArray(new Integer[all.size()]);
        List<Route> pagSize1 = daoFavorite.findList(integers, (nowPag - 1) * pagSize, pagSize);
        pageBean.setList(pagSize1);
        pageBean.setTotalCount(all.size());
        int pagesize = all.size() % pagSize == 0 ? all.size() / pagSize : all.size() / pagSize + 1;
        pageBean.setTotalPage(pagesize);
        return pageBean;
    }

    @Override
    public PageBean<Route> findRank(int nowPag, int pagSize) {
        List<Integer> rank = daoFavorite.findRank();
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(nowPag);
        pageBean.setPageSize(pagSize);
        Integer[] integers = rank.toArray(new Integer[rank.size()]);
        List<Route> list = daoFavorite.findList(integers, (nowPag - 1) * pagSize, pagSize);
        pageBean.setList(list);
        pageBean.setTotalCount(rank.size());
        int pagesize = rank.size() % pagSize == 0 ? rank.size() / pagSize : rank.size() / pagSize + 1;
        pageBean.setTotalPage(pagesize);
        List<Integer> count = this.findCount();
        pageBean.setCount(count);
        return pageBean;
    }

    @Override
    public List<Integer> findCount() {
        List<Integer> rank = daoFavorite.findRank();
        List list=new ArrayList();
        for (Integer integer : rank) {
            int i = daoCollect.selectCount(integer);
            list.add(i);
        }
        return list;
    }
}
