package com.example.saferide.controller;

import com.example.saferide.entity.*;
import com.example.saferide.repository.*;
import com.example.saferide.service.HoaDonService;
import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
        hoaDon.setNguoiTao("Phạm Anh Tuấn");

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

    @PreAuthorize("hasRole('ROLE_Admin')")
    @GetMapping("/in-hoa-don")
    public void inHoaDon(@RequestParam String maHoaDon, HttpServletResponse response) {
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

            //Thêm Logo vào Hóa đơn
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
            float[] columnWidths = {2, 1, 1, 1}; // Tỉ lệ độ rộng các cột
            Table table = new Table(columnWidths);

            // Thêm tiêu đề cột
            table.addHeaderCell(new Cell().add(new Paragraph("Tên sản phẩm").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Số lượng").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Đơn giá").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Thành tiền").setBold()));

            // Chi tiết hóa đơn
            BigDecimal tongTien = BigDecimal.ZERO;
            for (HoaDonChiTiet chiTiet : chiTietList) {
                String tenSanPham = (chiTiet.getIdSPCT() != null && chiTiet.getIdSPCT().getMa() != null)
                        ? chiTiet.getIdSPCT().getIdSanPham().getTen()
                        : "Không xác định";

                BigDecimal giaSanPham = chiTiet.getIdSPCT().getDonGia();
                String soLuong = String.valueOf(chiTiet.getSl());
                BigDecimal thanhTien = giaSanPham.multiply(BigDecimal.valueOf(chiTiet.getSl()));

                // Cộng dồn vào tổng tiền
                tongTien = tongTien.add(thanhTien);

                // Thêm chi tiết sản phẩm vào bảng
                table.addCell(new Cell().add(new Paragraph(tenSanPham)));
                table.addCell(new Cell().add(new Paragraph(soLuong)));
                table.addCell(new Cell().add(new Paragraph(giaSanPham.toString() + " VND")));
                table.addCell(new Cell().add(new Paragraph(thanhTien.toString() + " VND")));
            }

            // Thêm bảng vào tài liệu
            document.add(table);

            // Tổng tiền
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("TỔNG TIỀN: " + tongTien.toString() + " VND")
                    .setBold()
                    .setFontSize(14));

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


    //    @GetMapping("/chon-hoa-don/{hoaDonId}")
//    public ResponseEntity<?> selectInvoice(@PathVariable Integer hoaDonId) {
//        // Retrieve the invoice based on the ID        @GetMapping("/chon-hoa-don/{hoaDonId}")
//    public ResponseEntity<?> selectInvoice(@PathVariable Integer hoaDonId) {
//        // Retrieve the invoice based on the ID
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
//
////        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
//
//        // Create a response object to hold the data
//        InvoiceResponse response = new InvoiceResponse();
//        response.setHoaDon(hoaDon);
////        response.setListHoaDonChiTiet(listHoaDonChiTiet);
//        response.setSelectedHoaDonId(hoaDonId);
//        response.setListHoaDon(hoaDonRepository.findAll());
////        response.setListSanPhamChiTiet(spChiTietRepository.findAll());
//
//        return ResponseEntity.ok(response); // Return 200 OK with the response body
//    }
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
//                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
//
//        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
//
//        InvoiceResponse response = new InvoiceResponse();
//        response.setHoaDon(hoaDon);
//        response.setListHoaDonChiTiet(listHoaDonChiTiet);
//        response.setSelectedHoaDonId(hoaDonId);
//        response.setListHoaDon(hoaDonRepository.findAll());
//        response.setListSanPhamChiTiet(spChiTietRepository.findAll());
//
//        return ResponseEntity.ok(response); // Return 200 OK with the response body
//    }
    @PreAuthorize("hasRole('ROLE_Admin')")
    @PostMapping("/them-san-pham-off")
    public ResponseEntity<?> themSanPham(Integer hoaDonId, Integer sanPhamId, BigDecimal soLuong, Model model) {
        // Retrieve the selected invoice
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        if (hoaDonId == null) {
            model.addAttribute("error", "Không có hóa đơn để thêm sản phẩm.");
            List<HoaDon> hoaDonList = hoaDonRepository.findAll();
            model.addAttribute("listHoaDon", hoaDonList);
            model.addAttribute("listSanPhamChiTiet", spChiTietRepository.findAll());
            model.addAttribute("listHoaDonChiTiet", null);  // Đặt giá trị ban đầu là null
        }

        // Retrieve the product details
        SPChiTiet spChiTiet = spChiTietRepository.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

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
        } else {
            // Create a new invoice detail
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setIdHoaDon(hoaDon);
            hoaDonChiTiet.setIdSPCT(spChiTiet);
            hoaDonChiTiet.setSl(soLuong.intValue());
            hoaDonChiTiet.setTongTien(spChiTiet.getDonGia());

            BigDecimal newTotal = spChiTiet.getDonGia().multiply(soLuong);
            if (newTotal.compareTo(new BigDecimal("99999999.99")) > 0) {
                model.addAttribute("error", "Tổng tiền vượt quá giới hạn cho phép.");
//                return selectInvoice(hoaDonId, model);
            }
            hoaDonChiTiet.setTongTien(newTotal);

            // Generate mã for the new invoice detail
            String maHDCT = "HDCT-" + hoaDon.getId() + "-" + (hoaDonChiTietRepository.countByHoaDonId(hoaDon.getId()) + 1);
            hoaDonChiTiet.setMahdct(maHDCT);
            hoaDonChiTiet.setTt("Đang thanh toán");
            // Save the invoice detail
            hoaDonChiTietRepository.save(hoaDonChiTiet);

        }

        // Update total amount for the invoice
        updateTotalAmount(hoaDon);

        // Redirect to display the invoice details
        return ResponseEntity.ok(hoaDonChiTietRepository);
    }

    private void updateTotalAmount(HoaDon hoaDon) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        // Sử dụng BigDecimal để tính tổng tiền
        BigDecimal totalAmount = chiTietList.stream().map(HoaDonChiTiet::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng dồn các giá trị BigDecimal
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
    public ResponseEntity<?> thanhToan(@RequestParam(required = false) Integer hoaDonId, @RequestParam BigDecimal soTienKhachTra, Model model) {
        if (hoaDonId == null) {
            model.addAttribute("error", "Hóa đơn không tồn tại. Vui lòng chọn hóa đơn hợp lệ.");
            List<HoaDon> hoaDonList = hoaDonRepository.findAll();
            model.addAttribute("listHoaDon", hoaDonList);
            model.addAttribute("listSanPhamChiTiet", spChiTietRepository.findAll());
            model.addAttribute("listHoaDonChiTiet", null);  // Đặt giá trị ban đầu là null
        }

        // Retrieve the invoice and its details
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));


        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);

        // Check if there are any product details in the invoice
        if (listHoaDonChiTiet.isEmpty()) {
            model.addAttribute("error", "Hóa đơn này không có sản phẩm nào. Vui lòng thêm sản phẩm trước khi thanh toán.");
            //            return selectInvoice(hoaDonId, model);
        }

        // Check the payment amount
        if (soTienKhachTra.compareTo(hoaDon.getTongTien()) < 0) {
            model.addAttribute("error", "Số tiền khách trả không đủ. Vui lòng kiểm tra lại.");
            //            return selectInvoice(hoaDonId, model);
        }

        // Process the payment
        hoaDon.setTt("Đã thanh toán");
        hoaDonRepository.save(hoaDon);

        // Update product stock
        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            SPChiTiet spChiTiet = hdct.getIdSPCT();

            if (spChiTiet.getSl() < hdct.getSl()) {
                model.addAttribute("error", "Số lượng tồn kho không đủ cho sản phẩm: " + spChiTiet.getIdSanPham().getTen());
                //                return selectInvoice(hoaDonId, model);
            }

            spChiTiet.setSl(spChiTiet.getSl() - hdct.getSl());
            spChiTietRepository.save(spChiTiet);
        }
        hoaDonRepository.deleteById(hoaDonId);
        return ResponseEntity.ok("Thanh toán thành công");
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

    @Value("${ghn.token}")
    private String ghnToken;

}
