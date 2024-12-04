package com.example.saferide.request;

public class PhiVanChuyenRequest {
    private Integer fromDistrictId;
    private Integer toDistrictId;
    private Integer weight; // Đơn vị: gram
    private Integer insuranceValue; // Giá trị khai bảo hàng hóa
    private String coupon; // Mã khuyến mãi (nếu có)

    // Getters và Setters
    public Integer getFromDistrictId() { return fromDistrictId; }
    public void setFromDistrictId(Integer fromDistrictId) { this.fromDistrictId = fromDistrictId; }

    public Integer getToDistrictId() { return toDistrictId; }
    public void setToDistrictId(Integer toDistrictId) { this.toDistrictId = toDistrictId; }

    public Integer getWeight() { return weight; }
    public void setWeight(Integer weight) { this.weight = weight; }

    public Integer getInsuranceValue() { return insuranceValue; }
    public void setInsuranceValue(Integer insuranceValue) { this.insuranceValue = insuranceValue; }

    public String getCoupon() { return coupon; }
    public void setCoupon(String coupon) { this.coupon = coupon; }
}
