<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MiniBook - Login Page</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainStyle.css">
</head>
<body>
    <div class="login-container">
        <div class="login">
            <div class="sub_login">
                <form action="login_route?action=login" method="POST">
                    <%
                        String login_msg=(String)request.getAttribute("wrongCed");  
                        if (login_msg != null)
                            out.println("<font color=red size=4px>" + login_msg + "</font>");
                    %>
                    <div class="box" > 
                        <label for="email">Email</label>
                        <input type="text" name="email" id="email" />
                    </div>
                    <div class="box" >
                        <label for="pass">Password</label>
                        <input type="password" name="pass" id="pass" />
                    </div>
                    <div>
                        <input class="subLogin" type="submit" name="login" value="Login" id="login" />
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>