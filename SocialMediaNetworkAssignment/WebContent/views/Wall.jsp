<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="social.model.User, social.model.Post, 
				java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% 
User u = null;
List<Post> pList = null;
if(session.getAttribute("user") != null){ 
	u = (User) session.getAttribute("user");
}
if(session.getAttribute("pList") != null){
    pList = (List<Post>) session.getAttribute("pList");
}
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Mini Wall - <%=u.getFirst_name()%>'s Page</title>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/mainStyle.css">
</head>
<body>
<!-- pageContext.request.contextPath -->

<div class="main_container">
	<jsp:include page="Header.jsp"></jsp:include>
	<div class="section">
		<div class="img_container">
			<img src="${pageContext.request.contextPath}/images/<%=u.getImg()%>" />
		</div>
		<div class="write_post_container">
			<form action="Wall_route?action=writePost" method="POST">
				<textarea rows="10" cols="100" name="post" placeholder="Write Post"></textarea>
				<input type="submit" name="submit" value="Post" />
			</form>
		</div>
	</div>
	<div class="section_p">
	<%if(pList != null){
		for(int i = 0; i < pList.size(); i++){ %>
			<div class="post" >
				<h2><%=pList.get(i).getUser().getFirst_name() + " " + pList.get(i).getUser().getLast_name() %></h2>
				<p>Said at <%=pList.get(i).getDate_col().getTime() %>: <%=pList.get(i).getPost_text() %></p>
				<%if(u.getUser_id().equals(pList.get(i).getUser().getUser_id())) {%>
				<div>
					<form action="Wall_route?action=editPost" method="post">
						<input type="hidden" name="edit" value=<%=pList.get(i).getPost_id()%> />
						<input type="submit" name="submit" value="Edit" />
					</form>
				</div>
				<div>
					<form action="Wall_route?action=deletePost" method="post">
						<input type="hidden" name="delete" value=<%=pList.get(i).getPost_id()%> />
						<input type="submit" name="submit" value="Delete" />
					</form>
				</div>
				<%} %>
			</div>
	<%		} 
		}
	%>
	</div>
</div>
</body>
</html>