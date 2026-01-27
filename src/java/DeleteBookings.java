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

@WebServlet("/DeleteBookings")
public class DeleteBookings extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bookingId = request.getParameter("id");

        if (bookingId == null || bookingId.isEmpty()) {
            response.sendRedirect("MyBookings?error=invalidBookingId");
            return;
        }
        Connection con = null;
        PreparedStatement pstDeletePayments = null, pstDeleteBooking = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
            
            String deletePaymentsQuery = "DELETE FROM payments WHERE booking_id = ?";
            pstDeletePayments = con.prepareStatement(deletePaymentsQuery);
            pstDeletePayments.setString(1, bookingId);
            pstDeletePayments.executeUpdate();
            String deleteBookingQuery = "DELETE FROM bookings WHERE id = ?";
            pstDeleteBooking = con.prepareStatement(deleteBookingQuery);
            pstDeleteBooking.setString(1, bookingId);
            int rowsAffected = pstDeleteBooking.executeUpdate();
            if (rowsAffected > 0) {
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.println("<html><head><title>Booking Deleted</title></head><body>");
                out.println("<script type='text/javascript'>");
                out.println("alert('Booking deleted successfully. Please note: No refund policy applies.');");
                out.println("window.location.href = 'MyBookings';");
                out.println("</script>");
                out.println("</body></html>");
            } else {
                response.sendRedirect("MyBookings?error=bookingNotFound");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("MyBookings?error=" + e.getMessage());

        } finally {
            try { if (pstDeletePayments != null) pstDeletePayments.close(); } catch (Exception e) {}
            try { if (pstDeleteBooking != null) pstDeleteBooking.close(); } catch (Exception e) {}
            try { if (con != null) con.close(); } catch (Exception e) {}
        }
    }
}
