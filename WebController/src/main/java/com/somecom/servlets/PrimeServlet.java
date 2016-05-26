package com.somecom.servlets;

import com.somecom.dbc.DBControllerImp;
import com.somecom.parser.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
        List<Entry> entries = new ArrayList<>();

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html");

        String searchType = request.getParameter("searchType");
        String searchType2 = request.getParameter("searchType2");

        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<head>");
        out.println("<title>Project42</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<form method = POST>");
        out.println("<select name = searchType>");
        out.println("<option value = default selected><i>Select project</i></option>");
        out.println("<option value = all>All</option>");
        out.println("<option value  = 'project_1'>Project 1</option>");
        out.println("<option value = 'project_2'>Project 2</option>");
        out.println("</select>");
        out.println("<select name = searchType2>");
        out.println("<option value = default selected><i>Select keywords</i></option>");
        out.println("<option value = 'Уникальность данных'>Уникальность данных</option>");
        out.println("<option value = 'Безопасность'>Безопасность</option>");
        out.println("<option value = 'Логгирование'>Логгирование</option>");
        out.println("<option value = 'Аудит'>Аудит</option>");
        out.println("<option value = 'Аутентификация'>Аутентификация</option>");
        out.println("<option value = 'Данные'>Данные</option>");
        out.println("<option value = 'Траблшутинг'>Траблшутинг</option>");
        out.println("<option value = 'Логгирование и аудит'>Логгирование и аудит</option>");
        out.println("<option value = 'Данные, резервное копирование'>Данные, резервное копирование</option>");
        out.println("<option value = 'Безопасность, аутентификация'>Безопасность, аутентификация</option>");
        out.println("<option value = 'Логи'>Логи</option>");
        out.println("<option value = 'Учет входа в систему'>Учет входа в систему</option>");
        out.println("<option value = 'Факторы аутентификации'>Факторы аутентификации</option>");
        out.println("</select>");
        out.println("<input type = submit name = submit value = 'Search'/>");

        out.println("<br /><br />");
        out.println("<table border = 3>");
        out.println("<tr>" +
                "<th>Требования</th>" +
                "<th>Название требования</th>" +
                "<th>Комментарии</th>" +
                "<th>Выполняются ли требования</th>" +
                "<th>Проект</th>" +
                "</tr>");

        //Если не задан ни один критерий поиска
        if(searchType.equalsIgnoreCase("default") && searchType2.equalsIgnoreCase("default")){
            out.println("<p>Please specify your search criteria...</p>");
        }
        //Если задан критерий поиска по названию проекта...
        else  if(searchType2.equalsIgnoreCase("default")){
            //... ищем по всем проектам
            if(searchType.equalsIgnoreCase("all")){
                entries = controller.readAllFromDataBase(entries);
                if(!entries.isEmpty()){
                    for(Entry e : entries){
                        out.println("<tr>" +
                                "<td>"+e.getRequirement()+"</td>" +
                                "<td>"+e.getDescription()+"</td>" +
                                "<td>"+e.getComment()+"</td>" +
                                "<td>"+e.getResult()+"</td>" +
                                "<td>"+e.getProjectName()+"</td>" +
                                "</tr>");
                    }
                }
                else {
                    out.println("<br />No results found");
                }
            }
            //... ищем по указанному имени проекта
            else {
                entries = controller.readFromDataBaseByProjectName(entries, searchType);
                if(!entries.isEmpty()){
                    for(Entry e : entries){
                        out.println("<tr>" +
                                "<td>"+e.getRequirement()+"</td>" +
                                "<td>"+e.getDescription()+"</td>" +
                                "<td>"+e.getComment()+"</td>" +
                                "<td>"+e.getResult()+"</td>" +
                                "<td>"+e.getProjectName()+"</td>" +
                                "</tr>");
                 }
            }
                else
                {
                     out.println("<p>No results found!</p>");
                }
            }

            }
        //Если задан критерий поиска по ключевому слову
        else if(searchType.equalsIgnoreCase("default")){
            entries = controller.readFromDataBaseByRequirement(entries, searchType2);
                if(!entries.isEmpty()){
                    for(Entry e : entries){
                        out.println("<tr>" +
                                "<td>"+e.getRequirement()+"</td>" +
                                "<td>"+e.getDescription()+"</td>" +
                                "<td>"+e.getComment()+"</td>" +
                                "<td>"+e.getResult()+"</td>" +
                                "<td>"+e.getProjectName()+"</td>" +
                                "</tr>");
                    }
                }
                else {
                  out.println("<p>No results found!</p>");
                 }
            }
        //Если заданы оба критерия...
        else {
            //... ищем если в качестве первого критерия указан конкретный проект
            if(!searchType.equalsIgnoreCase("all")){
            entries = controller.readFromDataBaseByProjectNameAndRequirement(entries,searchType,searchType2);
                if(!entries.isEmpty()){
                    for(Entry e : entries){
                        out.println("<tr>" +
                                "<td>"+e.getRequirement()+"</td>" +
                                "<td>"+e.getDescription()+"</td>" +
                                "<td>"+e.getComment()+"</td>" +
                                "<td>"+e.getResult()+"</td>" +
                                "<td>"+e.getProjectName()+"</td>" +
                                "</tr>");
                    }
                }
                else{
                    out.println("<p>No results found!</p>");
                    out.println();
                }
            }
            //...ищем если в качестве первого критерия указаны все проекты
            if(searchType.equalsIgnoreCase("all")) {
                entries = controller.readFromDataBaseByRequirement(entries, searchType2);
                if(!entries.isEmpty()){
                    for(Entry e : entries){
                        out.println("<tr>" +
                                "<td>"+e.getRequirement()+"</td>" +
                                "<td>"+e.getDescription()+"</td>" +
                                "<td>"+e.getComment()+"</td>" +
                                "<td>"+e.getResult()+"</td>" +
                                "<td>"+e.getProjectName()+"</td>" +
                                "</tr>");
                    }
                }
                else {
                    out.println("<p>No results found!</p>");
                }
            }
        }
        out.println("</table>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");

controller.closeConnection();
    }
}
