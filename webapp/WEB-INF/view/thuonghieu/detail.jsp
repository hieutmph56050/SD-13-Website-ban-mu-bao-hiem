<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết thương hiệu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${thuonghieu.id}</div>
<br>
<div>Tên thương hiệu: ${thuonghieu.ten}</div>
<br/>
<div>Mô Tả: ${thuonghieu.moTa}</div>
<br/>
<div>Ngày Tạo: ${thuonghieu.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${thuonghieu.ngayCapNhat}</div>
<br/>
</body>
</html>
