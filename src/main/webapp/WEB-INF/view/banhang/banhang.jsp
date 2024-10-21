<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.6.0/css/all.min.css"
          crossorigin="anonymous" referrerpolicy="no-referrer"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

    <title>Bán mũ bảo hiểm SafeRide</title>
    <link rel="stylesheet" type="text/css" href="/css/banhang.css">
    <style>
        .ellipsis {
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .table th, .table td {
            vertical-align: middle; /* Align items vertically in the center */
        }

        .btn-container {
            display: flex;
            flex-direction: column; /* Stack buttons vertically */
            justify-content: center; /* Center the buttons */
        }
        .btn-container form, .btn-container a {
            margin-right: 5px; /* Add margin for spacing */
        }
    </style>
</head>
<body>
<div>
    <header>
        <h1> <i class="fa-solid fa-list"></i> Bán hàng tại quầy SafeRide</h1>
    </header>

    <main>
        <div class="row">
            <div class="col">
                <h2>Danh sách Hóa Đơn</h2>
                <!-- Button to open the modal for creating a new invoice -->
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#customerModal">
                    <i class="fa-solid fa-plus"></i> Tạo hóa đơn mới
                </button>

                <!-- Modal for customer input -->
                <div class="modal fade" id="customerModal" tabindex="-1" aria-labelledby="customerModalLabel" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h5 class="modal-title" id="customerModalLabel">Thông tin khách hàng</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                            </div>
                            <div class="modal-body">
                                <form action="/ban-hang/them-hoa-don" method="post">
                                    <div class="mb-3">
                                        <label for="customerName" class="form-label">Tên Khách Hàng</label>
                                        <input type="text" class="form-control" id="customerName" name="tenKhachHang" required>
                                    </div>
                                    <div class="mb-3">
                                        <label for="customerPhone" class="form-label">Số Điện Thoại</label>
                                        <input type="text" class="form-control" id="customerPhone" name="sdt" required>
                                    </div>
                                    <button type="submit" class="btn btn-primary"> <i class="fa-solid fa-plus"></i> Tạo hóa đơn</button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Ngày Lập</th>
                        <th>Khách Hàng</th>
                        <th>Tổng Tiền</th>
                        <th>Trạng Thái</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="hd" items="${listHoaDon}" varStatus="i">
                        <tr class="${hd.id == selectedHoaDonId ? 'table-info' : ''}">
                            <td>${i.index + 1}</td>
                            <td>${hd.ngayTao}</td>
                            <td class="ellipsis">${hd.idTaiKhoan.ten}</td> <!-- Truncate long customer names -->
                            <td>${hd.getFormattedGia()}</td>
                            <td>${hd.tt}</td>
                            <td>
                                <div class="btn-container">
                                    <form action="/ban-hang/chon-hoa-don" method="get">
                                        <input type="hidden" name="hoaDonId" value="${hd.id}"/>
                                        <button type="submit" class="btn btn-secondary">
                                                ${hd.id == selectedHoaDonId ? 'Đang chọn' : 'Chọn'}
                                        </button>
                                    </form>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="col">
                <h2>Hóa Đơn Chi Tiết</h2>
                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th>STT</th>
                        <th>Tên Sản Phẩm</th>
                        <th>Số Lượng</th>
                        <th>Đơn Giá</th>
                        <th>Tổng tiền</th>
                        <th>Thao tác</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:if test="${not empty listHoaDonChiTiet}">
                        <c:forEach var="hdct" items="${listHoaDonChiTiet}" varStatus="j">
                            <tr>
                                <td>${j.index + 1}</td>
                                <td class="ellipsis">${hdct.idSPCT.idSanPham.ten}</td> <!-- Truncate long product names -->
                                <td>${hdct.sl}</td>
                                <td>${hdct.getFormattedGia()}</td>
                                <td>${hdct.getFormattedTongTien()}</td>
                                <td>
                                    <a href="/ban-hang/xoa/hdct/${hdct.id}/${selectedHoaDonId}">
                                        <button class="btn btn-danger"> <i class="fa-solid fa-trash"></i> Xóa</button>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty listHoaDonChiTiet}">
                        <tr>
                            <td colspan="6">Chưa có sản phẩm nào trong hóa đơn này.</td>
                        </tr>
                    </c:if>
                    </tbody>
                </table>

                <label>Tổng tiền: ${hoaDon.getFormattedGia()}</label> VND <br>
                <label>Phải trả</label>
                <input type="text" id="customerPayment" oninput="calculateChange()" value="0"> VND <br>
                <label>Tiền dư</label>
                <input type="text" id="change" disabled value="0"> VND <br>
                <form action="/ban-hang/thanh-toan" method="post">
                    <input type="hidden" name="hoaDonId" value="${hoaDon.id}"/>
                    <input type="hidden" id="paymentAmount" name="soTienKhachTra" value="0"/>
                    <button type="submit" class="btn btn-success"> <i class="fa-regular fa-credit-card"></i> Thanh toán</button>
                </form>

                <div>
                    <c:if test="${not empty thanhcong}">
                        <div class="alert alert-success d-flex align-items-center" role="alert" style="position: relative;">
                            <span class="me-2">
                                <i class="fas fa-check-circle" style="color: green; font-size: 1.5rem;"></i>
                            </span>
                            <span>${thanhcong}</span>
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" style="position: absolute; right: 0;"></button>
                        </div>
                    </c:if>
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                </div>
            </div>
        </div>

        <h2>Sản Phẩm Chi Tiết</h2>
        <table class="table table-hover">
            <thead>
            <tr>
                <th>STT</th>
                <th>Tên Sản Phẩm</th>
                <th>Tên Thương Hiệu</th>
                <th>Chất Liệu Vỏ</th>
                <th>Loại Mũ</th>
                <th>Kích Thước</th>
                <th>Loại Kính</th>
                <th>Chất Liệu Đệm</th>
                <th>Màu Sắc</th>
                <th>Số Lượng</th>
                <th>Đơn Giá</th>
                <th>Xuất Xứ</th>
                <th colspan="3">Hành Động</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${listSanPhamChiTiet}" var="sp" varStatus="i">
                <tr>
                    <td>${i.index + 1}</td>
                    <td class="ellipsis">${sp.idSanPham.ten}</td> <!-- Truncate long product names -->
                    <td class="ellipsis">${sp.idThuongHieu.ten}</td> <!-- Truncate long brand names -->
                    <td>${sp.idChatLieuVo.ten}</td>
                    <td>${sp.idLoaiMu.ten}</td>
                    <td>${sp.idKichThuoc.ten}</td>
                    <td>${sp.idLoaiKinh.ten}</td>
                    <td>${sp.idChatLieuDem.ten}</td>
                    <td>${sp.idMauSac.ten}</td>
                    <td>${sp.sl}</td>
                    <td>${sp.getFormattedGia()}</td>
                    <td>${sp.xuatXu}</td>
                    <td colspan="3">
                        <form action="/ban-hang/them-san-pham" method="post">
                            <input type="hidden" name="hoaDonId" value="${selectedHoaDonId}"/>
                            <input type="hidden" name="sanPhamId" value="${sp.id}"/>
                            <input type="number" name="soLuong" value="1" min="1" class="form-control-sm"/>
                            <button type="submit" class="btn btn-success">Thêm</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </main>
</div>
<script>
    function formatCurrency(value) {
        const numberString = value.toString().replace(/[^0-9]/g, '');
        const formattedValue = numberString.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        return formattedValue;
    }

    function calculateChange() {
        const total = parseFloat("${hoaDon.tongTien}") || 0;
        const payment = parseFloat(document.getElementById("customerPayment").value.replace(/,/g, '')) || 0;

        const change = payment - total;

        if (change < 0) {
            document.getElementById("change").value = formatCurrency(change);
        } else {
            document.getElementById("change").value = formatCurrency(0);
        }

        document.getElementById("paymentAmount").value = payment;
        document.getElementById("customerPayment").value = formatCurrency(payment);
    }
</script>
</body>
</html>
