import java.io.*;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletExample extends HttpServlet {

public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
        PrintWriter out = response.getWriter();

        out.print("<html><body><table>");

       // String resultSet;
		// ... pseudocode
       // while (resultSet.next()) { 
            out.print("<tr><td>");
            out.print("hi");   
            out.print("</td></tr>");

     //   } 	


        out.print("</table></body></html>");
  }
}