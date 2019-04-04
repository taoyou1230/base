package cn.ubiot.common.user.entity;

public class User {

    /**用户id*/
    private Integer userId;
    /**公司*/
    private String companyName;

    private Long iat;

    private Long exp;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getIat() {
        return iat;
    }

    public void setIat(Long iat) {
        this.iat = iat;
    }

    public Long getExp() {
        return exp;
    }

    public void setExp(Long exp) {
        this.exp = exp;
    }


    private int ints;

    private double doubles1;
    private Double doubles2;
    public int getInts() {
        return ints;
    }

    public void setInts(int ints) {
        this.ints = ints;
    }

    public double getDoubles1() {
        return doubles1;
    }

    public void setDoubles1(double doubles1) {
        this.doubles1 = doubles1;
    }

    public Double getDoubles2() {
        return doubles2;
    }

    public void setDoubles2(Double doubles2) {
        this.doubles2 = doubles2;
    }
}
