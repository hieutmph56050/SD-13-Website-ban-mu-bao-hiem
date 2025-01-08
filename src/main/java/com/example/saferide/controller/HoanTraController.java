package com.example.saferide.controller;

import com.example.saferide.entity.HoanTra;
import com.example.saferide.request.HoanTraConfirmationRequest;
import com.example.saferide.request.HoanTraRequest;
import com.example.saferide.service.HoanTraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController

public class HoanTraController {

    @Autowired
    private HoanTraService hoanTraService;

    @PostMapping("/hoan-tra")
    public ResponseEntity<HoanTra> createHoanTra(@RequestBody HoanTraRequest request) {
        HoanTra hoanTra = hoanTraService.createHoanTra(
                request.getHoaDonId(),
                request.getSanPhamHoanTra(),
                request.getLyDo()
        );
        return ResponseEntity.ok(hoanTra);
    }

    @PutMapping("xac-nhan-hoan-tra")
    public ResponseEntity<HoanTra> confirmOrRejectHoanTra(@RequestBody HoanTraConfirmationRequest hoanTraConfirmationRequest) {
        HoanTra updatedHoanTra = hoanTraService.confirmHoanTra(hoanTraConfirmationRequest.getHoanTraId(),
                hoanTraConfirmationRequest.getTrangThai());
        return ResponseEntity.ok(updatedHoanTra);
    }

}
