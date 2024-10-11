<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết khuyến mãi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${thanhtoan.id}</div>
<br>
<div>Tên thanh toán: ${thanhtoan.ten}</div>
<br/>
<div>Mã hóa đơn: ${thanhtoan.idHoaDon.ma}</div>
<br/>
<div>Tổng tiền: ${thanhtoan.tongTien}</div>
<br/>
<div>Ngày thanh toán: ${thanhtoan.ngayThanhToan}</div>
<br/>
<div>Người Tạo: ${thanhtoan.nguoiTao}</div>
<br/>
<div>Người Cập Nhật: ${thanhtoan.nguoiCapNhat}</div>
<br/>
<div>Ngày Tạo: ${thanhtoan.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${thanhtoan.ngayCapNhat}</div>
<br/>
<div>Trạng Thái: ${thanhtoan.tt}</div>
<br/>
</body>
</html>
