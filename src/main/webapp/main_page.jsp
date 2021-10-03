<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.example.java_lab_3.models.FileModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File system</title>
</head>
<body>
    <a href="logout">Log out</a><br>

    <h3>
        ${date_now}
    </h3>
    <h1>
        ${pathToken} <br>
    </h1>
    <div> <%
        String path = (String) request.getAttribute("path");
        List<FileModel> list = (List<FileModel>) request.getAttribute("dirs");
        boolean isRoot = false;
        String prev = "";
        if (path != null) {
            isRoot = path.split("/").length < 2;
            prev = !isRoot ? "?path=" + String.join("/", Arrays.copyOfRange(
                path.split("/"), 0, path.split("/").length - 1
            )) : "";
        } %>
        Files count = <%=list.size()%> <br> <%
            if (path != null)
                if (!isRoot) { %>
                    <a href="<%=prev%> ">Up (..)</a>
                    <br> <%
                }
                else { %>
                    <p>You are in a root directory</p> <%
                }  %> <%
            for (FileModel directory : list) {
                String url = String.format("?path=%s/%s", path, directory.getPath()); %>
                <span> <%
                    if (directory.isDir()) { %>
                        <a href="<%=url%>"> <%
                    } %>
                    <%=directory.getPath()%> <%
                    String formatted = "download/" + url;
                    if (!directory.isDir()) { %> <a href="<%=formatted%>">Download</a> <% }
                    if (directory.isDir()) { %> </a> <% } %>
                </span> <br> <%
            } %>
    </div>
</body>
</html>
