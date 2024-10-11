<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tạo Hóa đơn chi tiết</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/hoadonchitiet/index/add" method="post">
    <div>Mã hoá đơn chi tiết: <input type="text" name="ma"/></div>
    <br>
    <div>
        Mã Hóa đơn:
        <select name="idHoaDon">
            <c:forEach items="${listHoaDon}" var="hoadon">
                <option value="${hoadon.id}" label="${hoadon.ma}" ${hoadonchitiet.idHoaDon.id == hoadon.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <div>
        Mã Sản phẩm chi tiết:
        <select name="idSPCT">
            <c:forEach items="${listSPChiTiet}" var="spchitiet">
                <option value="${spchitiet.id}" label="${spchitiet.ma}" ${hoadonchitiet.idSPCT.id == spchitiet.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <div>Tổng tiền: <input type="number" name="tongTien"/></div>
    <br>
    <div>Số lượng: <input type="number" name="sl"/></div>
    <br>
    <div>Ghi Chú: <input type="text" name="ghiChu"/></div>
    <br>
    <div>Trạng thái: <input type="text" name="tt"/></div>
    <br>
    <button type="submit">Thêm</button>
</form>
</body>
</html>
