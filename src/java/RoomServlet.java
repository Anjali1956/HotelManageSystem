import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RoomServlet")
public class RoomServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<title>Hotel Management System - Rooms</title>");
        out.println("<link rel='stylesheet' href='dnavbar.css'>");  
        out.println("<link rel='stylesheet' href='room.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<nav class='navbar'>");
        out.println("<div class='container'>");
        out.println("<ul class='nav-links'>");
        out.println("<li><a href='dashboard'>Dashboard</a></li>");
        out.println("<li><a href='RoomServlet'>Browse Rooms</a></li>");
        out.println("<li><a href='MyBookings'>My Bookings</a></li>"); 
        out.println("</ul>");
        out.println("</div>");
        out.println("</nav>");
        out.println("<div class='content'>");
        out.println("<h1>Our Available Rooms</h1>");
        out.println("<p>Choose from our variety of rooms for a comfortable stay. Click on a room for more details!</p>");
        out.println("<div class='room-container'>");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
            
            String query = "SELECT * FROM rooms ";
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            if (!rs.next()) {
                out.println("<p>No rooms available at the moment.</p>");
            } else {
                do {
                    String roomName = rs.getString("room_name");
                    String roomImage = rs.getString("room_image");
                    String roomPrice = String.valueOf(rs.getDouble("room_price"));
                    out.println("<div class='room-card'>");
                    out.println("<a href='details?roomName=" + URLEncoder.encode(roomName, "UTF-8") + "'>");
                    out.println("<img src='" + roomImage + "' alt='" + roomName + "'>");
                    out.println("</a>");
                    out.println("<h3>" + roomName + "</h3>");
                    out.println("<p>Price per night: " + roomPrice + "</p>");
                    out.println("</div>");
                } while (rs.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error occurred: " + e.getMessage() + "</p>");
        }
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
