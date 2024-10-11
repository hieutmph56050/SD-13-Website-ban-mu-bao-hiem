<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết loại kính</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${loaikinh.id}</div>
<br>
<div>Mã loại kính: ${loaikinh.ma}</div>
<br/>
<div>Tên loại kính: ${loaikinh.ten}</div>
<br/>
<div>Mô Tả: ${loaikinh.moTa}</div>
<br/>
<div>Người Tạo: ${loaikinh.nguoiTao}</div>
<br/>
<div>Người Cập Nhật: ${loaikinh.nguoiCapNhat}</div>
<br/>
<div>Ngày Tạo: ${loaikinh.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${loaikinh.ngayCapNhat}</div>
<br/>
<div>Trạng Thái: ${loaikinh.tt}</div>
<br/>
</body>
</html>
