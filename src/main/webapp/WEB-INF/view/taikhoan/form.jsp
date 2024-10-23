<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tạo tài khoản</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/taikhoan/index/add" method="post">
    <div>Tên đăng nhập: <input type="text" name="tenDangNhap"/></div>
    <br>
    <div>Mật khẩu: <input type="text" name="matKhau"/></div>
    <br>
    <div>
        Vai trò:
        <select name="idVaiTro">
            <c:forEach items="${listVaiTro}" var="vaitro">
                <option value="${vaitro.id}" label="${vaitro.ten}" ${taikhoan.idVaiTro.id == vaitro.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>Họ tên: <input type="text" name="ten"/></div>
    <br>
    <div>
        Giới tính:
        <br>
        <input type="radio" name="gioiTinh" checked value="true"/>Nam
        <input type="radio" name="gioiTinh" value="false"/>Nữ
    </div>
    <br>
    <div>Ngày sinh: <input type="date" name="ngaySinh"/></div>
    <br>
    <div>Số điện thoại: <input type="text" name="sdt"/></div>
    <br>
    <div>Email: <input type="text" name="email"/></div>
    <br>
    <div>Căn cước công dân: <input type="text" name="cccd"/></div>
    <br>
    <div>Avatar: <input type="text" name="avatar"/></div>
    <br>
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
