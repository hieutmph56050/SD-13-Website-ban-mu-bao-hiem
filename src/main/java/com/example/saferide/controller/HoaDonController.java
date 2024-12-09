package com.example.saferide.controller;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.repository.HoaDonChiTietRepository;
import com.example.saferide.repository.HoaDonRepository;
import com.example.saferide.response.HoaDonResponse;
import com.example.saferide.response.InvoiceResponse;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Comparator;

@RestController
@RequestMapping("/api/admin/hoa-don")
public class HoaDonController {
    @Autowired
    HoaDonRepository hdRepository;
    @Autowired
    HoaDonChiTietRepository HDCTRepo;
    @Autowired
    HoaDonService hoaDonService;


    @GetMapping("/danh-sach")
    public ResponseEntity<?> getList() {
        InvoiceResponse<HoaDon> listHoaDon = new InvoiceResponse<>();
        listHoaDon.data = hdRepository.findAll().stream().sorted(Comparator.comparing(HoaDon::getMa).reversed()).toList();
        return ResponseEntity.ok(listHoaDon);
    }

    @GetMapping("/danh-sach/{billType}")
    public ResponseEntity<?> getOnlineBill(@PathVariable Integer billType) {
        ProductResponse<HoaDon> listHoaDonChiTiet = new ProductResponse<>();
        listHoaDonChiTiet.data = hoaDonService.getListByBillType(billType);
        return ResponseEntity.ok(listHoaDonChiTiet);
    }

    @GetMapping("/chi-tiet/{maHoaDon}")
    public ResponseEntity<?> getHoaDonById(@PathVariable String maHoaDon) {
        HoaDon hoaDon = hdRepository.findByMaHoaDon(maHoaDon).orElseThrow(() -> new RuntimeException("No Invoice"));
        HoaDonResponse.DataHoaDonRes dataRes = new HoaDonResponse.DataHoaDonRes();
        dataRes.setHoaDon(hoaDon);
        dataRes.setChiTietList(HDCTRepo.findByHoaDonId(hoaDon.getId()));

        HoaDonResponse responseHD = new HoaDonResponse();
        responseHD.setData(Collections.singletonList(dataRes));
        return ResponseEntity.ok(responseHD);
    }

//@GetMapping("/tim-kiem")
//public ResponseEntity<?> search(
//        @RequestParam(required = false) String ma,
//        @RequestParam(required = false) String ghiChu,
//        @RequestParam(required = false) String diaChi,
//        @RequestParam(required = false) String nguoiTao,
//        Pageable pageable
//) {
//    return ResponseEntity.ok(hoaDonService.searchWithPaging(ma, ghiChu, diaChi, nguoiTao, pageable));
//}
}