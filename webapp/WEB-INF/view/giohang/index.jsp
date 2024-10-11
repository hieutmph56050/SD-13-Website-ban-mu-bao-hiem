<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Giỏ hàng</h2>
    <div><button><a href="/giohang/index/add">Tạo Giỏ hàng người dùng</a></button></div>
    <table class="table">
        <tr>
            <th>STT</th>
            <th>Tên Đăng Nhập</th>
            <th>Tên Người Dùng</th>
            <th>Vai Trò</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th>Trạng Thái</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="giohang" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${giohang.idTaiKhoan.tenDangNhap}</td>
                <td>${giohang.idTaiKhoan.ten}</td>
                <td>${giohang.idTaiKhoan.idVaiTro.ten}</td>
                <td>${giohang.ngayTao}</td>
                <td>${giohang.ngayCapNhat}</td>
                <td>${giohang.tt == true ? "Hoạt động" : "Ngừng hoạt động"}</td>
                <td colspan="3">
                    <a href="/giohang/index/detail/${giohang.id}">Xem</a>
                    <a href="/giohang/index/update/${giohang.id}">Sửa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
