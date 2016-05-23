<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<h2>Nobody knows that I'm Batman!</h2>
<form action = ${pageContext.servletContext.contextPath}/TestServlet method="post" >
    <table><tr><td>${items='controller' var = 'c'}</td></tr></table>
<form>
<p>${c}</p>
<p></p>
</body>
</html>
