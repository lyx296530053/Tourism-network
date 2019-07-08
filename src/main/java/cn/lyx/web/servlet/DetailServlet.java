package cn.lyx.web.servlet;

import cn.lyx.daomain.Route;
import cn.lyx.service.DetailService;
import cn.lyx.service.Imply.DetailImply;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/detail/*")
public class DetailServlet extends BaseServlet {
   public void findDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        int i = Integer.parseInt(rid);
        DetailService detailService=new DetailImply();
        Route one = detailService.findOne(i);
        ObjectMapper objectMapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),one);
    }
}
