package com.example.logistics.admin.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/12/02/下午 02:50
 */
public class DD_bean implements Serializable {
    @SerializedName("getPhone")
    private String getPhone;
    @SerializedName("orderNumber")
    private int orderNumber;
    @SerializedName("getName")
    private String getName;
    @SerializedName("sex")
    private int sex;

    @SerializedName("custumerId")
    private int custumerId;
    @SerializedName("eMail")
    private String eMail;
    @SerializedName("site")
    private String site;
    @SerializedName("deleted")
    private int deleted;
    @SerializedName("phone")
    private String phone;
    @SerializedName("createTime")
    private Date createTime;
    @SerializedName("company")
    private String company;
    @SerializedName("username")
    private String username;

    public String getGetPhone() {
        return getPhone;
    }

    public void setGetPhone(String getPhone) {
        this.getPhone = getPhone;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getGetName() {
        return getName;
    }

    public void setGetName(String getName) {
        this.getName = getName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }


    public int getCustumerId() {
        return custumerId;
    }

    public void setCustumerId(int custumerId) {
        this.custumerId = custumerId;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
