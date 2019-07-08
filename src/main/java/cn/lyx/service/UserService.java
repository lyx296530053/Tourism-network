package cn.lyx.service;

import cn.lyx.daomain.User;

public interface UserService {
    boolean register(User user);
    boolean checkEmail(String email);
    User logIn(String username, String password);
    String findUser(User user);
}
