package cn.lyx.dao;

import cn.lyx.daomain.Route;

import java.util.List;

public interface DaoPagSelect {
    int findAll(int cid,String rname);
    List<Route> getPagSize(int cid, int nowPag, int pigSize, String rname);
}
