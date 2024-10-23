<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/thanhtoan/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${thanhtoan.id}" readonly/></div>
    <br>
    <div>Tên thanh toán: <input type="text" name="ten" value="${thanhtoan.ten}"></div>
    <br/>
    <div>Tổng tiền: <input type="text" name="tongTien" value="${thanhtoan.tongTien}"></div>
    <br/>
    <div>Ngày thanh toán: <input type="datetime-local" name="ngayThanhToan" value="${thanhtoan.ngayThanhToan}"></div>
    <br/>
    <div>Trạng thái: <input type="text" name="tt"/></div>
    <br/>
    <button>Sửa</button>
</form>

</body>
</html>
