package cn.lyx.web.filter;

import cn.lyx.dao.DaoUesr;
import cn.lyx.dao.Imply.DaoImply;
import cn.lyx.daomain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/login.html")
public class LogInFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        Cookie[] cookies = request.getCookies();
        String username1 = null;
        String password1 = null;
        String uri = request.getRequestURI();
        if (request.getSession().getAttribute("userName") != null) {
            request.getRequestDispatcher(uri + "/index.html").forward(request, response);
        }
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("username")) {
                    username1 = cookie.getValue();
                }
                if (cookie.getName().equals("password")) {
                    password1 = cookie.getValue();
                }
            }
            System.out.println(username1);
            System.out.println(password1);
            DaoUesr daoUesr = new DaoImply();
            User login = daoUesr.login(username1, password1);
            if (login != null) {
                HttpSession session = request.getSession();
                session.setAttribute("userName", login);
                request.getRequestDispatcher("/index.html").forward(request, response);
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }
}
