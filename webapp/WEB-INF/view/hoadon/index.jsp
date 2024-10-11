<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Hoá đơn</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Hoá Đơn</h2>
<div>
    <div><button><a href="/hoadon/index/add">Thêm Hoá Đơn</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã hóa đơn</th>
            <th>Tên</th>
            <th>Voucher</th>
            <th>Ngày giao hàng</th>
            <th>Ngày nhận</th>
            <th>Giá giảm</th>
            <th>Tổng tiền</th>
            <th>Số tiền đã trả</th>
            <th>Ghi chú</th>
            <th>Địa chỉ</th>
            <th>Người Tạo</th>
            <th>Người Cập Nhật</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th>Trạng Thái</th>
            <th colspan="2">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="hoadon" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${hoadon.ma}</td>
                <td>${hoadon.idTaiKhoan.idVaiTro.ten}: ${hoadon.idTaiKhoan.ten}</td>
                <td>${hoadon.idVoucher.ten}</td>
                <td>${hoadon.ngayGiaoHang}</td>
                <td>${hoadon.ngayNhan}</td>
                <td>${hoadon.giaGiam}</td>
                <td>${hoadon.tongTien}</td>
                <td>${hoadon.soTienDaTra}</td>
                <td>${hoadon.ghiChu}</td>
                <td>${hoadon.diaChi}</td>
                <td>${hoadon.nguoiTao}</td>
                <td>${hoadon.nguoiCapNhat}</td>
                <td>${hoadon.ngayTao}</td>
                <td>${hoadon.ngayCapNhat}</td>
                <td>${hoadon.tt}</td>
                <td colspan="2">
                    <a href="/hoadon/index/detail/${hoadon.id}">Xem</a>
                    <a href="/hoadon/index/update/${hoadon.id}">Sửa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
