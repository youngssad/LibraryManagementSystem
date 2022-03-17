package com.enjoy.book.action;

import com.alibaba.fastjson.JSON;
import com.enjoy.book.bean.Member;
import com.enjoy.book.bean.MemberType;
import com.enjoy.book.bean.Record;
import com.enjoy.book.biz.MemberBiz;
import com.enjoy.book.biz.MemberTypeBiz;
import com.enjoy.book.biz.RecordBiz;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/member.let")
public class MemberServlet extends HttpServlet {
    MemberTypeBiz  memberTypeBiz = new MemberTypeBiz();
    MemberBiz  memberBiz = new MemberBiz();
    RecordBiz recordBiz = new RecordBiz();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

         req.setCharacterEncoding("utf-8");
         resp.setContentType("text/html;charset=utf-8");

        PrintWriter  out  = resp.getWriter();
        HttpSession session = req.getSession();
        if(session.getAttribute("user")==null){
            out.println("<script>alert('Please login');parent.window.location.href='login.html';</script>");
            return;
        }

        String type = req.getParameter("type");

        switch (type){
            case "addpre":

                List<MemberType> memberTypes = memberTypeBiz.getAll();

                req.setAttribute("memberTypes",memberTypes);

                req.getRequestDispatcher("mem_add.jsp").forward(req,resp);
                break;
            case "add":
                String name =  req.getParameter("name");
                String pwd =  req.getParameter("pwd");
                long memberTypeId =  Long.parseLong(req.getParameter("memberType"));
                double  balance = Double.parseDouble(req.getParameter("balance"));
                String tel =  req.getParameter("tel");
                String idNumber =  req.getParameter("idNumber");
                int count = memberBiz.add(name,pwd,memberTypeId,balance,tel,idNumber);
                if(count>0){
                    out.println("<script>alert('Membership add successfully'); location.href='member.let?type=query';</script>");
                }else{
                    out.println("<script>alert('Membership add fail'); location.href='member.let?type=query';</script>");
                }

                break;
            case "modifypre":

                long id = Long.parseLong(req.getParameter("id"));
                Member member = memberBiz.getById(id);

                List<MemberType> memberTypes2 = memberTypeBiz.getAll();

                req.setAttribute("member",member);
                req.setAttribute("memberTypes",memberTypes2);

                req.getRequestDispatcher("mem_modify.jsp").forward(req,resp);


                break;
            case "modify":
                long memberId = Long.parseLong( req.getParameter("id"));
                String name2 =  req.getParameter("name");
                String pwd2 =  req.getParameter("pwd");
                long memberTypeId2 =  Long.parseLong(req.getParameter("memberType"));
                double  balance2 = Double.parseDouble(req.getParameter("balance"));
                String tel2 =  req.getParameter("tel");
                String idNumber2 =  req.getParameter("idNumber");
                int count3 = memberBiz.modify(memberId,name2,pwd2,memberTypeId2,balance2,tel2,idNumber2);
                 if(count3>0){
                     out.println("<script>alert('Membership modify successfully'); location.href='member.let?type=query';</script>");
                 }else{
                     out.println("<script>alert('Membership modify fail'); location.href='member.let?type=query';</script>");
                 }

                break;
            case "remove":
                 long memId = Long.parseLong(req.getParameter("id"));
                try {
                    int count2 = memberBiz.remove(memId);
                    if(count2>0){
                        out.println("<script>alert('Membership delete successfully'); location.href='member.let?type=query';</script>");
                    }else{
                        out.println("<script>alert('Membership delete fail'); location.href='member.let?type=query';</script>");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    out.println("<script>alert('"+e.getMessage()+"'); location.href='member.let?type=query';</script>");
                }
                break;
            case "query":
                List<Member> memberList = memberBiz.getAll();
                req.setAttribute("memberList",memberList);
                req.getRequestDispatcher("mem_list.jsp").forward(req,resp);
                break;
            case "modifyrecharge":
                String idNumber3 = req.getParameter("idNumber");
                double amount = Double.parseDouble(req.getParameter("amount"));
                int count4 = memberBiz.modifyBalance(idNumber3,amount);
                if(count4>0){
                    out.println("<script>alert('Membership top up successfully '); location.href='member.let?type=query';</script>");
                }else{
                    out.println("<script>alert('Membership top up fail'); location.href='member.let?type=query';</script>");
                }
                break;
            case "doajax":
                String idNum = req.getParameter("idn");
                Member member1 = memberBiz.getByIdNumber(idNum);
                List<Record> records = recordBiz.getRecordsByMemberId(member1.getId());
                if(records.size()>0){
                    long size = member1.getType().getAmount()-records.size();
                    member1.getType().setAmount(size);
                }
                String memberJson = JSON.toJSONString(member1);
                out.print(memberJson);
                break;
            default:
                resp.sendError(404,"Requested address does not exist");
        }

    }
}
