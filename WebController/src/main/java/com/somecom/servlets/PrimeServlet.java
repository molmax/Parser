package com.somecom.servlets;


import com.somecom.dbc.DBControllerImp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;


/**
 * Created by Cannon on 17.05.2016.
 */
public class PrimeServlet  extends HttpServlet {

    @Override
    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DBControllerImp controller = new DBControllerImp();
        Connection connection = controller.getConnection();

        HttpSession session = request.getSession();

        String submit = request.getParameter("submit");
        String project = request.getParameter("project");
        session.setAttribute("submit", submit);
        session.setAttribute("connection", connection);
        session.setAttribute("controller", controller);

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<head>");
        out.println("<title> </title>");
        out.println("</head>");
        out.println("<body>");
        out.println(
                "<form method = 'POST'>" +
                "<select name = 'project'>" +
                "<option value = 'default' />" +
                "<option value = 'all'> all Projects</option> " +
                "<option value = 'project 1'> Project 1 </option> " +
                "<option value = 'project 2'> Project 2 </option> " +
                "</select>  " +
                "<input type='submit' value = 'Search'>" +
                "</form> "
        );

        if(project.equalsIgnoreCase("project 1")){
            out.println("<p>Первый проект</p>");
        }
        else if(project.equalsIgnoreCase("project 2")){
            out.println("<p>Второй проект</p>");
        }
        else if (project.equalsIgnoreCase("all")){
            out.println("<p>Все проекты</p>");
        }
        else {
            out.println("<p>Проект не выбран</p>");
        }

        out.println("</body>");
        out.println("</html>");

        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
}
