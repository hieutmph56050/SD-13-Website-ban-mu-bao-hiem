CREATE DATABASE SafeRide
USE SafeRide

CREATE TABLE TaiKhoan (
	ID BIGINT IDENTITY(1,1) PRIMARY KEY  NOT NULL,
	ID_VaiTro BIGINT NOT NULL,
	TenDangNhap VARCHAR(30) NOT NULL,
	MatKhau VARCHAR(30) NOT NULL,
	HoTen NVARCHAR(255) NOT NULL,
	NgaySinh DATE,
	GioiTinh BIT DEFAULT 0,
	SDT VARCHAR(15),
	Email VARCHAR(50),
	CCCD VARCHAR(12),
	Avatar VARCHAR(255),
	NguoiTao VARCHAR(100),
	NgayTao DATETIME DEFAULT GETDATE(),
	NgayCapNhat DATETIME,
	TrangThai BIT NOT NULL,
);	

CREATE TABLE VaiTro (
	ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),
	TenVaiTro NVARCHAR(100) NOT NULL,
	NguoiTao VARCHAR(100),
	NgayTao DATETIME DEFAULT GETDATE(),
	NguoiCapNhat VARCHAR(100),
	NgayCapNhat DATETIME,
	MoTa NVARCHAR(255)
);

CREATE TABLE SanPham (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),              -- Khóa chính, tự động tăng từ 1
    ID_SanPhamChiTiet BIGINT NOT NULL,                           -- Khóa ngoại liên kết với bảng SanPhamChiTiet
    Ten NVARCHAR(255) NOT NULL,                                  -- Tên sản phẩm (không được để NULL)
    TrangThai VARCHAR(50),                                       -- Trạng thái sản phẩm (có thể để NULL)
    MoTa NVARCHAR(1000),                                         -- Mô tả sản phẩm (có thể để NULL)
);

