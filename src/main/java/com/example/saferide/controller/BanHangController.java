
package com.example.saferide.controller;


import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.HoaDonChiTiet;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.HoaDonChiTietRepository;
import com.example.saferide.repository.HoaDonRepository;
import com.example.saferide.repository.SPChiTietRepository;
import com.example.saferide.repository.TaiKhoanRepository;
import com.example.saferide.request.ThanhToanRequest;
import com.example.saferide.request.ThemSanPhamRequest;
import com.example.saferide.response.InvoiceResponse;
import com.example.saferide.response.ProductInInvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/ban-hang")
public class BanHangController {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository hoaDonChiTietRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    @PostMapping("/them-hoa-don")
    public ResponseEntity<?> themHoaDon(HoaDon hoaDon, @RequestBody(required = false) String tenKhachHang, @RequestBody(required = false) String sdt) {
        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMdHms"));
        LocalDateTime fakedata = LocalDateTime.now();
        hoaDon.setMa("HD" + timeStamp);
        TaiKhoan taiKhoan = taiKhoanRepository.findById(1).orElse(null);
        hoaDon.setIdTaiKhoan(taiKhoan);
        hoaDon.setIdVoucher(null);
        hoaDon.setLoaiHoaDon(1);
        hoaDon.setNgayGiaoHang(fakedata);
        hoaDon.setNgayNhan(fakedata);
        hoaDon.setGiaGiam(null);
        hoaDon.setTongTien(new BigDecimal("0"));
        hoaDon.setSoTienDaTra(new BigDecimal("0"));
        hoaDon.setGhiChu("Don Hang Ban Tai Quay");
        hoaDon.setDiaChi("SafeRide - Hà Nội");
        hoaDon.setTt("Chưa Thanh Toan");
        hoaDon.setNguoiTao(taiKhoan != null ? taiKhoan.getTen() : "Nhan Vien 1");
        hoaDon.setNgayTao(LocalDateTime.now());
        HoaDon hoaDonSaved = hoaDonRepository.save(hoaDon);
//        themSanPham(hoaDon.getId(), 1, new BigDecimal("1"), model);
        return ResponseEntity.ok(hoaDonSaved);
    }

