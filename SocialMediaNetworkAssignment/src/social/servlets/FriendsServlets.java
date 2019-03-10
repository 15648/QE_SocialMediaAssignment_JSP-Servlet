package social.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
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
 * Servlet implementation class FriendsServlets
 */
@WebServlet("/Friends_routes")
public class FriendsServlets extends HttpServlet
{
    private static final long serialVersionUID = 1L;
    List<Friends> fList = null;
    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException
    {
        
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Friends> fList = null;
        HttpSession session = request.getSession();
        if(session.getAttribute("friends") == null) {
            FriendsDAO fDAO = new FriendsDAO();
            User u = (User) session.getAttribute("user");
            fList = fDAO.getAllUserFriends(u.getUser_id());
            session.setAttribute("friends", fList);
        }
        RequestDispatcher dis = request.getRequestDispatcher("views/Friends.jsp");
        dis.forward(request, response);
    }
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        FriendsDAO fDAO = new FriendsDAO();
        String action = request.getParameter("action");
        HttpSession session = null;
        if(action.equals("searchFriends")) {
            String SF = request.getParameter("SF");
            session = request.getSession();
            User u = (User) session.getAttribute("user");
            List<Friends> fList = null;
            if(SF.equals("myFriends")) {
                fList = fDAO.getAllUserFriends(u.getUser_id());
            } else if(SF.equals("nonFriends")) {
                fList = fDAO.getAllNonFriends(u.getUser_id());
            } 
            session.setAttribute("friends", fList);
            request.setAttribute("SF", SF);
            doGet(request, response);
        } else if(action.equals("requestForFriendship")) {
            Integer request_to = Integer.parseInt(request.getParameter("request_friend"));
            session = request.getSession();
            User u = (User) session.getAttribute("user");
            RequestDAO rDAO = new RequestDAO();
            rDAO.requestFriendShip(u.getUser_id(), request_to);
            doGet(request, response);
        }
    }
}
