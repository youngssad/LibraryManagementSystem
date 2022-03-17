<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="keywords"  content = "Book java jsp"/>
    <meta http-equiv="author" content="phenix"/>
    <script src="./Js/prototype.lite.js" type="text/javascript"></script>
    <script src="./Js/moo.fx.js" type="text/javascript"></script>
    <script src="./Js/moo.fx.pack.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="./Style/skin.css" />
    <script type="text/javascript">
        window.onload = function () {
            var contents = document.getElementsByClassName('content');
            var toggles = document.getElementsByClassName('type');

            var myAccordion = new fx.Accordion(
            toggles, contents, {opacity: true, duration: 400}
            );
            myAccordion.showThisHideOpen(contents[0]);
            for(var i=0; i<document .getElementsByTagName("a").length; i++){
                var dl_a = document.getElementsByTagName("a")[i];
                    dl_a.onfocus=function(){
                        this.blur();
                    }
            }
        }
    </script>
</head>

<body>
    <table width="100%" height="200" border="0" cellpadding="0" cellspacing="0" >
        <tr>
            <td width="182" valign="top">
                <div id="container">
                    <h1 class="type"><a href="javascript:void(0)">MemberShip Manage</a></h1>
                    <div class="content">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="./Images/menu_top_line.gif" width="182" height="5" /></td>
                            </tr>
                        </table>
                        <ul class="RM">
                            <li><a href="./member.let?type=addpre" target="main">Become Member</a></li>
                            <li><a href="./member.let?type=query" target="main">MemberShip Manage</a></li>
                            <li><a href="./mem_recharge.jsp" target="main">Recharge</a></li>
                        </ul>
                    </div>
                    <h1 class="type"><a href="javascript:void(0)">Book Manage</a></h1>
                    <div class="content">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="./Images/menu_top_line.gif" width="182" height="5" /></td>
                            </tr>
                        </table>
                        <ul class="RM">
                            <li><a href="./book_add.jsp" target="main">Add Book</a></li>
                            <li><a href="./book.let?type=query&pageIndex=1" target="main">Book List</a></li>
                        </ul>
                    </div>
                    <h1 class="type"><a href="javascript:void(0)">Book Type Manage</a></h1>
                    <div class="content">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="./Images/menu_top_line.gif" width="182" height="5" /></td>
                            </tr>
                        </table>
                        <ul class="RM">
                            <li><a href="type_add.jsp" target="main">Add Type</a></li>
                            <li><a href="type_list.jsp" target="main">Type List</a></li>
                        </ul>
                    </div>
                    <h1 class="type"><a href="javascript:void(0)">Regular Function</a></h1>
                    <div class="content">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="./Images/menu_top_line.gif" width="182" height="5" /></td>
                            </tr>
                        </table>
                        <ul class="RM">
                            <li><a href="./book_rent.jsp" target="main">Borrow Book</a></li>
                            <li><a href="./return_list.jsp" target="main">Return Book</a></li>
                            <li><a href="./rent_list.jsp" target="main">Borrow History</a></li>
                        </ul>
                    </div>
                    <h1 class="type"><a href="javascript:void(0)">个人中心</a></h1>
                    <div class="content">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="./Images/menu_top_line.gif" width="182" height="5" /></td>
                            </tr>
                        </table>
                        <ul class="RM">
                            <li><a href="./set_pwd.jsp" target="main">Change Password</a></li>
                        
                        </ul>
                    </div>

                    <div class="content">
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td><img src="./Images/menu_top_line.gif" width="182" height="5" /></td>
                            </tr>
                        </table>
                        <ul class="RM">
                            <li><a href="javascript:void(0)" target="main">Guide</a></li>
                            <li><a href="javascript:void(0)" target="main">Note</a></li>
                            <li><a href="javascript:void(0)" target="main">WebsiteVote</a></li>
                            <li><a href="javascript:void(0)" target="main">Email</a></li>
                            <li><a href="javascript:void(0)" target="main">PictureUpload</a></li>
                        </ul>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</body>
</html>
