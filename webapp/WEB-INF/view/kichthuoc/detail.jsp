<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết kích thước</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${kichthuoc.id}</div>
<br>
<div>Mã kích thước: ${kichthuoc.ma}</div>
<br/>
<div>Tên kích thước: ${kichthuoc.ten}</div>
<br/>
<div>Mô Tả: ${kichthuoc.moTa}</div>
<br/>
<div>Người Tạo: ${kichthuoc.nguoiTao}</div>
<br/>
<div>Người Cập Nhật: ${kichthuoc.nguoiCapNhat}</div>
<br/>
<div>Ngày Tạo: ${kichthuoc.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${kichthuoc.ngayCapNhat}</div>
<br/>
<div>Trạng Thái: ${kichthuoc.tt}</div>
<br/>
</body>
</html>
