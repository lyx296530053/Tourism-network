package cn.lyx.service.Imply;

import cn.lyx.dao.DaoPagSelect;
import cn.lyx.dao.Imply.PagSeleteImply;
import cn.lyx.daomain.PageBean;
import cn.lyx.daomain.Route;
import cn.lyx.service.SelectService;

import java.util.List;

public class selectImply implements SelectService {
    private DaoPagSelect daoPagSelect = new PagSeleteImply();

    @Override
    public PageBean<Route> limitPag(int cid, int nowPag, int pagSize, String rname) {
        int all = daoPagSelect.findAll(cid,rname);
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(nowPag);
        pageBean.setPageSize(pagSize);
        List<Route> pagSize1 = daoPagSelect.getPagSize(cid, (nowPag - 1) * pagSize, pagSize,rname);
        pageBean.setList(pagSize1);
        pageBean.setTotalCount(all);
        int pagesize=all%pagSize==0?all/pagSize:all/pagSize+1;
        pageBean.setTotalPage(pagesize);
        return pageBean;
    }
}
