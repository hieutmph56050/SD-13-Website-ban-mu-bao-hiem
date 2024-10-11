<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Voucher</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Voucher</h2>
<div>
    <div><button><a href="/voucher/index/add">Thêm Voucher</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã Voucher</th>
            <th>Tên Voucher</th>
            <th>Giá trị Voucher</th>
            <th>Giá trị Voucher tối đa</th>
            <th>Giới hạn số lượng</th>
            <th>Ngày bắt đầu</th>
            <th>Ngày kết thúc</th>
            <th>Mô tả</th>
            <th>Trạng Thái</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="voucher" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${voucher.ma}</td>
                <td>${voucher.ten}</td>
                <td>${voucher.giaTri}</td>
                <td>${voucher.giaTriMax}</td>
                <td>${voucher.gioihan}</td>
                <td>${voucher.ngayBD}</td>
                <td>${voucher.ngayKT}</td>
                <td>${voucher.moTa}</td>
                <td>${voucher.tt}</td>
                <td colspan="3">
                    <a href="/voucher/index/detail/${voucher.id}">Xem</a>
                    <a href="/voucher/index/update/${voucher.id}">Sửa</a>
                    <a href="/voucher/index/delete?id=${voucher.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
