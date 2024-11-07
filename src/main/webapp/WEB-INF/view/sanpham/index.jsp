<!DOCTYPE html>
<html lang="vi">
<head>
    <meta charset="UTF-8">
    <title>Danh Sách Sản Phẩm</title>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            fetch('/sanpham/danhsach')
                .then(response => response.json())
                .then(data => {
                    if (data.data && data.data.length > 0) {
                        let table = document.getElementById('productTable');
                        data.data.forEach(product => {
                            let row = table.insertRow();
                            let cell1 = row.insertCell(0);
                            let cell2 = row.insertCell(1);
                            let cell3 = row.insertCell(2);
                            let cell4 = row.insertCell(3);
                            cell1.innerHTML = product.id; // Đảm bảo thuộc tính đúng với đối tượng SanPham
                            cell2.innerHTML = product.ten; // Đảm bảo thuộc tính đúng với đối tượng SanPham
                            cell3.innerHTML = product.tt; // Đảm bảo thuộc tính đúng với đối tượng SanPham
                            cell4.innerHTML = product.moTa; // Đảm bảo thuộc tính đúng với đối tượng SanPham
                        });
                    } else {
                        console.error('Không có dữ liệu sản phẩm nào.');
                    }
                })
                .catch(error => console.error('Lỗi khi lấy dữ liệu:', error));
        });
    </script>
</head>
<body>
<h1>Danh Sách Sản Phẩm</h1>
<table id="productTable" border="1">
    <tr>
        <th>ID</th>
        <th>Tên Sản Phẩm</th>
        <th>Trạng Thái</th>
        <th>Mô Tả</th>
    </tr>
</table>
</body>
</html>
