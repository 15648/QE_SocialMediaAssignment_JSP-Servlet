<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="social.model.User, social.model.Friends, 
				java.util.List"%>
<%
    User u = null;
    List<Friends> fList = null;
    if (session.getAttribute("user") != null)
    {
        u = (User) session.getAttribute("user");
    }
    if (session.getAttribute("friends") != null)
    {
        fList = (List<Friends>) session.getAttribute("friends");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=u.getFirst_name()%>'s Friends - Search for Friends</title>
</head>
<body>
	<div class="main_container">
		<jsp:include page="Header.jsp"></jsp:include>
		<div class="section">
			<div class="img_container">
				<img src="${pageContext.request.contextPath}/images/<%=u.getImg()%>" />
			</div>
			<div class="write_post_container">
				<form action="Friends_routes?action=searchFriends" method="POST">
					<input type="radio" name="SF" value="myFriends" /><label>My Friends</label>
					<input type="radio" name="SF" value="nonFriends" /><label>New Friends</label>
					<input type="submit" name="submit" value="Search" />
				</form>
			</div>
		</div>
		<div class="section">
			<%
			    if (fList != null)
			    {
			        for (int i = 0; i < fList.size(); i++)
			        {
			%>
			<div class="post"
				style="display: inline-block; margin: 10px; border: 1px solid red; padding: 10px;">
				<h2><%=fList.get(i).getUser_accepted().getFirst_name()%></h2>
				<p><%=fList.get(i).getUser_accepted().getAboutMe()%></p>
				<%
				    if (fList.get(i).getUser_request() == null)
				    {
				%>
				<div>
					<form action="Friends_routes?action=requestForFriendship" method="post">
						<input type="hidden" name="request_friend" value=<%=fList.get(i).getUser_accepted().getUser_id()%> /> 
						<input type="submit" name="submit" value="Friend Request" />
					</form>
				</div>
				<%
				    }
				%>
			</div>
			<%
			       }
			   }
			%>
		</div>
	</div>
</body>
</html>