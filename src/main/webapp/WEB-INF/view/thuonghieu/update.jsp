<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/thuonghieu/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${thuonghieu.id}" readonly/></div>
    <br>
    <div>Tên thương hiệu: <input type="text" name="ten" value="${thuonghieu.ten}"></div>
    <br>
    <div>Mô Tả: <input type="text" name="moTa" value="${thuonghieu.moTa}"></div>
    <br>
<%--    <div>Ngày Tạo: <input type="datetime-local" name="ngayTao" value="${thuonghieu.ngayTao}" readonly></div>--%>
<%--    <br>--%>
<%--    <div>Ngày Cập Nhật: <input type="datetime-local" name="ngayCapNhat" value="${thuonghieu.ngayCapNhat}"></div>--%>
<%--    <br>--%>
    <button>Sửa</button>
</form>

</body>
</html>
