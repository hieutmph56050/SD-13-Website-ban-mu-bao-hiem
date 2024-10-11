<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Thương Hiệu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Thương Hiệu</h2>
    <div><button><a href="/thuonghieu/index/add">Thêm Thương Hiệu</a></button></div>
    <table class="table">
        <tr>
            <th>STT</th>
            <th>Tên Thương Hiệu</th>
            <th>Mô Tả</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="thuonghieu" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${thuonghieu.ten}</td>
                <td>${thuonghieu.moTa}</td>
                <td>${thuonghieu.ngayTao}</td>
                <td>${thuonghieu.ngayCapNhat}</td>
                <td colspan="3">
                    <a href="/thuonghieu/index/detail/${thuonghieu.id}">Xem</a>
                    <a href="/thuonghieu/index/update/${thuonghieu.id}">Sửa</a>
                    <a href="/thuonghieu/index/delete?id=${thuonghieu.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
