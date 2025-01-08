package com.example.saferide.controller;

import com.example.saferide.config.VNPayConfig;
import com.example.saferide.entity.*;
import com.example.saferide.repository.*;
import com.example.saferide.request.AddToCartRequest;
import com.example.saferide.request.HoanTraRequest;
import com.example.saferide.request.KhuyenMaiRequest;
import com.example.saferide.request.ThanhToanRequest;
import com.example.saferide.service.GioHangService;
import com.example.saferide.service.HoaDonService;
import com.example.saferide.service.HoanTraService;
import com.example.saferide.service.KhuyenMaiService;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private GioHangRepository gioHangRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private KhachHangRepository khachHangRepository;

    @Autowired
    private KhuyenMaiService khuyenMaiService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private HoanTraService hoanTraService;

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/them-hoa-don")
    public ResponseEntity<?> themHoaDon(HoaDon hoaDon,
                                        @RequestParam(required = false) String tenKhachHang,
                                        @RequestParam(required = false) String sdt) {

        KhachHang khachHang;

        // If customer name and phone number are provided, create a new customer
        if (tenKhachHang != null && sdt != null) {
            khachHang = new KhachHang();
            khachHang.setHoTen(tenKhachHang);
            khachHang.setSdt(sdt);
            khachHangRepository.save(khachHang);
        } else {
            // If no customer data is provided, set the default "Lẻ" customer
            khachHang = khachHangRepository.findById(6).get();
        }

        // Set order details
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMdHms"));
        hoaDon.setMa("HD" + timeStamp);
        TaiKhoan taiKhoan = taiKhoanRepository.findById(1).orElse(null);
        hoaDon.setIdTaiKhoan(taiKhoan);
        hoaDon.setIdVoucher(null);
        hoaDon.setLoaiHoaDon(1);
        hoaDon.setNgayGiaoHang(LocalDateTime.now());
        hoaDon.setNgayNhan(LocalDateTime.now());
        hoaDon.setGiaGiam(null);
        hoaDon.setTongTien(new BigDecimal("0"));
        hoaDon.setSoTienDaTra(new BigDecimal("0"));
        hoaDon.setGhiChu("Đơn Hàng Bán Tại Quầy");
        hoaDon.setDiaChi("SafeRide - Hà Nội");
        hoaDon.setTt("Chưa Thanh Toán");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {

            // Cast to UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Fetch user details from repository
            TaiKhoan taiKhoan1 = taiKhoanRepository.findByTenDangNhap(userDetails.getUsername())
                    .orElse(null);

            if (taiKhoan1 != null) {
                hoaDon.setNguoiTao(taiKhoan1.getTen());
                hoaDon.setIdTaiKhoan(taiKhoan1);
            } else {
                hoaDon.setNguoiTao("Unknown User");
            }
        } else {
            hoaDon.setNguoiTao("Unknown User");
        }


        hoaDon.setNgayTao(LocalDateTime.now());

        // Save HoaDon
        hoaDon.setIdKhachHang(khachHang);
        HoaDon hoaDonSaved = hoaDonRepository.save(hoaDon);

        return ResponseEntity.ok(hoaDonSaved);
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/them-san-pham-off")
    public ResponseEntity<?> themSanPham(Integer hoaDonId, Integer sanPhamId, BigDecimal soLuong) {
        HoaDon hoaDon = null; // Declare hoaDon variable outside the try block for use in finally
        try {
            // Retrieve the selected invoice
            hoaDon = hoaDonRepository.findById(hoaDonId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Hóa đơn không tồn tại"));

            // Retrieve the product details
            SPChiTiet spChiTiet = spChiTietRepository.findById(sanPhamId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sản phẩm không tồn tại"));

            // Validate the quantity to ensure it's positive
            if (soLuong.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest().body("Số lượng phải lớn hơn 0.");
            }

            // Check if the product already exists in the invoice detail
            Optional<HoaDonChiTiet> existingHDCT = hoaDonChiTietRepository.findByHoaDonIdAndSanPhamId(hoaDonId, sanPhamId);

            if (existingHDCT.isPresent()) {
                // If exists, update the quantity and total amount
                HoaDonChiTiet hdct = existingHDCT.get();
                BigDecimal currentQuantity = BigDecimal.valueOf(hdct.getSl());
                hdct.setSl(currentQuantity.add(soLuong).intValue());

                BigDecimal newTotal = spChiTiet.getDonGia().multiply(BigDecimal.valueOf(hdct.getSl()));
                hdct.setTongTien(newTotal);
                hoaDonChiTietRepository.save(hdct);

                // Return the updated invoice detail
                return ResponseEntity.ok(hdct);
            } else {
                // Create a new invoice detail
                HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
                hoaDonChiTiet.setIdHoaDon(hoaDon);
                hoaDonChiTiet.setIdSPCT(spChiTiet);
                hoaDonChiTiet.setSl(soLuong.intValue());

                // Calculate new total amount for the new product
                BigDecimal newTotal = spChiTiet.getDonGia().multiply(soLuong);
                if (newTotal.compareTo(new BigDecimal("99999999.99")) > 0) {
                    return ResponseEntity.badRequest().body("Tổng tiền vượt quá giới hạn cho phép.");
                }

                hoaDonChiTiet.setTongTien(newTotal);

                // Generate mã for the new invoice detail
                String maHDCT = "HDCT-" + hoaDon.getId() + "-" + (hoaDonChiTietRepository.countByHoaDonId(hoaDon.getId()) + 1);
                hoaDonChiTiet.setMahdct(maHDCT);
                hoaDonChiTiet.setTt("Đang thanh toán");

                // Save the new invoice detail
                hoaDonChiTietRepository.save(hoaDonChiTiet);

                // Return the newly created invoice detail
                return ResponseEntity.ok(hoaDonChiTiet);
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Lỗi trong quá trình xử lý: " + e.getMessage());
        } finally {
            if (hoaDon != null) {
                // Update total amount for the invoice after processing
                updateTotalAmount(hoaDon);
            }
        }
    }


    private void updateTotalAmount(HoaDon hoaDon) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());
        BigDecimal totalAmount = chiTietList.stream()
                .map(HoaDonChiTiet::getTongTien)
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng dồn các giá trị BigDecimal
        hoaDon.setTongTien(totalAmount);
        hoaDonRepository.save(hoaDon);
    }


    @PreAuthorize("hasRole('ROLE_Admin')")
    @DeleteMapping("/xoa/hdct/{id}/{hoaDonId}")
    public ResponseEntity<?> remove(@PathVariable Integer id, @PathVariable Integer hoaDonId) {
        // Check if the invoice detail exists
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn chi tiết không tồn tại"));

        // Remove the invoice detail
        hoaDonChiTietRepository.deleteById(id);

        // Update the total amount for the invoice
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        updateTotalAmount(hoaDon);

        // Redirect to the invoice details page
        return ResponseEntity.ok("Xóa thành công <3");
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/thanh-toan-off")
    public ResponseEntity<?> thanhToan(@RequestParam(required = false) Integer hoaDonId,
                                       @RequestParam BigDecimal soTienKhachTra) {

        // Retrieve the invoice and its details
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);

        // Check if there are any product details in the invoice
        if (listHoaDonChiTiet.isEmpty()) {
            return ResponseEntity.badRequest().body("Hóa đơn này không có sản phẩm nào. Vui lòng thêm sản phẩm trước khi thanh toán.");
        }

        // Check the payment amount
        if (soTienKhachTra.compareTo(hoaDon.getTongTien()) < 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Số tiền khách trả không đủ. Vui lòng kiểm tra lại.");
        }
        hoaDon.setSoTienDaTra(soTienKhachTra);
        hoaDon.setTt("Đã thanh toán");
        hoaDonRepository.save(hoaDon);

        // Update product stock
        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            SPChiTiet spChiTiet = hdct.getIdSPCT();

            if (spChiTiet.getSl() < hdct.getSl()) {
                return ResponseEntity.badRequest().body("Số lượng tồn kho không đủ cho sản phẩm: ");
            }

            spChiTiet.setSl(spChiTiet.getSl() - hdct.getSl());
            spChiTietRepository.save(spChiTiet);
        }
        return ResponseEntity.ok("Thanh toán thành công");
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/in-hoa-don-off")
    public void inHoaDon(@RequestParam String maHoaDon, HttpServletResponse response) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Tìm hóa đơn theo mã hóa đơn
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findByMa(maHoaDon);

        if (!hoaDonOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hóa đơn không tồn tại");
        }

        HoaDon hoaDon = hoaDonOptional.get();
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        try {
            // Thiết lập file PDF trả về
            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "inline; filename=HoaDon_" + hoaDon.getMa() + ".pdf";
            response.setHeader(headerKey, headerValue);

            // Tạo PDF
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Đọc font từ resources
            try (InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/times.ttf")) {
                if (fontStream == null) {
                    throw new RuntimeException("Không tìm thấy file font trong classpath: /fonts/times.ttf");
                }

                // Tạo font từ InputStream với PdfFontFactory.createFont
                byte[] fontBytes = fontStream.readAllBytes();
                PdfFont font = PdfFontFactory.createFont(fontBytes, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
                document.setFont(font);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi đọc file font: " + e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi tạo font từ file nội bộ: " + e.getMessage(), e);
            }

            // Thêm Logo vào Hóa đơn
            String logoPath = "static/images/logo.png"; // Đảm bảo đường dẫn đúng
            try {
                // Lấy InputStream từ tài nguyên
                InputStream logoStream = getClass().getClassLoader().getResourceAsStream(logoPath);

                if (logoStream == null) {
                    throw new RuntimeException("Không thể tìm thấy hình ảnh tại đường dẫn: " + logoPath);
                }

                // Tạo đối tượng Image từ InputStream
                Image logo = new Image(ImageDataFactory.create(logoStream.readAllBytes()));
                logo.setWidth(100f);  // Đặt kích thước cho hình ảnh
                logo.setHeight(100f);
                document.add(logo); // Thêm hình ảnh vào tài liệu PDF

                // Đóng InputStream sau khi sử dụng
                logoStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi đọc InputStream: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi thêm hình ảnh vào PDF: " + e.getMessage());
            }

            // Tiêu đề hóa đơn
            document.add(new Paragraph("HÓA ĐƠN CỬA HÀNG SAFE-RIDE")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            // Thông tin hóa đơn
            document.add(new Paragraph("Mã hóa đơn: " + hoaDon.getMa()));
            document.add(new Paragraph("Ngày tạo: " + hoaDon.getNgayTao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
            document.add(new Paragraph("Địa chỉ Shop (Address):  Số 68, SafeRide, Trịnh Văn Bô, Nam Từ Liêm, Hà Nội"));
            document.add(new Paragraph("Điện thoại (Tel) : 123456789"));
            document.add(new Paragraph("Email : saferide@gmail.com"));

            // Thông tin khách hàng
            String tenKhachHang = (hoaDon.getIdKhachHang() != null && hoaDon.getIdKhachHang().getHoTen() != null)
                    ? hoaDon.getIdKhachHang().getHoTen()
                    : "Không xác định";
            document.add(new Paragraph("Khách hàng: " + tenKhachHang));
            document.add(new Paragraph("\n"));

            // Thêm bảng chi tiết hóa đơn
            float[] columnWidths = {2, 1, 1, 1, 1}; // Thêm cột Giảm giá
            Table table = new Table(columnWidths);

            // Thêm tiêu đề cột
            table.addHeaderCell(new Cell().add(new Paragraph("Tên sản phẩm").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Số lượng").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Đơn giá").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Thành tiền").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Giảm giá").setBold()));

            // Chi tiết hóa đơn
            BigDecimal giamGia = hoaDon.getGiaGiam() != null ? hoaDon.getGiaGiam() : BigDecimal.ZERO;
            BigDecimal tongTienTruocGiam = BigDecimal.ZERO;
            for (HoaDonChiTiet chiTiet : chiTietList) {
                String tenSanPham = (chiTiet.getIdSPCT() != null && chiTiet.getIdSPCT().getMa() != null)
                        ? chiTiet.getIdSPCT().getIdSanPham().getTen()
                        : "Không xác định";

                BigDecimal giaSanPham = chiTiet.getIdSPCT().getDonGia();
                String soLuong = String.valueOf(chiTiet.getSl());
                BigDecimal thanhTien = giaSanPham.multiply(BigDecimal.valueOf(chiTiet.getSl()));

                // Cộng dồn vào tổng tiền trước giảm giá
                tongTienTruocGiam = tongTienTruocGiam.add(thanhTien);

                // Thêm chi tiết sản phẩm vào bảng
                String formatGiaSanPham = currencyFormat.format(giaSanPham);
                String formatThanhTien = currencyFormat.format(thanhTien);
                String formatGiamGia = currencyFormat.format(giamGia);

                table.addCell(new Cell().add(new Paragraph(tenSanPham)));
                table.addCell(new Cell().add(new Paragraph(soLuong)));
                table.addCell(new Cell().add(new Paragraph(formatGiaSanPham)));
                table.addCell(new Cell().add(new Paragraph(formatThanhTien)));
                table.addCell(new Cell().add(new Paragraph(formatGiamGia)));
            }

            // Thêm bảng vào tài liệu
            document.add(table);

            BigDecimal tongTienSauGiam = tongTienTruocGiam.subtract(giamGia);
            String formattedTongTien = currencyFormat.format(tongTienSauGiam);

            document.add(new Paragraph("\n"));

            Text text1 = new Text("TỔNG TIỀN: ").setFontSize(14).setBold();
            Text text2 = new Text(formattedTongTien)
                    .setFontSize(14)
                    .setFontColor(ColorConstants.RED);
            document.add(new Paragraph().add(text1).add(text2).setTextAlignment(TextAlignment.CENTER));
            // Đóng tài liệu
            document.close();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi tạo file PDF: " + e.getMessage());
        }
    }


    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/them-hoa-don-off")
    public ResponseEntity<?> themHoaDon(
            @RequestBody HoaDon hoaDon) {

        // Generate the bill code
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMdHms"));
        hoaDon.setMa("HD" + timeStamp);

        // Set default properties
        hoaDon.setLoaiHoaDon(1);
        hoaDon.setTongTien(new BigDecimal("0"));
        hoaDon.setSoTienDaTra(new BigDecimal("0"));
        hoaDon.setGhiChu("Đơn Hàng Bán Tại Quầy");
        hoaDon.setDiaChi("SafeRide - Hà Nội");
        hoaDon.setTt("Chưa thanh toán");
        hoaDon.setNgayTao(LocalDateTime.now());

        // Retrieve authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {

            // Cast to UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Fetch user details from repository
            TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(userDetails.getUsername())
                    .orElse(null);

            if (taiKhoan != null) {
                hoaDon.setNguoiTao(taiKhoan.getTen());
                hoaDon.setIdTaiKhoan(taiKhoan);
            } else {
                hoaDon.setNguoiTao("Unknown User");
            }
        } else {
            hoaDon.setNguoiTao("Unknown User");
        }

        // Save the invoice
        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);
        return ResponseEntity.ok(savedHoaDon);
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/giao-hang-quay")
    public ResponseEntity<?> giaoHangTaiQuay(@RequestParam Integer hoaDonId,
                                             @RequestParam(required = false) String diaChi) {
        // Lấy hóa đơn
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));

        hoaDon.setTt("Chờ xác nhận");
        hoaDon.setDiaChi(diaChi);
        hoaDonRepository.save(hoaDon);

        return ResponseEntity.ok(hoaDon);
    }

