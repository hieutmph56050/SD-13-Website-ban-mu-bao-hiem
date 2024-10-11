<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Thêm giỏ hàng chi tiết</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/giohangchitiet/index/add" method="post">
    <div>Mã: <input type="text" name="ma"/></div>
    <br>
    <div>
        Tên tài khoản:
        <select name="idGioHang">
            <c:forEach items="${listGioHang}" var="giohang">
                <option value="${giohang.id}" label="${giohang.idTaiKhoan.tenDangNhap}" ${giohangchitiet.idGioHang.id == giohang.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <div>
        Mã Sản phẩm chi tiết:
        <select name="idSPCT">
            <c:forEach items="${listSPChiTiet}" var="spchitiet">
                <option value="${spchitiet.id}" label="${spchitiet.ma}" ${giohangchitiet.idSPCT.id == spchitiet.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <div>Đơn giá: <input type="number" name="donGia"/></div>
    <br>
    <div>Số lượng: <input type="number" name="sl"/></div>
    <br>
    <div>Trạng thái: <input type="text" name="tt"/></div>
    <br>
    <button type="submit">Thêm</button>
</form>
</body>
</html>
