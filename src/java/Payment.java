import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;
import java.time.LocalDate;
@WebServlet("/Payment")
public class Payment extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");
        if (id == null || id.isEmpty()) {
            out.println("<p style='color:red;'>Payment details are missing! Please go back and try again.</p>");
            return;  }
        String totalAmount = "";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
           
           String sql = "SELECT price, checkin, checkout FROM bookings WHERE id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                double pricePerNight = rs.getDouble("price");
                LocalDate checkinDate = LocalDate.parse(rs.getString("checkin"));
                LocalDate checkoutDate = LocalDate.parse(rs.getString("checkout"));
                long numberOfNights = checkoutDate.getDayOfYear() - checkinDate.getDayOfYear();
if (numberOfNights < 1) {
    numberOfNights = 1; 
}       totalAmount = String.format("%.2f", pricePerNight * numberOfNights);
            }
            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error retrieving booking details: " + e.getMessage() + "</p>");
            return;  
                    }
        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>Payment</title>");
        out.println("<link rel='stylesheet' type='text/css' href='payment.css'>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='payment-form'>");
        out.println("<h1>Payment Form</h1>");
        out.println("<div class='form-group'>");
        out.println("<label for='paymentId'><strong>Payment ID:</strong></label>");
        out.println("<input type='text' id='paymentId' name='paymentId' value='" + id + "' readonly>");
        out.println("</div>");       
        out.println("<div class='form-group'>");
        out.println("<label for='totalAmount'><strong>Amount to Pay:</strong></label>");
        out.println("<input type='text' id='totalAmount' name='totalAmount' value='" + totalAmount + "' readonly>");
        out.println("</div>");
        out.println("<form action='Payment' method='post'>");
        out.println("<input type='hidden' name='id' value='" + id + "'>");
        out.println("<input type='hidden' name='amount' value='" + totalAmount + "'>");
        out.println("<div class='form-group'>");
        out.println("<label for='cardNumber'>Card Number:</label>");
        out.println("<input type='text' id='cardNumber' name='cardNumber' required>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='expiryDate'>Expiry Date:</label>");
        out.println("<input type='month' id='expiryDate' name='expiryDate' required>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='cvv'>CVV:</label>");
        out.println("<input type='text' id='cvv' name='cvv' required>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='billingAddress'>Billing Address:</label>");
        out.println("<input type='text' id='billingAddress' name='billingAddress' required>");
        out.println("</div>");
        out.println("<button type='submit'>Pay Now</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    String id = request.getParameter("id");
    String amount = request.getParameter("amount");
    String cardNumber = request.getParameter("cardNumber");
    String expiryDate = request.getParameter("expiryDate");
    String cvv = request.getParameter("cvv");
    String billingAddress = request.getParameter("billingAddress");
    if (id == null || amount == null || cardNumber == null || expiryDate == null || cvv == null || billingAddress == null ||
        id.isEmpty() || amount.isEmpty() || cardNumber.isEmpty() || expiryDate.isEmpty() || cvv.isEmpty() || billingAddress.isEmpty()) {
        out.println("<script type='text/javascript'>");
        out.println("alert('Please fill in all payment details!');");
        out.println("window.history.back();");
        out.println("</script>");
        return;    }
    Connection con = null;
    PreparedStatement pst = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
        
        String insertPaymentSQL = "INSERT INTO payments (booking_id, amount, card_number, expiry_date, cvv, billing_address) VALUES (?, ?, ?, ?, ?, ?)";
        pst = con.prepareStatement(insertPaymentSQL);
        pst.setString(1, id);
        pst.setString(2, amount);
        pst.setString(3, cardNumber);
        pst.setString(4, expiryDate);
        pst.setString(5, cvv);
        pst.setString(6, billingAddress);
        int paymentInserted = pst.executeUpdate();
        if (paymentInserted > 0) {
            String updateBookingSQL = "UPDATE bookings SET status = 'Confirmed' WHERE id = ?";
            pst = con.prepareStatement(updateBookingSQL);
            pst.setString(1, id);
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                out.println("<script type='text/javascript'>");
                out.println("alert('Payment successful!');");
                out.println("window.location.href='MyBookings';");  
                out.println("</script>");
            } else {
                out.println("<script type='text/javascript'>");
                out.println("alert('Payment recorded, but booking status update failed.');");
                out.println("window.location.href='MyBookings';");  
                out.println("</script>");   }
        } else {
            out.println("<script type='text/javascript'>");
            out.println("alert('Payment failed! Please try again.');");
            out.println("window.history.back();");
            out.println("</script>");       }
    } catch (Exception e) {
        out.println("<script type='text/javascript'>");
        out.println("alert('Error: " + e.getMessage() + "');");
        out.println("window.history.back();");
        out.println("</script>");
    } finally {
        try {
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (Exception ignored) {}  }}}
