<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết màu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${mausac.id}</div>
<br>
<div>Mã màu: ${mausac.ma}</div>
<br/>
<div>Tên màu: ${mausac.ten}</div>
<br/>
<div>Mô Tả: ${mausac.moTa}</div>
<br/>
<div>Ngày Tạo: ${mausac.ngayTao}</div>
<br/>
<div>Ngày Cập Nhật: ${mausac.ngayCapNhat}</div>
<br/>
</body>
</html>
