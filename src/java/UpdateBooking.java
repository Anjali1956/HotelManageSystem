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

@WebServlet("/UpdateBooking")
public class UpdateBooking extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
          
            if (id != null) {
                String sql = "SELECT * FROM bookings WHERE id = ?";
                PreparedStatement pst = con.prepareStatement(sql);
                pst.setString(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    out.println("<html><head><title>Update Booking</title>");
                    out.println("<link rel='stylesheet' href='update.css'></head><body>");
                    out.println("<div class='container'>");
                    out.println("<h2>Update Booking Details</h2>");
                    out.println("<form action='UpdateBooking' method='post'>");

                    out.println("<label for='id'><b>BOOKING ID:</b></label>");
                    out.println("<input type='text' id='id' name='id' value='" + id + "' readonly><br><br>");
                    out.println("<label for='name'><b>NAME:</b></label>");
                    out.println("<input type='text' id='name' name='name' value='" + rs.getString("name") + "' required><br><br>");
                    out.println("<label for='gender'><b>GENDER:</b></label>");
                    out.println("<input type='text' id='gender' name='gender' value='" + rs.getString("gender") + "' required><br><br>");

                    out.println("<label for='room_name'><b>ROOM NAME:</b></label>");
                    out.println("<input type='text' id='room_name' name='room_name' value='" + rs.getString("room_name") + "' required><br><br>");

                    out.println("<label for='price'><b>PRICE:</b></label>");
                    out.println("<input type='text' id='price' name='price' value='" + rs.getString("price") + "' required><br><br>");

                    out.println("<label for='checkin'><b>CHECK_IN:</b></label>");
                    out.println("<input type='date' id='checkin' name='checkin' value='" + rs.getString("checkin") + "' required><br><br>");

                    out.println("<label for='checkout'><b>CHECK-OUT:</b></label>");
                    out.println("<input type='date' id='checkout' name='checkout' value='" + rs.getString("checkout") + "' required><br><br>");

                    out.println("<label for='special_requests'><b>SPECIAL REQUESTS:</b></label>");
                    out.println("<input type='text' id='special_requests' name='special_requests' value='" + rs.getString("special_requests") + "'><br><br>");

                    out.println("<input type='submit' value='UPDATE' class='submit-btn'>");
                    out.println("</form>");
                    out.println("</div></body></html>");
                } else {
                    out.println("<h3>No booking found for the given ID.</h3>");
                }
            } else {
                out.println("<h3>No ID provided.</h3>");
            }
            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String roomName = request.getParameter("room_name");
        String price = request.getParameter("price");
        String checkin = request.getParameter("checkin");
        String checkout = request.getParameter("checkout");
        String specialRequests = request.getParameter("special_requests");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
          
            if (id != null && !id.isEmpty()) {
                String updateSql = "UPDATE bookings SET name=?, gender=?, room_name=?, price=?, checkin=?, checkout=?, special_requests=? WHERE id=?";
                PreparedStatement pstUpdate = con.prepareStatement(updateSql);
                pstUpdate.setString(1, name);
                pstUpdate.setString(2, gender);
                pstUpdate.setString(3, roomName);
                pstUpdate.setString(4, price);
                pstUpdate.setString(5, checkin);
                pstUpdate.setString(6, checkout);
                pstUpdate.setString(7, specialRequests);
                pstUpdate.setString(8, id);
                int rowsAffected = pstUpdate.executeUpdate();
                if (rowsAffected > 0) {
                    out.println("<html><head><title>Booking Update</title>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Booking details updated successfully.');");
                    out.println("window.location.href = 'MyBookings'");
                    out.println("</script></head><body></body></html>");
                } else {
                    out.println("<html><head><title>Booking Update</title>");
                    out.println("<script type='text/javascript'>");
                    out.println("alert('Failed to update booking. Please try again.');");
                    out.println("window.location.href = 'UpdateBooking?id=" + id + "';");
                    out.println("</script></head><body></body></html>");
                }
            } else {
                out.println("<h3>Invalid booking ID.</h3>");
            }
            con.close();
        } catch (Exception e) {
            out.println("<h3>Error: " + e.getMessage() + "</h3>");
        }
    }
}
