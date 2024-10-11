<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${giohang.id}</div>
<br>
<div>Tài khoản: ${giohang.idTaiKhoan.tenDangNhap}</div>
<br/>
<div>Tên người dùng: ${giohang.idTaiKhoan.ten}</div>
<br/>
<div>Vai trò: ${giohang.idTaiKhoan.idVaiTro.ten}</div>
<br/>
<div>Trạng thái: ${giohang.tt ? "Hoạt động" : "Không hoạt động"}</div>
<br/>
<div>Ngày Tạo: ${giohang.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${giohang.ngayCapNhat}</div>
<br/>
</body>
</html>
