package com.example.saferide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "GioHang")
public class GioHang {
    @Id
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_taikhoan", referencedColumnName = "id")
    private TaiKhoan idTaiKhoan;

    @Column(name = "trangthai", columnDefinition = "BIT")
    private boolean tt;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;

    @OneToMany(mappedBy = "idGioHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GioHangChiTiet> gioHangChiTietList;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "ngaycapnhat")
    private LocalDateTime ngayCapNhat;

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat = LocalDateTime.now();
    }
}
