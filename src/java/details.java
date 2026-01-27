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

@WebServlet("/details")                                                              
public class details extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String roomName = request.getParameter("roomName");
        if (roomName == null || roomName.isEmpty()) {
            out.println("<p>Room name is missing!</p>");
            return;
        }
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Room Details</title>");
        out.println("<link rel='stylesheet' href='details.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='content'>");
        out.println("<h1>Room Details</h1>");
        try {                                                               
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
            
            String query = "SELECT room_image, room_price, room_size, bed_type, max_occupancy, room_features, room_amenities, room_view, room_rating, room_description FROM rooms WHERE room_name = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setString(1, roomName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String roomImage = rs.getString("room_image");
                double roomPrice = rs.getDouble("room_price");
                String roomSize = rs.getString("room_size");
                String bedType = rs.getString("bed_type");
                int maxOccupancy = rs.getInt("max_occupancy");
                String roomFeatures = rs.getString("room_features");
                String roomAmenities = rs.getString("room_amenities");
                String roomView = rs.getString("room_view");
                String roomRating = rs.getString("room_rating");
                String roomDescription = rs.getString("room_description");

                out.println("<div class='details-container'>");
                out.println("<div class='image-container'>");
                out.println("<img src='" + roomImage + "' alt='" + roomName + "'>");
                out.println("</div>");
                out.println("<div class='info-container'>");
                out.println("<h2>" + roomName + "</h2>");
                out.println("<p><b>Description:</b> " + roomDescription + "</p>");
                out.println("<p><b>Price per night:</b> $" + roomPrice + "</p>");
                out.println("<p><b>Size:</b> " + roomSize + "</p>");
                out.println("<p><b>Bed Type:</b> " + bedType + "</p>");
                out.println("<p><b>Max Occupancy:</b> " + maxOccupancy + " persons</p>");
                out.println("<p><b>Features:</b> " + roomFeatures + "</p>");
                out.println("<p><b>Amenities:</b> " + roomAmenities + "</p>");
                out.println("<p><b>View:</b> " + roomView + "</p>");
                out.println("<p><b>Rating:</b> " + roomRating + "</p>");
                out.println("<a href='" + request.getContextPath() + "/Booking?roomName=" + URLEncoder.encode(roomName, "UTF-8") + "&price=" + roomPrice + "'>");
                out.println("<button>Book Now</button></a>");
                out.println("</div>"); 
                out.println("</div>"); 
            } else {
                out.println("<p style='color:red;'>Room not found.</p>");
            }
            rs.close();
            stmt.close();
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
