<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${sanpham.id}</div>
<br>
<%--<div>ID_SPCT: ${sanpham.idSPCT}</div>--%>
<%--<br/>--%>
<div>Tên Sản Phẩm: ${sanpham.ten}</div>
<br/>
<div>Mô Tả: ${sanpham.moTa}</div>
<br/>
<div>Trạng Thái: ${sanpham.tt}</div>
<br/>
<%--<div>Ngày Tạo: ${sanpham.ngayTao}</div>--%>
<%--<br/>--%>
<%--<div>Ngày Cập Nhật: ${sanpham.ngayCapNhat}</div>--%>
<%--<br/>--%>
</body>
</html>
