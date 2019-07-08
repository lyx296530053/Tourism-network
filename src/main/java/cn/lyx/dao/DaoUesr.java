package cn.lyx.dao;

import cn.lyx.daomain.User;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;

public interface DaoUesr {
    User checkForm(String username);
    void save(User user);
    User checkCode(String str);
   void setStauds(User user,String str);
   User login(String username,String password);
   User findUser(User user);
}
