package com.example.saferide.controller;


import com.example.saferide.entity.Voucher;
import com.example.saferide.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/voucher/index")
public class VoucherController {
    @Autowired
    VoucherService service;

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("list", service.getList());
        return "voucher/index";
    }
    @GetMapping("/add")
    public String addForm(){
        return "voucher/form";
    }
    @PostMapping("/add")
    public String addVoucher(Voucher voucher){
        service.add(voucher);
        return "redirect:/voucher/index/list";
    }
    @GetMapping("/detail/{id}")
    public String showDetail(@PathVariable("id") Integer id, Model model){
        model.addAttribute("voucher",service.findById(id));
        return "voucher/detail";
    }
    @GetMapping("/update/{id}")
    public String showUpdate(@PathVariable("id") Integer id,Model model){
        model.addAttribute("voucher",service.findById(id));
        return "voucher/update";
    }
    @PostMapping("/update")
    public String updateVoucher(Voucher voucher){
        service.update(voucher);
        return "redirect:/voucher/index/list";
    }
    @GetMapping("/delete")
    public String deleteVoucher(@RequestParam("id") Integer id){
        service.delete(id);
        return "redirect:/voucher/index/list";
    }
}