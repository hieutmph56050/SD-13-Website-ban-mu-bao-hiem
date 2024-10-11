<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Kích thước</h2>
<div>
    <div><button><a href="/kichthuoc/index/add">Thêm Kích Thước</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã kích thước</th>
            <th>Tên kích thước</th>
            <th>Mô Tả</th>
            <th>Người Tạo</th>
            <th>Người Cập Nhật</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th>Trạng Thái</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="kichthuoc" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${kichthuoc.ma}</td>
                <td>${kichthuoc.ten}</td>
                <td>${kichthuoc.moTa}</td>
                <td>${kichthuoc.nguoiTao}</td>
                <td>${kichthuoc.nguoiCapNhat}</td>
                <td>${kichthuoc.ngayTao}</td>
                <td>${kichthuoc.ngayCapNhat}</td>
                <th>${kichthuoc.tt}</th>
                <td colspan="3">
                    <a href="/kichthuoc/index/detail/${kichthuoc.id}">Xem</a>
                    <a href="/kichthuoc/index/update/${kichthuoc.id}">Sửa</a>
                    <a href="/kichthuoc/index/delete?id=${kichthuoc.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
