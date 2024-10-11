<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${spchitiet.id}</div>
<br>
<div>Mã Sản phẩm chi tiết: ${spchitiet.ma} </div>
<br>
<div>Sản phẩm: ${spchitiet.idSanPham.ten} </div>
<br>
<div>Thương hiệu: ${spchitiet.idThuongHieu.ten}</div>
<br>
<div>Chất liệu vỏ: ${spchitiet.idChatLieuVo.ten}</div>
<br>
<div>Chất liệu đệm: ${spchitiet.idChatLieuDem.ten}</div>
<br>
<div>Loại mũ: ${spchitiet.idLoaiMu.ten}</div>
<br>
<div>Loại kính: ${spchitiet.idLoaiKinh.ten}</div>
<br>
<div>Kích thước: ${spchitiet.idKichThuoc.ten}</div>
<br>
<div>Màu Sắc: ${spchitiet.idMauSac.ten}</div>
<br>
<div>Khuyến mãi: ${spchitiet.idKhuyenMai.ten}</div>
<br>
<div>Số lượng: ${spchitiet.sl}</div>
<br>
<div>Đơn giá: ${spchitiet.donGia}</div>
<br>
<div>Xuất xứ: ${spchitiet.xuatXu}</div>
<br>
<div>Mô tả chi tiết: ${spchitiet.moTaCT}</div>
<br>
<div>Trạng thái: ${spchitiet.tt}</div>
<br>
</body>
</html>
