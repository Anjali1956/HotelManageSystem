import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Dashboard")
public class dashboard extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
                if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("Login");
            return;
        }
        String username = (String) session.getAttribute("username");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Dashboard</title>");
        out.println("<link rel='stylesheet' href='dashboard.css'>");
        out.println("<link rel='stylesheet' href='dnavbar.css'>");  
        out.println("</head>");
        out.println("<body>");
        out.println("<nav class='navbar'>");
        out.println("<div class='container'>");
        out.println("<ul class='nav-links'>");
        out.println("<li><a href='dashboard'>Dashboard</a></li>");
        out.println("<li><a href='RoomServlet'>Browse Rooms</a></li>");
        out.println("<li><a href='MyBookings'>My Bookings</a></li>");
        out.println("<li><a href='LogoutServlet'>Logout</a></li>");  
        out.println("</ul>");
        out.println("</div>");
        out.println("</nav>");
        out.println("<div class='main-content'>");
        out.println("<h1>Welcome, " + username + "!</h1>");
        out.println("<p>Explore our services and manage your bookings with ease.</p>");
        out.println("<div class='dashboard-sections'>");
        out.println("<div class='card'><a href='RoomServlet'><h2>Browse Available Rooms</h2></a></div>");
        out.println("<div class='card'><a href='MyBookings'><h2>View Your Bookings</h2></a></div>");
        out.println("</div>");
        out.println("</div>");  
        out.println("<footer>");
        out.println("<p>&copy; 2025 Hotel Management System | All Rights Reserved</p>");
        out.println("</footer>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }
}
