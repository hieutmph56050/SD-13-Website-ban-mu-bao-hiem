package com.example.saferide.request;

import com.example.saferide.entity.SPChiTiet;
import com.example.saferide.response.ProductOrderDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DatHangRequest {
    private Integer idTaiKhoan;
    private String diachi;
    private List<SPChiTiet> sanPhamList;
}