<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết chất liệu vỏ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${chatlieuvo.id}</div>
<br>
<div>Mã chất liệu: ${chatlieuvo.ma}</div>
<br/>
<div>Tên chất liệu vỏ: ${chatlieuvo.ten}</div>
<br/>
<div>Mô Tả: ${chatlieuvo.moTa}</div>
<br/>
<div>Người Tạo: ${chatlieuvo.nguoiTao}</div>
<br/>
<div>Người Cập Nhật: ${chatlieuvo.nguoiCapNhat}</div>
<br/>
<div>Ngày Tạo: ${chatlieuvo.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${chatlieuvo.ngayCapNhat}</div>
<br/>
<div>Trạng thái: ${chatlieuvo.tt}</div>
<br/>
</body>
</html>