    @GetMapping("/hoa-don/{maHoaDon}")
    public ResponseEntity<?> selectInvoice(@PathVariable String maHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHoaDon).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        InvoiceResponse<HoaDon> response = new InvoiceResponse<>();
        if (response.data == null) {
            response.data = new ArrayList<>();
        }
        response.data.add(0, hoaDon);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/hoa-don/{maHoaDon}/sanpham")
    public ResponseEntity<?> productInInvoice(@PathVariable String maHoaDon) {
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHoaDon).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        ProductInInvoiceResponse<HoaDonChiTiet> response = new ProductInInvoiceResponse<>();
        response.data = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());
        return ResponseEntity.ok(response);
    }


    @PostMapping("/them-san-pham")
    public ResponseEntity<?> themSanPham(@RequestBody ThemSanPhamRequest request) {
        String maHoaDon = request.getMaHoaDon();
        Integer sanPhamId = request.getSanPhamId();
        BigDecimal soLuong = request.getSoLuong();

        // Retrieve the invoice by maHoaDon
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHoaDon).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        // Retrieve the product details
        SPChiTiet spChiTiet = spChiTietRepository.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Check if the product already exists in the invoice detail
        Optional<HoaDonChiTiet> existingHDCT = hoaDonChiTietRepository.findByHoaDonIdAndSanPhamId(hoaDon.getId(), sanPhamId);

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

            BigDecimal newTotal = spChiTiet.getDonGia().multiply(soLuong);
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

        // Return the updated invoice details
        return ResponseEntity.ok(hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId()));
    }

    private void updateTotalAmount(HoaDon hoaDon) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        // Sử dụng BigDecimal để tính tổng tiền
        BigDecimal totalAmount = chiTietList.stream().map(HoaDonChiTiet::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng dồn các giá trị BigDecimal
        hoaDon.setTongTien(totalAmount);
        hoaDonRepository.save(hoaDon);
    }


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

    @PostMapping("/thanh-toan")
    public ResponseEntity<?> thanhToan(@RequestBody ThanhToanRequest request) {
        String maHD = request.getMaHoaDon();
        BigDecimal soTienKhachTra = request.getSoTienKhachTra();

        // Kiểm tra mã hóa đơn
        if (maHD == null) {
            return ResponseEntity.badRequest().body("Hóa đơn không tồn tại. Vui lòng chọn hóa đơn hợp lệ.");
        }

        // Lấy hóa đơn và chi tiết hóa đơn
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHD).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());

        // Kiểm tra xem hóa đơn có sản phẩm không
        if (listHoaDonChiTiet.isEmpty()) {
            return ResponseEntity.badRequest().body("Hóa đơn này không có sản phẩm nào. Vui lòng thêm sản phẩm trước khi thanh toán.");
        }

        // Kiểm tra số tiền khách trả

        if (soTienKhachTra.compareTo(hoaDon.getTongTien()) < 0) {
            return ResponseEntity.badRequest().body("Số tiền khách trả không đủ. Vui lòng kiểm tra lại.");
        }

        // Xử lý thanh toán
        hoaDon.setTt("Đã thanh toán");
        hoaDon.setSoTienDaTra(soTienKhachTra);
        hoaDonRepository.save(hoaDon);

        // Cập nhật tồn kho sản phẩm
        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            SPChiTiet spChiTiet = hdct.getIdSPCT();

            if (spChiTiet.getSl() < hdct.getSl()) {
                return ResponseEntity.badRequest().body("Số lượng tồn kho không đủ cho sản phẩm: " + spChiTiet.getIdSanPham().getTen());
            }

            spChiTiet.setSl(spChiTiet.getSl() - hdct.getSl());
            spChiTietRepository.save(spChiTiet);
        }
        return ResponseEntity.ok("Thanh toán thành công");
    }

}
//package com.example.saferide.controller;
//
//
//import com.example.saferide.entity.HoaDon;
//import com.example.saferide.entity.HoaDonChiTiet;
//import com.example.saferide.entity.SPChiTiet;
//import com.example.saferide.entity.TaiKhoan;
//import com.example.saferide.repository.HoaDonChiTietRepository;
//import com.example.saferide.repository.HoaDonRepository;
//import com.example.saferide.repository.SPChiTietRepository;
//import com.example.saferide.repository.TaiKhoanRepository;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.*;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.ui.Model;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/ban-hang")
//public class BanHangController {
//
//    @Autowired
//    private HoaDonRepository hoaDonRepository;
//
//    @Autowired
//    private HoaDonChiTietRepository hoaDonChiTietRepository;
//
//    @Autowired
//    private SPChiTietRepository spChiTietRepository;
//
//    @Autowired
//    private TaiKhoanRepository taiKhoanRepository;
//
//    @PreAuthorize("hasAnyRole('ROLE_Admin', 'ROLE_Staff')")
//    @PostMapping("/them-hoa-don")
//    public ResponseEntity<?> themHoaDon(
//            @RequestBody HoaDon hoaDon) {
//
//        // Generate the bill code
//        String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMdHms"));
//        hoaDon.setMa("HD" + timeStamp);
//
//        // Set default properties
//        hoaDon.setLoaiHoaDon("Hóa đơn Tại Quầy");
//        hoaDon.setNgayGiaoHang(LocalDateTime.now());
//        hoaDon.setNgayNhan(LocalDateTime.now());
//        hoaDon.setTongTien(new BigDecimal("100000"));
//        hoaDon.setSoTienDaTra(new BigDecimal("100000"));
//        hoaDon.setGhiChu("Đơn Hàng Bán Tại Quầy");
//        hoaDon.setDiaChi("SafeRide - Hà Nội");
//        hoaDon.setTt("Đã thanh toán");
//        hoaDon.setNgayTao(LocalDateTime.now());
//
//        // Retrieve authenticated user
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated() &&
//                !"anonymousUser".equals(authentication.getPrincipal())) {
//
//            // Cast to UserDetails
//            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//
//            // Fetch user details from repository
//            TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(userDetails.getUsername())
//                    .orElse(null);
//
//            if (taiKhoan != null) {
//                hoaDon.setNguoiTao(taiKhoan.getTen());
//                hoaDon.setIdTaiKhoan(taiKhoan);
//            } else {
//                hoaDon.setNguoiTao("Unknown User");
//            }
//        } else {
//            hoaDon.setNguoiTao("Unknown User");
//        }
//
//        // Save the invoice
//        HoaDon savedHoaDon = hoaDonRepository.save(hoaDon);
//        return ResponseEntity.ok(savedHoaDon);
//    }
//
//
////    @GetMapping("/chon-hoa-don/{hoaDonId}")
////    public ResponseEntity<?> selectInvoice(@PathVariable Integer hoaDonId) {
////        // Retrieve the invoice based on the ID        @GetMapping("/chon-hoa-don/{hoaDonId}")
////    public ResponseEntity<?> selectInvoice(@PathVariable Integer hoaDonId) {
////        // Retrieve the invoice based on the ID
////        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
////
//////        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
////
////        // Create a response object to hold the data
////        InvoiceResponse response = new InvoiceResponse();
////        response.setHoaDon(hoaDon);
//////        response.setListHoaDonChiTiet(listHoaDonChiTiet);
////        response.setSelectedHoaDonId(hoaDonId);
////        response.setListHoaDon(hoaDonRepository.findAll());
//////        response.setListSanPhamChiTiet(spChiTietRepository.findAll());
////
////        return ResponseEntity.ok(response); // Return 200 OK with the response body
////    }
////        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
////                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
////
////        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
////
////        InvoiceResponse response = new InvoiceResponse();
////        response.setHoaDon(hoaDon);
////        response.setListHoaDonChiTiet(listHoaDonChiTiet);
////        response.setSelectedHoaDonId(hoaDonId);
////        response.setListHoaDon(hoaDonRepository.findAll());
////        response.setListSanPhamChiTiet(spChiTietRepository.findAll());
////
////        return ResponseEntity.ok(response); // Return 200 OK with the response body
////    }
//
//    @PostMapping("/them-san-pham")
//    public ResponseEntity<?> themSanPham(Integer hoaDonId, Integer sanPhamId, BigDecimal soLuong, Model model) {
//        // Retrieve the selected invoice
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
//
//        if (hoaDonId == null) {
//            model.addAttribute("error", "Không có hóa đơn để thêm sản phẩm.");
//            List<HoaDon> hoaDonList = hoaDonRepository.findAll();
//            model.addAttribute("listHoaDon", hoaDonList);
//            model.addAttribute("listSanPhamChiTiet", spChiTietRepository.findAll());
//            model.addAttribute("listHoaDonChiTiet", null);  // Đặt giá trị ban đầu là null
//        }
//
//        // Retrieve the product details
//        SPChiTiet spChiTiet = spChiTietRepository.findById(sanPhamId).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
//
//        // Check if the product already exists in the invoice detail
//        Optional<HoaDonChiTiet> existingHDCT = hoaDonChiTietRepository.findByHoaDonIdAndSanPhamId(hoaDonId, sanPhamId);
//
//        if (existingHDCT.isPresent()) {
//            // If exists, update the quantity and total amount
//            HoaDonChiTiet hdct = existingHDCT.get();
//            BigDecimal currentQuantity = BigDecimal.valueOf(hdct.getSl());
//            hdct.setSl(currentQuantity.add(soLuong).intValue());
//
//            BigDecimal newTotal = spChiTiet.getDonGia().multiply(BigDecimal.valueOf(hdct.getSl()));
//            hdct.setTongTien(newTotal);
//            hoaDonChiTietRepository.save(hdct);
//        } else {
//            // Create a new invoice detail
//            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
//            hoaDonChiTiet.setIdHoaDon(hoaDon);
//            hoaDonChiTiet.setIdSPCT(spChiTiet);
//            hoaDonChiTiet.setSl(soLuong.intValue());
//            hoaDonChiTiet.setTongTien(spChiTiet.getDonGia());
//
//            BigDecimal newTotal = spChiTiet.getDonGia().multiply(soLuong);
//            if (newTotal.compareTo(new BigDecimal("99999999.99")) > 0) {
//                model.addAttribute("error", "Tổng tiền vượt quá giới hạn cho phép.");
////                return selectInvoice(hoaDonId, model);
//            }
//            hoaDonChiTiet.setTongTien(newTotal);
//
//            // Generate mã for the new invoice detail
//            String maHDCT = "HDCT-" + hoaDon.getId() + "-" + (hoaDonChiTietRepository.countByHoaDonId(hoaDon.getId()) + 1);
//            hoaDonChiTiet.setMahdct(maHDCT);
//            hoaDonChiTiet.setTt("Đang thanh toán");
//            // Save the invoice detail
//            hoaDonChiTietRepository.save(hoaDonChiTiet);
//
//        }
//
//        // Update total amount for the invoice
//        updateTotalAmount(hoaDon);
//
//        // Redirect to display the invoice details
//        return ResponseEntity.ok(hoaDonChiTietRepository);
//    }
//
//    private void updateTotalAmount(HoaDon hoaDon) {
//        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());
//
//        // Sử dụng BigDecimal để tính tổng tiền
//        BigDecimal totalAmount = chiTietList.stream().map(HoaDonChiTiet::getTongTien).reduce(BigDecimal.ZERO, BigDecimal::add); // Cộng dồn các giá trị BigDecimal
//        hoaDon.setTongTien(totalAmount);
//        hoaDonRepository.save(hoaDon);
//    }
//
//
//    @DeleteMapping("/xoa/hdct/{id}/{hoaDonId}")
//    public ResponseEntity<?> remove(@PathVariable Integer id, @PathVariable Integer hoaDonId) {
//        // Check if the invoice detail exists
//        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(id).orElseThrow(() -> new RuntimeException("Hóa đơn chi tiết không tồn tại"));
//
//        // Remove the invoice detail
//        hoaDonChiTietRepository.deleteById(id);
//
//        // Update the total amount for the invoice
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
//        updateTotalAmount(hoaDon);
//
//        // Redirect to the invoice details page
//        return ResponseEntity.ok("Xóa thành công <3");
//    }
//
//
//    @PostMapping("/thanh-toan")
//    public ResponseEntity<?> thanhToan(@RequestParam(required = false) Integer hoaDonId, @RequestParam BigDecimal soTienKhachTra, Model model) {
//        if (hoaDonId == null) {
//            model.addAttribute("error", "Hóa đơn không tồn tại. Vui lòng chọn hóa đơn hợp lệ.");
//            List<HoaDon> hoaDonList = hoaDonRepository.findAll();
//            model.addAttribute("listHoaDon", hoaDonList);
//            model.addAttribute("listSanPhamChiTiet", spChiTietRepository.findAll());
//            model.addAttribute("listHoaDonChiTiet", null);  // Đặt giá trị ban đầu là null
//        }
//
//        // Retrieve the invoice and its details
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
//
//
//        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
//
//        // Check if there are any product details in the invoice
//        if (listHoaDonChiTiet.isEmpty()) {
//            model.addAttribute("error", "Hóa đơn này không có sản phẩm nào. Vui lòng thêm sản phẩm trước khi thanh toán.");
//            //            return selectInvoice(hoaDonId, model);
//        }
//
//        // Check the payment amount
//        if (soTienKhachTra.compareTo(hoaDon.getTongTien()) < 0) {
//            model.addAttribute("error", "Số tiền khách trả không đủ. Vui lòng kiểm tra lại.");
//            //            return selectInvoice(hoaDonId, model);
//        }
//
//        // Process the payment
//        hoaDon.setTt("Đã thanh toán");
//        hoaDonRepository.save(hoaDon);
//
//        // Update product stock
//        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
//            SPChiTiet spChiTiet = hdct.getIdSPCT();
//
//            if (spChiTiet.getSl() < hdct.getSl()) {
//                model.addAttribute("error", "Số lượng tồn kho không đủ cho sản phẩm: " + spChiTiet.getIdSanPham().getTen());
//                //                return selectInvoice(hoaDonId, model);
//            }
//
//            spChiTiet.setSl(spChiTiet.getSl() - hdct.getSl());
//            spChiTietRepository.save(spChiTiet);
//        }
//        hoaDonRepository.deleteById(hoaDonId);
//        return ResponseEntity.ok("Thanh toán thành công");
//    }
//
//    @PreAuthorize("hasAnyRole('ROLE_Admin','ROLE_Staff')")
//    @PostMapping("/giao-hang-quay")
//    public ResponseEntity<?> giaoHangTaiQuay(@RequestParam Integer hoaDonId,
//                                             @RequestParam(required = false) String diaChi) {
//        // Lấy hóa đơn
//        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy hóa đơn"));
//
//        hoaDon.setTt("Chờ xác nhận");
//        hoaDon.setDiaChi(diaChi);
//        hoaDonRepository.save(hoaDon);
//
//        // Lấy các thông tin địa chỉ người nhận (cần phân tách tỉnh, quận/huyện từ diaChi)
//        String receiverProvince = getProvinceFromDiaChi(diaChi);  // Phân tách tỉnh từ địa chỉ
//        String receiverDistrict = getDistrictFromDiaChi(diaChi);  // Phân tách quận từ địa chỉ
//
//        // Lấy phí vận chuyển từ API GHN
//        BigDecimal shippingFee = getShippingFeeFromGHN(receiverProvince, receiverDistrict);
//        System.out.println("Địa chỉ nhận: " + diaChi);
//        if (shippingFee == null) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Không thể lấy phí vận chuyển từ API GHN.");
//        }
//
//        // Lưu phí vận chuyển vào hóa đơn
//        hoaDon.setPhiVanChuyen(shippingFee);
//        hoaDonRepository.save(hoaDon);
//
//        return ResponseEntity.ok(hoaDon);
//    }
//
//    // Lấy phí vận chuyển từ API GHN
//    private BigDecimal getShippingFeeFromGHN(String province, String district) {
//        try {
//            // Cấu hình RestTemplate
//            RestTemplate restTemplate = new RestTemplate();
//            String ghnApiUrl = "https://online-gateway.ghn.vn/shiip/public-api/v2/shipping-order/fee";
//
//            String pickProvince = "Hà Nội";  // Địa chỉ lấy hàng (có thể chỉnh sửa)
//            String pickDistrict = "Nam Từ Liêm";  // Quận lấy hàng (có thể chỉnh sửa)
//            String weight = "80";  // Cân nặng hàng hóa (cần lấy từ dữ liệu hóa đơn)
//
//            // Dữ liệu yêu cầu gửi lên GHN API
//            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
//            requestBody.add("token", "abbdf073-b076-11ef-9f10-7e9ee24084dc");  // Token GHN của bạn
//            requestBody.add("from_province", pickProvince);  // Tỉnh của người gửi
//            requestBody.add("from_district", pickDistrict);  // Quận của người gửi
//            requestBody.add("to_province", province); // Tỉnh người nhận
//            requestBody.add("to_district", district); // Quận người nhận
//            requestBody.add("weight", weight); // Trọng lượng hàng (gram)
//
//            // Cấu hình request
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(requestBody, headers);
//
//            // Gửi request đến GHN API
//            ResponseEntity<String> response = restTemplate.exchange(ghnApiUrl, HttpMethod.POST, entity, String.class);
//
//            if (response.getStatusCode() == HttpStatus.OK) {
//                String feeString = response.getBody();
//                System.out.println("API Response: " + feeString);
//                JSONObject jsonResponse = new JSONObject(feeString);
//                if (jsonResponse.has("code") && jsonResponse.getInt("code") == 200) {
//                    JSONObject feeDetails = jsonResponse.getJSONObject("data");
//                    if (feeDetails.has("fee")) {
//                        return feeDetails.getBigDecimal("fee");
//                    }
//                } else {
//                    System.err.println("Phản hồi API không thành công: " + jsonResponse.optString("message"));
//                }
//            } else {
//                System.err.println("Lỗi HTTP từ API GHN: " + response.getStatusCode());
//            }
//        } catch (Exception e) {
//            System.err.println("Lỗi khi gọi API GHN: " + e.getMessage());
//        }
//        return null;
//    }
//
//    // Phân tách tỉnh từ địa chỉ
//    private String getProvinceFromDiaChi(String diaChi) {
//        if (diaChi == null || diaChi.isEmpty()) {
//            return null;
//        }
//        String[] parts = diaChi.split(",");
//        if (parts.length > 0) {
//            return parts[0].trim();
//        }
//        return null;
//    }
//
//    // Phân tách quận từ địa chỉ
//    private String getDistrictFromDiaChi(String diaChi) {
//        if (diaChi == null || diaChi.isEmpty()) {
//            return null;
//        }
//        String[] parts = diaChi.split(",");
//        if (parts.length > 1) {
//            return parts[1].trim();
//        }
//        return null;
//    }
//
//
//}