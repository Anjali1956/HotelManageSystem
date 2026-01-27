import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Test extends HttpServlet {
      String URL = "jdbc:mysql://localhost:3306/student?useSSL=false";
      String USER = "root";
      String PASSWORD = "root75";  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
         
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            out.println("<h1>5251:-Anjali Adak</h1>");
            out.println("<h1>Database Connection Successful</h1>");
            conn.close();
        } catch (ClassNotFoundException e) {
            out.println("<h1>Error: JDBC Driver not found</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<h1>Error: Database Connection Failed</h1>");
            out.println("<p>" + e.getMessage() + "</p>");
        }
        
        out.println("</body>");
        out.println("</html>");
    }
}
