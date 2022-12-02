package com.example.logistics.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @user 公杰
 * @方法描述 。。。。
 * @Date 2022/11/30/下午 02:58
 */
public class F2_Bean implements Serializable {

    @SerializedName("site")
    private String site;
    @SerializedName("orderNumber")
    private int orderNumber;
    @SerializedName("deleted")
    private int deleted;
    @SerializedName("phone")
    private String phone;
    @SerializedName("createTime")
    private Date createTime;
    @SerializedName("sex")
    private int sex;
    @SerializedName("company")
    private String company;
    @SerializedName("stepList")
    private List<StepListDTO> stepList;
    @SerializedName("custumerId")
    private int custumerId;
    @SerializedName("username")
    private String username;
    @SerializedName("eMail")
    private String eMail;

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<StepListDTO> getStepList() {
        return stepList;
    }

    public void setStepList(List<StepListDTO> stepList) {
        this.stepList = stepList;
    }

    public int getCustumerId() {
        return custumerId;
    }

    public void setCustumerId(int custumerId) {
        this.custumerId = custumerId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getEMail() {
        return eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public static class StepListDTO {
        @SerializedName("id")
        private int id;
        @SerializedName("orderNumber")
        private int orderNumber;
        @SerializedName("stateName")
        private String stateName;
        @SerializedName("createTime")
        private Date createTime;
        @SerializedName("addressAt")
        private String addressAt;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(int orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public Date getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Date createTime) {
            this.createTime = createTime;
        }

        public String getAddressAt() {
            return addressAt;
        }

        public void setAddressAt(String addressAt) {
            this.addressAt = addressAt;
        }
    }
}
