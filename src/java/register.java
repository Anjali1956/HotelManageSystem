
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class register extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Registration Form</title>");
        out.println("<link rel='stylesheet' type='text/css' href='register.css'>"); 
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='form-box-wrapper'>");
        out.println("<div class='form-box'>");
        out.println("<h2>Register</h2>");
        out.println("<form action='register' method='POST'>");
        out.println("<div class='form-group'>");
        out.println("<label for='name'>Full Name:</label>");
        out.println("<input type='text' name='name' required><br>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='username'>Username:</label>");
        out.println("<input type='text' name='username' required><br>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='email'>Email:</label>");
        out.println("<input type='email' name='email' required><br>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='phone'>Phone:</label>");
        out.println("<input type='text' name='phone'><br>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='address'>Address:</label>");
        out.println("<textarea name='address'></textarea><br>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='gender'>Gender:</label>");
        out.println("<select name='gender'>");
        out.println("<option value='Male'>Male</option>");
        out.println("<option value='Female'>Female</option>");
        out.println("</select><br>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' name='password' required><br>");
        out.println("</div>");
        out.println("<div class='form-group'>");
        out.println("<label for='confirmPassword'>Confirm Password:</label>");
        out.println("<input type='password' name='confirmPassword' required><br>");
        out.println("</div>");
        out.println("<button type='submit'>Register</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
      protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Get form parameters
        String name = request.getParameter("name");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String gender = request.getParameter("gender");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // Check passwords
        if (!password.equals(confirmPassword)) {
            out.println("<script>alert('Passwords do not match!'); window.location='register';</script>");
            return;
        }

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
           con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");

            // Check if username already exists
            ps = con.prepareStatement("SELECT * FROM user WHERE username = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            if (rs.next()) {
                out.println("<script>alert('Username already exists!'); window.location='register';</script>");
                return;
            }
            rs.close();
            ps.close();

            // Insert new user
            String sql = "INSERT INTO user (name, username, email, phone, address, gender, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, address);
            ps.setString(6, gender);
            ps.setString(7, password);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                out.println("<script>alert('Registration Successful!'); window.location='dashboard';</script>");
            } else {
                out.println("<script>alert('Registration Failed!'); window.location='register';</script>");
            }

        } catch (SQLException | ClassNotFoundException e) {
            out.println("<script>alert('Error: " + e.getMessage() + "'); window.history.back();</script>");
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Keep your doGet method the same for showing the form
}