CREATE TABLE SanPhamChiTiet (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),              -- Khóa chính, tự động tăng từ 1
    ID_SP BIGINT NOT NULL,                                      -- Khóa ngoại liên kết với bảng SanPham
    ID_ThuongHieu BIGINT,                                      -- Khóa ngoại liên kết với bảng ThuongHieu
    ID_ChatLieuVo BIGINT,                                      -- Khóa ngoại liên kết với bảng ChatLieuVo                                   
    ID_LoaiMu BIGINT,                                          -- Khóa ngoại liên kết với bảng LoaiMu
    ID_KichThuoc BIGINT,                                       -- Khóa ngoại liên kết với bảng KichThuoc
    ID_KhuyenMai BIGINT,                                       -- Khóa ngoại liên kết với bảng KhuyenMai
    ID_LoaiKinh BIGINT,                                       -- Khóa ngoại liên kết với bảng KhuyenMai
    ID_ChatLieuDem BIGINT,                                     -- Khóa ngoại liên kết với bảng ChatLieuDem
    ID_MauSac BIGINT,                                          -- Khóa ngoại liên kết với bảng MauSac
    SoLuong INT NOT NULL,                                      -- Số lượng sản phẩm (không được để NULL)
    MoTaChiTiet NVARCHAR(1000),                                -- Mô tả chi tiết sản phẩm (có thể để NULL)
    TrangThai VARCHAR(50) NOT NULL,                            -- Trạng thái sản phẩm (không được để NULL)
    NguoiTao NVARCHAR(255) NOT NULL,                           -- Người tạo sản phẩm (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,               -- Ngày tạo (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                                -- Người cập nhật sản phẩm (có thể để NULL)
    XuatXu NVARCHAR(255),                                      -- Xuất xứ sản phẩm (có thể để NULL)
    NgayCapNhat DATETIME,                                      -- Ngày cập nhật sản phẩm (có thể để NULL)
);

CREATE TABLE GioHang (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),  -- Khóa chính, tự động tăng từ 1
    ID_TaiKhoan BIGINT NOT NULL,                    -- Khóa ngoại liên kết với bảng TaiKhoan
    TrangThai BIT NOT NULL,                         -- Trạng thái giỏ hàng (1: Hoạt động, 0: Đã thanh toán)
    NgayTao DATETIME DEFAULT GETDATE(),             -- Ngày tạo (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME,                           -- Ngày cập nhật (có thể để NULL)
);


CREATE TABLE GioHangChiTiet (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),  -- Khóa chính, tự động tăng từ 1
    ID_GioHang BIGINT NOT NULL,                     -- Khóa ngoại liên kết với bảng GioHang
    ID_SanPham BIGINT NOT NULL,                     -- Khóa ngoại liên kết với bảng SanPham
    Ma VARCHAR(50),                                -- Mã sản phẩm (có thể để NULL)
    DonGia DECIMAL(18, 2) NOT NULL,                 -- Đơn giá của sản phẩm (không được để NULL)
    SoLuong INT NOT NULL,                           -- Số lượng sản phẩm (không được để NULL)
    TrangThai VARCHAR(50),                          -- Trạng thái sản phẩm trong giỏ (có thể để NULL)
    NgayTao DATETIME DEFAULT GETDATE(),             -- Ngày tạo (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME,                           -- Ngày cập nhật (có thể để NULL)
);

CREATE TABLE Voucher (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    ID_HoaDon BIGINT NOT NULL,                              -- Khóa ngoại liên kết với bảng HoaDon
    MaVoucher VARCHAR(50) NOT NULL,                        -- Mã Voucher (không được để NULL)
    TenVoucher NVARCHAR(255) NOT NULL,                      -- Tên Voucher (không được để NULL)
    NgayBatDau DATETIME NOT NULL,                           -- Ngày bắt đầu hiệu lực của voucher
    NgayKetThuc DATETIME NOT NULL,                          -- Ngày kết thúc hiệu lực của voucher
    GiaTriVoucher DECIMAL(18, 2) NOT NULL,                  -- Giá trị của voucher (không được để NULL)
    GiaTriVoucherToiDa DECIMAL(18, 2),                      -- Giá trị tối đa mà voucher có thể giảm (có thể NULL)
    GioiHanSoLuong INT,                                     -- Giới hạn số lượng voucher có thể sử dụng (có thể NULL)
    TrangThai VARCHAR(50),                                  -- Trạng thái của voucher (có thể NULL)
    MoTa NVARCHAR(1000),                                    -- Mô tả voucher (có thể NULL)
);

CREATE TABLE ThanhToanChiTiet (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    ID_HoaDon BIGINT NOT NULL,                              -- Khóa ngoại liên kết với bảng HoaDon
    TenThanhToan NVARCHAR(255) NOT NULL,                   -- Tên hình thức thanh toán (không được để NULL)
    TongTien DECIMAL(18, 2) NOT NULL,                       -- Tổng tiền thanh toán (không được để NULL)
    NgayThanhToan DATETIME NOT NULL,                        -- Ngày thanh toán (không được để NULL)
    TrangThai VARCHAR(50) NOT NULL,                         -- Trạng thái của thanh toán (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,            -- Ngày tạo chi tiết thanh toán (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME,                                   -- Ngày cập nhật chi tiết thanh toán (có thể để NULL)
    NguoiTao NVARCHAR(255) NOT NULL,                        -- Người tạo chi tiết thanh toán (không được để NULL)
    NguoiCapNhat NVARCHAR(255),                             -- Người cập nhật chi tiết thanh toán (có thể để NULL)
);

CREATE TABLE HoaDon (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    MaHoaDon NVARCHAR(50) NOT NULL,                        -- Mã hóa đơn (không được để NULL)
    ID_TaiKhoan BIGINT NOT NULL,                           -- Khóa ngoại liên kết với bảng TaiKhoan
    ID_Voucher BIGINT,                                     -- Khóa ngoại liên kết với bảng Voucher (có thể để NULL)
    ID_ThanhToan BIGINT NOT NULL,                          -- Khóa ngoại liên kết với bảng ThanhToan
    NgayThanhToan DATETIME NOT NULL,                       -- Ngày thanh toán (không được để NULL)
    NgayGiaoHang DATETIME,                                 -- Ngày giao hàng (có thể để NULL)
    NgayNhan DATETIME,                                     -- Ngày nhận hàng (có thể để NULL)
    GiaGiam DECIMAL(18, 2),                                -- Giá trị giảm từ voucher hoặc khuyến mãi (có thể để NULL)
    TongTien DECIMAL(18, 2) NOT NULL,                      -- Tổng tiền của hóa đơn (không được để NULL)
    SoTienDaTra DECIMAL(18, 2) NOT NULL,                   -- Số tiền đã thanh toán (không được để NULL)
    GhiChu NVARCHAR(1000),                                 -- Ghi chú (có thể để NULL)
    DiaChi NVARCHAR(255) NOT NULL,                         -- Địa chỉ giao hàng (không được để NULL)
    TrangThai VARCHAR(50) NOT NULL,                        -- Trạng thái của hóa đơn (không được để NULL)
    NguoiTao NVARCHAR(255) NOT NULL,                       -- Người tạo hóa đơn (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,           -- Ngày tạo hóa đơn (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                            -- Người cập nhật hóa đơn (có thể để NULL)
    NgayCapNhat DATETIME,                                  -- Ngày cập nhật hóa đơn (có thể để NULL)
);

CREATE TABLE HoaDonChiTiet (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    ID_HoaDon BIGINT NOT NULL,                              -- Khóa ngoại liên kết với bảng HoaDon
    ID_SPCT BIGINT NOT NULL,                                -- Khóa ngoại liên kết với bảng SanPhamChiTiet
    MaHDCT NVARCHAR(50) NOT NULL,                           -- Mã chi tiết hóa đơn (không được để NULL)
    TongTien DECIMAL(18, 2) NOT NULL,                       -- Tổng tiền cho chi tiết hóa đơn (không được để NULL)
    SoLuong INT NOT NULL,                                   -- Số lượng sản phẩm (không được để NULL)
    GhiChu NVARCHAR(1000),                                 -- Ghi chú (có thể để NULL)
    TrangThai VARCHAR(50) NOT NULL,                         -- Trạng thái của chi tiết hóa đơn (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,            -- Ngày tạo chi tiết hóa đơn (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME,                                   -- Ngày cập nhật chi tiết hóa đơn (có thể để NULL)
);

CREATE TABLE KhuyenMai (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),                -- Khóa chính, tự động tăng từ 1
    MaKhuyenMai NVARCHAR(50) NOT NULL,                            -- Mã khuyến mãi (không được để NULL)
    TenKhuyenMai NVARCHAR(255) NOT NULL,                         -- Tên khuyến mãi (không được để NULL)
    GiaTriKhuyenMai DECIMAL(18, 2) NOT NULL,                     -- Giá trị khuyến mãi (không được để NULL)
    NgayBatDau DATETIME NOT NULL,                                 -- Ngày bắt đầu khuyến mãi (không được để NULL)
    NgayKetThuc DATETIME NOT NULL,                                -- Ngày kết thúc khuyến mãi (không được để NULL)
    PhuongThucKhuyenMai NVARCHAR(50),                            -- Phương thức khuyến mãi (có thể để NULL)
    DieuKienKhuyenMai NVARCHAR(255),                             -- Điều kiện khuyến mãi (có thể để NULL)
    TrangThai VARCHAR(50) NOT NULL,                               -- Trạng thái khuyến mãi (không được để NULL)
    NguoiTao NVARCHAR(255) NOT NULL,                             -- Người tạo khuyến mãi (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,                 -- Ngày tạo khuyến mãi (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                                  -- Người cập nhật khuyến mãi (có thể để NULL)
    NgayCapNhat DATETIME                                         -- Ngày cập nhật khuyến mãi (có thể để NULL)
);


CREATE TABLE LoaiKinh (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    MaLoaiKinh NVARCHAR(50) NOT NULL,                       -- Mã loại kính (không được để NULL)
    TenLoaiKinh NVARCHAR(255) NOT NULL,                     -- Tên loại kính (không được để NULL)
    TrangThai VARCHAR(50) NOT NULL,                         -- Trạng thái của loại kính (không được để NULL)
    NguoiTao NVARCHAR(255) NOT NULL,                        -- Người tạo loại kính (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,            -- Ngày tạo loại kính (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                             -- Người cập nhật loại kính (có thể để NULL)
    NgayCapNhat DATETIME,                                   -- Ngày cập nhật loại kính (có thể để NULL)
    MoTa NVARCHAR(1000)                                    -- Mô tả loại kính (có thể để NULL)
);

CREATE TABLE ThuongHieu (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    TenThuongHieu NVARCHAR(255) NOT NULL,                   -- Tên thương hiệu (không được để NULL)
    MoTa NVARCHAR(1000),                                     -- Mô tả thương hiệu (có thể để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,            -- Ngày tạo thương hiệu (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME                                     -- Ngày cập nhật thương hiệu (có thể để NULL)
);

CREATE TABLE ChatLieuVo (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),         -- Khóa chính, tự động tăng từ 1
    MaChatLieu NVARCHAR(50) NOT NULL,                      -- Mã chất liệu (không được để NULL)
    TenChatLieuVo NVARCHAR(255) NOT NULL,                  -- Tên chất liệu vỏ (không được để NULL)
    TrangThai NVARCHAR(50) NOT NULL,                       -- Trạng thái của chất liệu vỏ (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,           -- Ngày tạo chất liệu vỏ (tự động lấy thời gian hiện tại)
    NguoiTao NVARCHAR(255) NOT NULL,                       -- Người tạo chất liệu (không được để NULL)
    NguoiCapNhat NVARCHAR(255),                            -- Người cập nhật chất liệu (có thể để NULL)
    NgayCapNhat DATETIME,                                  -- Ngày cập nhật chất liệu (có thể để NULL)
    MoTa NVARCHAR(1000)                                    -- Mô tả chất liệu vỏ (có thể để NULL)
);

CREATE TABLE LoaiMu (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    MaLoai NVARCHAR(50) NOT NULL,                           -- Mã loại (không được để NULL)
    MoTa NVARCHAR(1000),                                    -- Mô tả loại mũ (có thể để NULL)
    TenLoaiMu NVARCHAR(255) NOT NULL,                       -- Tên loại mũ (không được để NULL)
    NguoiTao NVARCHAR(255) NOT NULL,                        -- Người tạo loại mũ (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,            -- Ngày tạo (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                             -- Người cập nhật loại mũ (có thể để NULL)
    NgayCapNhat DATETIME,                                   -- Ngày cập nhật loại mũ (có thể để NULL)
    TrangThai NVARCHAR(50) NOT NULL                          -- Trạng thái loại mũ (không được để NULL)
);

CREATE TABLE KichThuoc (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    MaKichThuoc NVARCHAR(50) NOT NULL,                     -- Mã kích thước (không được để NULL)
    TenKichThuoc NVARCHAR(255) NOT NULL,                   -- Tên kích thước (không được để NULL)
    NguoiTao NVARCHAR(255) NOT NULL,                        -- Người tạo kích thước (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,            -- Ngày tạo kích thước (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                             -- Người cập nhật kích thước (có thể để NULL)
    NgayCapNhat DATETIME,                                   -- Ngày cập nhật kích thước (có thể để NULL)
    MoTa NVARCHAR(1000),                                    -- Mô tả kích thước (có thể để NULL)
    TrangThai VARCHAR(50) NOT NULL                          -- Trạng thái của kích thước (không được để NULL)
);

CREATE TABLE ChatLieuDem (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    MaChatLieu NVARCHAR(50) NOT NULL,                       -- Mã chất liệu đệm (không được để NULL)
    TenChatLieuDem NVARCHAR(255) NOT NULL,                  -- Tên chất liệu đệm (không được để NULL)
    TrangThai VARCHAR(50) NOT NULL,                          -- Trạng thái của chất liệu đệm (không được để NULL)
    NguoiTao NVARCHAR(255) NOT NULL,                        -- Người tạo chất liệu đệm (không được để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,            -- Ngày tạo (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                             -- Người cập nhật chất liệu đệm (có thể để NULL)
    NgayCapNhat DATETIME,                                   -- Ngày cập nhật chất liệu đệm (có thể để NULL)
    MoTa NVARCHAR(1000)                                     -- Mô tả chất liệu đệm (có thể để NULL)
);

CREATE TABLE MauSac (
    ID BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),          -- Khóa chính, tự động tăng từ 1
    MaMau NVARCHAR(50) NOT NULL,                            -- Mã màu (không được để NULL)
    TenMau NVARCHAR(255) NOT NULL,                          -- Tên màu (không được để NULL)
    MoTa NVARCHAR(1000),                                    -- Mô tả màu (có thể để NULL)
    NgayTao DATETIME DEFAULT GETDATE() NOT NULL,           -- Ngày tạo (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME                                    -- Ngày cập nhật màu (có thể để NULL)
);

-- Khóa ngoại cho bảng SanPhamChiTiet
ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_SanPham
FOREIGN KEY (ID_SP) REFERENCES SanPham(ID);

ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_ThuongHieu
FOREIGN KEY (ID_ThuongHieu) REFERENCES ThuongHieu(ID);

ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_ChatLieuVo
FOREIGN KEY (ID_ChatLieuVo) REFERENCES ChatLieuVo(ID);

ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_LoaiMu
FOREIGN KEY (ID_LoaiMu) REFERENCES LoaiMu(ID);

ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_KichThuoc
FOREIGN KEY (ID_KichThuoc) REFERENCES KichThuoc(ID);

ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_KhuyenMai
FOREIGN KEY (ID_KhuyenMai) REFERENCES KhuyenMai(ID);

ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_ChatLieuDem
FOREIGN KEY (ID_ChatLieuDem) REFERENCES ChatLieuDem(ID);

ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_MauSac
FOREIGN KEY (ID_MauSac) REFERENCES MauSac(ID);

-- Khóa ngoại cho bảng GioHang
ALTER TABLE GioHang
ADD CONSTRAINT FK_GioHang_TaiKhoan
FOREIGN KEY (ID_TaiKhoan) REFERENCES TaiKhoan(ID);

-- Khóa ngoại cho bảng GioHangChiTiet
ALTER TABLE GioHangChiTiet
ADD CONSTRAINT FK_GioHangChiTiet_GioHang
FOREIGN KEY (ID_GioHang) REFERENCES GioHang(ID);

ALTER TABLE GioHangChiTiet
ADD CONSTRAINT FK_GioHangChiTiet_SanPham
FOREIGN KEY (ID_SanPham) REFERENCES SanPham(ID);

-- Khóa ngoại cho bảng Voucher
ALTER TABLE Voucher
ADD CONSTRAINT FK_Voucher_HoaDon
FOREIGN KEY (ID_HoaDon) REFERENCES HoaDon(ID);

-- Khóa ngoại cho bảng ThanhToanChiTiet
ALTER TABLE ThanhToanChiTiet
ADD CONSTRAINT FK_ThanhToanChiTiet_HoaDon
FOREIGN KEY (ID_HoaDon) REFERENCES HoaDon(ID);

-- Khóa ngoại cho bảng HoaDon
ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_TaiKhoan
FOREIGN KEY (ID_TaiKhoan) REFERENCES TaiKhoan(ID);

ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_Voucher
FOREIGN KEY (ID_Voucher) REFERENCES Voucher(ID);

ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_ThanhToan
FOREIGN KEY (ID_ThanhToan) REFERENCES ThanhToanChiTiet(ID);

-- Khóa ngoại cho bảng HoaDonChiTiet
ALTER TABLE HoaDonChiTiet
ADD CONSTRAINT FK_HoaDonChiTiet_HoaDon
FOREIGN KEY (ID_HoaDon) REFERENCES HoaDon(ID);

ALTER TABLE HoaDonChiTiet
ADD CONSTRAINT FK_HoaDonChiTiet_SanPhamChiTiet
FOREIGN KEY (ID_SPCT) REFERENCES SanPhamChiTiet(ID);

-- Khóa ngoại cho bảng TaiKhoan
ALTER TABLE TaiKhoan
ADD CONSTRAINT FK_TaiKhoan_VaiTro
FOREIGN KEY (ID_VaiTro) REFERENCES VaiTro(ID);

-- Khóa ngoại cho bảng SanPhamChiTiet
ALTER TABLE SanPhamChiTiet
ADD CONSTRAINT FK_SanPhamChiTiet_LoaiKinh
FOREIGN KEY (ID_LoaiKinh) REFERENCES LoaiKinh(ID);

---INSERT VaiTro
DBCC CHECKIDENT ('VaiTro', RESEED, 0);
INSERT INTO VaiTro (TenVaiTro, NguoiTao, MoTa)
VALUES 
    (N'Admin', 'System', N'Quản trị hệ thống với đầy đủ quyền hạn'),
    (N'Nhân viên', 'System', N'Nhân viên của công ty, có quyền hạn hạn chế'),
    (N'Khách hàng', 'System', N'Khách hàng sử dụng dịch vụ của công ty');

--INSERT TaiKhoan
INSERT INTO TaiKhoan (ID_VaiTro, TenDangNhap, MatKhau, HoTen, NgaySinh, GioiTinh, SDT, Email, CCCD, Avatar, NguoiTao, TrangThai)
VALUES 
    (1, 'tuan', 'tuan', N'Phạm Anh Tuấn', '2004-12-01', 1, '0123456789', 'tuanpaph35819@fpt.edu.vn', '123456789012', 'avatar1.jpg', NULL, 1),
    (1, 'nhanvien1', 'password123', N'Trần Thị B', '1992-02-02', 0, '0987654321', 'nhanvien1@example.com', '123456789013', 'avatar2.jpg', NULL, 1),
    (1, 'khachhang1', 'password123', N'Lê Văn C', '1995-03-03', 1, '0123987654', 'khachhang1@example.com', '123456789014', 'avatar3.jpg', NULL, 1),
    (1, 'admin2', 'password123', N'Phạm Văn D', '1985-04-04', 1, '0123456780', 'admin2@example.com', '123456789015', 'avatar4.jpg', NULL, 1),
    (2, 'nhanvien2', 'password123', N'Nguyễn Thị E', '1993-05-05', 0, '0123456790', 'nhanvien2@example.com', '123456789016', 'avatar5.jpg', NULL, 1);
--INSERT MauSac
INSERT INTO MauSac (MaMau, TenMau, MoTa, NgayCapNhat)
VALUES 
    (N'M1', N'Màu Đỏ', N'Màu đỏ tươi, thể hiện sức mạnh và đam mê.', NULL),
    (N'M2', N'Màu Xanh', N'Màu xanh dương, mang lại cảm giác bình yên và tươi mát.', NULL),
    (N'M3', N'Màu Vàng', N'Màu vàng tươi sáng, biểu tượng của sự vui vẻ và lạc quan.', NULL),
    (N'M4', N'Màu Xanh Lục', N'Màu xanh lục, đại diện cho thiên nhiên và sự sống.', NULL),
    (N'M5', N'Màu Tím', N'Màu tím lãng mạn, tượng trưng cho sự sang trọng và bí ẩn.', NULL),
    (N'M6', N'Màu Cam', N'Màu cam rực rỡ, thể hiện sự sáng tạo và năng lượng.', NULL),
    (N'M7', N'Màu Hồng', N'Màu hồng nhẹ nhàng, biểu tượng của tình yêu và sự ngọt ngào.', NULL),
    (N'M8', N'Màu Trắng', N'Màu trắng tinh khiết, đại diện cho sự thuần khiết và đơn giản.', NULL),
    (N'M9', N'Màu Đen', N'Màu đen mạnh mẽ, thể hiện sự bí ẩn và thanh lịch.', NULL);

--INSERT KichThuoc
INSERT INTO KichThuoc (MaKichThuoc, TenKichThuoc, NguoiTao, TrangThai, MoTa)
VALUES
('S', N'Nhỏ (S)', N'Admin', N'Hoạt động', N'Kích thước nhỏ, dành cho người có vòng đầu nhỏ'),
('M', N'Vừa (M)', N'Admin', N'Hoạt động', N'Kích thước vừa, phù hợp cho nhiều người'),
('L', N'Lớn (L)', N'Admin', N'Hoạt động', N'Kích thước lớn, dành cho người có vòng đầu lớn'),
('XL', N'Cực Lớn (XL)', N'Admin', N'Hoạt động', N'Kích thước cực lớn, dành cho người có vòng đầu rất lớn'),
('XXL', N'Siêu Lớn (XXL)', N'Admin', N'Hoạt động', N'Kích thước siêu lớn, phù hợp cho người có vòng đầu lớn nhất');

--INSERT LoaiMu
INSERT INTO LoaiMu (MaLoai, MoTa, TenLoaiMu, NguoiTao, TrangThai)
VALUES
('MBH01', N'Mũ bảo hiểm nửa đầu, thường dùng cho người lái xe máy trong đô thị', N'Mũ bảo hiểm nửa đầu', N'Admin', N'Hoạt động'),
('MBH02', N'Mũ bảo hiểm cả đầu, bao phủ toàn bộ phần đầu, thường dùng cho những người đi xe phân khối lớn', N'Mũ bảo hiểm cả đầu', N'Admin', N'Hoạt động'),
('MBH03', N'Mũ bảo hiểm 3/4 đầu, bảo vệ phần lớn đầu, phù hợp cho cả xe máy và xe phân khối lớn', N'Mũ bảo hiểm 3/4 đầu', N'Admin', N'Hoạt động'),
('MBH04', N'Mũ bảo hiểm lật hàm, có thể chuyển đổi giữa mũ nửa đầu và mũ cả đầu bằng cách lật phần hàm lên', N'Mũ bảo hiểm lật hàm', N'Admin', N'Hoạt động'),
('MBH05', N'Mũ bảo hiểm có kính, tích hợp kính chắn gió, giúp giảm bớt ánh sáng mặt trời và bụi bẩn', N'Mũ bảo hiểm có kính', N'Admin', N'Hoạt động');

--INSERT ChatLieuVo
INSERT INTO ChatLieuVo (MaChatLieu, TenChatLieuVo, TrangThai, NguoiTao, MoTa)
VALUES
('CLV01', N'Nhựa ABS', N'Hoạt động', N'Admin', N'Chất liệu nhựa ABS có khả năng chịu lực tốt, thường được sử dụng cho mũ bảo hiểm nửa đầu và mũ bảo hiểm 3/4 đầu.'),
('CLV02', N'Nhựa Polycarbonate', N'Hoạt động', N'Admin', N'Nhựa Polycarbonate nhẹ, bền và có khả năng chịu va đập tốt, thường được sử dụng cho mũ bảo hiểm lật hàm và mũ bảo hiểm cả đầu.'),
('CLV03', N'Kim loại hợp kim', N'Hoạt động', N'Admin', N'Chất liệu kim loại hợp kim thường dùng cho các mũ bảo hiểm phân khối lớn, cung cấp sự bảo vệ vượt trội.'),
('CLV04', N'Vải tổng hợp', N'Hoạt động', N'Admin', N'Vải tổng hợp được sử dụng cho phần lót trong của mũ bảo hiểm, giúp thấm hút mồ hôi và mang lại cảm giác thoải mái.'),
('CLV05', N'Carbon', N'Hoạt động', N'Admin', N'Chất liệu carbon siêu nhẹ và bền, được sử dụng cho mũ bảo hiểm cao cấp, có khả năng chống va đập cực tốt.');
