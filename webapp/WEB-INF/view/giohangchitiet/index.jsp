<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Giỏ hàng chi tiết</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Giỏ hàng chi tiết</h2>
    <div><button><a href="/giohangchitiet/index/add">Thêm giỏ hàng chi tiết</a></button></div>
    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã</th>
            <th>Tên tài khoản</th>
            <th>Mã Sản phẩm chi tiết</th>
            <th>Đơn giá</th>
            <th>Số lượng</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th>Trạng Thái</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="giohangchitiet" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${giohangchitiet.ma}</td>
                <td>${giohangchitiet.idGioHang.idTaiKhoan.tenDangNhap}</td>
                <td>${giohangchitiet.idSPCT.ma}</td>
                <td>${giohangchitiet.donGia}</td>
                <td>${giohangchitiet.sl}</td>
                <td>${giohangchitiet.ngayTao}</td>
                <td>${giohangchitiet.ngayCapNhat}</td>
                <td>${giohangchitiet.tt}</td>
                <td colspan="3">
                    <a href="/giohangchitiet/index/detail/${giohangchitiet.id}">Xem</a>
                    <a href="/giohangchitiet/index/update/${giohangchitiet.id}">Sửa</a>
                    <a href="/giohangchitiet/index/delete?id=${giohangchitiet.id}">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
