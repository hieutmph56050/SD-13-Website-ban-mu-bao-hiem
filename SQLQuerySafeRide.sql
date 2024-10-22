

CREATE TABLE TaiKhoan
(
    ID          BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
    ID_VaiTro   BIGINT      NOT NULL,
    TenDangNhap VARCHAR(30) NOT NULL,
    MatKhau     VARCHAR(30) NOT NULL,
    HoTen       NVARCHAR(255) NOT NULL,
    NgaySinh    DATE,
    GioiTinh    BIT      DEFAULT 0,
    SDT         VARCHAR(15),
    Email       VARCHAR(50),
    CCCD        VARCHAR(12),
    Avatar      VARCHAR(255),
    NgayTao     DATETIME DEFAULT GETDATE(),
    NgayCapNhat DATETIME,
    TrangThai   BIT         NOT NULL,
);

CREATE TABLE VaiTro
(
    ID           BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1),
    TenVaiTro    NVARCHAR(100) NOT NULL,
	MoTa         NVARCHAR(255),
    NguoiTao     VARCHAR(100),
    NgayTao      DATETIME DEFAULT GETDATE(),
    NguoiCapNhat VARCHAR(100),
    NgayCapNhat  DATETIME
);

CREATE TABLE SanPham
(
    ID                BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    Ten               NVARCHAR(255) NOT NULL,
    TrangThai         VARCHAR(50),                                -- Trạng thái sản phẩm (có thể để NULL)
    MoTa              NVARCHAR(1000),                             -- Mô tả sản phẩm (có thể để NULL)
);

CREATE TABLE SanPhamChiTiet
(
    ID             BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    ID_SP          BIGINT                     NOT NULL,                            -- Khóa ngoại liên kết với bảng SanPham
    ID_ThuongHieu  BIGINT,                                                         -- Khóa ngoại liên kết với bảng ThuongHieu
    ID_ChatLieuVo  BIGINT,                                                         -- Khóa ngoại liên kết với bảng ChatLieuVo
    ID_LoaiMu      BIGINT,                                                         -- Khóa ngoại liên kết với bảng LoaiMu
    ID_KichThuoc   BIGINT,                                                         -- Khóa ngoại liên kết với bảng KichThuoc
    ID_KhuyenMai   BIGINT,                                                         -- Khóa ngoại liên kết với bảng KhuyenMai
    ID_LoaiKinh    BIGINT,                                                         -- Khóa ngoại liên kết với bảng KhuyenMai
    ID_ChatLieuDem BIGINT,                                                         -- Khóa ngoại liên kết với bảng ChatLieuDem
    ID_MauSac      BIGINT,
	MaSPCT varchar(100) , -- Khóa ngoại liên kết với bảng MauSac
    SoLuong        INT                        NOT NULL,                            -- Số lượng sản phẩm (không được để NULL)
    DonGia DECIMAL(10, 2) NOT NULL,
	Anh				Text,															-- Ảnh
	XuatXu			NVARCHAR(255),                                                  -- Xuất xứ sản phẩm (có thể để NULL)
    MoTaChiTiet		NVARCHAR(1000),													-- Mô tả chi tiết sản phẩm 
	TrangThai      VARCHAR(50)                NOT NULL,                            -- Trạng thái sản phẩm (không được để NULL)
    NguoiTao       NVARCHAR(255) NOT NULL,                                         -- Người tạo sản phẩm (không được để NULL)
    NgayTao        DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo (tự động lấy thời gian hiện tại)
    NguoiCapNhat   NVARCHAR(255),                                                  -- Người cập nhật sản phẩm (có thể để NULL)
    NgayCapNhat    DATETIME,                                                       -- Ngày cập nhật sản phẩm (có thể để NULL)
);

