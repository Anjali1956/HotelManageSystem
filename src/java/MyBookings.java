import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import javax.servlet.http.HttpSession;
@WebServlet("/MyBookings")
public class MyBookings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Your Bookings</title>");
        out.println("<link rel='stylesheet' href='MyBookings.css'>");  
        out.println("<link rel='stylesheet' href='dnavbar.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<nav class='navbar'>");
out.println("<div class='container'>");
out.println("<ul class='nav-links'>");
out.println("<li><a href='Dashboard'>Dashboard</a></li>");
out.println("<li><a href='RoomServlet'>Browse Rooms</a></li>");
out.println("<li><a href='MyBookings'>My Bookings</a></li>");
out.println("<li><a href='LogoutServlet'>Logout</a></li>");
out.println("</ul>");
out.println("</div>");
out.println("</nav>");

        out.println("<div class='content'>");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            out.println("<p>You need to log in to view your bookings.</p>");
            out.println("<a href='Login'>Click here to log in</a>");
            out.println("</div>");  
            out.println("</body>");
            out.println("</html>");
            return;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
            
            String sql = "SELECT id, room_name, price, checkin, checkout, special_requests, name, gender, booking_date, status FROM bookings WHERE username = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username); 
            ResultSet rs = pst.executeQuery();
            out.println("<h2>Your Bookings</h2>");
            out.println("<table>");
            out.println("<tr><th>Booking ID</th><th>Name</th><th>Gender</th><th>Room Name</th><th>Price</th><th>Check-in</th><th>Check-out</th><th>Booking Date</th><th>Special Requests</th><th>Status</th><th>Actions</th><th>Payment</th></tr>");
            while (rs.next()) {
                String bookingId = rs.getString("id");  
                String roomName = rs.getString("room_name");
                String price = rs.getString("price");
                String checkin = rs.getString("checkin");
                String checkout = rs.getString("checkout");
                String specialRequests = rs.getString("special_requests");
                String name = rs.getString("name");
                String gender = rs.getString("gender");
                String bookingDate = rs.getString("booking_date");
                String status = rs.getString("status"); 
                out.println("<tr>");
                out.println("<td>" + bookingId + "</td>");  
                out.println("<td>" + name + "</td>");
                out.println("<td>" + gender + "</td>");
                out.println("<td>" + roomName + "</td>");
                out.println("<td>" + price + "</td>");
                out.println("<td>" + checkin + "</td>");
                out.println("<td>" + checkout + "</td>");
                out.println("<td>" + bookingDate + "</td>");
                out.println("<td>" + specialRequests + "</td>");
                out.println("<td>" + status + "</td>");  
                out.println("<td><a href='http://localhost:8080/HotelManageSytem/UpdateBooking?id=" + bookingId + "'>Update</a> | <a href='http://localhost:8080/HotelManageSytem/DeleteBookings?id=" + bookingId + "'>Delete</a></td>");
                if ("Pending".equals(status)) {
                     out.println("<td><a href='Payment?id=" + bookingId + "'>Pay Now</a></td>");
                } else {
                    out.println("<td>Paid</td>");
                }
                out.println("</tr>");
            }
            out.println("</table>");
            con.close();
        } catch (Exception e) {
            out.println("<p>Error: " + e.getMessage() + "</p>");
        }

        out.println("</div>");  
        out.println("</body>");
        out.println("</html>");
    }
}
