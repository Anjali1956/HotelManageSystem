import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AboutUsServlet")
public class AboutUsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        request.getRequestDispatcher("/NavigationBarServlet").include(request, response);  
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Hotel Management System - About Us</title>");
        out.println("<link rel='stylesheet' type='text/css' href='"  + request.getContextPath() + "/aboutUs.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='about-us-container'>");
        out.println("<h1 class='about-us-heading'>ABOUT US</h1>");
        out.println("<div class='about-us-text'>");
        out.println("<p>Welcome to <strong>The Grand Stay Hotel</strong>, your home away from home. Our hotel offers luxury, comfort, and excellent service to ensure a memorable stay.</p>");
        out.println("<p>We believe in making your stay stress-free and enjoyable with personalized service, elegant rooms, and exquisite dining options. With our prime location and exceptional facilities, we promise a stay that exceeds expectations.</p>");
        out.println("<p>At <strong>The Grand Stay Hotel</strong>, we cater to all types of travelers, from solo adventurers to families, and business professionals to tourists. Join us to experience the finest in hospitality.</p>");
        out.println("<p>Our state-of-the-art facilities include a luxurious spa, fitness center, and a variety of dining options to suit every taste. Our guests can enjoy 24/7 room service, free Wi-Fi, and comfortable accommodations designed for relaxation.</p>");
        out.println("<p>Whether you're hosting a conference, a wedding, or simply unwinding after a long day, we offer the perfect environment for every occasion. Visit us and create unforgettable memories.</p>");
        out.println("</div>");
        out.println("<div class='about-us-image'></div>"); 
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}

