package com.example.saferide.controller;


import com.example.saferide.entity.*;
import com.example.saferide.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
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

    @GetMapping("/hien-thi")
    public String viewBanHang(Model model) {
        List<HoaDon> hoaDonList = hoaDonRepository.findAll();
        model.addAttribute("listHoaDon", hoaDonList);
        model.addAttribute("listSanPhamChiTiet", spChiTietRepository.findAll());
        model.addAttribute("listHoaDonChiTiet", null);  // Đặt giá trị ban đầu là null
        return "banhang/banhang";
    }

    @PostMapping("/them-hoa-don")
    public String themHoaDon(HoaDon hoaDon) {
        hoaDon.setNgayTao(LocalDateTime.now());
        hoaDon.setTongTien(0);
        hoaDon.setTt("Chưa thanh toán");
        hoaDon.setDiaChi("SafeRide - Hà Nội");
        TaiKhoan taiKhoan = taiKhoanRepository.findById(6).orElse(null);
        hoaDon.setIdTaiKhoan(taiKhoan);
        hoaDonRepository.save(hoaDon);
        return "redirect:/ban-hang/hien-thi";
    }

    @GetMapping("/chon-hoa-don")
    public String selectInvoice(Integer hoaDonId, Model model) {
        // Retrieve the invoice based on the ID
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);

        // Update the model to send to the JSP
        model.addAttribute("hoaDon", hoaDon);
        model.addAttribute("listHoaDonChiTiet", listHoaDonChiTiet);
        model.addAttribute("selectedHoaDonId", hoaDonId);
        model.addAttribute("listHoaDon", hoaDonRepository.findAll());
        model.addAttribute("listSanPhamChiTiet", spChiTietRepository.findAll());

        return "banhang/banhang"; // Return the same JSP view
    }

    @PostMapping("/them-san-pham")
    public String themSanPham(Integer hoaDonId, Integer sanPhamId, int soLuong, Model model) {
        // Retrieve the selected invoice
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        // Retrieve the product details
        SPChiTiet spChiTiet = spChiTietRepository.findById(sanPhamId)
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));

        // Check if the product already exists in the invoice detail
        Optional<HoaDonChiTiet> existingHDCT = hoaDonChiTietRepository.findByHoaDonIdAndSanPhamId(hoaDonId, sanPhamId);

        if (existingHDCT.isPresent()) {
            // If exists, update the quantity and total amount
            HoaDonChiTiet hdct = existingHDCT.get();
            hdct.setSl(hdct.getSl() + soLuong);
            hdct.setTongTien(hdct.getSl() * hdct.getGia());
            hoaDonChiTietRepository.save(hdct);
        } else {
            // If does not exist, create a new invoice detail
            HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet();
            hoaDonChiTiet.setIdHoaDon(hoaDon);
            hoaDonChiTiet.setIdSPCT(spChiTiet);
            hoaDonChiTiet.setSl(soLuong);
            hoaDonChiTiet.setGia(spChiTiet.getDonGia());
            hoaDonChiTiet.setTongTien(spChiTiet.getDonGia() * soLuong);

            // Generate mã for the new invoice detail
            String maHDCT = "HDCT-" + hoaDon.getId() + "-" + (hoaDonChiTietRepository.countByHoaDonId(hoaDon.getId()) + 1);
            hoaDonChiTiet.setMa(maHDCT);
            hoaDonChiTiet.setTt("Đang thanh toán");

            // Save the invoice detail
            hoaDonChiTietRepository.save(hoaDonChiTiet);
        }

        // Update total amount for the invoice
        updateTotalAmount(hoaDon);

        // Redirect to display the invoice details
        return "redirect:/ban-hang/chon-hoa-don?hoaDonId=" + hoaDonId;
    }


    private void updateTotalAmount(HoaDon hoaDon) {
        List<HoaDonChiTiet> chiTietList = hoaDonChiTietRepository.findByHoaDonId(hoaDon.getId());
        int totalAmount = chiTietList.stream()
                .mapToInt(HoaDonChiTiet::getTongTien)
                .sum();

        hoaDon.setTongTien(totalAmount);
        hoaDonRepository.save(hoaDon);
    }

    @GetMapping("/xoa/hdct/{id}")
    public String remove(@PathVariable Integer id, Integer hoaDonId) {
        // Check if the invoice detail exists
        HoaDonChiTiet hoaDonChiTiet = hoaDonChiTietRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hóa đơn chi tiết không tồn tại"));

        // Remove the invoice detail
        hoaDonChiTietRepository.deleteById(id);

        // Update the total amount for the invoice
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        updateTotalAmount(hoaDon);

        // Redirect to the invoice details page
        return "redirect:/ban-hang/chon-hoa-don?hoaDonId=" + hoaDonId;
    }

    @PostMapping("/thanh-toan")
    public String thanhToan(@RequestParam Integer hoaDonId, @RequestParam int soTienKhachTra, Model model) {
        // Lấy hóa đơn dựa trên ID

        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        // Kiểm tra xem hóa đơn có sản phẩm chi tiết nào không
        List<HoaDonChiTiet> listHoaDonChiTiet = hoaDonChiTietRepository.findByHoaDonId(hoaDonId);
        if (listHoaDonChiTiet.isEmpty()) {
            model.addAttribute("error", "Hóa đơn này không có sản phẩm nào. Vui lòng thêm sản phẩm trước khi thanh toán.");
            return selectInvoice(hoaDonId, model);
        }

        Optional<HoaDon> hoaDonList = hoaDonRepository.findById(hoaDonId);


        // Kiểm tra số tiền khách trả
        if (soTienKhachTra < hoaDon.getTongTien()) {
            model.addAttribute("error", "Số tiền khách trả không đủ. Vui lòng kiểm tra lại.");
            return selectInvoice(hoaDonId, model);
        }

        // Nếu đủ, thực hiện thanh toán
        hoaDon.setTt("Đã thanh toán"); // Cập nhật trạng thái hóa đơn
        hoaDonRepository.save(hoaDon);

        // Trừ số lượng sản phẩm trong kho dựa trên hóa đơn chi tiết
        for (HoaDonChiTiet hdct : listHoaDonChiTiet) {
            SPChiTiet spChiTiet = hdct.getIdSPCT(); // Lấy sản phẩm chi tiết

            // Kiểm tra số lượng tồn kho
            if (spChiTiet.getSl() < hdct.getSl()) {
                model.addAttribute("error", "Số lượng tồn kho không đủ cho sản phẩm: " + spChiTiet.getIdSanPham().getTen());
                return selectInvoice(hoaDonId, model);
            }

            // Trừ số lượng trong kho
            spChiTiet.setSl(spChiTiet.getSl() - hdct.getSl());
            spChiTietRepository.save(spChiTiet); // Cập nhật lại sản phẩm chi tiết sau khi trừ
        }

        hoaDonRepository.deleteById(hoaDonId);
        model.addAttribute("thanhcong", "Thanh toán thành công!");
        return "redirect:/ban-hang/hien-thi";
    }



}
