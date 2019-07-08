package cn.lyx.service;

public interface CollectService {
    boolean selectFavorite(int uid,int rid);
    int selectCount(int rid);
    void addFavorite(int uid ,int rid);
    void removeFavorite(int uid,int rid);
}
