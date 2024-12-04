package com.example.saferide.controller;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.*;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_Staff')")
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

    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_Staff')")
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

    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_Staff')")
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

    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_Staff')")
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
}
