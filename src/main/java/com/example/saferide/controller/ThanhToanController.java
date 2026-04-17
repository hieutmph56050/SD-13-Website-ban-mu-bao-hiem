package com.example.saferide.controller;

import com.example.saferide.config.VNPayConfig;
import com.example.saferide.entity.HoaDon;
import com.example.saferide.entity.ThanhToan;
import com.example.saferide.repository.HoaDonRepository;
import com.example.saferide.request.ThanhToanRequest;
import com.example.saferide.response.ProductResponse;
import com.example.saferide.service.ThanhToanService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/thanhtoan")
public class ThanhToanController {
    @Autowired
    ThanhToanService service;

    @Autowired
    private HoaDonRepository hoaDonRepository;

    @GetMapping("/danhsach")
    public ResponseEntity<?> getAll() {
        ProductResponse<ThanhToan> listThanhToan = new ProductResponse<>();
        listThanhToan.data = service.getList();
        return ResponseEntity.ok(listThanhToan);
    }

    @RequestMapping("/index")
    public String showThanhToanList(Model model) {
        List<ThanhToan> listThanhToan = service.getList();
        model.addAttribute("list", listThanhToan);
        return "thanhtoan/index";
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ThanhToan thanhToan) {
        return ResponseEntity.ok(service.add(thanhToan));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @RequestBody ThanhToan thanhToan) {
        return ResponseEntity.ok(service.update(thanhToan, id));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(service.delete(id));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam String keyword, Pageable pageable) {
        Page<ThanhToan> result = service.search(keyword, pageable);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/vnpay")
    public ResponseEntity<?> thanhToanVNPay(@RequestBody ThanhToanRequest request) throws UnsupportedEncodingException {
        String maHD = request.getMaHoaDon();
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(maHD).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        BigDecimal tongTien = hoaDon.getTongTien();
        if (tongTien.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("Tổng tiền không hợp lệ để thanh toán.");
        }

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        String vnp_Amount = tongTien.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.DOWN).toString();
        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(vnp_Amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        String vnp_ReturnUrl = "http://localhost:3000/sucess";
        vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;
        return ResponseEntity.ok(Collections.singletonMap("data", paymentUrl));
    }


    @PostMapping("/return")
    public void vnpayReturn(HttpServletResponse response, @RequestParam Map<String, String> params) throws IOException {
        String vnp_TxnRef = params.get("vnp_TxnRef");
        String vnp_ResponseCode = params.get("vnp_ResponseCode");
        String vnp_SecureHash = params.get("vnp_SecureHash");
        System.out.println("vnp_SecureHash" + vnp_SecureHash);
        HoaDon hoaDon = hoaDonRepository.findByMaHoaDon(vnp_TxnRef).orElseThrow(() -> new RuntimeException("Hóa đơn không tồn tại"));
        params.remove("vnp_SecureHash");
        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String value = params.get(fieldName);
            if (value != null && !value.isEmpty()) {
                hashData.append(fieldName).append('=').append(value).append('&');
            }
        }
        hashData.deleteCharAt(hashData.length() - 1);

        String vnp_HashSecret = "222";
        String secureHash = hmacSHA512(vnp_HashSecret, hashData.toString());
        System.out.println("secureHash: " + secureHash);

        if (!secureHash.equals(vnp_SecureHash)) {
            response.sendRedirect("http://localhost:3000/error?message=invalid_signature");
            return;
        }

        if ("00".equals(vnp_ResponseCode)) {
            hoaDon.setTt("Đã thanh toán");
            hoaDonRepository.save(hoaDon);
            response.sendRedirect("http://localhost:3000/success");
        } else {
            hoaDon.setTt("Thanh toán thất bại");
            hoaDonRepository.save(hoaDon);
            response.sendRedirect("http://localhost:3000/");
        }


    }

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
}