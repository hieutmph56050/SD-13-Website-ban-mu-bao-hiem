package com.example.saferide.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "HoanTra")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoanTra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ID_HoaDon")
    private HoaDon idHoaDon;

    @Column(name = "NgayHoanTra")
    private LocalDateTime ngayHoanTra;

    @Column(name = "LyDo", length = 200)
    private String lyDo;

    @Column(name = "TongTienHoanTra", precision = 18, scale = 2)
    private BigDecimal tongTienHoanTra;

    private String trangThai;

}
