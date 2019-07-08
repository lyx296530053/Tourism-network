package cn.lyx.web.servlet;

import cn.lyx.daomain.PageBean;
import cn.lyx.daomain.Route;
import cn.lyx.service.Imply.selectImply;
import cn.lyx.service.SelectService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/select/*")
public class SelectServlet extends BaseServlet {
    public void pagLimit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        int i = 0;
        if(cid != null && cid.length() > 0 && !"null".equals(cid)){
            i = Integer.parseInt(cid);
        }
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
        String rname = request.getParameter("rname");
        SelectService selectService = new selectImply();
        PageBean<Route> pageBean = selectService.limitPag(i, i1, i2,rname);
        ObjectMapper objectMapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),pageBean);
    }
}
