<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Hóa đơn chi tiết</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">
<h2 align="center">Hóa đơn chi tiết</h2>
    <div><button><a href="/hoadonchitiet/index/add">Tạo Hóa đơn chi tiết</a></button></div>
    <table class="table">
        <tr>
            <th>STT</th>
            <th>Mã Hóa đơn chi tiết</th>
            <th>Mã Hóa đơn</th>
            <th>Mã Sản phẩm chi tiết</th>
            <th>Tổng tiền</th>
            <th>Số lượng</th>
            <th>Ghi chú</th>
            <th>Ngày Tạo</th>
            <th>Ngày Cập Nhật</th>
            <th>Trạng Thái</th>
            <th colspan="3">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${list}" var="hoadonchitiet" varStatus="i">
            <tr>
                <td>${i.index + 1}</td>
                <td>${hoadonchitiet.ma}</td>
                <td>${hoadonchitiet.idHoaDon.ma}</td>
                <td>${hoadonchitiet.idSPCT.ma}</td>
                <td>${hoadonchitiet.tongTien}</td>
                <td>${hoadonchitiet.sl}</td>
                <td>${hoadonchitiet.ghiChu}</td>
                <td>${hoadonchitiet.ngayTao}</td>
                <td>${hoadonchitiet.ngayCapNhat}</td>
                <td>${hoadonchitiet.tt}</td>
                <td colspan="3">
                    <a href="/hoadonchitiet/index/detail/${hoadonchitiet.id}">Xem</a>
                    <a href="/hoadonchitiet/index/update/${hoadonchitiet.id}">Sửa</a>
                    <a href="/hoadonchitiet/index/delete?id=${hoadonchitiet.id}">Xóa</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
