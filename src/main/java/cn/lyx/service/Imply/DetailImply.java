package cn.lyx.service.Imply;

import cn.lyx.dao.DaoOne;
import cn.lyx.dao.Imply.OneImply;
import cn.lyx.daomain.Route;
import cn.lyx.daomain.RouteImg;
import cn.lyx.daomain.Seller;
import cn.lyx.service.DetailService;

import java.util.List;

public class DetailImply implements DetailService {
   private DaoOne daoOne=new OneImply();
    @Override
    public Route findOne(int rid) {
        Route one = daoOne.findOne(rid);
        List<RouteImg> image = daoOne.findImage(one.getRid());
        Seller seller = daoOne.finSeller(one.getSid());
        one.setRouteImgList(image);
        one.setSeller(seller);
        return one;
    }
}
