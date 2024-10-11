<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Tài Khoản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
        <h2 align="center">Tài Khoản</h2>
        <div><button><a href="/taikhoan/index/add">Tạo Tài Khoản</a></button></div>
        <br>
    <table class="table">
        <tr>
            <th>STT</th>
           <th>Tên đăng nhập</th>
            <th>Mật khẩu</th>
            <th>Vai trò</th>
            <th>Họ tên</th>
            <th>Giới tính</th>
            <th>Ngày sinh</th>
            <th>Số điện thoại</th>
            <th>Email</th>
            <th>Căn cước công dân</th>
            <th>Avatar</th>
            <th>Ngày tạo</th>
            <th>Người cập nhật</th>
            <th>Trạng Thái</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="taikhoan" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${taikhoan.tenDangNhap}</td>
                <td>${taikhoan.matKhau}</td>
                <td>${taikhoan.idVaiTro.ten}</td>
                <td>${taikhoan.ten}</td>
                <td>${taikhoan.gioiTinh == true ? "Nam" : " Nữ"}</td>
                <td>${taikhoan.ngaySinh}</td>
                <td>${taikhoan.sdt}</td>
                <td>${taikhoan.email}</td>
                <td>${taikhoan.cccd}</td>
                <td>${taikhoan.avatar}</td>
                <td>${taikhoan.ngayTao}</td>
                <td>${taikhoan.ngayCapNhat}</td>
                <td>${taikhoan.tt == true ? "Còn hoạt động" : "Ngừng hoạt động"}</td>
                <td colspan="3">
                    <a href="/taikhoan/index/detail/${taikhoan.id}">Xem</a>
                    <a href="/taikhoan/index/update/${taikhoan.id}">Sửa</a>
                    <a href="/taikhoan/index/delete?id=${taikhoan.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
