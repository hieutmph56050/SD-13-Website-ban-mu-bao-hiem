package com.example.saferide.service;

import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.GioHangChiTiet;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangService {
    @Autowired
    GioHangRepository giohangRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    @Autowired
    private GioHangChiTietRepository gioHangChiTietRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    public List<GioHang> getList() {
        return giohangRepository.findAll();
    }

    public GioHang findById(Integer id) {
        return giohangRepository.findById(id).get();
    }

    public GioHang add(GioHang gioHang) {
        return giohangRepository.save(gioHang);
    }

    public GioHang update(GioHang gioHang, Integer id) {
        Optional<GioHang> optional = giohangRepository.findById(id);
        return optional.map(gioHang1 -> {
            gioHang1.setIdTaiKhoan(gioHang.getIdTaiKhoan());
            gioHang1.setNgayCapNhat(LocalDateTime.now());
            gioHang1.setTt(gioHang.isTt());
            return giohangRepository.save(gioHang1);
        }).orElse(null);
    }

    public void deleteById(Integer id) {
        giohangRepository.deleteById(id);
    }

    public Page<GioHang> search(Integer idTaiKhoan, Boolean tt, Pageable pageable) {
        return giohangRepository.findByIdTaiKhoan_IdOrTt(idTaiKhoan, tt, pageable);
    }

    public GioHang createNewCart() {
        GioHang gioHang = new GioHang();
        gioHang.setIdTaiKhoan(taiKhoanRepository.findById(31)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng")));
        gioHang.setNgayTao(LocalDateTime.now());
        gioHang.setTt(true); // Trạng thái hoạt động
        return giohangRepository.save(gioHang);
    }

    public void addProductToCart(int cartId, int productId, int quantity, int idKichThuoc) {
        SPChiTiet product = spChiTietRepository.findByIdAndIdKichThuoc_Id(productId, idKichThuoc)
                .orElseThrow(() -> new RuntimeException("Product not found or Size"));
        GioHang gioHang = giohangRepository.findById(cartId)
                .orElseGet(() -> {
                    // If cart does not exist, create a new one
                    GioHang newCart = new GioHang();
                    newCart.setIdTaiKhoan(taiKhoanRepository.findById(31).orElse(null));
                    newCart.setTt(true);
                    newCart.setNgayTao(LocalDateTime.now());
                    return giohangRepository.save(newCart);
                });
        GioHangChiTiet cartItem = new GioHangChiTiet();
        cartItem.setIdGioHang(gioHang);
        cartItem.setIdSPCT(product);
        cartItem.setSl(quantity);
        cartItem.setDonGia(product.getDonGia().multiply(new BigDecimal(quantity)));
        cartItem.setTt("Chưa thanh toán");
        cartItem.setNgayTao(LocalDateTime.now());
        gioHangChiTietRepository.save(cartItem);
    }

    public List<GioHangChiTiet> getCartDetails(int cartId) {
        return gioHangChiTietRepository.findAll();
    }

    public void checkoutCart(int cartId) {
        GioHang gioHang = giohangRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));
        gioHang.setTt(true); // Giỏ hàng đã thanh toán
        giohangRepository.save(gioHang);
    }

}
