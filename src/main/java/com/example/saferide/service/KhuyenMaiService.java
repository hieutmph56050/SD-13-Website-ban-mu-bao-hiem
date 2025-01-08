package com.example.saferide.service;

import com.example.saferide.entity.KhuyenMai;
import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.entity.TaiKhoan;
import com.example.saferide.repository.KhuyenMaiRepository;
import com.example.saferide.repository.SPChiTietRepository;
import com.example.saferide.repository.SanPhamRepository;
import com.example.saferide.repository.TaiKhoanRepository;
import com.example.saferide.request.KhuyenMaiRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class KhuyenMaiService {

    @Autowired
    KhuyenMaiRepository khuyenmaiRepository;

    @Autowired
    private SPChiTietRepository spChiTietRepository;

    private final KhuyenMaiRepository khuyenMaiRepository;

    @Autowired
    private TaiKhoanRepository taiKhoanRepository;

    private final SanPhamRepository sanPhamRepository;

    public List<KhuyenMai> getList() {
        return khuyenmaiRepository.findAll();
    }

    public KhuyenMai findById(Integer id) {
        return khuyenmaiRepository.findById(id).get();
    }

    public KhuyenMaiService(KhuyenMaiRepository khuyenMaiRepository, SanPhamRepository sanPhamRepository) {
        this.khuyenMaiRepository = khuyenMaiRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    public KhuyenMai update(KhuyenMai khuyenMai, Integer id) {
        Optional<KhuyenMai> optional = khuyenmaiRepository.findById(id);
        return optional.map(khuyenMai1 -> {
            khuyenMai1.setMa(khuyenMai.getMa());
            khuyenMai1.setTen(khuyenMai.getTen());
            khuyenMai1.setGiaTri(khuyenMai.getGiaTri());
            khuyenMai1.setNgayBD(khuyenMai.getNgayBD());
            khuyenMai1.setNgayKT(khuyenMai.getNgayKT());
            khuyenMai1.setPTKM(khuyenMai.getPTKM());
            khuyenMai1.setDKKM(khuyenMai.getDKKM());
            khuyenMai1.setNguoiCapNhat(khuyenMai.getNguoiCapNhat());
            khuyenMai1.setNgayCapNhat(LocalDateTime.now());
            khuyenMai1.setTt(khuyenMai.getTt());
            return khuyenmaiRepository.save(khuyenMai1);
        }).orElse(null);
    }
    public KhuyenMai delete(Integer id) {
        Optional<KhuyenMai> optional = khuyenmaiRepository.findById(id);
        return optional.map(khuyenMai1 -> {
            khuyenmaiRepository.delete(khuyenMai1);
            return khuyenMai1;
        }).orElse(null);
    }
    // Tìm kiếm theo tên hoặc mã
    public List<KhuyenMai> search(String keyword) {
        if (keyword == null || keyword.isEmpty()) {
            return khuyenmaiRepository.findAll();
        }
        return khuyenmaiRepository.findByMaContainingOrTenContaining(keyword, keyword);
    }

    // Phân trang
    public Page<KhuyenMai> getPage(Pageable pageable) {
        return khuyenmaiRepository.findAll(pageable);
    }

    @Transactional
    public void addKhuyenMaiToProductDetails(KhuyenMaiRequest request) {
        // Tạo đối tượng khuyến mãi

        Optional<KhuyenMai> existingKhuyenMai = khuyenMaiRepository.findByMa(request.getMa());
        if (existingKhuyenMai.isPresent()) {
            throw new IllegalArgumentException("Mã khuyến mại đã tồn tại");
        }

        KhuyenMai khuyenMai = new KhuyenMai();
        khuyenMai.setMa(request.getMa());
        khuyenMai.setTen(request.getTen());
        khuyenMai.setGiaTri(request.getGiaTri());
        LocalDateTime today = LocalDateTime.now();
        khuyenMai.setNgayBD(today);
        khuyenMai.setNgayKT(request.getNgayKT());
        khuyenMai.setPTKM(request.getPTKM());
        khuyenMai.setDKKM(request.getDKKM());
        khuyenMai.setTt(request.getTt());

        // Lấy thông tin người tạo từ SecurityContextHolder
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nguoiTao = "Unknown User"; // Giá trị mặc định
        if (authentication != null && authentication.isAuthenticated() &&
                !"anonymousUser".equals(authentication.getPrincipal())) {

            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(userDetails.getUsername())
                        .orElse(null);
                if (taiKhoan != null) {
                    nguoiTao = taiKhoan.getTen();
                }
            }
        }
        if (request.getNguoiTao() != null && !request.getNguoiTao().isEmpty()) {
            nguoiTao = request.getNguoiTao();
        }

        khuyenMai.setNguoiTao(nguoiTao);

        // Lưu khuyến mãi
        khuyenMaiRepository.save(khuyenMai);

        // Liên kết khuyến mãi với chi tiết sản phẩm
        for (Integer productDetailId : request.getProductDetailIds()) {
            SPChiTiet spChiTiet = spChiTietRepository.findById(productDetailId)
                    .orElseThrow(() -> new IllegalArgumentException("Product Detail ID " + productDetailId + " not found."));
            spChiTiet.setIdKhuyenMai(khuyenMai); // Gán khuyến mãi vào sản phẩm

            // Tính giá sau khuyến mãi
            BigDecimal giaGoc = spChiTiet.getDonGia(); // Lấy giá gốc
            BigDecimal giaGiam = BigDecimal.ZERO;

            if (khuyenMai.getPTKM() != null && khuyenMai.getGiaTri().compareTo(BigDecimal.ZERO) > 0) {
                // Tính theo phần trăm khuyến mãi
                giaGiam = giaGoc.multiply(khuyenMai.getGiaTri()).divide(BigDecimal.valueOf(100));
            } else if (khuyenMai.getGiaTri() != null && khuyenMai.getGiaTri().compareTo(BigDecimal.ZERO) > 0) {
                // Tính theo giá trị khuyến mãi
                giaGiam = khuyenMai.getGiaTri();
            }

            // Cập nhật giá mới (không âm)
            BigDecimal newPrice = giaGoc.subtract(giaGiam);
            spChiTiet.setGiaGiam(newPrice.compareTo(BigDecimal.ZERO) > 0 ? newPrice : BigDecimal.ZERO);

            spChiTietRepository.save(spChiTiet);
        }
    }

    @Transactional
    public void updateKhuyenMai(KhuyenMaiRequest request) {
        // Check if the promo code exists
        Optional<KhuyenMai> existingKhuyenMai = khuyenMaiRepository.findByMa(request.getMa());
        if (!existingKhuyenMai.isPresent()) {
            throw new IllegalArgumentException("Mã khuyến mại không tồn tại");
        }

        KhuyenMai khuyenMai = existingKhuyenMai.get();

        // Update the promo details
        khuyenMai.setTen(request.getTen());
        khuyenMai.setGiaTri(request.getGiaTri());
        khuyenMai.setNgayKT(request.getNgayKT());
        khuyenMai.setPTKM(request.getPTKM());
        khuyenMai.setDKKM(request.getDKKM());
        khuyenMai.setTt(request.getTt());

        // Set the creator (optional)
        String nguoiTao = "Unknown User"; // Default value
        if (request.getNguoiTao() != null && !request.getNguoiTao().isEmpty()) {
            nguoiTao = request.getNguoiTao();
        } else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated() &&
                    !"anonymousUser".equals(authentication.getPrincipal())) {

                Object principal = authentication.getPrincipal();
                if (principal instanceof UserDetails) {
                    UserDetails userDetails = (UserDetails) principal;
                    TaiKhoan taiKhoan = taiKhoanRepository.findByTenDangNhap(userDetails.getUsername())
                            .orElse(null);
                    if (taiKhoan != null) {
                        nguoiTao = taiKhoan.getTen();
                    }
                }
            }
        }
        khuyenMai.setNguoiTao(nguoiTao);

        // Save the updated promo
        khuyenMaiRepository.save(khuyenMai);

        // Check if product details are provided
        if (request.getProductDetailIds() != null && !request.getProductDetailIds().isEmpty()) {
            // Link promo to product details
            for (Integer productDetailId : request.getProductDetailIds()) {
                SPChiTiet spChiTiet = spChiTietRepository.findById(productDetailId)
                        .orElseThrow(() -> new IllegalArgumentException("Product Detail ID " + productDetailId + " not found."));
                spChiTiet.setIdKhuyenMai(khuyenMai); // Assign the promo to the product detail

                // Calculate the price after discount
                BigDecimal giaGoc = spChiTiet.getDonGia(); // Original price
                BigDecimal giaGiam = BigDecimal.ZERO;

                if (khuyenMai.getPTKM() != null && khuyenMai.getGiaTri().compareTo(BigDecimal.ZERO) > 0) {
                    // Calculate based on promo percentage
                    giaGiam = giaGoc.multiply(khuyenMai.getGiaTri()).divide(BigDecimal.valueOf(100));
                } else if (khuyenMai.getGiaTri() != null && khuyenMai.getGiaTri().compareTo(BigDecimal.ZERO) > 0) {
                    // Calculate based on promo value
                    giaGiam = khuyenMai.getGiaTri();
                }

                // Update the new price (non-negative)
                BigDecimal newPrice = giaGoc.subtract(giaGiam);
                spChiTiet.setGiaGiam(newPrice.compareTo(BigDecimal.ZERO) > 0 ? newPrice : BigDecimal.ZERO);

                spChiTietRepository.save(spChiTiet); // Save product detail
            }
        }
    }


}
