import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminLogin")
public class AdminLogin extends HttpServlet {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";  

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            response.sendRedirect("http://localhost:8080/HotelManageSytem/Adashboard");
        } else {
            displayLoginForm(response, "Invalid username or password");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        displayLoginForm(response, null);
    }
    private void displayLoginForm(HttpServletResponse response, String errorMessage) throws IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
    out.println("<title>Admin Login</title>");
    out.println("<style>");
    out.println("body {");
    out.println("  font-family: Arial, sans-serif;");
    out.println("  margin: 0;");
    out.println("  padding: 0;");
    out.println("  height: 100vh;");
    out.println("  display: flex;");
    out.println("  justify-content: center;");
    out.println("  align-items: center;");
    out.println("  background: url('./images/g2.jpg') no-repeat center center fixed;");
    out.println("  background-size: cover;");
    out.println("}");
    out.println(".container {");
    out.println("  background-color: rgba(255, 255, 255, 0.9);"); 
    out.println("  padding: 30px;");
    out.println("  border-radius: 10px;");
    out.println("  width: 350px;");
    out.println("  text-align: center;");
    out.println("  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);");
    out.println("}");
    out.println(".container h1 { margin-bottom: 20px; color: #333; }");
    out.println(".container input { width: 100%; padding: 10px; margin: 10px 0; border-radius: 5px; border: 1px solid #ccc; }");
    out.println(".container button { width: 100%; padding: 10px; border: none; border-radius: 5px; background-color: #4CAF50; color: white; font-size: 16px; cursor: pointer; }");
    out.println(".container button:hover { background-color: #45a049; }");
    out.println(".error { color: red; font-size: 14px; margin-top: 10px; }");
    out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='container'>");
    out.println("<h1>Admin Login</h1>");
    out.println("<form action='AdminLogin' method='post'>");
    out.println("<input type='text' name='username' placeholder='Enter Username' required>");
    out.println("<input type='password' name='password' placeholder='Enter Password' required>");
    out.println("<button type='submit'>Login</button>");
    out.println("</form>");
    if (errorMessage != null) {
        out.println("<p class='error'>" + errorMessage + "</p>");
    }
    out.println("</div>");
    out.println("</body>");
    out.println("</html>");
}
}