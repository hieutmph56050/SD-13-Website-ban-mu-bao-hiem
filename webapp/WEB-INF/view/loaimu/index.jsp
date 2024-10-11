<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Loại Mũ</h2>
<div>
    <div><button><a href="/loaimu/index/add">Thêm Loại Mũ</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã loại mũ</th>
            <th>Tên loại mũ</th>
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
        <c:forEach items="${list}" var="loaimu" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${loaimu.ma}</td>
                <td>${loaimu.ten}</td>
                <td>${loaimu.moTa}</td>
                <td>${loaimu.nguoiTao}</td>
                <td>${loaimu.nguoiCapNhat}</td>
                <td>${loaimu.ngayTao}</td>
                <td>${loaimu.ngayCapNhat}</td>
                <td>${loaimu.tt}</td>
                <td colspan="3">
                    <a href="/loaimu/index/detail/${loaimu.id}">Xem</a>
                    <a href="/loaimu/index/update/${loaimu.id}">Sửa</a>
                    <a href="/loaimu/index/delete?id=${loaimu.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
