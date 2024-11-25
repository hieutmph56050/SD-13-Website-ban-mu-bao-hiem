package com.example.saferide.controller;

import com.example.saferide.entity.*;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/online")
public class BanHangOnlineController {

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

    @DeleteMapping("/xoa/{id}")
    public ResponseEntity<?> xoaGioHang(@PathVariable Integer id) {
        gioHangRepository.deleteById(id);
        return ResponseEntity.ok("Xóa thành công");
    }

    @PostMapping("/them-san-pham")
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
        hoaDon.setLoaiHoaDon("Hóa Đơn Online");
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
            hoaDonChiTiet.setGia(gioHangChiTiet.getDonGia());
            hoaDonChiTiet.setSl(gioHangChiTiet.getSl());
            hoaDonChiTiet.setTt("Chưa thanh toán");
            String maHDCT = "HDCT" + hoaDon.getId();
            hoaDonChiTiet.setMahdct(maHDCT);
            hoaDonChiTiet.setTongTien(gioHangChiTiet.getDonGia().multiply(BigDecimal.valueOf(gioHangChiTiet.getSl())));

            hoaDonChiTietRepository.save(hoaDonChiTiet);
        }

        return ResponseEntity.ok("Đặt hàng thành công, Mã hóa đơn: " + maHD);
    }

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

                BigDecimal giaSanPham = (chiTiet.getGia() != null) ? chiTiet.getGia() : BigDecimal.ZERO;
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


}
