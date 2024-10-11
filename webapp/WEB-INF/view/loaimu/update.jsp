<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/loaimu/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${loaimu.id}" readonly/></div>
    <br>
    <div>Mã loại mũ: <input type="text" name="ma" value="${loaimu.ma}"></div>
    <br>
    <div>Tên loại mũ: <input type="text" name="ten" value="${loaimu.ten}"></div>
    <br>
    <div>Mô Tả: <input type="text" name="moTa" value="${loaimu.moTa}"></div>
    <br>
    <div>Trạng thái: <input type="text" name="tt" value="${loaimu.tt}"></div>
    <br>
    <button>Sửa</button>
</form>

</body>
</html>
