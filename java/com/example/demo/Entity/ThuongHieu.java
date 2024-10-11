package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "thuonghieu")
public class ThuongHieu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "tenthuonghieu")
    private String ten;

    @Column(name = "mota")
    private String moTa;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "ngaycapnhat")
    private LocalDateTime ngayCapNhat;

    @PrePersist
    protected void onCreate() {
        ngayTao = LocalDateTime.now(); // Gán thời gian hiện tại
    }
    @PreUpdate
    protected void onUpdate() {
        ngayCapNhat =LocalDateTime.now(); // Gán thời gian hiện tại khi cập nhật
    }
}
