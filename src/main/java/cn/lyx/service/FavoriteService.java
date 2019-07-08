package cn.lyx.service;

import cn.lyx.daomain.PageBean;
import cn.lyx.daomain.Route;

import java.util.List;


public interface FavoriteService {
    PageBean<Route> findFavorite(int uid,int nowPag, int pagSize);
   PageBean<Route> findRank( int nowPag,int pagSize);
   List<Integer> findCount();
}
