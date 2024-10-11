<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Chi Tiết Sản Phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
        <h2 align="center">Chi Tiết Sản Phẩm</h2>
        <div><button><a href="/spchitiet/index/add">Tạo Chi Tiết Sản Phẩm</a></button></div>
        <br>
    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã sản phẩm chi tiết</th>
            <th>Tên sản phẩm</th>
            <th>Thương hiệu</th>
            <th>Chất liệu vỏ</th>
            <th>Chất liệu đệm</th>
            <th>Loại mũ</th>
            <th>Loại kính</th>
            <th>Kích thước</th>
            <th>Màu sắc</th>
            <th>Khuyến mãi</th>
            <th>Số lượng</th>
<%--            <th>Đơn giá</th>--%>
            <th>Xuất xứ</th>
            <th>Mô tả chi tiết</th>
            <th>Trạng Thái</th>
            <th>Người tạo</th>
            <th>Ngày tạo</th>
            <th>Người cập nhật</th>
            <th>Ngày cập nhật</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="spchitiet" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${spchitiet.ma}</td>
                <td>${spchitiet.idSanPham.ten}</td>
                <td>${spchitiet.idThuongHieu.ten}</td>
                <td>${spchitiet.idChatLieuVo.ten}</td>
                <td>${spchitiet.idChatLieuDem.ten}</td>
                <td>${spchitiet.idLoaiMu.ten}</td>
                <td>${spchitiet.idLoaiKinh.ten}</td>
                <td>${spchitiet.idKichThuoc.ten}</td>
                <td>${spchitiet.idMauSac.ten}</td>
                <td>${spchitiet.idKhuyenMai.ten}</td>
                <td>${spchitiet.sl}</td>
<%--                <td>${spchitiet.donGia}</td>--%>
                <td>${spchitiet.xuatXu}</td>
                <td>${spchitiet.moTaCT}</td>
                <td>${spchitiet.tt}</td>
                <td>${spchitiet.nguoiTao}</td>
                <td>${spchitiet.ngayTao}</td>
                <td>${spchitiet.nguoiCapNhat}</td>
                <td>${spchitiet.ngayCapNhat}</td>
                <td colspan="3">
                    <a href="/spchitiet/index/detail/${spchitiet.id}">Xem</a>
                    <a href="/spchitiet/index/update/${spchitiet.id}">Sửa</a>
                    <a href="/spchitiet/index/delete?id=${spchitiet.id}">Xoá</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
