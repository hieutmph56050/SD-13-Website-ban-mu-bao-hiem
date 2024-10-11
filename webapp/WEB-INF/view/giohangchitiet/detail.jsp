<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết giỏ hàng</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${giohangchitiet.id}</div>
<br>
<div>Mã: ${giohangchitiet.ma}</div>
<br/>
<div>Tên tài khoản: ${giohangchitiet.idGioHang.idTaiKhoan.tenDangNhap}</div>
<br/>
<div>Mã Sản phẩm chi tiết: ${giohangchitiet.idSPCT.ma}</div>
<br/>
<div>Đơn giá: ${giohangchitiet.donGia}</div>
<br/>
<div>Số lượng: ${giohangchitiet.sl}</div>
<br/>
<div>Ngày Tạo: ${giohangchitiet.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${giohangchitiet.ngayCapNhat}</div>
<br/>
<div>Trạng thái: ${giohangchitiet.tt}</div>
<br/>
</body>
</html>
