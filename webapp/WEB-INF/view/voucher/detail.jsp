<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Chi tiết Voucher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<div>ID: ${voucher.id}</div>
<br>
<div>Mã Voucher: ${voucher.ma}</div>
<br/>
<div>Tên Voucher: ${voucher.ten}</div>
<br/>
<div>Giá trị Voucher: ${voucher.giaTri}</div>
<br/>
<div>Giá trị Voucher tối đa: ${voucher.giaTriMax}</div>
<br/>
<div>Giới hạn số lượng: ${voucher.gioihan}</div>
<br/>
<div>Ngày bắt đầu: ${voucher.ngayBD}</div>
<br/>
<div>Ngày kết thúc: ${voucher.ngayKT}</div>
<br/>
<div>Mô tả: ${voucher.moTa}</div>
<br/>
<div>Trạng thái: ${voucher.tt}</div>
<br/>
</body>
</html>
