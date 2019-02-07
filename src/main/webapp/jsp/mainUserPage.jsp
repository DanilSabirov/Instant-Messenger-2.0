<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: danil
  Date: 24.08.18
  Time: 17:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="resources/stylesheet/main.css">
</head>
<body>
<section>
    <p>Dialogs</p>
    <div>
        <c:forEach var="dialog" items="${dialogs}">
            <p><c:out value="${dialgo.name}"/></p>
        </c:forEach>
    </div>
</section>
</body>
</html>
