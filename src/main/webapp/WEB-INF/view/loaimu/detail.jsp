<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết loại mũ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${loaimu.id}</div>
<br>
<div>Mã loại mũ: ${loaimu.ma}</div>
<br/>
<div>Tên loại mũ: ${loaimu.ten}</div>
<br/>
<div>Mô Tả: ${loaimu.moTa}</div>
<br/>
<div>Người Tạo: ${loaimu.nguoiTao}</div>
<br/>
<div>Người Cập Nhật: ${loaimu.nguoiCapNhat}</div>
<br/>
<div>Ngày Tạo: ${loaimu.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${loaimu.ngayCapNhat}</div>
<br/>
<div>Trạng Thái: ${loaimu.tt}</div>
<br/>
</body>
</html>
