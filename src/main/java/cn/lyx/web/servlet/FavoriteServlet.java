package cn.lyx.web.servlet;

import cn.lyx.daomain.PageBean;
import cn.lyx.daomain.Route;
import cn.lyx.daomain.User;
import cn.lyx.service.CollectService;
import cn.lyx.service.FavoriteService;
import cn.lyx.service.Imply.CollectImply;
import cn.lyx.service.Imply.FavoriteImply;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/favorite/*")
public class FavoriteServlet extends BaseServlet {
    public void favorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User userName = (User) session.getAttribute("userName");
        if (userName == null) {
            response.setContentType("html/text;charset=utf-8");
            response.getWriter().write("denglu");
            return;
        }
        String rid = request.getParameter("rid");
        int i = Integer.parseInt(rid);
        CollectService service = new CollectImply();
        boolean b = service.selectFavorite(userName.getUid(), i);
        response.setContentType("html/text;charset=utf-8");
        if (b) {
            response.getWriter().write("true");
        } else {
            response.getWriter().write("false");
        }
    }

    public void selectCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        int i = Integer.parseInt(rid);
        CollectService service = new CollectImply();
        int i1 = service.selectCount(i);
        response.setContentType("html/text;charset=utf-8");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(), i1);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        User userName = (User) session.getAttribute("userName");
        if (userName == null) {
            response.setContentType("html/text;charset=utf-8");
            response.getWriter().write("denglu");
            return;
        }
        String rid = request.getParameter("rid");
        int i = Integer.parseInt(rid);
        CollectService service = new CollectImply();
        service.addFavorite(userName.getUid(), i);
        response.getWriter().write("null");
    }

    public void remove(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        int i = Integer.parseInt(rid);
        HttpSession session = request.getSession();
        User userName = (User) session.getAttribute("userName");
        CollectService service = new CollectImply();
        service.removeFavorite(userName.getUid(), i);
        response.getWriter().write("null");
    }

    public void findFavoriteList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nowPag = request.getParameter("nowPag");
        int i1, i2;
        if (nowPag == null) {
            i1 = 1;
        } else {
            i1 = Integer.parseInt(nowPag);
        }
        String pagSize = request.getParameter("pagSize");
        if (pagSize == null) {
            i2 = 10;
        } else {
            i2 = Integer.parseInt(pagSize);
        }
        HttpSession session = request.getSession();
        User userName = (User) session.getAttribute("userName");
        if (userName == null) {
            response.setContentType("html/text;charset=utf-8");
            response.getWriter().write("denglu");
            return;
        }
        FavoriteService service = new FavoriteImply();
        PageBean<Route> favorite = service.findFavorite(userName.getUid(), i1, i2);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(), favorite);
    }

    public void findRank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nowPag = request.getParameter("nowPag");
        int i1, i2;
        if (nowPag == null) {
            i1 = 1;
        } else {
            i1 = Integer.parseInt(nowPag);
        }
        String pagSize = request.getParameter("pagSize");
        if (pagSize == null) {
            i2 = 10;
        } else {
            i2 = Integer.parseInt(pagSize);
        }
        FavoriteService service = new FavoriteImply();
        PageBean<Route> favorite = service.findRank(i1, i2);
        ObjectMapper objectMapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(), favorite);
    }
}