//  Bán hàng Online

    @PreAuthorize("hasRole('ROLE_Admin')")
    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<?> xoaGioHang(@PathVariable Integer id) {
        gioHangRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/them-san-pham/on")
    public ResponseEntity<?> themSanPham(@RequestParam Integer productId,
                                         @RequestParam Integer idTaiKhoan,
                                         @RequestParam int soLuong) {
        // Find product by ID
        SPChiTiet spChiTiet = spChiTietRepository.findById(productId).orElse(null);
        if (spChiTiet == null) {
            return ResponseEntity.badRequest().body("Sản phẩm chi tiết không tồn tại");
        }

        // Find account by ID
        TaiKhoan taiKhoan = taiKhoanRepository.findById(idTaiKhoan).orElse(null);
        if (taiKhoan == null) {
            return ResponseEntity.badRequest().body("Tài khoản không tồn tại");
        }

        // Find or create cart
        GioHang gioHang = gioHangRepository.findByIdTaiKhoanGH(idTaiKhoan);
        if (gioHang == null) {
            gioHang = new GioHang();
            gioHang.setIdTaiKhoan(taiKhoan);
            gioHang.setTt(false);
            gioHangRepository.save(gioHang);
        }

        Optional<GioHangChiTiet> existingGioHangChiTiet = gioHangChiTietRepository
                .findByIdGioHangAndIdSPCT(gioHang, spChiTiet);


        if (soLuong <= 0) {
            return ResponseEntity.badRequest().body("Số lượng sản phẩm phải lớn hơn 0");
        }

        if (existingGioHangChiTiet.isPresent()) {
            // Nếu sản phẩm đã có trong giỏ hàng, cập nhật số lượng
            GioHangChiTiet gioHangChiTiet = existingGioHangChiTiet.get();
            gioHangChiTiet.setSl(gioHangChiTiet.getSl() + soLuong);
            gioHangChiTietRepository.save(gioHangChiTiet);
        } else {
            // Nếu sản phẩm chưa có, thêm sản phẩm mới vào giỏ hàng
            GioHangChiTiet gioHangChiTiet = new GioHangChiTiet();
            gioHangChiTiet.setIdGioHang(gioHang);
            gioHangChiTiet.setIdSPCT(spChiTiet);
            gioHangChiTiet.setMa(spChiTiet.getMa());
            gioHangChiTiet.setDonGia(spChiTiet.getDonGia());
            gioHangChiTiet.setSl(soLuong);  // Đặt số lượng
            gioHangChiTiet.setTt("Đang tạo");
            gioHangChiTietRepository.save(gioHangChiTiet);
        }
        return ResponseEntity.ok("Sản phẩm đã thêm vào giỏ hàng");
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/dat-hang")
    public ResponseEntity<?> datHangOnline(@RequestParam Integer gioHangId,
                                           @RequestParam Integer idTaiKhoan,
                                           @RequestParam String soNha,
                                           @RequestParam String xa,
                                           @RequestParam String huyen,
                                           @RequestParam String thanhPho) {
        // Kiểm tra giỏ hàng tồn tại
        Optional<GioHang> gioHangOpt = gioHangRepository.findById(gioHangId);
        if (!gioHangOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Giỏ hàng không tồn tại");
        }

        GioHang gioHang = gioHangOpt.get();

        // Kiểm tra tài khoản tồn tại
        Optional<TaiKhoan> taiKhoanOpt = taiKhoanRepository.findById(idTaiKhoan);
        if (!taiKhoanOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Tài khoản không tồn tại");
        }

        TaiKhoan taiKhoan = taiKhoanOpt.get();

        // Tính tổng tiền giỏ hàng
        BigDecimal tongTien = gioHang.getGioHangChiTietList().stream()
                .map(gioHangChiTiet -> gioHangChiTiet.getDonGia().multiply(BigDecimal.valueOf(gioHangChiTiet.getSl())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Tạo hóa đơn mới
        HoaDon hoaDon = new HoaDon();
        hoaDon.setIdTaiKhoan(taiKhoan);
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setTongTien(tongTien);
        hoaDon.setLoaiHoaDon(2);
        String diaChi = soNha + ", Xã " + xa + ", Huyện " + huyen + ", " + thanhPho;
        hoaDon.setDiaChi(diaChi);
        hoaDon.setTt("Chưa thanh toán");
        // Retrieve authenticated user

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {

            // Cast to UserDetails
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            // Fetch user details from repository
            TaiKhoan taiKhoan1 = taiKhoanRepository.findByTenDangNhap(userDetails.getUsername())
                    .orElse(null);

            if (taiKhoan1 != null) {
                hoaDon.setNguoiTao(taiKhoan1.getTen());
                hoaDon.setIdTaiKhoan(taiKhoan1);
            } else {
                hoaDon.setNguoiTao("Unknown User");
            }
        } else {
            hoaDon.setNguoiTao("Unknown User");
        }

        // Lưu hóa đơn vào cơ sở dữ liệu để lấy ID
        hoaDonRepository.save(hoaDon);

        // Sau khi lưu, tạo mã hóa đơn từ ID
        String maHD = "HD" + hoaDon.getId();
        hoaDon.setMa(maHD);
        hoaDonRepository.save(hoaDon);

        // Tạo chi tiết hóa đơn
        for (GioHangChiTiet gioHangChiTiet : gioHang.getGioHangChiTietList()) {
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setIdHoaDon(hoaDon);
            hoaDonChiTiet.setIdSPCT(gioHangChiTiet.getIdSPCT());
            hoaDonChiTiet.setSl(gioHangChiTiet.getSl());
            hoaDonChiTiet.setTt("Chưa thanh toán");
            String maHDCT = "HDCT" + hoaDon.getId();
            hoaDonChiTiet.setMahdct(maHDCT);
            hoaDonChiTiet.setTongTien(gioHangChiTiet.getDonGia().multiply(BigDecimal.valueOf(gioHangChiTiet.getSl())));

            hoaDonChiTietRepository.save(hoaDonChiTiet);
        }

        return ResponseEntity.ok("Đặt hàng thành công, Mã hóa đơn: " + maHD);
    }

    //In hóa đơn Online

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/in-hoa-don-on")
    public void inHoaDonOnline(@RequestParam String maHoaDon, HttpServletResponse response) {

        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

        // Tìm hóa đơn theo mã hóa đơn
        Optional<HoaDon> hoaDonOptional = hoaDonRepository.findByMa(maHoaDon);

        if (!hoaDonOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Hóa đơn không tồn tại");
        }

        HoaDon hoaDon = hoaDonOptional.get();
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        try {
            // Thiết lập file PDF trả về
            response.setContentType("application/pdf");
            String headerKey = "Content-Disposition";
            String headerValue = "inline; filename=HoaDon_" + hoaDon.getMa() + ".pdf";
            response.setHeader(headerKey, headerValue);

            // Tạo PDF
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Đọc font từ resources
            try (InputStream fontStream = getClass().getClassLoader().getResourceAsStream("fonts/times.ttf")) {
                if (fontStream == null) {
                    throw new RuntimeException("Không tìm thấy file font trong classpath: /fonts/times.ttf");
                }

                // Tạo font từ InputStream với PdfFontFactory.createFont
                byte[] fontBytes = fontStream.readAllBytes();
                PdfFont font = PdfFontFactory.createFont(fontBytes, PdfEncodings.IDENTITY_H, PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED);
                document.setFont(font);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi đọc file font: " + e.getMessage(), e);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi tạo font từ file nội bộ: " + e.getMessage(), e);
            }

            // Thêm Logo vào Hóa đơn
            String logoPath = "static/images/logo.png"; // Đảm bảo đường dẫn đúng
            try {
                // Lấy InputStream từ tài nguyên
                InputStream logoStream = getClass().getClassLoader().getResourceAsStream(logoPath);

                if (logoStream == null) {
                    throw new RuntimeException("Không thể tìm thấy hình ảnh tại đường dẫn: " + logoPath);
                }

                // Tạo đối tượng Image từ InputStream
                Image logo = new Image(ImageDataFactory.create(logoStream.readAllBytes()));
                logo.setWidth(100f);  // Đặt kích thước cho hình ảnh
                logo.setHeight(100f);
                document.add(logo); // Thêm hình ảnh vào tài liệu PDF

                // Đóng InputStream sau khi sử dụng
                logoStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi đọc InputStream: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Lỗi khi thêm hình ảnh vào PDF: " + e.getMessage());
            }

            // Tiêu đề hóa đơn
            document.add(new Paragraph("HÓA ĐƠN CỬA HÀNG SAFE-RIDE")
                    .setBold()
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            // Thông tin hóa đơn
            document.add(new Paragraph("Mã hóa đơn: " + hoaDon.getMa()));
            document.add(new Paragraph("Ngày tạo: " + hoaDon.getNgayTao().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
            document.add(new Paragraph("Địa chỉ Shop (Address):  Số 68, SafeRide, Trịnh Văn Bô, Nam Từ Liêm, Hà Nội"));
            document.add(new Paragraph("Điện thoại (Tel) : 123456789"));
            document.add(new Paragraph("Email : saferide@gmail.com"));

            // Thông tin khách hàng
            String tenKhachHang = (hoaDon.getIdTaiKhoan() != null && hoaDon.getIdTaiKhoan().getTen() != null)
                    ? hoaDon.getIdTaiKhoan().getTen()
                    : "Không xác định";
            document.add(new Paragraph("Khách hàng: " + tenKhachHang));
            document.add(new Paragraph("Địa chỉ giao hàng: " + hoaDon.getDiaChi()));
            document.add(new Paragraph("\n"));

            // Thêm bảng chi tiết hóa đơn
            float[] columnWidths = {2, 1, 1, 1, 1}; // Thêm cột Giảm giá
            Table table = new Table(columnWidths);

            // Thêm tiêu đề cột
            table.addHeaderCell(new Cell().add(new Paragraph("Tên sản phẩm").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Số lượng").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Đơn giá").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Thành tiền").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Giảm giá").setBold()));

            // Chi tiết hóa đơn
            BigDecimal giamGia = hoaDon.getGiaGiam() != null ? hoaDon.getGiaGiam() : BigDecimal.ZERO;
            BigDecimal tongTienTruocGiam = BigDecimal.ZERO;
            for (HoaDonChiTiet chiTiet : chiTietList) {
                String tenSanPham = (chiTiet.getIdSPCT() != null && chiTiet.getIdSPCT().getMa() != null)
                        ? chiTiet.getIdSPCT().getIdSanPham().getTen()
                        : "Không xác định";

                BigDecimal giaSanPham = chiTiet.getIdSPCT().getDonGia();
                String soLuong = String.valueOf(chiTiet.getSl());
                BigDecimal thanhTien = giaSanPham.multiply(BigDecimal.valueOf(chiTiet.getSl()));

                // Cộng dồn vào tổng tiền trước giảm giá
                tongTienTruocGiam = tongTienTruocGiam.add(thanhTien);

                // Thêm chi tiết sản phẩm vào bảng
                String formatGiaSanPham = currencyFormat.format(giaSanPham);
                String formatThanhTien = currencyFormat.format(thanhTien);
                String formatGiamGia = currencyFormat.format(giamGia);

                table.addCell(new Cell().add(new Paragraph(tenSanPham)));
                table.addCell(new Cell().add(new Paragraph(soLuong)));
                table.addCell(new Cell().add(new Paragraph(formatGiaSanPham)));
                table.addCell(new Cell().add(new Paragraph(formatThanhTien)));
                table.addCell(new Cell().add(new Paragraph(formatGiamGia)));
            }

            // Thêm bảng vào tài liệu
            document.add(table);

            BigDecimal tongTienSauGiam = tongTienTruocGiam.subtract(giamGia);
            String formattedTongTien = currencyFormat.format(tongTienSauGiam);

            document.add(new Paragraph("\n"));

            Text text1 = new Text("TỔNG TIỀN: ").setFontSize(14).setBold();
            Text text2 = new Text(formattedTongTien)
                    .setFontSize(14)
                    .setFontColor(ColorConstants.RED);
            document.add(new Paragraph().add(text1).add(text2).setTextAlignment(TextAlignment.CENTER));
            // Đóng tài liệu
            document.close();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi khi tạo file PDF: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/thongke/ngay")
    public ResponseEntity<BigDecimal> getDoanhThuTheoNgay(@RequestParam String ngay) {
        try {
            LocalDate date = LocalDate.parse(ngay);
            BigDecimal doanhThu = hoaDonService.getDoanhThuTheoNgay(date);
            return ResponseEntity.ok(doanhThu);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build(); // 400 Bad Request
        }
    }


    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/thongke/thang")
    public ResponseEntity<BigDecimal> getDoanhThuTheoThang(@RequestParam int thang, @RequestParam int nam) {
        BigDecimal doanhThu = hoaDonService.getDoanhThuTheoThang(thang, nam);
        if (doanhThu != null) {
            return ResponseEntity.ok(doanhThu);
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/thongke/nam")
    public ResponseEntity<BigDecimal> getDoanhThuTheoNam(@RequestParam("nam") int nam) {
        BigDecimal doanhThu = hoaDonService.getDoanhThuTheoNam(nam);
        return ResponseEntity.ok(doanhThu);
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/thongke/tong")
    public ResponseEntity<BigDecimal> getTongDoanhThu() {
        BigDecimal doanhThu = hoaDonService.getTongDoanhThu();
        return ResponseEntity.ok(doanhThu);
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/thongke/tong-tai-quay")
    public ResponseEntity<BigDecimal> getTongTienTaiQuay() {
        BigDecimal doanhThu = hoaDonService.getTongDoanhThu();
        return ResponseEntity.ok(doanhThu);
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/thongke/tong-online")
    public ResponseEntity<BigDecimal> getTongTienOnline() {
        BigDecimal doanhThu = hoaDonService.getTongDoanhThu();
        return ResponseEntity.ok(doanhThu);
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/trang-thai")
    public ResponseEntity<?> capNhatTrangThaiHoaDon(@RequestParam Integer hoaDonId,
                                                    @RequestParam String trangThaiMoi) {
        // Kiểm tra hóa đơn tồn tại
        Optional<HoaDon> hoaDonOpt = hoaDonRepository.findById(hoaDonId);
        if (!hoaDonOpt.isPresent()) {
            return ResponseEntity.badRequest().body("Hóa đơn không tồn tại");
        }

        HoaDon hoaDon = hoaDonOpt.get();

        // Kiểm tra trạng thái hóa đơn (phải là "Chưa thanh toán")
        if (!hoaDon.getTt().equals("Chưa thanh toán")) {
            return ResponseEntity.badRequest().body("Hóa đơn đã được thanh toán hoặc không hợp lệ");
        }

        // Danh sách trạng thái hợp lệ
        List<String> trangThaiHopLe = List.of("Đã gửi cho bên vận chuyển", "Đang giao", "Đã giao", "Hoàn thành");

        // Kiểm tra xem trạng thái mới có hợp lệ không
        if (!trangThaiHopLe.contains(trangThaiMoi)) {
            return ResponseEntity.badRequest().body("Trạng thái không hợp lệ");
        }

        // Cập nhật trạng thái hóa đơn
        hoaDon.setTt(trangThaiMoi);

        // Cập nhật ngày giao hàng nếu trạng thái mới không phải "Hoàn thành"
        if (trangThaiMoi.equals("Hoàn thành")) {
            hoaDon.setNgayNhan(LocalDateTime.now());
        } else {
            hoaDon.setNgayGiaoHang(LocalDateTime.now());
        }

        // Lưu hóa đơn vào cơ sở dữ liệu
        hoaDonRepository.save(hoaDon);

        return ResponseEntity.ok("Cập nhật trạng thái thành công");
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/voucher")
    public ResponseEntity<?> themVoucher(@RequestParam Integer hoaDonId, @RequestParam Integer voucherId) {
        // Tìm hóa đơn theo ID
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Mã hóa đơn không tồn tại!"));

        // Tìm voucher theo ID
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại Voucher"));

        // Kiểm tra nếu hóa đơn đã có voucher được áp dụng
        if (hoaDon.getIdVoucher() != null) {
            return ResponseEntity.badRequest().body("Hóa đơn đã được áp dụng voucher khác.");
        }

        // Kiểm tra ngày hiệu lực của voucher
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(voucher.getNgayBD()) || now.isAfter(voucher.getNgayKT())) {
            return ResponseEntity.badRequest().body("Voucher đã hết hạn hoặc chưa có hiệu lực.");
        }

        // Kiểm tra điều kiện tổng tiền tối thiểu để áp dụng voucher
        if (hoaDon.getTongTien().compareTo(voucher.getGiaTriMax()) < 0) {
            return ResponseEntity.badRequest().body("Hóa đơn không đủ điều kiện áp dụng voucher này.");
        }

        // Áp dụng voucher vào hóa đơn
        hoaDon.setIdVoucher(voucher);
        BigDecimal giaTriGiam = voucher.getGiaTri(); // Giá trị giảm từ voucher

        // Đảm bảo giá trị giảm không vượt quá tổng tiền
        if (hoaDon.getTongTien().compareTo(giaTriGiam) < 0) {
            giaTriGiam = hoaDon.getTongTien();
        }
        hoaDon.setGiaGiam(giaTriGiam); // Lưu giá trị giảm vào hóa đơn
        hoaDon.setTongTien(hoaDon.getTongTien().subtract(giaTriGiam)); // Cập nhật tổng tiền

        // Lưu hóa đơn sau khi áp dụng voucher
        hoaDonRepository.save(hoaDon);

        return ResponseEntity.ok("Voucher đã được áp dụng thành công.");
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/thanh-toan-vnpay")
    public ResponseEntity<?> thanhToanVNPay(@RequestBody ThanhToanRequest request) throws UnsupportedEncodingException {
        String maHD = request.getMaHoaDon();

        // Lấy hóa đơn từ cơ sở dữ liệu
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHD)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        BigDecimal tongTien = hoaDon.getTongTien(); // Tổng tiền từ hóa đơn
        if (tongTien.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Tổng tiền không hợp lệ để thanh toán.");
        }

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_Amount = tongTien.multiply(BigDecimal.valueOf(100))   // Nhân với 100 (sửa từ 10 thành 100)
                .setScale(0, RoundingMode.DOWN)       // Đảm bảo không có thập phân
                .toString();  // Chuyển thành chuỗi

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");

        // Update the return URL here
        String vnp_ReturnUrl = "http://localhost:8080/admin/return";  // Replace this with your actual callback URL
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);

        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return ResponseEntity.ok(paymentUrl);
    }


    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/return")
    public ResponseEntity<?> vnpayReturn(@RequestParam Map<String, String> params) {
        String vnp_TxnRef = params.get("vnp_TxnRef");
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        String vnp_SecureHash = params.get("vnp_SecureHash");
        System.out.println("vnp_SecureHash" + vnp_SecureHash);
        // Lấy hóa đơn từ mã giao dịch
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(vnp_TxnRef)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        // Xác minh chữ ký bảo mật
        params.remove("vnp_SecureHash");
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String value = params.get(fieldName);
            if (value != null && value.length() > 0) {
                hashData.append(fieldName).append('=').append(value).append('&');
            }
        }
        hashData.deleteCharAt(hashData.length() - 1);

        String vnp_HashSecret = "222"; // Mã bí mật từ VNPay
        String secureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        System.out.println("secureHash: " + secureHash);

        if (!secureHash.equals(vnp_SecureHash)) {
            return ResponseEntity.badRequest().body("Chữ ký không hợp lệ.");
        }

        // Kiểm tra kết quả giao dịch
        if ("00".equals(vnp_ResponseCode)) {
            hoaDon.setTt("Đã thanh toán");  // Cập nhật trạng thái hóa đơn
            hoaDonRepository.save(hoaDon);   // Lưu lại trạng thái đã thanh toán
            return ResponseEntity.ok("Thanh toán thành công.");
        } else {
            hoaDon.setTt("Thanh toán thất bại");  // Cập nhật trạng thái hóa đơn nếu thanh toán thất bại
            hoaDonRepository.save(hoaDon);   // Lưu lại trạng thái thanh toán thất bại
            return ResponseEntity.badRequest().body("Thanh toán thất bại.");
        }
    }

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/khuyen-mai")
    public ResponseEntity<String> addKhuyenMai(@RequestBody KhuyenMaiRequest request) {
        try {
            khuyenMaiService.addKhuyenMaiToProductDetails(request);
            return ResponseEntity.ok("Thêm khuyến mãi thành công");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ROLE_Admin')")
    @PutMapping("/sua-khuyen-mai")
    public ResponseEntity<String> suaKhuyenMai(@RequestBody KhuyenMaiRequest request) {
        try {
            khuyenMaiService.updateKhuyenMai(request);
            return ResponseEntity.ok("Cập nhật thành công !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/them-sp-online")
    public ResponseEntity<String> addProductToCart(@RequestBody AddToCartRequest request) {
        gioHangService.addProductToCart(
                request.getCartId()
                , request.getProductId()
                , request.getQuantity()
                , request.getIdKichThuoc());
        return ResponseEntity.ok("Product added to cart successfully");
    }

}