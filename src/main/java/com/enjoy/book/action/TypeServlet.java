package com.enjoy.book.action;

import com.enjoy.book.biz.TypeBiz;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.enjoy.book.bean.*;


@WebServlet("/type.let")
public class TypeServlet extends HttpServlet {
    TypeBiz typeBiz = new TypeBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        ServletContext application = req.getServletContext();
        HttpSession session = req.getSession();
        if(session.getAttribute("user")==null){
            out.println("<script>alert('Please login');parent.window.location.href='login.html';</script>");
            return;
        }

        String type = req.getParameter("type");
        switch (type){
            case "add":
                add(req,resp,out,application);
                break;
            case "modifypre":
                modifyPre(req,resp,out,application);
                break;
            case "modify":
                modify(req,resp,out,application);
                break;
            case "remove":
                remove(req,resp,out,application);
                break;

        }
    }

    private void modify(HttpServletRequest req, HttpServletResponse resp,PrintWriter out, ServletContext application) {
        long id = Long.parseLong(req.getParameter("typeId"));
        String name  = req.getParameter("typeName");
        long parentId = Long.parseLong(req.getParameter("parentType"));
        int count = typeBiz.modify(id,name,parentId);
        if(count>0){
            List<Type> types = typeBiz.getAll();
            application.setAttribute("types",types);
            out.println("<script>alert('Modify successfully');location.href = 'type_list.jsp';</script>");
        }else{
            out.println("<script>alert('Modify fail');location.href = 'type_list.jsp';</script>");
        }
    }
    private void modifyPre(HttpServletRequest req, HttpServletResponse resp,PrintWriter out, ServletContext application) throws ServletException, IOException {
      long id = Long.parseLong( req.getParameter("id"));
      Type type = typeBiz.getById(id);
        req.setAttribute("type",type);
        req.getRequestDispatcher("type_modify.jsp").forward(req,resp);
    }

    private void add(HttpServletRequest req, HttpServletResponse resp,PrintWriter out, ServletContext application) {
         String typeName = req.getParameter("typeName");
         long parentId = Long.parseLong(req.getParameter("parentType"));
        int count =  typeBiz.add(typeName,parentId);
        if(count>0){
            List<Type> types = typeBiz.getAll();
            application.setAttribute("types",types);
            out.println("<script>alert('Add successfully');location.href = 'type_list.jsp';</script>");
        }else{
            out.println("<script>alert('Add fail');location.href = 'type_add.jsp';</script>");
        }
    }

    private void remove(HttpServletRequest req, HttpServletResponse resp,PrintWriter out, ServletContext application) {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            int count = typeBiz.remove(id);
            if(count>0){
                List<Type> types = typeBiz.getAll();
                application.setAttribute("types",types);
                out.println("<script>alert('Delete successfully');location.href = 'type_list.jsp';</script>");
            }else{
                out.println("<script>alert('Delete fail');location.href = 'type_list.jsp';</script>");
            }
        } catch (Exception e) {
            out.println("<script>alert('"+e.getMessage()+"');location.href='type_list.jsp';</script>");
        }
    }

}
