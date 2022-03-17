package com.enjoy.book.action;

import com.alibaba.fastjson.JSON;
import com.enjoy.book.bean.Member;
import com.enjoy.book.bean.Record;
import com.enjoy.book.bean.User;
import com.enjoy.book.biz.MemberBiz;
import com.enjoy.book.biz.RecordBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/record.let")
public class RecordServlet extends HttpServlet {
    RecordBiz recordBiz = new RecordBiz();
    MemberBiz memberBiz = new MemberBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        String type = req.getParameter("type");
        HttpSession session = req.getSession();
        PrintWriter out = resp.getWriter();
        User user = (User)session.getAttribute("user");

        if(user==null){
            out.println("<script>alert('Please Login');parent.window.location.href='login.html';</script>");
            return;
        }

        switch(type){
            case "add":

                long memberId = Long.parseLong(req.getParameter("mid"));

                String ids = req.getParameter("ids");
                String []strs= ids.split("_");
                List<Long> bookIds = new ArrayList<Long>();
                for(String s:strs){
                    Long id = Long.parseLong(s);
                    bookIds.add(id);
                }

                long userId = user.getId();

               int count=  recordBiz.add(memberId,bookIds,userId);
               if(count>0){
                   out.println("<script>alert('book borrow successfully');location.href='main.jsp';</script>");
               }else{
                   out.println("<script>alert('book borrow fail');location.href='main.jsp';</script>");
               }
                break;
            case "queryback":

                 String idn = req.getParameter("idn");

                 Member member = memberBiz.getByIdNumber(idn);
                 List<Record> records = recordBiz.getRecordsByMemberId(member.getId());

                req.setAttribute("member",member);
                req.setAttribute("records",records);

                req.getRequestDispatcher("return_list.jsp").forward(req,resp);
                break;
            case "back":

                long memberId2 = Long.parseLong(req.getParameter("mid"));

                String idStr = req.getParameter("ids");
                String []idStrs = idStr.split("_");
                List<Long>  recordIds = new ArrayList<Long>();
                for(String s:idStrs){
                    recordIds.add(Long.parseLong(s));
                }

                long userId2 = user.getId();

                int count2 = recordBiz.modify(memberId2, recordIds,userId2);

                if(count2>0){
                    out.println("<script>alert('book return successfully');location.href='main.jsp';</script>");
                }else{
                    out.println("<script>alert('book return fail');location.href='main.jsp';</script>");
                }
                break;
            case "keep":
                long recordId = Long.parseLong(req.getParameter("id"));
                int count3 = recordBiz.modify(recordId);
                if(count3>0){
                    out.println("<script>alert('book rekeep successfully');location.href='main.jsp';</script>");
                }else{
                    out.println("<script>alert('book rekeep fail');location.href='main.jsp';</script>");
                }
                break;
            case "doajax":
                 int typeId = Integer.parseInt(req.getParameter("typeId"));

                 String keyword = req.getParameter("keyword");
                  keyword = keyword.isEmpty()?null:keyword;

                  List<Map<String,Object>> rows  = recordBiz.query(typeId,keyword);

                 out.print(JSON.toJSONString(rows));
                break;
            default:
                resp.sendError(404,"Requested address does not exist");
        }

    }
}
