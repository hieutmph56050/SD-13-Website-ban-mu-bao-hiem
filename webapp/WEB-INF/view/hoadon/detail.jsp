<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết khuyến mãi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${hoadon.id}</div>
<br>
<div>Mã hóa đơn: ${hoadon.ma}</div>
<br/>
<div>Tên: ${hoadon.idTaiKhoan.idVaiTro.ten} ${hoadon.idTaiKhoan.ten}</div>
<br/>
<div>Voucher: ${hoadon.idVoucher.ten}</div>
<br/>
<div>Ngày giao hàng: ${hoadon.ngayGiaoHang}</div>
<br/>
<div>Ngày nhận: ${hoadon.ngayNhan}</div>
<br/>
<div>Giá giảm: ${hoadon.giaGiam}</div>
<br/>
<div>Tổng tiền: ${hoadon.tongTien}</div>
<br/>
<div>Số tiền đã trả: ${hoadon.soTienDaTra}</div>
<br/>
<div>Ghi chú: ${hoadon.ghiChu}</div>
<br/>
<div>Địa chỉ: ${hoadon.diaChi}</div>
<br/>
<div>Người tạo: ${hoadon.nguoiTao}</div>
<br/>
<div>Ngày tạo: ${hoadon.ngayTao}</div>
<br/>
<div>Người cập nhật: ${hoadon.nguoiCapNhat}</div>
<br/>
<div>Ngày cập nhật: ${hoadon.ngayCapNhat}</div>
<br/>
<div>Trạng Thái: ${hoadon.tt}</div>
<br/>
</body>
</html>
