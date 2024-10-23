<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/taikhoan/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${taikhoan.id}" readonly/></div>
    <br>
    <div>Tên đăng nhập: <input type="text" name="tenDangNhap" value="${taikhoan.tenDangNhap}"/></div>
    <br>
    <div>Mật khẩu: <input type="text" name="matKhau" value="${taikhoan.matKhau}"/></div>
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
    <div>Họ tên: <input type="text" name="ten" value="${taikhoan.ten}"/></div>
    <br>
    Giới tính:
    <br>
    <input type="radio" name="gioiTinh" ${taikhoan.gioiTinh ? "checked" : ""} value="true"/>Nam
    <input type="radio" name="gioiTinh" ${taikhoan.gioiTinh ? "" : "checked"} value="false"/>Nữ
    <br>
    <div>Ngày sinh: <input type="date" name="ngaySinh" value="${taikhoan.ngaySinh}"/></div>
    <br>
    <div>Số điện thoại: <input type="text" name="sdt" value="${taikhoan.sdt}"/></div>
    <br>
    <div>Email: <input type="text" name="email" value="${taikhoan.email}"/></div>
    <br>
    <div>Căn cước công dân: <input type="text" name="cccd" value="${taikhoan.cccd}"/></div>
    <br>
    <div>Avatar: <input type="text" name="avatar" value="${taikhoan.avatar}"/></div>
    <br>
    <div>
    Trạng thái:
    <br>
    <input type="radio" name="tt" ${taikhoan.tt ? "checked" : ""} value="true"/>Hoạt động
    <input type="radio" name="tt" ${taikhoan.tt ? "" : "checked"} value="false"/>Không hoạt động
    </div>
    <br>
    <button>Sửa</button>
</form>

</body>
</html>
