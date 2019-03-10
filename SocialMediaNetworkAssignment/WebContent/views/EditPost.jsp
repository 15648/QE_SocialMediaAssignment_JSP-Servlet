<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="social.model.Post"%>
<%
    Post p = null;
    String postName = "";
    if(request.getAttribute("editPost") != null){
        p = (Post) request.getAttribute("editPost");
        postName = p.getUser().getFirst_name() + "'s Post";
    } else {
        postName = "User's Post";
    }
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=postName%> - Edit Post Page</title>
</head>
<body>
    <jsp:include page="Header.jsp"></jsp:include>
    <form action="Wall_route?action=submitEditPost" method="POST">
        <input type="hidden" name="post_id" value=<%=p.getPost_id()%> />
        <textarea rows="5" cols="50" name="editPostText" placeholder="Write Post" ><%=p.getPost_text()%></textarea>
        <input type="submit" name="submit" value="Edit" />
    </form>
</body>
</html>