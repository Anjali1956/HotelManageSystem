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

@WebServlet("/roomopt")
public class roomopt extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String successMessage = request.getParameter("success");
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Manage Rooms</title>");

        out.println("<style>");
        out.println("body{margin:0;font-family:Arial;background:#f4f4f9}");
        out.println(".navbar{position:fixed;top:0;left:0;width:100%;height:60px;background:#2c3e50;display:flex;align-items:center;z-index:1000}");
        out.println(".navbar h1{color:white;font-size:20px;margin:0 20px}");
        out.println(".navbar div{margin-left:auto;margin-right:20px}");
        out.println(".navbar a{color:white;text-decoration:none;margin-left:20px;font-size:15px}");
        out.println(".navbar a:hover{text-decoration:underline}");
        out.println(".container{width:80%;margin:100px auto 20px;background:white;padding:20px;border-radius:8px;box-shadow:0 2px 10px rgba(0,0,0,0.1)}");
        out.println("table{width:100%;border-collapse:collapse}");
        out.println("th,td{padding:10px;border:1px solid #ddd;text-align:center}");
        out.println("th{background:#2c3e50;color:white}");
        out.println(".delete-btn{background:red;color:white;border:none;padding:6px 10px;cursor:pointer}");
        out.println("</style>");

        out.println("</head>");
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

        if ("true".equals(successMessage)) {
            out.println("<script>alert('Room deleted successfully');</script>");
        }

        out.println("<div class='container'>");
        out.println("<h2 style='text-align:center'>All Rooms</h2>");
        out.println("<table>");
        out.println("<tr><th>Room Name</th><th>Price</th><th>Size</th><th>Bed Type</th><th>Max</th><th>Status</th><th>Action</th></tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
          Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM rooms");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getString("room_name") + "</td>");
                out.println("<td>" + rs.getBigDecimal("room_price") + "</td>");
                out.println("<td>" + rs.getString("room_size") + "</td>");
                out.println("<td>" + rs.getString("bed_type") + "</td>");
                out.println("<td>" + rs.getInt("max_occupancy") + "</td>");
                out.println("<td>" + rs.getString("status") + "</td>");
                out.println("<td>");
                out.println("<form method='post' action='roomopt'>");
                out.println("<input type='hidden' name='deleteRoom' value='" + rs.getString("room_name") + "'>");
                out.println("<button class='delete-btn'>Delete</button>");
                out.println("</form>");
                out.println("</td>");
                out.println("</tr>");
            }
            con.close();
        } catch (Exception e) {
            out.println("<tr><td colspan='7'>Error</td></tr>");
        }
        out.println("</table>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String deleteRoom = request.getParameter("deleteRoom");
        if (deleteRoom != null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");

                PreparedStatement ps = con.prepareStatement(
                        "DELETE FROM rooms WHERE room_name=?");
                ps.setString(1, deleteRoom);
                int rows = ps.executeUpdate();
                con.close();

                response.sendRedirect("roomopt?success=" + (rows > 0));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
