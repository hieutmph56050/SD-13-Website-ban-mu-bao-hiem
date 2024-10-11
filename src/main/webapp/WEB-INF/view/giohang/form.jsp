<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tạo Giỏ Hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/giohang/index/add" method="post">
    <div>
        Tài Khoản:
        <select name="idTaiKhoan">
            <c:forEach items="${listTaiKhoan}" var="taikhoan">
                <option value="${taikhoan.id}" label="${taikhoan.tenDangNhap}" ${giohang.idTaiKhoan.id == taikhoan.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <div>
        Trạng thái:
        <br>
        <input type="radio" name="tt" checked value="true"/>Còn Hoạt động
        <input type="radio" name="tt" value="false"/>Không hoạt động
    </div>
    <br>
    <button type="submit">Thêm</button>
</form>
</body>
</html>
