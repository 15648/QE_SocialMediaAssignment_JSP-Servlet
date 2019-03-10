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

import social.dao.PostDAO;
import social.model.Post;
import social.model.User;

/**
 * Servlet implementation class WallServlet
 */
@WebServlet("/Wall_route")
public class WallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
        PostDAO pDAO = new PostDAO();
        List<Post> pList = null;
	    User u = (User) session.getAttribute("user");
        pList = pDAO.getPostRelatedToUser(u.getUser_id());
        session.setAttribute("pList", pList);
	    RequestDispatcher dis = request.getRequestDispatcher("views/Wall.jsp");
        dis.forward(request, response);
        }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		HttpSession session = request.getSession();
		PostDAO pDAO = new PostDAO();
		List<Post> pList = null;
		if(action.equals("myrelatedposts")) {
		    User u = (User) session.getAttribute("user");
		    pList = pDAO.getPostRelatedToUser(u.getUser_id());
		    //continue to display post list on wall
		    session.setAttribute("pList", pList);
		    doGet(request, response);
		} else if(action.equals("writePost")) {
		    String post_text = request.getParameter("post");
		    User u = (User) session.getAttribute("user");
		    Post p = new Post();
		    p.setUser_id(u.getUser_id());
		    p.setPost_text(post_text);
		    pDAO.postOnWall(p);
		    RequestDispatcher dis = request.getRequestDispatcher("Wall_route?action=myrelatedposts");
	        dis.forward(request, response);
		} else if(action.equals("editPost")) {
		    User u = (User) session.getAttribute("user");
            Integer post_id = Integer.parseInt(request.getParameter("edit"));
		    
            Post p = pDAO.getSinglePost(u.getUser_id(), post_id);
            request.setAttribute("editPost", p);
            RequestDispatcher dis = request.getRequestDispatcher("views/EditPost.jsp");
            dis.forward(request, response);
		} else if(action.equals("submitEditPost")) { 
		    String postText = request.getParameter("editPostText");
		    Integer post_id = Integer.parseInt(request.getParameter("post_id"));
		    Post p = new Post(postText, post_id);
		    pDAO.editPostOnWall(p);
		    RequestDispatcher dis = request.getRequestDispatcher("Wall_route?action=myrelatedposts");
            dis.forward(request, response);
	     } else if(action.equals("deletePost")) {
            Integer post_id = Integer.parseInt(request.getParameter("delete"));
            pDAO.deletePostOnWall(post_id);
            RequestDispatcher dis = request.getRequestDispatcher("Wall_route?action=myrelatedposts");
            dis.forward(request, response);
        }
	}
}
