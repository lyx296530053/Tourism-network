package cn.lyx.web.servlet;

import cn.lyx.dao.DaoUesr;
import cn.lyx.dao.Imply.DaoImply;
import cn.lyx.daomain.ResultInfo;
import cn.lyx.daomain.User;
import cn.lyx.service.Imply.ServiceImply;
import cn.lyx.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService userService = new ServiceImply();

    public void changeHeader(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userName");
        if (user == null) {
            session.removeAttribute("userName");
            String user1 = "游客登陆";
            response.setContentType("html:text;charset=utf-8");
            response.getWriter().write(user1);
            return;
        }
        String user1 = userService.findUser(user);
        response.setContentType("html:text;charset=utf-8");
        response.getWriter().write(user1);
    }

    public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("pragma", "no-cache");
        response.setHeader("cache-control", "no-cache");
        response.setHeader("expires", "0");
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, width, height);
        String checkCode = getCheckCode();
        request.getSession().setAttribute("yan", checkCode);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("黑体", Font.BOLD, 24));
        g.drawString(checkCode, 15, 25);
        ImageIO.write(image, "PNG", response.getOutputStream());
    }

    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 4; i++) {
            int index = r.nextInt(size);
            char c = base.charAt(index);
            sb.append(c);
        }
        return sb.toString();
    }

    public void checkEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String code = request.getParameter("code");
        boolean b = userService.checkEmail(code);
        String msg = null;
        if (b == false) {
            msg = "激活失败请联系管理员";
        } else {
            msg = "<a href='http://localhost/tour/login.html'>激活成功请登录</a>";

        }
        response.setContentType("html:text;charset=UTF-8");
        response.getWriter().write(msg);
    }

    public void checkForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("yan");
        session.removeAttribute("yan");
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
        Map<String, String[]> map = request.getParameterMap();

        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean flag = userService.register(user);
        ResultInfo info = new ResultInfo();
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败!");
        }

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);

    }

    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("userName");
    }

    public void logIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        String checkBox = request.getParameter("checkBox");
        HttpSession session = request.getSession();
        String yan = (String) session.getAttribute("yan");
        session.removeAttribute("yan");
        if (yan == null || !yan.equalsIgnoreCase(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userService.logIn(username, password);
        ResultInfo info = new ResultInfo();
        if (user == null) {
            info.setFlag(false);
            info.setErrorMsg("账户不存在或密码错误!");
        } else if (user.getStatus().equals("N")) {
            info.setFlag(false);
            info.setErrorMsg("jihuo");
        } else {
            info.setFlag(true);
            HttpSession session1 = request.getSession();
            session1.setAttribute("userName", user);
            if ("on".equals(checkBox)) {
                Cookie cookie = new Cookie("username", username);
                Cookie cookie1 = new Cookie("password", password);
                cookie.setMaxAge(60 * 60 * 24 );
                cookie1.setMaxAge(60 * 60 * 24 );
                cookie.setPath("/");
                cookie1.setPath("/");
                response.addCookie(cookie);
                response.addCookie(cookie1);
            }
        }
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);
        response.setContentType("application/+json;charset=utf-8");
        response.getWriter().write(json);
    }
}
