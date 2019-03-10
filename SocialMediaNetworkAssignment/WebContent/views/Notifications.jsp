<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page
	import="social.model.User, social.model.Request,
    				java.util.List"%>
<%
    User u = null;
    List<Request> allRequests = null;
    if (session.getAttribute("user") != null)
    {
        u = (User) session.getAttribute("user");
    }
    if (request.getAttribute("friendRequest") != null)
    {
        allRequests = (List<Request>) request.getAttribute("friendRequest");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=u.getFirst_name()%>'s Notifications</title>
</head>
<body>
	<div class="main_container">
		<jsp:include page="Header.jsp"></jsp:include>
		<h1>Notifications</h1>
		<%
			if(allRequests.size() == 0)
			out.println("No new notifications");
			
		    for (int i = 0; i < allRequests.size(); i++) {
		    if (!allRequests.get(i).isResponded())
		        {
		%>
						<h2><%=allRequests.get(i).getUser_request().getEmail()%></h2>
		<form action="Notifications_routes?action=requestAction" method="post">
			<input type="hidden" name="user_request"
				value=<%=allRequests.get(i).getUser_request().getUser_id()%> /> <input
				type="hidden" name="request_id"
				value=<%=allRequests.get(i).getRequest_id()%> /> <input
				type="submit" name="accept" value="Accept" /> <input type="submit"
				name="decline" value="Decline" />
		</form>
		<%
		    }
		    }
		%>
	</div>
</body>
</html>