package com.example.saferide.service;

import com.example.saferide.entity.*;
import com.example.saferide.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class HoanTraService {
    @Autowired
    private HoaDonRepository hoaDonRepository;

    @Autowired
    private HoaDonChiTietRepository chiTietHoaDonRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    @Autowired
    private HoanTraRepository hoanTraRepository;

    @Transactional
    public HoanTra createHoanTra(Integer hoaDonId, Map<Integer, Integer> sanPhamHoanTra, String lyDo) {
        // Lấy thông tin hóa đơn
        HoaDon hoaDon = hoaDonRepository.findById(hoaDonId)
                .orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));

        // Tính toán tổng tiền hoàn trả
        BigDecimal tongTienHoanTra = BigDecimal.ZERO;

        // Duyệt qua các sản phẩm cần hoàn trả
        for (Map.Entry<Integer, Integer> entry : sanPhamHoanTra.entrySet()) {
            Integer sanPhamId = entry.getKey();
            Integer soLuongHoanTra = entry.getValue();

            // Kiểm tra số lượng hợp lệ
            if (soLuongHoanTra <= 0) {
                throw new RuntimeException("Số lượng hoàn trả phải lớn hơn 0");
            }

            // Lấy thông tin sản phẩm trong hóa đơn chi tiết
            HoaDonChiTiet chiTiet = chiTietHoaDonRepository.findByIdHoaDon_Id(hoaDonId).stream()
                    .filter(ct -> ct.getIdSPCT().getId() == sanPhamId)
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong hóa đơn"));

            if (soLuongHoanTra > chiTiet.getSl()) {
                throw new RuntimeException("Số lượng hoàn trả vượt quá số lượng trong hóa đơn");
            }

            // Cập nhật số lượng tồn của sản phẩm
            SPChiTiet sanPhamChiTiet = chiTiet.getIdSPCT();
            sanPhamChiTiet.setSl(sanPhamChiTiet.getSl() + soLuongHoanTra);
            spChiTietRepository.save(sanPhamChiTiet);

            // Tính tiền hoàn trả (ưu tiên giá sau giảm nếu có)
            BigDecimal giaHoanTra = sanPhamChiTiet.getGiaGiam() != null && sanPhamChiTiet.getGiaGiam().compareTo(BigDecimal.ZERO) > 0
                    ? sanPhamChiTiet.getGiaGiam()
                    : sanPhamChiTiet.getDonGia();

            tongTienHoanTra = tongTienHoanTra.add(giaHoanTra.multiply(BigDecimal.valueOf(soLuongHoanTra)));
        }

        // Lưu thông tin hoàn trả và đặt trạng thái là "Chờ xác nhận"
        HoanTra hoanTra = new HoanTra();
        hoanTra.setIdHoaDon(hoaDon);
        hoanTra.setNgayHoanTra(LocalDateTime.now());
        hoanTra.setLyDo(lyDo);
        hoanTra.setTongTienHoanTra(tongTienHoanTra);
        hoanTra.setTrangThai("Chờ xác nhận");

        return hoanTraRepository.save(hoanTra);
    }

    @Transactional
    public HoanTra confirmHoanTra(Integer hoanTraId, String trangThai) {
        // Tìm hóa đơn hoàn trả theo ID
        HoanTra hoanTra = hoanTraRepository.findById(hoanTraId)
                .orElseThrow(() -> new RuntimeException("Hoàn trả không tồn tại"));

        // Kiểm tra trạng thái và cập nhật
        if ("Đã xác nhận".equalsIgnoreCase(trangThai)) {
            hoanTra.setTrangThai("Đã xác nhận");  // Cập nhật trạng thái hoàn trả thành "Đã xác nhận"
        } else if ("Đã từ chối".equalsIgnoreCase(trangThai)) {
            hoanTra.setTrangThai("Đã từ chối");  // Cập nhật trạng thái hoàn trả thành "Đã từ chối"
        } else {
            throw new RuntimeException("Trạng thái không hợp lệ");
        }

        // Cập nhật trạng thái hóa đơn nếu trạng thái hoàn trả là "Đã xác nhận"
        if ("Đã xác nhận".equals(hoanTra.getTrangThai())) {
            HoaDon hoaDon = hoanTra.getIdHoaDon();
            hoaDon.setTt("Hoàn trả đã xác nhận");  // Cập nhật trạng thái hóa đơn
            hoaDonRepository.save(hoaDon);
        }

        // Lưu lại thông tin hoàn trả đã cập nhật
        return hoanTraRepository.save(hoanTra);
    }


}
