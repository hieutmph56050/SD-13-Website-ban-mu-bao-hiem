<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Sản Phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Sản Phẩm</h2>
<div>
    <div><button><a href="/sanpham/index/add">Thêm Sản Phẩm</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Tên Sản Phẩm</th>
            <th>Mô Tả</th>
            <th>Trạng Thái</th>
<%--            <th>Ngày Tạo</th>--%>
<%--            <th>Ngày Cập Nhật</th>--%>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="sanpham" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${sanpham.ten}</td>
                <td>${sanpham.moTa}</td>
                <td>${sanpham.tt}</td>
<%--                <td>${sanpham.ngayTao}</td>--%>
<%--                <td>${sanpham.ngayCapNhat}</td>--%>
                <td colspan="3">
                    <a href="/sanpham/index/detail/${sanpham.id}">Xem</a>
                    <a href="/sanpham/index/update/${sanpham.id}">Sửa</a>
                    <a href="/sanpham/index/delete?id=${sanpham.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
