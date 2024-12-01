package com.example.saferide.controller;

import com.example.saferide.entity.HoaDon;
import com.example.saferide.repository.HoaDonRepository;
import com.example.saferide.response.InvoiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;

@RestController
@RequestMapping("/api/admin/hoa-don")
public class HoaDonController {
    @Autowired
    HoaDonRepository hdRepository;


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
}