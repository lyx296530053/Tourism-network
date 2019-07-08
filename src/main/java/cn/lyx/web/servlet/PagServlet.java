package cn.lyx.web.servlet;

import cn.lyx.daomain.Category;
import cn.lyx.service.Imply.PagImply;
import cn.lyx.service.PagService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/pag/*")
public class PagServlet extends BaseServlet{
    public void pagCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectMapper objectMapper=new ObjectMapper();
        PagService pagService=new PagImply();
        List<Category> finall = pagService.finall();
        response.setContentType("application/json;charset=utf-8");
        objectMapper.writeValue(response.getOutputStream(),finall);
    }
}
