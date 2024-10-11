<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Thanh Toán</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Thanh Toán</h2>
<div>
    <div><button><a href="/thanhtoan/index/add">Thêm Thanh Toán</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Tên thanh toán</th>
            <th>Mã hóa đơn</th>
            <th>Tổng tiền</th>
            <th>Ngày thanh toán</th>
            <th>Người Tạo</th>
            <th>Người Cập Nhật</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th>Trạng Thái</th>
            <th colspan="2">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="thanhtoan" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${thanhtoan.ten}</td>
                <td>${thanhtoan.idHoaDon.ma}</td>
                <td>${thanhtoan.tongTien}</td>
                <td>${thanhtoan.ngayThanhToan}</td>
                <td>${thanhtoan.nguoiTao}</td>
                <td>${thanhtoan.nguoiCapNhat}</td>
                <td>${thanhtoan.ngayTao}</td>
                <td>${thanhtoan.ngayCapNhat}</td>
                <td>${thanhtoan.tt}</td>
                <td colspan="2">
                    <a href="/thanhtoan/index/detail/${thanhtoan.id}">Xem</a>
                    <a href="/thanhtoan/index/update/${thanhtoan.id}">Sửa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
