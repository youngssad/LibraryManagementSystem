<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html >
<html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="keywords"  content = "Book java jsp"/>
    <meta http-equiv="author" content="phenix"/>
    <link rel="stylesheet" type="text/css" href="./Style/skin.css" />
</head>
    <body>
        <table width="100%" border="0" cellpadding="0" cellspacing="0">
            <tr>
                <td width="17" valign="top" background="./Images/mail_left_bg.gif">
                    <img src="./Images/left_top_right.gif" width="17" height="29" />
                </td>
                <td valign="top" background="./Images/content_bg.gif">
                    
                </td>
                <td width="16" valign="top" background="./Images/mail_right_bg.gif"><img src="./Images/nav_right_bg.gif" width="16" height="29" /></td>
            </tr>
            <tr>
                <td valign="middle" background="./Images/mail_left_bg.gif">&nbsp;</td>
                <td valign="top" bgcolor="#F7F8F9">
                    <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
                        <tr><td colspan="2" valign="top">&nbsp;</td><td>&nbsp;</td><td valign="top">&nbsp;</td></tr>
                        <tr>
                            <td colspan="4">
                                <table>
                                    <tr>
                                        <td width="100" align="center"><img src="./Images/mime.gif" /></td>
                                        <td valign="bottom"><h3 style="letter-spacing:1px;">Book Manage > Book List </h3></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td height="40" colspan="4">
                                <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
                                    <tr><td></td></tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td width="2%">&nbsp;</td>
                            <td width="96%">
                                <table width="100%">
                                    <tr>
                                        <td colspan="2">

                                                <table width="100%"  class="cont tr_color">
                                                    <tr>
                                                        <th>Id</th>
                                                        <th>Name</th>
                                                        <th>Type</th>
                                                        <th>Author</th>
                                                        <th>Publishing House</th>
                                                        <th>Stock</th>
                                                        <th>Cover</th>
                                                        <th>Order</th>
                                                    </tr>
                                                    <c:forEach items="${books}" var="b">
                                                        <tr align="center" class="d">
                                                            <td><a href="book.let?type=details&id=${b.id}">${b.id}</a></td>
                                                            <td>${b.name}</td>
                                                            <td>${b.type.name}</td>
                                                            <td>${b.author}</td>
                                                            <td>${b.publish}</td>
                                                            <td>${b.stock}</td>
                                                            <td><img src="${b.pic}" class="cover"/></td>
                                                            <td>
                                                                <a onclick="return confirm('Confirm modify');" href="book.let?type=modifypre&id=${b.id}">Modify</a>&nbsp;&nbsp;
                                                                <a onclick="return confirm('Comfirm delete');" href="book.let?type=remove&id=${b.id}">Delete</a>
                                                            </td>
                                                        </tr>
                                                    </c:forEach>

                                                   <tr><td colspan="8" align="center">
                                                    <div class="pager">
                                                        <ul class="clearfix">
                                                            <li><a href="book.let?type=query&pageIndex=${param.pageIndex-1}">上一页</a></li>
                                                            <c:forEach var="i" begin="1" end="${pageCount}" step="1">
                                                                <c:if test="${i==param.pageIndex}">
                                                                    <li class="current"><a href="book.let?type=query&pageIndex=${i}">${i}</a></li>
                                                                </c:if>
                                                                <c:if test="${i!=param.pageIndex}">
                                                                    <li><a href="book.let?type=query&pageIndex=${i}">${i}</a></li>
                                                                </c:if>
                                                            </c:forEach>
                                                            <li><a href="book.let?type=query&pageIndex=${param.pageIndex+1}">下一页</a></li>
                                                        </ul>
                                                    </div>
                                                   </td></tr>
                                                </table>

                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td width="2%">&nbsp;</td>
                        </tr>
                        <tr>
                            <td height="40" colspan="4">
                                <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
                                    <tr><td></td></tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td width="2%">&nbsp;</td>
                            <td width="51%" class="left_txt">
                                <img src="./Images/icon_mail.gif" width="16" height="11">Customer Service 999-999-9999<br />
                            </td>
                            <td>&nbsp;</td><td>&nbsp;</td>
                        </tr>
                    </table>
                </td>
                <td background="./Images/mail_right_bg.gif">&nbsp;</td>
            </tr>
            <tr>
                <td valign="bottom" background="./Images/mail_left_bg.gif">
                    <img src="./Images/buttom_left.gif" width="17" height="17" />
                </td>
                <td background="./Images/buttom_bgs.gif">
                    <img src="./Images/buttom_bgs.gif" width="17" height="17">
                </td>
                <td valign="bottom" background="./Images/mail_right_bg.gif">
                    <img src="./Images/buttom_right.gif" width="16" height="17" />
                </td>           
            </tr>
        </table>
    </body>
</html>