<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Vai Trò</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Vai Trò</h2>
<div>
    <div><button><a href="/vaitro/index/add">Thêm Vai Trò</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Tên Vai Trò</th>
            <th>Mô Tả</th>
            <th>Người Tạo</th>
            <th>Người Cập Nhật</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="vaitro" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${vaitro.ten}</td>
                <td>${vaitro.moTa}</td>
                <td>${vaitro.nguoiTao}</td>
                <td>${vaitro.nguoiCapNhat}</td>
                <td>${vaitro.ngayTao}</td>
                <td>${vaitro.ngayCapNhat}</td>
                <td colspan="3">
                    <a href="/vaitro/index/detail/${vaitro.id}">Xem</a>
                    <a href="/vaitro/index/update/${vaitro.id}">Sửa</a>
                    <a href="/vaitro/index/delete?id=${vaitro.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
