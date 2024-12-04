package com.example.saferide.controller;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.repository.HoaDonRepository;
import com.example.saferide.response.InvoiceResponse;
import com.example.saferide.service.HoaDonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Comparator;

@RestController
@RequestMapping("/hoa-don")
public class HoaDonController {
    @Autowired
    HoaDonRepository hdRepository;
    @Autowired
    HoaDonService hoaDonService;


    @GetMapping("/danh-sach")
    public ResponseEntity<?> getList() {
        InvoiceResponse<HoaDon> listHoaDon = new InvoiceResponse<>();
        listHoaDon.data = hdRepository.findAll().stream().sorted(Comparator.comparing(HoaDon::getMa).reversed()).toList();
        return ResponseEntity.ok(listHoaDon);
    }

//
//    @PostMapping("/add")
//    public ResponseEntity<?> add(@RequestBody HoaDon hoaDon){
//        return ResponseEntity.ok(hdRepository.add(hoaDon));
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody HoaDon hoaDon){
//        return ResponseEntity.ok(hdRepository.update(hoaDon,id));
//    }
//
//    @GetMapping("{id}")
//    public ResponseEntity<?> findById(@PathVariable Integer id){
//        return ResponseEntity.ok(service.findById(id));
//    }
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