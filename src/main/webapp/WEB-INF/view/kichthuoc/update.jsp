<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/kichthuoc/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${kichthuoc.id}" readonly/></div>
    <br>
    <div>Mã kích thước: <input type="text" name="ma" value="${kichthuoc.ma}"></div>
    <br>
    <div>Tên kích thước: <input type="text" name="ten" value="${kichthuoc.ten}"></div>
    <br>
    <div>Mô Tả: <input type="text" name="moTa" value="${kichthuoc.moTa}"></div>
    <br>
    <div>Trạng Thái: <input type="text" name="tt" value="${kichthuoc.tt}"></div>
    <br>
    <button>Sửa</button>
</form>

</body>
</html>
