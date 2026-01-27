import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Gallery")
public class Gallery extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/NavigationBarServlet").include(request, response);  
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Hotel Management System - Gallery</title>");
        out.println("<link rel='stylesheet' type='text/css' href='gallery.css'>"); 
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='content'>");
        out.println("<h1>\"Explore Our Luxurious Spaces\"</h1>");
        out.println("<p>Explore our hotel through the images below. Click the button to login and make a booking!</p>");
        out.println("<div class='gallery-container'>");
        String[] images = {
            "images/r1.jpg",
            "images/r2.jpg",
            "images/r3.jpg",
            "images/r4.jpg",
            "images/r5.jpg",
            "images/r6.jpg",
             "images/r7.jpg",
            "images/r8.jpg",
            "images/r9.jpg",
            "images/r10.jpg",
             "images/r11.jpg",
            "images/r12.jpg",
     };
        for (String image : images) {
            out.println("<div class='gallery-item'>");
            out.println("<img src='" + image + "' alt='Gallery Image'>");
            out.println("</div>");
        }
        out.println("</div>"); 
        out.println("<div class='login-redirect'>");
        out.println("<button onclick=\"window.location.href='http://localhost:8080/HotelManageSytem/Login'\">Login to Book</button>");
        out.println("</div>");
        out.println("</div>"); 
        out.println("</body>");
        out.println("</html>");
    }
}
