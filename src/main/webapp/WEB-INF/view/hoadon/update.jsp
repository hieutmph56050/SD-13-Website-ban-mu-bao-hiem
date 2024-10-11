<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Sửa thông tin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/hoadon/index/update" method="post">
    <div>ID: <input type="text" name="id" value="${hoadon.id}" readonly></div>
    <br/>
    <div>Mã hoá đơn: <input type="text" name="ma" value="${hoadon.ma}"></div>
    <br/>
    <div>
        Tài khoản:
        <select name="idTaiKhoan">
            <c:forEach items="${listTaiKhoan}" var="taikhoan">
                <option value="${taikhoan.id}" label="${taikhoan.ten}" ${hoadon.idTaiKhoan.id == taikhoan.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Voucher:
        <select name="idVoucher">
            <c:forEach items="${listVoucher}" var="voucher">
                <option value="${voucher.id}" label="${voucher.ten}" ${hoadon.idVoucher.id == voucher.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>Ngày giao hàng: <input type="date" name="ngayGiaoHang" value="${hoadon.ngayGiaoHang}"></div>
    <br/>
    <div>Ngày nhận: <input type="date" name="ngayNhan" value="${hoadon.ngayNhan}"></div>
    <br/>
    <div>Giá giảm: <input type="number" name="giaGiam" value="${hoadon.giaGiam}"></div>
    <br/>
    <div>Tổng tiền: <input type="number" name="tongTien" value="${hoadon.tongTien}"></div>
    <br/>
    <div>Số tiền đã trả: <input type="number" name="soTienDaTra" value="${hoadon.soTienDaTra}"></div>
    <br/>
    <div>Ghi chú: <input type="text" name="ghiChu" value="${hoadon.ghiChu}"></div>
    <br/>
    <div>Địa chỉ: <input type="text" name="diaChi" value="${hoadon.diaChi}"></div>
    <br/>
    <div>Trạng thái: <input type="text" name="tt" value="${hoadon.tt}"></div>
    <br/>
    <button>Sửa</button>
</form>
</body>
</html>
