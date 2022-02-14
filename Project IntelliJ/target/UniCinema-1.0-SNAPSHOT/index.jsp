<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <jsp:include page="views/partial/head.jsp">
        <jsp:param name="styles" value="bootstrap"/>
    </jsp:include>
</head>
<body>
<% response.sendRedirect(application.getContextPath()+"/pages");%> <!--richiama la request di /pages -->

</body>
</html>