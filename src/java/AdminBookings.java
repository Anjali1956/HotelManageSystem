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

@WebServlet("/AdminBookings")
public class AdminBookings extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String successMessage = request.getParameter("success");

        out.println("<html>");
        out.println("<head>");
        out.println("<title>Confirmed Bookings</title>");
        out.println("<style>");

        out.println(".navbar{position:fixed;top:0;left:0;width:100%;height:60px;background-color:#2c3e50;display:flex;align-items:center;z-index:1000}");
        out.println(".navbar h1{color:white;font-size:20px;margin:0 30px 0 20px}");
        out.println(".navbar a{color:white;text-decoration:none;margin-right:20px;font-size:15px}");
        out.println(".navbar a:first-of-type{margin-left:auto}");

        out.println(".navbar a:hover{color:#1abc9c}");
        out.println("body{margin-top:80px;font-family:Arial,sans-serif;background-color:#f4f4f9}");

        out.println(".container{width:85%;margin:20px auto;padding:20px;background:white;border-radius:8px;box-shadow:0 2px 10px rgba(0,0,0,0.1)}");
        out.println("h2{text-align:center;color:#333}");
        out.println("table{width:100%;border-collapse:collapse;margin-top:20px}");
        out.println("th,td{border:1px solid #ddd;padding:10px;text-align:center}");
        out.println("th{background:#4CAF50;color:white}");
        out.println("td{background:#f9f9f9}");
        out.println("button{padding:6px 12px;background:#e74c3c;color:white;border:none;border-radius:4px;cursor:pointer}");
        out.println("button:hover{background:#c0392b}");

        out.println("</style>");
        out.println("</head>");
        out.println("<body>");

        out.println("<div class='navbar'>");
        out.println("<h1>Admin Dashboard</h1>");
        out.println("<a href='Adashboard'>Dashboard</a>");
        out.println("<a href='manageRoom'>Add Room</a>");
        out.println("<a href='AdminBookings'>Bookings</a>");
        out.println("<a href='roomopt'>Manage Rooms</a>");
        out.println("</div>");

        out.println("<div class='container'>");

        if ("true".equals(successMessage)) {
            out.println("<script>alert('Booking deleted successfully');</script>");
        } else if ("false".equals(successMessage)) {
            out.println("<script>alert('Failed to delete booking');</script>");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");

            String sql = "SELECT id,room_name,price,checkin,checkout,special_requests,name,gender,booking_date,status FROM bookings WHERE status='Confirmed'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            out.println("<h2>Confirmed Bookings</h2>");
            out.println("<table>");
            out.println("<tr><th>ID</th><th>Name</th><th>Gender</th><th>Room</th><th>Price</th><th>Check-in</th><th>Check-out</th><th>Date</th><th>Requests</th><th>Status</th><th>Action</th></tr>");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("id") + "</td>");
                out.println("<td>" + rs.getString("name") + "</td>");
                out.println("<td>" + rs.getString("gender") + "</td>");
                out.println("<td>" + rs.getString("room_name") + "</td>");
                out.println("<td>" + rs.getString("price") + "</td>");
                out.println("<td>" + rs.getString("checkin") + "</td>");
                out.println("<td>" + rs.getString("checkout") + "</td>");
                out.println("<td>" + rs.getString("booking_date") + "</td>");
                out.println("<td>" + rs.getString("special_requests") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("<td>");
                out.println("<form method='post' action='AdminBookings'>");
                out.println("<input type='hidden' name='bookingId' value='" + rs.getString("id") + "'>");
                out.println("<button type='submit'>Delete</button>");
                out.println("</form>");
                out.println("</td>");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookingId = request.getParameter("bookingId");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
 
       
            PreparedStatement ps1 = con.prepareStatement("DELETE FROM payments WHERE booking_id=?");
            ps1.setString(1, bookingId);
            ps1.executeUpdate();

            PreparedStatement ps2 = con.prepareStatement("DELETE FROM bookings WHERE id=?");
            ps2.setString(1, bookingId);
            int rows = ps2.executeUpdate();

            con.close();

            if (rows > 0)
                response.sendRedirect("AdminBookings?success=true");
            else
                response.sendRedirect("AdminBookings?success=false");

        } catch (Exception e) {
            response.sendRedirect("AdminBookings?success=false");
        }
    }
}
