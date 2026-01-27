import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@WebServlet("/Booking")
public class Booking extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String roomName = request.getParameter("roomName");
        String price = request.getParameter("price");
        if (roomName == null || roomName.isEmpty() || price == null || price.isEmpty()) {
            out.println("<p style='color:red;'>Room details are missing!</p>");
            return;
        }
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Booking Form</title>");
        out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/Booking.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='booking-form'>");
        out.println("<h1>Booking Form</h1>");
        out.println("<form action='Booking' method='post'>");
        out.println("<p><strong>Room:</strong> " + roomName + "</p>");
        out.println("<p><strong>Price per night:</strong> $" + price + "</p>");
        out.println("<input type='hidden' name='roomName' value='" + roomName + "'>");
        out.println("<input type='hidden' name='price' value='" + price + "'>");
        out.println("<label for='name'>Full Name:</label>");
        out.println("<input type='text' id='name' name='name' required><br>");
        out.println("<label for='email'>Email Address:</label>");
        out.println("<input type='email' id='email' name='email' required><br>");
        out.println("<label for='phone'>Phone Number:</label>");
        out.println("<input type='text' id='phone' name='phone' required><br>");
        out.println("<label for='gender'>Gender:</label>");
        out.println("<select id='gender' name='gender' required>");
        out.println("<option value='Male'>Male</option>");
        out.println("<option value='Female'>Female</option>");
        out.println("<option value='Other'>Other</option>");
        out.println("</select><br>");
        out.println("<label for='idType'>ID Type:</label>");
        out.println("<select id='idType' name='idType' required>");
        out.println("<option value='Passport'>Passport</option>");
        out.println("<option value='Driving License'>Driving License</option>");
        out.println("<option value='Aadhar Card'>Aadhar Card</option>");
        out.println("<option value='Other'>Other</option>");
        out.println("</select><br>");
        out.println("<label for='checkin'>Check-in Date:</label>");
        out.println("<input type='date' id='checkin' name='checkin' required><br>");
        out.println("<label for='checkout'>Check-out Date:</label>");
        out.println("<input type='date' id='checkout' name='checkout' required><br>");
        out.println("<label for='specialRequests'>Special Requests:</label>");
        out.println("<textarea id='specialRequests' name='specialRequests'></textarea><br>");
        out.println("<label for='bookingDate'>Booking Date:</label>");
        out.println("<input type='date' id='bookingDate' name='bookingDate' value='" + LocalDate.now() + "' readonly><br>");
        out.println("<button type='submit'>Confirm Booking</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String roomName = request.getParameter("roomName");
        String price = request.getParameter("price");
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String gender = request.getParameter("gender");
        String idType = request.getParameter("idType");
        String bookingDate = request.getParameter("bookingDate");
        String specialRequests = request.getParameter("specialRequests");
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            out.println("<script>alert('Please log in first!'); window.location.href='Login';</script>");
            return;
        }
        if (roomName == null || price == null || name == null || email == null || phone == null || checkin == null || checkout == null || gender == null || idType == null || bookingDate == null) {
            out.println("<script>alert('Please fill all required fields!'); window.history.back();</script>");
            return;
        }
        Connection con = null;
        PreparedStatement ps = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");

            String sql = "INSERT INTO bookings (room_name, price, name, email, phone, checkin, checkout, gender, id_type, booking_date, special_requests, username, status, message) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, 'Pending', '')";
            ps = con.prepareStatement(sql);
            ps.setString(1, roomName);
            ps.setBigDecimal(2, new java.math.BigDecimal(price));
            ps.setString(3, name);
            ps.setString(4, email);
            ps.setString(5, phone);
            ps.setString(6, checkin);
            ps.setString(7, checkout);
            ps.setString(8, gender);
            ps.setString(9, idType);
            ps.setString(10, bookingDate);
            ps.setString(11, specialRequests);
            ps.setString(12, username);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                out.println("<script>alert('Room booked successfully!'); window.location.href='dashboard';</script>");
            } else {
                out.println("<script>alert('Booking failed!'); window.history.back();</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.history.back();</script>");
        } finally {
            try { if (ps != null) ps.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}
