<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%@ page import="com.example.java_lab_3.models.FileModel" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h3>
        ${date_now}
    </h3>
    <h1>
        ${pathToken} <br>
    </h1>
    <div>
        <%
            String path = (String) request.getAttribute("path");
            List<FileModel> list = (List<FileModel>) request.getAttribute("dirs");
            boolean isRoot = path.split("/").length < 2;
            String prev = !isRoot ?
                    "?path=" + String.join("/", Arrays.copyOfRange(
                            path.split("/"), 0, path.split("/").length - 1
                    )) : "";
        %>
        Dirs count =
        <%=list.size()%> <br>
        <%
            if (!isRoot) {
        %>
            <a href="<%=prev%> ">Up (..)</a>
        <%
            }
            else {
        %>
            <p>You are in a root directory</p>
        <%
            }
        %>
        <br>
        <%
            for (FileModel directory : list) {
                String url = String.format("?path=%s/%s", path, directory.getPath());
        %>
                <span>
                    <%
                        if (directory.isDir()) {
                    %>
                        <a href="<%=url%>">
                    <%
                        }
                    %>
                        <%=directory.getPath()%>
                        <%
                            String formatted = "http://localhost:8080/java_lab_war_exloded" + url.split("path=/")[1];
                            if (!directory.isDir()) {
                        %>
                                <a href="<%=formatted%>" download>Download</a>
                        <%
                            }
                        %>
                    <%
                        if (directory.isDir()) {
                    %>
                        </a>
                    <%
                        }
                    %>
                </span> <br>
        <%
            }
        %>
    </div>

</body>
</html>
