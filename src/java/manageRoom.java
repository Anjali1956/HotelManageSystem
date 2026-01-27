import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/manageroom")
public class manageRoom extends HttpServlet {
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<title>Manage Rooms</title>");
    out.println("<style>");
out.println("body { margin: 0; font-family: Arial, sans-serif; background: linear-gradient(135deg, #f5f7fa, #c3cfe2); }");

out.println(".navbar { position: fixed; top: 0; left: 0; width: 100%; background-color: #2c3e50; color: white; padding: 15px 15px; display: flex; justify-content: space-between; align-items: center; z-index: 1000; box-shadow: 0 2px 6px rgba(0,0,0,0.3); }");
out.println(".navbar h1 { margin: 0; font-size: 20px; color: white }");
out.println(".navbar a { color: white; text-decoration: none; margin-left: 20px; font-size: 16px; }");
out.println(".navbar a:hover { text-decoration: underline; }");
out.println(".content { margin-top: 120px; width: 100%; display: flex; justify-content: center; }");

out.println("</style>");
out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/manageroom.css'>");

    out.println("</head>");
    out.println("<body>");
    out.println("<body>");
out.println("<div class='navbar'>");
out.println("<h1>Admin Dashboard</h1>");
out.println("<div>");
out.println("<a href='Adashboard'>Dashboard</a>");
out.println("<a href='manageRoom'>Add Room</a>");
out.println("<a href='AdminBookings'>Bookings</a>");
out.println("<a href='roomopt'>Manage Rooms</a>");
out.println("</div>");
out.println("</div>");
out.println("<div class='content'>");

    out.println("<form action='manageRoom' method='post'>");
    out.println("<h1>Add Rooms</h1>");
    out.println("<label>Room Name:</label>");
    out.println("<input type='text' name='roomName' required><br>");
    out.println("<label>Room Image:</label>");
    out.println("<input type='text' name='roomImage' required><br>");
    out.println("<label>Price per Night:</label>");
    out.println("<input type='number' step='0.01' name='roomPrice' required><br>");
    out.println("<label>Room Size:</label>");
    out.println("<input type='text' name='roomSize' required><br>");
    out.println("<label>Bed Type:</label>");
    out.println("<input type='text' name='bedType' required><br>");
    out.println("<label>Max Occupancy:</label>");
    out.println("<input type='number' name='maxOccupancy' required><br>");
    out.println("<label>Room Features:</label>");
    out.println("<textarea name='roomFeatures' required></textarea><br>");
    out.println("<label>Room Amenities:</label>");
    out.println("<textarea name='roomAmenities' required></textarea><br>");
    out.println("<label>Room View:</label>");
    out.println("<input type='text' name='roomView'><br>");
    out.println("<label>Room Rating:</label>");
    out.println("<input type='text' name='roomRating'><br>");
    out.println("<label>Status:</label>");
    out.println("<select name='status'>");
    out.println("<option value='Available'>Available</option>");
    out.println("<option value='Booked'>Booked</option>");
    out.println("</select><br>");
    out.println("<label>Room Description:</label>");
    out.println("<textarea name='roomDescription' required></textarea><br>");
    out.println("<button type='submit'>Add Room</button>");
    out.println("</form>");
    out.println("</div>");

    out.println("</body>");
    out.println("</html>");
}
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String roomName = request.getParameter("roomName");
    String roomImage = request.getParameter("roomImage");
    String roomPrice = request.getParameter("roomPrice");
    String roomSize = request.getParameter("roomSize");
    String bedType = request.getParameter("bedType");
    int maxOccupancy = Integer.parseInt(request.getParameter("maxOccupancy"));
    String roomFeatures = request.getParameter("roomFeatures");
    String roomAmenities = request.getParameter("roomAmenities");
    String roomView = request.getParameter("roomView");
    String roomRating = request.getParameter("roomRating");
    String status = request.getParameter("status");
    String roomDescription = request.getParameter("roomDescription");
    try {
    Class.forName("com.mysql.cj.jdbc.Driver");
    Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");

    // Check if the room already exists
    String checkQuery = "SELECT COUNT(*) FROM rooms WHERE room_name = ?";
    PreparedStatement checkStmt = con.prepareStatement(checkQuery);
    checkStmt.setString(1, roomName);
    ResultSet rs = checkStmt.executeQuery();
    rs.next();
    int count = rs.getInt(1);
    rs.close();
    checkStmt.close();

    if (count > 0) {
        // If the room exists, update its status and description
        String updateQuery = "UPDATE rooms SET status = ?, room_description = ? WHERE room_name = ?";
        PreparedStatement ps = con.prepareStatement(updateQuery);
        ps.setString(1, status);
        ps.setString(2, roomDescription);
        ps.setString(3, roomName);
        int result = ps.executeUpdate();
        ps.close();

        if (result > 0) {
            out.println("<script>alert('Room status updated successfully!'); window.location.href='manageRoom';</script>");
        } else {
            out.println("<script>alert('Failed to update room status. Try again!'); window.location.href='manageRoom';</script>");
        }
    } else {
        // If the room does not exist, insert a new record
        String insertQuery = "INSERT INTO rooms (room_name, room_image, room_price, room_size, bed_type, max_occupancy, room_features, room_amenities, room_view, room_rating, status, room_description) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(insertQuery);
        ps.setString(1, roomName);
        ps.setString(2, roomImage);
        ps.setBigDecimal(3, new BigDecimal(roomPrice));
        ps.setString(4, roomSize);
        ps.setString(5, bedType);
        ps.setInt(6, maxOccupancy);
        ps.setString(7, roomFeatures);
        ps.setString(8, roomAmenities);
        ps.setString(9, roomView);
        ps.setString(10, roomRating);
        ps.setString(11, status);
        ps.setString(12, roomDescription);
        int result = ps.executeUpdate();
        ps.close();

        if (result > 0) {
            out.println("<script>alert('Room added successfully!'); window.location.href='manageRoom';</script>");
        } else {
            out.println("<script>alert('Failed to add room. Try again!'); window.location.href='manageRoom';</script>");
        }
    }

    con.close();
} catch (Exception e) {
    e.printStackTrace();
}
   }
}
