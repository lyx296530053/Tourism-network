package cn.lyx.service;

import cn.lyx.daomain.PageBean;
import cn.lyx.daomain.Route;


public interface SelectService {
    PageBean<Route> limitPag(int cid, int nowPag, int pagSize, String rname);
}
