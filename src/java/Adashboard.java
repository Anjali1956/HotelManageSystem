import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet("/Adashboard")
public class Adashboard extends HttpServlet {
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    response.setContentType("text/html");
    PrintWriter out = response.getWriter();    
    out.println("<!DOCTYPE html>");
    out.println("<html lang='en'>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
    out.println("<title>Admin Dashboard</title>");
    out.println("<style>");  
    out.println("body { font-family: Arial, sans-serif; background: linear-gradient(135deg, #f5f7fa, #c3cfe2); margin: 0; padding: 0; display: flex; flex-direction: column; align-items: center; height: 100vh; }");

    out.println(".navbar { position: absolute; top: 0; width: 100%; background-color: #333; color: white; padding: 15px; display: flex; justify-content: space-between; align-items: center; }");
    out.println(".navbar h1 { margin: 0; font-size: 20px; }");
    out.println(".navbar a { color: white; text-decoration: none; margin: 0 15px; font-size: 16px; }");
    out.println(".navbar a:hover { text-decoration: underline; }"); 
    out.println(".title { font-size: 28px; margin-bottom: 20px; color: black !important; text-align: center; }");
    out.println(".container { display: flex; flex-direction: column; align-items: center; justify-content: center; margin-top: 80px; gap: 20px; }");
    out.println(".title { font-size: 28px; margin-bottom: 20px; color: white; text-align: center; }");
    out.println(".card { background: white; border-radius: 12px; box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2); padding: 20px; width: 300px; text-align: center; }");
    out.println(".card h3 { margin: 0 0 10px; font-size: 20px; color: #333; }");
    out.println(".btn { display: inline-block; background-color: #007bff; color: white; padding: 12px 18px; border-radius: 8px; text-decoration: none; font-weight: bold; transition: 0.3s; }");
    out.println(".btn:hover { background-color: #0056b3; }");
   out.println("</style>");
    out.println("</head>");
    out.println("<body>");
    out.println("<div class='navbar'>");
    out.println("<h1>Admin Dashboard</h1>");
    out.println("<div>");
    out.println("<a href='http://localhost:8080/HotelManageSytem/manageRoom'>Add Room</a>");
    out.println("<a href='http://localhost:8080/HotelManageSytem/AdminBookings'>View Bookings</a>");
    out.println("<a href='http://localhost:8080/HotelManageSytem/roomopt'>Manage Room</a>");
     out.println("<a href='http://localhost:8080/HotelManageSytem/Home'>Logout</a>");
    out.println("</div>");
    out.println("</div>");
    out.println("<div class='container'>");
    out.println("<h2 class='title'>Welcome, Admin!</h2>");
    out.println("<div class='card'>");
    out.println("<h3>Add Rooms</h3>");
    out.println("<a href='http://localhost:8080/HotelManageSytem/manageRoom' class='btn'>Add Rooms</a>");
    out.println("</div>");
    out.println("<div class='card'>");
    out.println("<h3>View Bookings</h3>");
    out.println("<a href='http://localhost:8080/HotelManageSytem/AdminBookings' class='btn'>Check Bookings</a>");
    out.println("</div>");
    out.println("<div class='card'>");
    out.println("<h3>Manage Room</h3>");
    out.println("<a href='http://localhost:8080/HotelManageSytem/roomopt' class='btn'>Manage Room</a>");
    out.println("</div>");
    out.println("</div>"); 
    out.println("</body>");
    out.println("</html>");
   }
}
