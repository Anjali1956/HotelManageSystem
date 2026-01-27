import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Home")
public class Home extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/NavigationBarServlet").include(request, response);  

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hotel Management System - Home</title>");       
        out.println("<link rel='stylesheet' type='text/css' href='home.css'>");  
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='container'>");
        out.println("<h1>Welcome to Our Hotel Management System</h1>");
        out.println("<br>");
        out.println("<p>Experience luxury and comfort at our hotel. Browse through our rooms and book now!</p>");
        out.println("</div>"); 

        out.println("</body>");
        out.println("</html>");
    }
}
