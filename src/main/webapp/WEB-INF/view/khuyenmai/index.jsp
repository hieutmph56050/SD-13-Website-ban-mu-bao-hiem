<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Khuyến Mãi</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Khuyến Mãi</h2>
<div>
    <div><button><a href="/khuyenmai/index/add">Thêm Khuyến Mãi</a></button></div>
</div>

    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã khuyến mãi</th>
            <th>Tên khuyến mãi</th>
            <th>Giá trị khuyến mãi</th>
            <th>Ngày bắt đầu</th>
            <th>Ngày kết thúc</th>
            <th>Phương thức khuyến mãi</th>
            <th>Điều kiện khuyến mãi</th>
            <th>Người Tạo</th>
            <th>Người Cập Nhật</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th>Trạng Thái</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="khuyenmai" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${khuyenmai.ma}</td>
                <td>${khuyenmai.ten}</td>
                <td>${khuyenmai.giaTri}</td>
                <td>${khuyenmai.ngayBD}</td>
                <td>${khuyenmai.ngayKT}</td>
                <td>${khuyenmai.PTKM}</td>
                <td>${khuyenmai.DKKM}</td>
                <td>${khuyenmai.nguoiTao}</td>
                <td>${khuyenmai.nguoiCapNhat}</td>
                <td>${khuyenmai.ngayTao}</td>
                <td>${khuyenmai.ngayCapNhat}</td>
                <td>${khuyenmai.tt}</td>
                <td colspan="3">
                    <a href="/khuyenmai/index/detail/${khuyenmai.id}">Xem</a>
                    <a href="/khuyenmai/index/update/${khuyenmai.id}">Sửa</a>
                    <a href="/khuyenmai/index/delete?id=${khuyenmai.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
