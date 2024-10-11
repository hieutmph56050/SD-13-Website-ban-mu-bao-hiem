<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Loại Kính</h2>
<div>
    <div><button><a href="/loaikinh/index/add">Thêm Loại Kính</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã loại kính</th>
            <th>Tên loại kính</th>
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
        <c:forEach items="${list}" var="loaikinh" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${loaikinh.ma}</td>
                <td>${loaikinh.ten}</td>
                <td>${loaikinh.moTa}</td>
                <td>${loaikinh.nguoiTao}</td>
                <td>${loaikinh.nguoiCapNhat}</td>
                <td>${loaikinh.ngayTao}</td>
                <td>${loaikinh.ngayCapNhat}</td>
                <td>${loaikinh.tt}</td>
                <td colspan="3">
                    <a href="/loaikinh/index/detail/${loaikinh.id}">Xem</a>
                    <a href="/loaikinh/index/update/${loaikinh.id}">Sửa</a>
                    <a href="/loaikinh/index/delete?id=${loaikinh.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
