import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/navbar")
public class NavigationBarServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<style>");
out.println(".navbar {");
out.println("    display: flex;");
out.println("    justify-content: center;");
out.println("    align-items: center;");
out.println("    gap: 20px;");
out.println("    background: rgba(51, 51, 51, 0.8);");
out.println("    padding: 8px 0;");
out.println("    position: fixed;");
out.println("    width: 100%;");
out.println("    top: 0;");
out.println("    left: 0;");
out.println("    z-index: 1000;");
out.println("}");

out.println(".navbar a {");
out.println("    color: white;");
out.println("    text-decoration: none;");
out.println("    padding: 10px 15px;");
out.println("    font-size: 22px;");
out.println("    transition: 0.3s;");
out.println("}");

out.println(".navbar a:hover {");
out.println("    background: rgba(255, 255, 255, 0.2);");
out.println("}");

out.println(".dropdown {");
out.println("    position: relative;");
out.println("    display: inline-block;");
out.println("}");

out.println(".dropdown-content {");
out.println("    display: none;");
out.println("    position: absolute;");
out.println("    background: rgba(51, 51, 51, 0.9);");
out.println("    min-width: 150px;");
out.println("    border-radius: 5px;");
out.println("}");

out.println(".dropdown-content a {");
out.println("    color: white;");
out.println("    padding: 10px;");
out.println("    display: block;");
out.println("    text-align: left;");
out.println("}");

out.println(".dropdown-content a:hover {");
out.println("    background: rgba(255, 255, 255, 0.2);");
out.println("}");

out.println(".dropdown:hover .dropdown-content {");
out.println("    display: block;");
out.println("}");
out.println("</style>");

        out.println("</head>");
        out.println("<body>");
        out.println("<div class='navbar'>");

        String currentPage = request.getRequestURI(); 
        out.println("<a href='http://localhost:8080/HotelManageSytem/Home' class='" + (currentPage.endsWith("/Home") ? "active" : "") + "'>Home</a>");
        out.println("<a href='http://localhost:8080/HotelManageSytem/AboutUsServlet' class='" + (currentPage.endsWith("/AboutUsServlet") ? "active" : "") + "'>About Us</a>");
        out.println("<a href='http://localhost:8080/HotelManageSytem/Gallery' class='" + (currentPage.endsWith("/Gallery") ? "active" : "") + "'>Gallery</a>");
        out.println("<div class='dropdown'>");
        out.println("<a href='#' class='dropbtn'>Login</a>");
        out.println("<div class='dropdown-content'>");
        out.println("<a href='http://localhost:8080/HotelManageSytem/AdminLogin'>Admin Login</a>");
        out.println("<a href='http://localhost:8080/HotelManageSytem/Login'>User Login</a>");
        out.println("</div>");
        out.println("</div>");
        out.println("<a href='http://localhost:8080/HotelManageSytem/register'>Register</a>");     
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
