<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${hoadonchitiet.id}</div>
<br>
<div>Mã Hóa đơn chi tiết: ${hoadonchitiet.ma}</div>
<br/>
<div>Mã Sản phẩm chi tiết: ${hoadonchitiet.idSPCT.ma}</div>
<br/>
<div>Mã Hóa đơn: ${hoadonchitiet.idHoaDon.ma}</div>
<br/>
<div>Tổng tiền: ${hoadonchitiet.tongTien}</div>
<br/>
<div>Số lượng: ${hoadonchitiet.sl}</div>
<br/>
<div>Ghi chú: ${hoadonchitiet.ghiChu}</div>
<br/>
<div>Ngày Tạo: ${hoadonchitiet.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${hoadonchitiet.ngayCapNhat}</div>
<br/>
<div>Trạng thái: ${hoadonchitiet.tt}</div>
<br/>
</body>
</html>
