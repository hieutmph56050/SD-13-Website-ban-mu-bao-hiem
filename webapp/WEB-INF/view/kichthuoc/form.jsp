<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Thêm Màu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/kichthuoc/index/add" method="post">
    <div>Mã kích thước: <input type="text" name="ma"/></div>
    <br/>
    <div>Tên kích thước: <input type="text" name="ten"/></div>
    <br/>
    <div>Mô Tả: <input type="text" name="moTa"/></div>
    <br/>
    <div>Trạng Thái: <input type="text" name="tt"/></div>
    <br/>
    <button type="submit">Thêm</button>
</form>
</body>
</html>
