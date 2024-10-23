<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết chất liệu đệm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${chatlieudem.id}</div>
<br>
<div>Mã chất liệu: ${chatlieudem.ma}</div>
<br/>
<div>Tên chất liệu đệm: ${chatlieudem.ten}</div>
<br/>
<div>Mô Tả: ${chatlieudem.moTa}</div>
<br/>
<div>Người Tạo: ${chatlieudem.nguoiTao}</div>
<br/>
<div>Người Cập Nhật: ${chatlieudem.nguoiCapNhat}</div>
<br/>
<div>Ngày Tạo: ${chatlieudem.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${chatlieudem.ngayCapNhat}</div>
<br/>
<div>Trạng thái: ${chatlieudem.tt}</div>
<br/>
</body>
</html>
