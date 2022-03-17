<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html >
<html >
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="keywords"  content = "Book java jsp"/>
    <meta http-equiv="author" content="phenix"/>
    <link rel="stylesheet" type="text/css" href="./Style/skin.css" />
    <script src="Js/jquery-3.3.1.min.js"></script>
    <script>

        function getCurrentDate(){
            var dateObj = new Date();
            var year = dateObj.getFullYear();
            var month = dateObj.getMonth()+1;//0~ 11
            var date = dateObj.getDate();
            var dateStr = year+"-"+(month>=10?month:"0"+month)+"-"+(date>=10?date:"0"+date);
            return dateStr;
        }

        function getBackDate(count){
            var dateObj = new Date();
            var mills = dateObj.getMilliseconds();
            mills += count*24*60*60*1000;
            dateObj.setMilliseconds(mills);

            var year = dateObj.getFullYear();
            var month = dateObj.getMonth()+1;//0~ 11
            var date = dateObj.getDate();
            var dateStr = year+"-"+(month>=10?month:"0"+month)+"-"+(date>=10?date:"0"+date);
            return dateStr;

        }


        $(function(){


            $("#btnQueryBook").prop("disabled","disabled");
            $("#btnSubmit").prop("disabled","disabled");
            var member = null;
            $("#btnQuery").click(function(){
                var content = $("#memberId").val();
                if(!content){
                   alert("Please enter UserId");
                   return;
                }
                var url="member.let?type=doajax&idn="+content;
                $.get(url,function(data,status){
                    console.log(data);
                    member = JSON.parse(data);
                    console.log(member.balance+","+member.type.name+","+member.type.amount+","+member.name);
                    $("#name").val(member.name);
                    $("#type").val(member.type.name);
                    $("#amount").val(member.type.amount);
                    $("#balance").val(member.balance);

                });
                $(this).prop("disabled","disabled");
                $("#btnQueryBook").removeAttr("disabled");
            });

            var bookNameList = new Array();
            $("#btnQueryBook").click(function(){
                var name=$("#bookContent").val();
                var url = "book.let?type=doajax&name="+name;
                $.get(url,function(data,status){
                    console.log(data);
                    if(data==="{}"){
                        alert("Book does not exist");
                        $("#bookContent").val("");
                        return;
                    }
                    if(bookNameList.indexOf(name)>=0){
                        alert("Book already added");
                        return;
                    }
                    bookNameList.push(name);
                    var book = JSON.parse(data);

                    var tr = $("<tr align=\"center\" class=\"d\">");
                    var tdCheck = $("<td><input type=\"checkbox\" value=\""+book.id+"\" class=\"ck\" checked /></td>");
                    var tdName = $("<td>"+book.name+"</td>");

                    var tdRentDate = $("<td>"+getCurrentDate()+"</td>");
                    var tdBackDate = $("<td>"+getBackDate(member.type.keepDay)+"</td>");
                    var tdPublish = $("<td>"+book.publish+"</td>");
                    var tdAddress = $("<td>"+book.address+"</td>");
                    var tdPrice = $("<td>"+book.price+"</td>");

                    tr.append(tdCheck);
                    tr.append(tdName);
                    tr.append(tdRentDate);
                    tr.append(tdBackDate);
                    tr.append(tdPublish);
                    tr.append(tdAddress);
                    tr.append(tdPrice);

                    $("#tdBook").append(tr);
                    $("#bookContent").val("");
                    $("#btnSubmit").removeAttr("disabled");
                });

            });


            $("#ckAll").click(function(){
                $(".ck").prop("checked",$(this).prop("checked"));
            });

            $("#btnSubmit").click(function(){

                var ids =  new Array();
                var count = 0;
                $(".ck").each(function () {
                   if($(this).prop("checked")){
                       ids.push($(this).val());
                       count++;
                   }
                });
                if(count===0){
                    alert("Please choose book");
                    return;
                }
                if(count>member.type.amount){
                    alert("Out of the borrow limit");
                    return;
                }
                location.href="record.let?type=add&mid="+member.id+"&ids="+ids.join("_");
            });
        });

    </script>

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
                                        <td valign="bottom"><h3 style="letter-spacing:1px;">Regular Function > Borrow Book </h3></td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td height="20" colspan="4">
                                <table width="100%" height="1" border="0" cellpadding="0" cellspacing="0" bgcolor="#CCCCCC">
                                    <tr><td></td></tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td width="2%">&nbsp;</td>
                            <td width="96%">
                                <fieldset>
                                    <legend>QueryMemberShip</legend>
                                    <table width="100%" border="0" class="cont"  >
                                        <tr>
                                            <td width="8%" class="run-right"> MemberId</td>
                                            <td colspan="7"><input class="text" type="text" id="memberId" name="memberId"/> 
                                                 <input type="button" id="btnQuery" value="Comfirm" style="width: 80px;"/></td>
                           
                                            </td>
                                         
                                        </tr>
                                        <tr>
                                            <td width="8%" class="run-right">Name</td>
                                            <td width="17%"><input class="text" type="text" id="name" disabled/></td>
                                            <td width="8%" class="run-right">Type:</td>
                                            <td width="17%"><input class="text" type="text" id="type" disabled/></td>
                                            <td width="8%" class="run-right">Borrow Limit</td>
                                            <td width="17%"><input class="text" type="text" id="amount"  disabled/></td>
                                            <td width="8%" class="run-right">Account Balance</td>
                                            <td width="17%"><input class="text" type="text" id="balance"  disabled/></td>
                                        </tr>
                                    </table>
                                </fieldset>
                            </td>
                            <td width="2%">&nbsp;</td>
                        </tr>
                        <tr>
                            <td height="40" colspan="3">
                            </td>
                        </tr>
                        
                        <tr>
                            <td width="2%">&nbsp;</td>
                            <td width="96%">
                                <fieldset>
                                    <legend>Query Book</legend>
                                    <table width="100%" border="1" class="cont"  >
                                        <tr>
                                            <td colspan="8">
                                               
                                                Please Enter:&nbsp;&nbsp;<input class="text" type="text" id="bookContent" name="bookContent" placeholder="BarCode/BookName"/>
                                                <input type="button" id="btnQueryBook" value="Confirm" style="width: 80px;"/>
                                                <input type="button" id="btnSubmit" value="Finish Borrow" style="width: 80px;"/>
                                            </td>
                                         
                                        </tr>
                                       
                                    </table>
                                </fieldset>
                            </td>
                            <td width="2%">&nbsp;</td>
                        </tr>
                        <tr><td height="20" colspan="3"></td></tr>
                        <tr>
                            <td width="2%">&nbsp;</td>
                            <td width="96%">
                                <table width="100%">
                                    <tr>
                                        <td colspan="2">
                                            <form action="" method="">
                                                <table width="100%"  class="cont tr_color" id="tdBook">
                                                    <tr>
                                                        <th><input type="checkbox" id="ckAll" checked/>ChooseAll/ChooseNone</th>
                                                        <th>Book Name</th>
                                                        <th>Borrow Time</th>
                                                        <th>Return Time</th>
                                                        <th>Publishing House</th>
                                                        <th>Bookshelf</th>
                                                        <th>Price</th>
                                                    </tr>


                                                </table>
                                            </form>
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
                                <img src="./Images/icon_mail.gif" width="16" height="11"> Customer Service 999-999-9999<br />
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