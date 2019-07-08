package cn.lyx.service.Imply;

import cn.lyx.dao.DaoCollect;
import cn.lyx.service.CollectService;

public class CollectImply implements CollectService {
    private DaoCollect daoCollect=new cn.lyx.dao.Imply.CollectImply();
    @Override
    public boolean selectFavorite(int uid, int rid) {
        boolean b = daoCollect.selcctFavoriter(uid, rid);
        return b;
    }

    @Override
    public int selectCount(int rid) {
        int i = daoCollect.selectCount(rid);
        return i;
    }

    @Override
    public void addFavorite(int uid, int rid) {
        daoCollect.addFavoiter(uid,rid);
    }

    @Override
    public void removeFavorite(int uid, int rid) {
        daoCollect.removeFavorite(uid,rid);
    }
}
