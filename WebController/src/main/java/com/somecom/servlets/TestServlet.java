package com.somecom.servlets;

import com.somecom.dbc.DBControllerImp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Cannon on 22.05.2016.
 */
public class TestServlet extends HttpServlet {

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
        session.setAttribute("submit", submit);
        session.setAttribute("connection", connection);
        session.setAttribute("controller", controller);

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<head>");
        out.println("<title> </title>");
        out.println("</head>");
        out.println("<form>");
        out.println("<input type = submit value = 'submit'>Push</button>");
        out.println("</form>");
        out.println("<p>connection</p>"+connection);
        out.println("</body>");
        out.println("</html>");

        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
}
