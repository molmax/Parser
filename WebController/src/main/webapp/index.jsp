<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Project search</h2>
<form action = ${pageContext.servletContext.contextPath}/PrimeServlet method="post" >
    <select name = 'project'>
        <option value = 'default' />
        <option value = 'all'> all Projects</option>
        <option value = 'project 1'> Project 1 </option>
        <option value = 'project 2'> Project 2 </option>
    </select>
    <input type='submit' value = 'Search'>
</form>
</body>
</html>
