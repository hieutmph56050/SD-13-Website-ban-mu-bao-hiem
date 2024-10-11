<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết vai trò</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${vaitro.id}</div>
<br>
<div>Tên loại mũ: ${vaitro.ten}</div>
<br/>
<div>Mô Tả: ${vaitro.moTa}</div>
<br/>
<div>Người Tạo: ${vaitro.nguoiTao}</div>
<br/>
<div>Người Cập Nhật: ${vaitro.nguoiCapNhat}</div>
<br/>
<div>Ngày Tạo: ${vaitro.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${vaitro.ngayCapNhat}</div>
<br/>
</body>
</html>
