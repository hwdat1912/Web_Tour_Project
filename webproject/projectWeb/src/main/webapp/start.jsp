<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 12/9/2023
  Time: 2:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--    <jsp:forward page="<%=request.getContextPath()%>/user/views/home"></jsp:forward>--%>
    <%

        response.sendRedirect(request.getContextPath()+"/user/views/home");
    %>


</body>
</html>
