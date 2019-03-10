package social.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import social.dao.UserDAO;
import social.model.User;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login_route")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher dis = request.getRequestDispatcher("views/Login.jsp");
		dis.forward(request, response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = "";
		String pass = "";
		UserDAO uDAO = null;
		HttpSession session = request.getSession();
		String action = request.getParameter("action");
		if(action.equals("login")) {
		    System.out.println("trying to login");
		    uDAO = new UserDAO();
		    User u = null;
		    email = request.getParameter("email");
		    pass = request.getParameter("pass");
		    try
            {
                u = uDAO.getUserByEmail(email);
                if(u != null && u.getPass().equals(pass)) {
                    session.setAttribute("user", u);
                    RequestDispatcher dis = request.getRequestDispatcher("Wall_route?action=myrelatedposts");
                    dis.forward(request, response);
                } else {
                    request.setAttribute("wrongCed", "User Name And Password are incorrect");
                    doGet(request, response);
                }
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
		    
		} else {
		    doGet(request, response);
		}
	}
}