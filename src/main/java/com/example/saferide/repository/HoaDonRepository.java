package com.example.saferide.repository;


import com.example.saferide.entity.GioHang;
import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.HoaDonChiTiet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Integer> {

    @Query("SELECT h FROM HoaDon h WHERE h.idTaiKhoan.ten LIKE %:HoTen%")
    List<HoaDon> findByCustomerName(String HoTen);

    Optional<HoaDon> findByMa(String maHoaDon);

    @Query("SELECT h FROM HoaDon h WHERE h.ma = :maHoaDon")
    Optional<HoaDon> findByMaHoaDon(String maHoaDon);

    @Query("SELECT hdc FROM HoaDon hdc WHERE hdc.loaiHoaDon = :loaiHoaDon")
    List<HoaDon> findHDByLoaiHoaDon(@Param("loaiHoaDon") Integer loaiHoaDon);

    // Lấy doanh thu theo ngày
    @Query(value = "SELECT SUM(h.tongTien) " +
            "FROM hoadon h " +
            "WHERE DATE(h.ngayTao) = :ngay",
            nativeQuery = true)
    BigDecimal getDoanhThuTheoNgay(@Param("ngay") LocalDate ngay);

    // Lấy doanh thu theo tháng và năm
    @Query(value = "SELECT SUM(h.tongTien) " +
            "FROM HoaDon h " +
            "WHERE MONTH(h.ngayTao) = :thang AND YEAR(h.ngayTao) = :nam", nativeQuery = true)
    BigDecimal getDoanhThuTheoThang(@Param("thang") int thang, @Param("nam") int nam);

    // Lấy doanh thu theo năm
    @Query(value = "SELECT SUM(h.tongTien) " +
            "FROM HoaDon h " +
            "WHERE YEAR(h.ngayTao) = :nam", nativeQuery = true)
    BigDecimal getDoanhThuTheoNam(@Param("nam") int nam);

    // Tổng doanh thu toàn bộ
    @Query("SELECT SUM(h.tongTien) FROM HoaDon h")
    BigDecimal getTongDoanhThu();

    @Query(value = "SELECT SUM(h.TongTien) FROM HoaDon h WHERE h.LoaiHoaDon = N'Tại quầy'", nativeQuery = true)
    BigDecimal tongTienTaiQuay();

    @Query(value = "SELECT SUM(h.TongTien) FROM HoaDon h WHERE h.LoaiHoaDon = 'Online'", nativeQuery = true)
    BigDecimal tongTienOnline();
}