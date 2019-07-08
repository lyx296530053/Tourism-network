package cn.lyx.service.Imply;

import cn.lyx.dao.DaoUesr;
import cn.lyx.dao.Imply.DaoImply;
import cn.lyx.daomain.User;
import cn.lyx.service.UserService;
import cn.lyx.utils.MailUtils;
import cn.lyx.utils.UuidUtil;



public class ServiceImply implements UserService {
   private DaoUesr daoUesr=new DaoImply();
    @Override
    public boolean register(User user) {
        User user1 = daoUesr.checkForm(user.getUsername());
        if(user1!=null){
            return false;
        }
        user.setCode(UuidUtil.getUuid());
        user.setStatus("N");
        System.out.println(user);
        daoUesr.save(user);
        String content="<a href='http://localhost/user/checkEmail?code="+user.getCode()+"'>点击激活</a>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");
        return true;
    }

    @Override
    public boolean checkEmail(String email) {
        User user = daoUesr.checkCode(email);
        if(user!=null){
          daoUesr.setStauds(user,email);
          return true;
        }
        return false;
    }

    @Override
    public User logIn(String username, String password) {
        User login = daoUesr.login(username, password);
        return login;
    }

    @Override
    public String findUser(User user) {
        User user1 = daoUesr.findUser(user);
        if(user1!=null){
            return user1.getName();
        }
        return null;
    }
}
