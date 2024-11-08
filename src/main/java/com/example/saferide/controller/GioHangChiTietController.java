//package com.example.saferide.controller;
//
//import com.example.saferide.entity.GioHangChiTiet;
//import com.example.saferide.service.GioHangChiTietService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/api/giohangchitiet")
//public class GioHangChiTietController {
//    @Autowired
//    GioHangChiTietService service;
//
//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        return ResponseEntity.ok(service.getList());
//    }
//
//    @PostMapping("/add")
//    public ResponseEntity<?> add(@RequestBody GioHangChiTiet giohangChiTiet){
//        return ResponseEntity.ok(service.add(giohangChiTiet));
//    }
//
//    @PutMapping("/update/{id}")
//    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody GioHangChiTiet giohangChiTiet){
//        return ResponseEntity.ok(service.update(giohangChiTiet,id));
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> delete(@PathVariable Integer id){
//        return ResponseEntity.ok(service.delete(id));
//    }
//    @GetMapping("{id}")
//    public ResponseEntity<?> findById(@PathVariable Integer id){
//        return ResponseEntity.ok(service.findById(id));
//    }
//}