CREATE TABLE GioHang
(
    ID          BIGINT NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    ID_TaiKhoan BIGINT NOT NULL,                            -- Khóa ngoại liên kết với bảng TaiKhoan
    TrangThai   BIT    NOT NULL,                            -- Trạng thái giỏ hàng (1: Hoạt động, 0: Đã thanh toán)
    NgayTao     DATETIME DEFAULT GETDATE(),                 -- Ngày tạo (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME,                                   -- Ngày cập nhật (có thể để NULL)
);


CREATE TABLE GioHangChiTiet
(
    ID          BIGINT         NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    ID_GioHang  BIGINT         NOT NULL,                            -- Khóa ngoại liên kết với bảng GioHang
    ID_SPCT		BIGINT         NOT NULL,                            -- Khóa ngoại liên kết với bảng SanPham
    Ma          VARCHAR(50),                                        -- Mã sản phẩm (có thể để NULL)
    DonGia      DECIMAL(18, 2) NOT NULL,                            -- Đơn giá của sản phẩm (không được để NULL)
    SoLuong     INT            NOT NULL,                            -- Số lượng sản phẩm (không được để NULL)
    TrangThai   VARCHAR(50),                                        -- Trạng thái sản phẩm trong giỏ (có thể để NULL)
    NgayTao     DATETIME DEFAULT GETDATE(),                         -- Ngày tạo (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME,                                           -- Ngày cập nhật (có thể để NULL)
);

CREATE TABLE Voucher
(
    ID                 BIGINT         NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaVoucher          VARCHAR(50)    NOT NULL,                            -- Mã Voucher (không được để NULL)
    TenVoucher         NVARCHAR(255) NOT NULL,                             -- Tên Voucher (không được để NULL)
    NgayBatDau         DATETIME       NOT NULL,                            -- Ngày bắt đầu hiệu lực của voucher
    NgayKetThuc        DATETIME       NOT NULL,                            -- Ngày kết thúc hiệu lực của voucher
    GiaTriVoucher      DECIMAL(18, 2) NOT NULL,                            -- Giá trị của voucher (không được để NULL)
    GiaTriVoucherToiDa DECIMAL(18, 2),                                     -- Giá trị tối đa mà voucher có thể giảm (có thể NULL)
    GioiHanSoLuong     INT,                                                -- Giới hạn số lượng voucher có thể sử dụng (có thể NULL)
    TrangThai          VARCHAR(50),                                        -- Trạng thái của voucher (có thể NULL)
    MoTa               NVARCHAR(1000),                                     -- Mô tả voucher (có thể NULL)
);

CREATE TABLE ThanhToan
(
    ID            BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    ID_HoaDon     BIGINT                     NOT NULL,                            -- Khóa ngoại liên kết với bảng HoaDon
    TenThanhToan  NVARCHAR(255) NOT NULL,                                         -- Tên hình thức thanh toán (không được để NULL)
    TongTien      DECIMAL(18, 2)             NOT NULL,                            -- Tổng tiền thanh toán (không được để NULL)
    NgayThanhToan DATETIME                   NOT NULL,                            -- Ngày thanh toán (không được để NULL)
    TrangThai     VARCHAR(50)                NOT NULL,                            -- Trạng thái của thanh toán (không được để NULL)
    NgayTao       DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo chi tiết thanh toán (tự động lấy thời gian hiện tại)
    NgayCapNhat   DATETIME,                                                       -- Ngày cập nhật chi tiết thanh toán (có thể để NULL)
    NguoiTao      NVARCHAR(255) NOT NULL,                                         -- Người tạo chi tiết thanh toán (không được để NULL)
    NguoiCapNhat  NVARCHAR(255),                                                  -- Người cập nhật chi tiết thanh toán (có thể để NULL)
);

CREATE TABLE HoaDon (
    ID				BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaHoaDon		VARCHAR(100),
    ID_TaiKhoan		INT NOT NULL,
	ID_Voucher		INT,
    LoaiHoaDon		bit,                                       -- Loại hóa đơn tại quầy hoặc qua đặt hàng
	NgayGiaoHang	Date,
	NgayNhan		Date,
	GiaGiam			DECIMAL(18, 2),
	TongTien		DECIMAL(18, 2) NOT NULL,                   -- Tổng tiền hóa đơn
    SoTienDaTra		DECIMAL(18, 2),
	GhiChu			NVARCHAR(1000),
	DiaChi			VARCHAR(255) NOT NULL,                        -- Địa chỉ giao hàng
	TrangThai		NVARCHAR(50) NOT NULL,                         -- Trạng thái thanh toán
	NguoiTao		NVARCHAR(255) NOT NULL,
	NgayTao			DATETIME DEFAULT CURRENT_TIMESTaMP NOT NULL, -- Ngày tạo hóa đơn
	NguoiCapNhat	NVARCHAR(255),
	NgayCapNhat		DATETIME,
);

CREATE TABLE HoaDonChiTiet
(
    ID          BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    ID_HoaDon   BIGINT                     NOT NULL,                            -- Khóa ngoại liên kết với bảng HoaDon
    ID_SPCT     BIGINT                     NOT NULL,                            -- Khóa ngoại liên kết với bảng SanPhamChiTiet
    MaHDCT      NVARCHAR(50) NOT NULL,                                          -- Mã chi tiết hóa đơn (không được để NULL)
    TongTien    DECIMAL(18, 2)             NOT NULL,                            -- Tổng tiền cho chi tiết hóa đơn (không được để NULL)
    SoLuong     INT                        NOT NULL,                            -- Số lượng sản phẩm (không được để NULL)
    GhiChu      NVARCHAR(1000),                                                 -- Ghi chú (có thể để NULL)
    TrangThai   VARCHAR(50)                NOT NULL,   							-- Trạng thái của chi tiết hóa đơn (không được để NULL)
    NgayTao     DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo chi tiết hóa đơn (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME,                                                       -- Ngày cập nhật chi tiết hóa đơn (có thể để NULL)
);

CREATE TABLE KhuyenMai
(
    ID                  BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaKhuyenMai         NVARCHAR(50) NOT NULL,                                          -- Mã khuyến mãi (không được để NULL)
    TenKhuyenMai        NVARCHAR(255) NOT NULL,                                         -- Tên khuyến mãi (không được để NULL)
    GiaTriKhuyenMai     DECIMAL(18, 2)             NOT NULL,                            -- Giá trị khuyến mãi (không được để NULL)
    NgayBatDau          DATETIME                   NOT NULL,                            -- Ngày bắt đầu khuyến mãi (không được để NULL)
    NgayKetThuc         DATETIME                   NOT NULL,                            -- Ngày kết thúc khuyến mãi (không được để NULL)
    PhuongThucKhuyenMai NVARCHAR(50),                                                   -- Phương thức khuyến mãi (có thể để NULL)
    DieuKienKhuyenMai   NVARCHAR(255),                                                  -- Điều kiện khuyến mãi (có thể để NULL)
    TrangThai           VARCHAR(50)                NOT NULL,                            -- Trạng thái khuyến mãi (không được để NULL)
    NguoiTao            NVARCHAR(255) NOT NULL,                                         -- Người tạo khuyến mãi (không được để NULL)
    NgayTao             DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo khuyến mãi (tự động lấy thời gian hiện tại)
    NguoiCapNhat        NVARCHAR(255),                                                  -- Người cập nhật khuyến mãi (có thể để NULL)
    NgayCapNhat         DATETIME                                                        -- Ngày cập nhật khuyến mãi (có thể để NULL)
);


CREATE TABLE LoaiKinh
(
    ID           BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaLoaiKinh   NVARCHAR(50) NOT NULL,                                          -- Mã loại kính (không được để NULL)
    TenLoaiKinh  NVARCHAR(255) NOT NULL,                                         -- Tên loại kính (không được để NULL)
    TrangThai    VARCHAR(50)                NOT NULL,                            -- Trạng thái của loại kính (không được để NULL)
    NguoiTao     NVARCHAR(255) NOT NULL,                                         -- Người tạo loại kính (không được để NULL)
    NgayTao      DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo loại kính (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                                                  -- Người cập nhật loại kính (có thể để NULL)
    NgayCapNhat  DATETIME,                                                       -- Ngày cập nhật loại kính (có thể để NULL)
    MoTa         NVARCHAR(1000)                                                  -- Mô tả loại kính (có thể để NULL)
);

CREATE TABLE ThuongHieu
(
    ID            BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    TenThuongHieu NVARCHAR(255) NOT NULL,                                         -- Tên thương hiệu (không được để NULL)
    MoTa          NVARCHAR(1000),                                                 -- Mô tả thương hiệu (có thể để NULL)
    NgayTao       DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo thương hiệu (tự động lấy thời gian hiện tại)
    NgayCapNhat   DATETIME                                                        -- Ngày cập nhật thương hiệu (có thể để NULL)
);

CREATE TABLE ChatLieuVo
(
    ID            BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaChatLieu    NVARCHAR(50) NOT NULL,                                          -- Mã chất liệu (không được để NULL)
    TenChatLieuVo NVARCHAR(255) NOT NULL,                                         -- Tên chất liệu vỏ (không được để NULL)
    TrangThai     NVARCHAR(50) NOT NULL,                                          -- Trạng thái của chất liệu vỏ (không được để NULL)
    NgayTao       DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo chất liệu vỏ (tự động lấy thời gian hiện tại)
    NguoiTao      NVARCHAR(255) NOT NULL,                                         -- Người tạo chất liệu (không được để NULL)
    NguoiCapNhat  NVARCHAR(255),                                                  -- Người cập nhật chất liệu (có thể để NULL)
    NgayCapNhat   DATETIME,                                                       -- Ngày cập nhật chất liệu (có thể để NULL)
    MoTa          NVARCHAR(1000)                                                  -- Mô tả chất liệu vỏ (có thể để NULL)
);

CREATE TABLE LoaiMu
(
    ID           BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaLoaiMu	 NVARCHAR(50) NOT NULL,                                          -- Mã loại (không được để NULL)
    MoTa         NVARCHAR(1000),                                                 -- Mô tả loại mũ (có thể để NULL)
    TenLoaiMu    NVARCHAR(255) NOT NULL,                                         -- Tên loại mũ (không được để NULL)
    NguoiTao     NVARCHAR(255) NOT NULL,                                         -- Người tạo loại mũ (không được để NULL)
    NgayTao      DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                                                  -- Người cập nhật loại mũ (có thể để NULL)
    NgayCapNhat  DATETIME,                                                       -- Ngày cập nhật loại mũ (có thể để NULL)
    TrangThai    NVARCHAR(50) NOT NULL                                           -- Trạng thái loại mũ (không được để NULL)
);

CREATE TABLE KichThuoc
( 
    ID           BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaKichThuoc  NVARCHAR(50) NOT NULL,                                          -- Mã kích thước (không được để NULL)
    TenKichThuoc NVARCHAR(255) NOT NULL,                                         -- Tên kích thước (không được để NULL)
    NguoiTao     NVARCHAR(255) NOT NULL,                                         -- Người tạo kích thước (không được để NULL)
    NgayTao      DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo kích thước (tự động lấy thời gian hiện tại)
    NguoiCapNhat NVARCHAR(255),                                                  -- Người cập nhật kích thước (có thể để NULL)
    NgayCapNhat  DATETIME,                                                       -- Ngày cập nhật kích thước (có thể để NULL)
    MoTa         NVARCHAR(1000),                                                 -- Mô tả kích thước (có thể để NULL)
    TrangThai    VARCHAR(50)                NOT NULL                             -- Trạng thái của kích thước (không được để NULL)
);

CREATE TABLE ChatLieuDem
(
    ID             BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaChatLieu     NVARCHAR(50) NOT NULL,                                          -- Mã chất liệu đệm (không được để NULL)
    TenChatLieuDem NVARCHAR(255) NOT NULL,                                         -- Tên chất liệu đệm (không được để NULL)
    TrangThai      VARCHAR(50)                NOT NULL,                            -- Trạng thái của chất liệu đệm (không được để NULL)
    NguoiTao       NVARCHAR(255) NOT NULL,                                         -- Người tạo chất liệu đệm (không được để NULL)
    NgayTao        DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo (tự động lấy thời gian hiện tại)
    NguoiCapNhat   NVARCHAR(255),                                                  -- Người cập nhật chất liệu đệm (có thể để NULL)
    NgayCapNhat    DATETIME,                                                       -- Ngày cập nhật chất liệu đệm (có thể để NULL)
    MoTa           NVARCHAR(1000)                                                  -- Mô tả chất liệu đệm (có thể để NULL)
);

CREATE TABLE MauSac
(
    ID          BIGINT                     NOT NULL PRIMARY KEY IDENTITY(1, 1), -- Khóa chính, tự động tăng từ 1
    MaMau       NVARCHAR(50) NOT NULL,                                          -- Mã màu (không được để NULL)
    TenMau      NVARCHAR(255) NOT NULL,                                         -- Tên màu (không được để NULL)
    MoTa        NVARCHAR(1000),                                                 -- Mô tả màu (có thể để NULL)
    NgayTao     DATETIME DEFAULT GETDATE() NOT NULL,                            -- Ngày tạo (tự động lấy thời gian hiện tại)
    NgayCapNhat DATETIME                                                        -- Ngày cập nhật màu (có thể để NULL)
);

-- Khóa ngoại cho bảng SanPhamChiTiet
ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_SanPham
        FOREIGN KEY (ID_SP) REFERENCES SanPham (ID);

ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_ThuongHieu
        FOREIGN KEY (ID_ThuongHieu) REFERENCES ThuongHieu (ID);

ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_ChatLieuVo
        FOREIGN KEY (ID_ChatLieuVo) REFERENCES ChatLieuVo (ID);

ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_LoaiMu
        FOREIGN KEY (ID_LoaiMu) REFERENCES LoaiMu (ID);

ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_KichThuoc
        FOREIGN KEY (ID_KichThuoc) REFERENCES KichThuoc (ID);

ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_KhuyenMai
        FOREIGN KEY (ID_KhuyenMai) REFERENCES KhuyenMai (ID);

ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_ChatLieuDem
        FOREIGN KEY (ID_ChatLieuDem) REFERENCES ChatLieuDem (ID);

ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_MauSac
        FOREIGN KEY (ID_MauSac) REFERENCES MauSac (ID);

ALTER TABLE SanPhamChiTiet
    ADD CONSTRAINT FK_SanPhamChiTiet_LoaiKinh
        FOREIGN KEY (ID_LoaiKinh) REFERENCES LoaiKinh (ID);

-- Khóa ngoại cho bảng GioHang
ALTER TABLE GioHang
    ADD CONSTRAINT FK_GioHang_TaiKhoan
        FOREIGN KEY (ID_TaiKhoan) REFERENCES TaiKhoan (ID);

-- Khóa ngoại cho bảng GioHangChiTiet
ALTER TABLE GioHangChiTiet
    ADD CONSTRAINT FK_GioHangChiTiet_GioHang
        FOREIGN KEY (ID_GioHang) REFERENCES GioHang (ID);

ALTER TABLE GioHangChiTiet
    ADD CONSTRAINT FK_GioHangChiTiet_SanPhamChiTiet
        FOREIGN KEY (ID_SPCT) REFERENCES SanPhamChiTiet (ID);

-- Khóa ngoại cho bảng ThanhToan
ALTER TABLE ThanhToan
    ADD CONSTRAINT FK_ThanhToan_HoaDon
        FOREIGN KEY (ID_HoaDon) REFERENCES HoaDon (ID);

-- Khóa ngoại cho bảng HoaDon
ALTER TABLE HoaDon
    ADD CONSTRAINT FK_HoaDon_TaiKhoan
        FOREIGN KEY (ID_TaiKhoan) REFERENCES TaiKhoan (ID);

ALTER TABLE HoaDon
    ADD CONSTRAINT FK_HoaDon_Voucher
        FOREIGN KEY (ID_Voucher) REFERENCES Voucher (ID);

-- Khóa ngoại cho bảng HoaDonChiTiet
ALTER TABLE HoaDonChiTiet
    ADD CONSTRAINT FK_HoaDonChiTiet_HoaDon
        FOREIGN KEY (ID_HoaDon) REFERENCES HoaDon (ID);

ALTER TABLE HoaDonChiTiet
    ADD CONSTRAINT FK_HoaDonChiTiet_SanPhamChiTiet
        FOREIGN KEY (ID_SPCT) REFERENCES SanPhamChiTiet (ID);

-- Khóa ngoại cho bảng TaiKhoan
ALTER TABLE TaiKhoan
    ADD CONSTRAINT FK_TaiKhoan_VaiTro
        FOREIGN KEY (ID_VaiTro) REFERENCES VaiTro (ID);

---INSERT VaiTro
DBCC
CHECKIDENT ('VaiTro', RESEED, 0);
INSERT INTO VaiTro (TenVaiTro, NguoiTao, MoTa)
VALUES (N'Admin', 'System', N'Quản trị hệ thống với đầy đủ quyền hạn'),
       (N'Nhân viên', 'System', N'Nhân viên của công ty, có quyền hạn hạn chế'),
       (N'Khách hàng', 'System', N'Khách hàng sử dụng dịch vụ của công ty');

--INSERT TaiKhoan
INSERT INTO TaiKhoan (ID_VaiTro, TenDangNhap, MatKhau, HoTen, NgaySinh, GioiTinh, SDT, Email, CCCD, Avatar, NguoiTao,
                      TrangThai)
VALUES (1, 'tuan', 'tuan', N'Phạm Anh Tuấn', '2004-12-01', 1, '0123456789', 'tuanpaph35819@fpt.edu.vn', '123456789012',
        'avatar1.jpg', NULL, 1),
       (1, 'nhanvien1', 'password123', N'Trần Thị B', '1992-02-02', 0, '0987654321', 'nhanvien1@example.com',
        '123456789013', 'avatar2.jpg', NULL, 1),
       (1, 'khachhang1', 'password123', N'Lê Văn C', '1995-03-03', 1, '0123987654', 'khachhang1@example.com',
        '123456789014', 'avatar3.jpg', NULL, 1),
       (1, 'admin2', 'password123', N'Phạm Văn D', '1985-04-04', 1, '0123456780', 'admin2@example.com', '123456789015',
        'avatar4.jpg', NULL, 1),
       (2, 'nhanvien2', 'password123', N'Nguyễn Thị E', '1993-05-05', 0, '0123456790', 'nhanvien2@example.com',
        '123456789016', 'avatar5.jpg', NULL, 1);
--INSERT MauSac
INSERT INTO MauSac (MaMau, TenMau, MoTa, NgayCapNhat)
VALUES (N'M1', N'Màu Đỏ', N'Màu đỏ tươi, thể hiện sức mạnh và đam mê.', NULL),
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
VALUES ('S', N'Nhỏ (S)', N'Admin', N'Hoạt động', N'Kích thước nhỏ, dành cho người có vòng đầu nhỏ'),
       ('M', N'Vừa (M)', N'Admin', N'Hoạt động', N'Kích thước vừa, phù hợp cho nhiều người'),
       ('L', N'Lớn (L)', N'Admin', N'Hoạt động', N'Kích thước lớn, dành cho người có vòng đầu lớn'),
       ('XL', N'Cực Lớn (XL)', N'Admin', N'Hoạt động', N'Kích thước cực lớn, dành cho người có vòng đầu rất lớn'),
       ('XXL', N'Siêu Lớn (XXL)', N'Admin', N'Hoạt động',
        N'Kích thước siêu lớn, phù hợp cho người có vòng đầu lớn nhất');

--INSERT LoaiMu
INSERT INTO LoaiMu (MaLoaiMu, MoTa, TenLoaiMu, NguoiTao, TrangThai)
VALUES ('MBH01', N'Mũ bảo hiểm nửa đầu, thường dùng cho người lái xe máy trong đô thị', N'Mũ bảo hiểm nửa đầu',
        N'Admin', N'Hoạt động'),
       ('MBH02', N'Mũ bảo hiểm cả đầu, bao phủ toàn bộ phần đầu, thường dùng cho những người đi xe phân khối lớn',
        N'Mũ bảo hiểm cả đầu', N'Admin', N'Hoạt động'),
       ('MBH03', N'Mũ bảo hiểm 3/4 đầu, bảo vệ phần lớn đầu, phù hợp cho cả xe máy và xe phân khối lớn',
        N'Mũ bảo hiểm 3/4 đầu', N'Admin', N'Hoạt động'),
       ('MBH04', N'Mũ bảo hiểm lật hàm, có thể chuyển đổi giữa mũ nửa đầu và mũ cả đầu bằng cách lật phần hàm lên',
        N'Mũ bảo hiểm lật hàm', N'Admin', N'Hoạt động'),
       ('MBH05', N'Mũ bảo hiểm có kính, tích hợp kính chắn gió, giúp giảm bớt ánh sáng mặt trời và bụi bẩn',
        N'Mũ bảo hiểm có kính', N'Admin', N'Hoạt động');

--INSERT ChatLieuVo
--INSERT SanPham

INSERT INTO SanPham (Ten, TrangThai, MoTa)
VALUES 
    (N'Mũ bảo hiểm 1', N'Còn hàng', N'Mũ bảo hiểm chất lượng cao, đạt tiêu chuẩn an toàn.'),
    (N'Mũ bảo hiểm 2', N'Còn hàng', N'Mũ bảo hiểm thời trang, phong cách trẻ trung.'),
    (N'Mũ bảo hiểm 3', N'Hết hàng', N'Mũ bảo hiểm thể thao, thoải mái khi đội.'),
    (N'Mũ bảo hiểm 4', N'Còn hàng', N'Mũ bảo hiểm tiện dụng cho đi lại hàng ngày.'),
    (N'Mũ bảo hiểm 5', N'Còn hàng', N'Mũ bảo hiểm trẻ em, an toàn và dễ thương.'),
    (N'Mũ bảo hiểm 6', N'Hết hàng', N'Mũ bảo hiểm mini, thích hợp cho xe đạp.'),
    (N'Mũ bảo hiểm 7', N'Còn hàng', N'Mũ bảo hiểm đa năng, cho mọi loại xe.'),
    (N'Mũ bảo hiểm 8', N'Còn hàng', N'Mũ bảo hiểm cao cấp, thiết kế sang trọng.');

--INSERT SanPhamChiTiet

INSERT INTO SanPhamChiTiet (ID_SanPham, ID_ThuongHieu, ID_ChatLieuVo, ID_ChatLieuDem, ID_LoaiMu, ID_KichThuoc, ID_KhuyenMai,
ID_LoaiKinh, ID_MauSac, MaSPCT, DonGia, SoLuong, XuatXu, MoTaChiTiet, TrangThai, NguoiTao) 
VALUES
(1, 1, 1, 1, 1, 1, Null, 1, 1, 'SPCT001', 500000, 10, N'Việt Nam', N'Chất liệu cao cấp từ Việt Nam, mang lại sự thoải mái và bền bỉ.', 1, 'Admin'),
(3, 2, 2, 2, 1, 2, Null, 3, 2, 'SPCT002', 700000, 5, N'Nhật Bản', N'Thiết kế hiện đại từ Nhật Bản, đảm bảo tính thẩm mỹ và chất lượng cao.', 1, 'Admin'),
(2, 3, 3, 1, 1, 3, Null, 2, 3, 'SPCT003', 900000, 15, N'Hàn Quốc', N'Sản phẩm được làm từ chất liệu nhập khẩu từ Hàn Quốc, bền và đẹp.', 1, 'Admin'),
(2, 1, 3, 2, 3, 1, Null, 3, 4, 'SPCT004', 600000, 8, N'Mỹ', N'Chất lượng đỉnh cao từ Mỹ, mang lại sự sang trọng và đẳng cấp.', 1, 'Admin'),
(1, 2, 1, 3, 2, 2, Null, 1, 5, 'SPCT005', 800000, 12, N'Đức', N'Sản phẩm xuất xứ từ Đức, nổi bật với độ bền và thiết kế tinh tế.', 1, 'Admin');


--INSERT ThuongHieu

INSERT INTO ThuongHieu (TenThuongHieu, MoTa)
VALUES ('Yamaha', N'Thương hiệu nổi tiếng chuyên cung cấp mũ bảo hiểm thể thao và đi phượt, chất lượng cao.'),
       ('HJC', N'Thương hiệu hàng đầu thế giới trong lĩnh vực mũ bảo hiểm fullface và mũ bảo hiểm nửa đầu.'),
       ('Shoei', N'Thương hiệu Nhật Bản chuyên sản xuất mũ bảo hiểm cao cấp, nổi bật với thiết kế an toàn.'),
       ('LS2', N'Thương hiệu cung cấp các loại mũ bảo hiểm thời trang và thể thao, phù hợp cho giới trẻ.'),
       ('BOSCH', N'Thương hiệu chuyên sản xuất mũ bảo hiểm cho trẻ em với thiết kế an toàn và ngộ nghĩnh.'),
       ('KYT', N'Thương hiệu cung cấp mũ bảo hiểm chất lượng với nhiều loại mẫu mã và kích thước khác nhau.'),
       ('Royal Enfield',
        N'Thương hiệu nổi tiếng về xe máy phân khối lớn, cũng sản xuất mũ bảo hiểm phong cách cổ điển.');

-- INSERT ChatLieuDem
INSERT INTO ChatLieuDem (MaChatLieu, TenChatLieuDem, TrangThai, NguoiTao, MoTa)
VALUES (N'CLD001', N'Bọt EPS', N'Còn hàng', N'Nguyen Van A',
        N'Chất liệu đệm nhẹ, có khả năng hấp thụ va chạm tốt, thường được sử dụng trong mũ bảo hiểm.'),
       (N'CLD002', N'Đệm vải thấm hút', N'Còn hàng', N'Nguyen Van B',
        N'Chất liệu vải mềm mại, thấm hút mồ hôi, tạo cảm giác thoải mái cho người dùng.'),
       (N'CLD003', N'Đệm gel', N'Còn hàng', N'Nguyen Van C',
        N'Chất liệu gel giúp giảm lực tác động, bảo vệ đầu an toàn hơn.'),
       (N'CLD004', N'Đệm xốp PU', N'Còn hàng', N'Nguyen Van D',
        N'Chất liệu xốp PU có độ đàn hồi cao, hỗ trợ tốt trong việc giảm chấn.'),
       (N'CLD005', N'Đệm chống sốc', N'Còn hàng', N'Nguyen Van E',
        N'Chất liệu đặc biệt được thiết kế để chống lại lực va chạm mạnh.'),
       (N'CLD006', N'Bọt polyurethane', N'Còn hàng', N'Nguyen Van F',
        N'Chất liệu bọt polyurethane có khả năng chịu lực và độ bền cao.'),
       (N'CLD007', N'Chất liệu thông gió', N'Còn hàng', N'Nguyen Van G',
        N'Chất liệu được thiết kế để cải thiện khả năng thông gió, giữ cho đầu luôn mát mẻ.');

--INSET LoaiKinh
INSERT INTO LoaiKinh (MaLoaiKinh, TenLoaiKinh, TrangThai, NguoiTao, MoTa)
VALUES (N'LK001', N'Kính trong suốt', N'Còn hàng', N'Nguyen Van A',
        N'Kính trong suốt cho tầm nhìn rõ ràng, thường dùng cho các loại mũ bảo hiểm.'),
       (N'LK002', N'Kính chống UV', N'Còn hàng', N'Nguyen Van B',
        N'Kính có khả năng chống lại tia UV, bảo vệ mắt người dùng khỏi tác hại của ánh sáng mặt trời.'),
       (N'LK003', N'Kính mờ', N'Còn hàng', N'Nguyen Van C',
        N'Kính mờ giúp giảm độ chói và tạo cảm giác thoải mái cho người sử dụng trong thời tiết nắng gắt.'),
       (N'LK004', N'Kính đổi màu', N'Còn hàng', N'Nguyen Van D',
        N'Kính có khả năng tự động đổi màu theo cường độ ánh sáng, thích hợp cho việc sử dụng ngoài trời.'),
       (N'LK005', N'Kính chống trầy xước', N'Còn hàng', N'Nguyen Van E',
        N'Kính được xử lý đặc biệt để chống trầy xước, giữ cho kính luôn trong tình trạng tốt.'),
       (N'LK006', N'Kính gương', N'Còn hàng', N'Nguyen Van F',
        N'Kính gương giúp giảm độ chói và tăng cường độ thẩm mỹ cho mũ bảo hiểm.'),
       (N'LK007', N'Kính chống hơi nước', N'Còn hàng', N'Nguyen Van G',
        N'Kính có khả năng chống ngưng tụ hơi nước, đảm bảo tầm nhìn rõ ràng trong mọi điều kiện thời tiết.');


--INSERT KhuyenMai
INSERT INTO KhuyenMai (MaKhuyenMai, TenKhuyenMai, GiaTriKhuyenMai, NgayBatDau, NgayKetThuc, PhuongThucKhuyenMai,
                       DieuKienKhuyenMai, TrangThai, NguoiTao, NgayTao)
VALUES (N'KM001', N'Giảm 10% cho tất cả mũ bảo hiểm', 10.00, '2024-10-01', '2024-10-31', N'Giảm giá trực tiếp',
        N'Mua 1 mũ bảo hiểm bất kỳ', N'Còn hiệu lực', N'Nguyen Van A', GETDATE()),
       (N'KM002', N'Mua 2 tặng 1', 100.00, '2024-10-15', '2024-11-15', N'Tặng sản phẩm', N'Mua 2 mũ bảo hiểm bất kỳ',
        N'Còn hiệu lực', N'Nguyen Van B', GETDATE()),
       (N'KM003', N'Tặng kèm mũ bảo hiểm cho đơn hàng trên 500k', 50.00, '2024-10-20', '2024-11-20', N'Tặng sản phẩm',
        N'Đơn hàng trên 500.000 VNĐ', N'Còn hiệu lực', N'Nguyen Van C', GETDATE()),
       (N'KM004', N'Giảm giá theo số lượng', 15.00, '2024-11-01', '2024-12-01', N'Giảm giá trực tiếp',
        N'Mua từ 3 chiếc trở lên', N'Chưa bắt đầu', N'Nguyen Van D', GETDATE());

--INSERT Voucher

INSERT INTO Voucher
    (MaVoucher, TenVoucher, NgayBatDau, NgayKetThuc, GiaTriVoucher, GiaTriVoucherToiDa, GioiHanSoLuong, TrangThai, MoTa)
VALUES
    ('MU2024A', N'Giảm giá mũ bảo hiểm mùa xuân', '2024-02-01', '2024-03-01', 50000, 30000, 100, N'Hoạt động', N'Giảm giá cho tất cả các loại mũ bảo hiểm chính hãng'),
    ('MU2024B', N'Khuyến mãi mũ bảo hiểm - Mua 1 tặng 1', '2024-04-10', '2024-05-10', 100000, 50000, 50, N'Hoạt động', N'Áp dụng khi mua một mũ bảo hiểm, tặng thêm một mũ bảo hiểm tương đương'),
    ('MU2024C', N'Giảm giá mũ bảo hiểm mùa hè', '2024-06-01', '2024-07-01', 80000, 40000, 150, N'Hoạt động', N'Giảm giá cho mũ bảo hiểm bảo hành 1 năm'),
    ('MU2024D', N'Ưu đãi mũ bảo hiểm mùa mưa', '2024-09-01', '2024-09-30', 120000, 60000, 75, N'Đang chờ', N'Khuyến mãi cho mũ bảo hiểm chống nước mùa mưa'),
    ('MU2024E', N'Giảm giá cuối năm - Mũ bảo hiểm', '2024-12-01', '2024-12-31', 150000, 75000, 200, N'Hoạt động', N'Áp dụng cho tất cả các sản phẩm mũ bảo hiểm trong tháng 12');