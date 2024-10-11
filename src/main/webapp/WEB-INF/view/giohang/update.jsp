<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/giohang/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${giohang.id}" readonly/></div>
    <br>
<%--    <div>--%>
<%--        Tài khoản:--%>
<%--        <select name="idTaiKhoan">--%>
<%--            <c:forEach items="${listTaiKhoan}" var="taikhoan">--%>
<%--                <option value="${taikhoan.id}" label="${taikhoan.tenDangNhap}" ${giohang.idTaiKhoan.id == taikhoan.id ? "selected" : ""}></option>--%>
<%--            </c:forEach>--%>
<%--        </select>--%>
<%--    </div>--%>
    <div>Tài khoản: <input type="text" name="idTaiKhoan.tenDangNhap" value="${giohang.idTaiKhoan.tenDangNhap}" readonly/></div>
    <br>
    <div>
        Trạng thái:
        <br>
        <input type="radio" name="tt" ${giohang.tt ? "checked" : ""} value="true"/>Hoạt động
        <input type="radio" name="tt" ${giohang.tt ? "" : "checked"} value="false"/>Không hoạt động
        <br>
    </div>

    <button>Sửa</button>
</form>
</body>
</html>
