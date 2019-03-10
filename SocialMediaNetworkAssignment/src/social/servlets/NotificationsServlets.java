package social.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import social.dao.FriendsDAO;
import social.dao.RequestDAO;
import social.model.Friends;
import social.model.Request;
import social.model.User;

/**
 * Servlet implementation class NotificationsServlets
 */
@WebServlet("/Notifications_routes")
public class NotificationsServlets extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getAttribute("friendRequest") == null) {
			HttpSession session = request.getSession();
			User u = (User) session.getAttribute("user");
			RequestDAO rDAO = new RequestDAO();
			List<Request> allRequests = rDAO.getRequest_To(u.getUser_id());
			request.setAttribute("friendRequest", allRequests);


			RequestDispatcher dis = request.getRequestDispatcher("views/Notifications.jsp");
			dis.forward(request, response);

			if (allRequests.size()==0 ) {
				System.out.println("No new messages");
				request.setAttribute("NoNotif","No new Notifications");
				doGet(request, response);
			}
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = null;
		if(action.equals("requestAction")) {
			System.out.println(action);
			Integer reply = 0;
			if(request.getParameter("accept") != null && request.getParameter("decline") == null) {
				reply = 1;
			} else if(request.getParameter("decline") != null && request.getParameter("accept") == null) {
				reply = -1;
			}
			System.out.println("reply = " + reply);
			Integer user_request = Integer.parseInt(request.getParameter("user_request"));
			Integer request_id = Integer.parseInt(request.getParameter("request_id"));
			System.out.println("user_request = " + user_request);
			System.out.println("request_id = " + request_id);
			session = request.getSession();
			User u = (User) session.getAttribute("user");
			if(reply == 1) {
				FriendsDAO fDAO = new FriendsDAO();
				Friends f = new Friends();
				f.setRequest_id(request_id);
				f.setUser_request(user_request);
				f.setUser_accepted(u);
				fDAO.confirmFriendShip(f);
			}
			RequestDAO rDAO = new RequestDAO();
			rDAO.requestUpdate(request_id, reply);
			response.sendRedirect("Notifications_routes");
		}
	}
}