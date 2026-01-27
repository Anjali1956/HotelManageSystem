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
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String error = request.getParameter("error");

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Login</title>");
        out.println("<link rel='stylesheet' type='text/css' href='" + request.getContextPath() + "/login.css'>");

        out.println("<script>");
        out.println("function showAlert(message) {");
        out.println("  alert(message);");
        out.println("}");
        out.println("</script>");
        out.println("</head>");
        out.println("<body>");
        if ("notregistered".equals(error)) {
        out.println("<script>showAlert('User not found! Please register first.');</script>");
        }
        out.println("<div class='background-container'>");
        out.println("<div class='login-container'>");
        out.println("<h2>Login</h2>");
        out.println("<form action='Login' method='POST'>");
        out.println("<label for='username'>Username:</label>");
        out.println("<input type='text' name='username' required><br>");
        out.println("<label for='password'>Password:</label>");
        out.println("<input type='password' name='password' required><br>");
        out.println("<button type='submit'>Login</button>");
        out.println("</form>");
        out.println("<p>Don't have an account? <a href='register'>Register here</a></p>");
        out.println("</div>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                   "jdbc:mysql://tramway.proxy.rlwy.net:15642/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true",
                    "root", "hkUDBZINkEHiAfkVUhQDeiDdgsbueMdZ");
           
            String query = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                response.sendRedirect("dashboard");
            } else {
                response.sendRedirect("Login?error=notregistered");
            }
            con.close();
        } catch (Exception e) {
            response.sendRedirect("Login?error=server");
        }
    }
}
