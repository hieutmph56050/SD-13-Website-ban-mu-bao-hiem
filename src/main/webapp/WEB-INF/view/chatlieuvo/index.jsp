<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Chất Liệu Vỏ</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Chất Liệu Vỏ</h2>
<div>
    <div><button><a href="/chatlieuvo/index/add">Thêm Chất Liệu Vỏ</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã chất liệu</th>
            <th>Tên chất liệu vỏ</th>
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
        <c:forEach items="${list}" var="chatlieuvo" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${chatlieuvo.ma}</td>
                <td>${chatlieuvo.ten}</td>
                <td>${chatlieuvo.moTa}</td>
                <td>${chatlieuvo.nguoiTao}</td>
                <td>${chatlieuvo.nguoiCapNhat}</td>
                <td>${chatlieuvo.ngayTao}</td>
                <td>${chatlieuvo.ngayCapNhat}</td>
                <td>${chatlieuvo.tt}</td>
                <td colspan="3">
                    <a href="/chatlieuvo/index/detail/${chatlieuvo.id}">Xem</a>
                    <a href="/chatlieuvo/index/update/${chatlieuvo.id}">Sửa</a>
                    <a href="/chatlieuvo/index/delete?id=${chatlieuvo.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
