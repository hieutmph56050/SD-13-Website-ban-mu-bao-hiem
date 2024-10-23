<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/voucher/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${voucher.id}" readonly/></div>
    <br>
    <div>Mã Voucher: <input type="text" name="ma" value="${voucher.ma}"></div>
    <br>
    <div>Tên Voucher: <input type="text" name="ten" value="${voucher.ten}"></div>
    <br>
    <div>Giá trị Voucher: <input type="text" name="giaTri" value="${voucher.giaTri}"></div>
    <br>
    <div>Giá trị Voucher tối đa: <input type="text" name="giaTriMax" value="${voucher.giaTriMax}"></div>
    <br>
    <div>Giới hạn số lượng: <input type="text" name="gioihan" value="${voucher.gioihan}"></div>
    <br>
    <div>Ngày bắt đầu: <input type="datetime-local" name="ngayBD" value="${voucher.ngayBD}"></div>
    <br>
    <div>Ngày kết thúc: <input type="datetime-local" name="ngayKT" value="${voucher.ngayKT}"></div>
    <br>
    <div>Mô tả: <input type="text" name="moTa" value="${voucher.moTa}"></div>
    <br>
    <div>Trạng thái: <input type="text" name="tt" value="${voucher.tt}"></div>
    <br>
    <button>Sửa</button>
</form>

</body>
</html>
