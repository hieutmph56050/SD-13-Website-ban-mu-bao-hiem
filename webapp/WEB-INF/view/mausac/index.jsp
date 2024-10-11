<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Màu Sắc</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Màu Sắc</h2>
<div>
    <div><button><a href="/mausac/index/add">Thêm Màu</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã màu</th>
            <th>Tên màu</th>
            <th>Mô Tả</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="mausac" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${mausac.ma}</td>
                <td>${mausac.ten}</td>
                <td>${mausac.moTa}</td>
                <td>${mausac.ngayTao}</td>
                <td>${mausac.ngayCapNhat}</td>
                <td colspan="3">
                    <a href="/mausac/index/detail/${mausac.id}">Xem</a>
                    <a href="/mausac/index/update/${mausac.id}">Sửa</a>
                    <a href="/mausac/index/delete?id=${mausac.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
