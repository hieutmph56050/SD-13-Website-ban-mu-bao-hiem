<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/mausac/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${mausac.id}" readonly/></div>
    <br>
    <div>Mã màu: <input type="text" name="ma" value="${mausac.ma}"></div>
    <br>
    <div>Tên màu: <input type="text" name="ten" value="${mausac.ten}"></div>
    <br>
    <div>Mô Tả: <input type="text" name="moTa" value="${mausac.moTa}"></div>
    <br>
<%--    <div>Ngày Tạo: <input type="datetime-local" name="ngayTao" value="${mausac.ngayTao}" readonly></div>--%>
<%--    <br>--%>
<%--    <div>Ngày Cập Nhật: <input type="datetime-local" name="ngayCapNhat" value="${mausac.ngayCapNhat}"></div>--%>
<%--    <br>--%>
    <button>Sửa</button>
</form>

</body>
</html>
