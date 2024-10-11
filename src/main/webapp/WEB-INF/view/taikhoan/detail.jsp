<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết tài khoản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${taikhoan.id}</div>
<br>
<div>Vai trò: ${taikhoan.idVaiTro.ten} </div>
<br>
<div>Tên đăng nhập: ${taikhoan.tenDangNhap}</div>
<br>
<div>Mật khẩu: ${taikhoan.matKhau}</div>
<br>
<div>Họ tên: ${taikhoan.ten}</div>
<br>
<div>Ngày sinh: ${taikhoan.ngaySinh}</div>
<br>
<div>Giới tính: ${taikhoan.gioiTinh ? "Nam" : "Nữ"}</div>
<br>
<div>Số điện thoại: ${taikhoan.sdt}</div>
<br>
<div>Email: ${taikhoan.email}</div>
<br>
<div>Căn cước công dân: ${taikhoan.cccd}</div>
<br>
<div>Avatar: ${taikhoan.avatar}</div>
<br>
<div>Ngày tạo: ${taikhoan.ngayTao}</div>
<br>
<div>Ngày cập nhật: ${taikhoan.ngayCapNhat}</div>
<br>
<div>Trạng thái: ${taikhoan.tt ? "Hoạt động" : "Không hoạt động"}</div>
<br>
</body>
</html>
