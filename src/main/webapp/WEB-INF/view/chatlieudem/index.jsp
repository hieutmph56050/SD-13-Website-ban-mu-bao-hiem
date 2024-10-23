<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Chất Liệu Đệm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Chất Liệu Đệm</h2>
<div>
    <div><button><a href="/chatlieudem/index/add">Thêm Chất Liệu Đệm</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã chất liệu</th>
            <th>Tên chất liệu đệm</th>
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
        <c:forEach items="${list}" var="chatlieudem" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${chatlieudem.ma}</td>
                <td>${chatlieudem.ten}</td>
                <td>${chatlieudem.moTa}</td>
                <td>${chatlieudem.nguoiTao}</td>
                <td>${chatlieudem.nguoiCapNhat}</td>
                <td>${chatlieudem.ngayTao}</td>
                <td>${chatlieudem.ngayCapNhat}</td>
                <td>${chatlieudem.tt}</td>
                <td colspan="3">
                    <a href="/chatlieudem/index/detail/${chatlieudem.id}">Xem</a>
                    <a href="/chatlieudem/index/update/${chatlieudem.id}">Sửa</a>
                    <a href="/chatlieudem/index/delete?id=${chatlieudem.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
