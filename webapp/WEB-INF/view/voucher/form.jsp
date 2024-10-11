<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm Voucher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/voucher/index/add" method="post">
    <div>Mã Voucher: <input type="text" name="ma"/></div>
    <br/>
    <div>Tên Voucher: <input type="text" name="ten"/></div>
    <br/>
    <div>Giá trị Voucher: <input type="number" name="giaTri"/></div>
    <br/>
    <div>Giá trị Voucher tối đa: <input type="number" name="giaTriMax"/></div>
    <br/>
    <div>Giới hạn số lượng: <input type="number" name="gioihan"/></div>
    <br/>
    <div>Ngày bắt đầu: <input type="datetime-local" name="ngayBD"/></div>
    <br/>
    <div>Ngày kết thúc: <input type="datetime-local" name="ngayKT"/></div>
    <br/>
    <div>Mô tả: <input type="text" name="moTa"></div>
    <br/>
    <div>Trạng thái: <input type="text" name="tt"/></div>
    <br/>
    <button type="submit">Thêm</button>
</form>
</body>
</html>
