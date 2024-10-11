<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix = "c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tạo chi tiết sản phẩm</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/spchitiet/index/add" method="post">
    <div>Mã Sản phẩm chi tiết: <input type="text" name="ma"/></div>
    <br>
    <div>
        Sản phẩm:
        <select name="idSanPham">
            <c:forEach items="${listSanPham}" var="sanpham">
                <option value="${sanpham.id}" label="${sanpham.ten}" ${spchitiet.idSanPham.id == sanpham.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Thương hiệu:
        <select name="idThuongHieu">
            <c:forEach items="${listThuongHieu}" var="thuonghieu">
                <option value="${thuonghieu.id}" label="${thuonghieu.ten}" ${spchitiet.idThuongHieu.id == thuonghieu.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Chất liệu vỏ:
        <select name="idChatLieuVo">
            <c:forEach items="${listChatLieuVo}" var="chatlieuvo">
                <option value="${chatlieuvo.id}" label="${chatlieuvo.ten}" ${spchitiet.idChatLieuVo.id == chatlieuvo.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Chất liệu đệm:
        <select name="idChatLieuDem">
            <c:forEach items="${listChatLieuDem}" var="chatlieudem">
                <option value="${chatlieudem.id}" label="${chatlieudem.ten}" ${spchitiet.idChatLieuDem.id == chatlieudem.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Loại mũ:
        <select name="idLoaiMu">
            <c:forEach items="${listLoaiMu}" var="loaimu">
                <option value="${loaimu.id}" label="${loaimu.ten}" ${spchitiet.idLoaiMu.id == loaimu.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Loại kính:
        <select name="idLoaiKinh">
            <c:forEach items="${listLoaiKinh}" var="loaikinh">
                <option value="${loaikinh.id}" label="${loaikinh.ten}" ${spchitiet.idLoaiKinh.id == loaikinh.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Kích thước:
        <select name="idKichThuoc">
            <c:forEach items="${listKichThuoc}" var="kichthuoc">
                <option value="${kichthuoc.id}" label="${kichthuoc.ten}" ${spchitiet.idKichThuoc.id == kichthuoc.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Màu Sắc:
        <select name="idMauSac">
            <c:forEach items="${listMauSac}" var="mausac">
                <option value="${mausac.id}" label="${mausac.ten}" ${spchitiet.idMauSac.id == mausac.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>
        Khuyến mãi:
        <select name="idKhuyenMai">
            <c:forEach items="${listKhuyenMai}" var="khuyenmai">
                <option value="${khuyenmai.id}" label="${khuyenmai.ten}" ${spchitiet.idKhuyenMai.id == khuyenmai.id ? "selected" : ""}></option>
            </c:forEach>
        </select>
    </div>
    <br>
    <div>Số lượng: <input type="number" name="sl"/></div>
    <br>
    <div>Đơn giá: <input type="number" name="donGia"/></div>
    <br>
    <div>Xuất xứ: <input type="text" name="xuatXu"/></div>
    <br>
    <div>Mô tả chi tiết: <input type="text" name="moTaCT"/></div>
    <br>
    <div>Trạng thái: <input type="text" name="tt"/></div>
    <br>
    <button type="submit">Thêm</button>
</form>
</body>
</html>
