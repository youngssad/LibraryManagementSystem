package com.enjoy.book.action;

import com.enjoy.book.bean.User;
import com.enjoy.book.biz.UserBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user.let")
public class UserServlet extends HttpServlet {
    UserBiz userBiz = new UserBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        String method = req.getParameter("type");
        switch (method){
            case "login":
                String name = req.getParameter("name");
                String pwd = req.getParameter("pwd");
                String userCode = req.getParameter("valcode");
               String code =session.getAttribute("code").toString();
                if(!code.equalsIgnoreCase(userCode)){
                    out.println("<script>alert('Wrong security code');location.href = 'login.html';</script>");
                    return;
                }
                User user = userBiz.getUser(name,pwd);
                if(user==null){
                 out.println("<script>alert('Username or password does not exist');location.href = 'login.html';</script>");
                }else {
                  session.setAttribute("user",user);
                  out.println("<script>alert('Login successfully');location.href='index.jsp';</script>");
                }
                break;
            case "exit":
                if(session.getAttribute("user")==null){
                    out.println("<script>alert('Please login');parent.window.location.href='login.html';</script>");
                    return;
                }
                session.invalidate();
                out.println("<script>parent.window.location.href='login.html';</script>");
                break;
            case "modifyPwd":
                if(session.getAttribute("user")==null){
                    out.println("<script>alert('Please login');parent.window.location.href='login.html';</script>");
                    return;
                }

                String newPwd = req.getParameter("newpwd");
                long id = ((User)session.getAttribute("user")).getId();
                int count = userBiz.modifyPwd(id,newPwd);
                if(count>0){
                    out.println("<script>alert('Password change successfully');parent.window.location.href='login.html';</script>");
                }else{
                    out.println("<script>alert('Password change fail');parent.window.location.href='login.html';</script>");
                }
                break;


        }


    }
}
