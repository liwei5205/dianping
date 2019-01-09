package com.kirck.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分店表
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */
 

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_merchant_branch")
@ApiModel(value = "分店表", description = "分店表")
public class MerchantBranchEntity extends Model<MerchantBranchEntity> {

    private static final long serialVersionUID = 1L;

   @ApiModelProperty(value = "", name = "id", required = false)
      @TableId(value = "id", type = IdType.AUTO)
   private String id;
    /**
     * 商户id
     */
   @ApiModelProperty(value = "商户id", name = "merchantId", required = false)
      @TableField("merchant_id")
   private String merchantId;
   @ApiModelProperty(value = "", name = "address", required = false)
      @TableField("address")
   private String address;
   @ApiModelProperty(value = "", name = "telephone", required = false)
      @TableField("telephone")
   private String telephone;
   @ApiModelProperty(value = "", name = "merchantName", required = false)
      @TableField("branch_name")
   private String branchName;
   @ApiModelProperty(value = "", name = "businessHours", required = false)
      @TableField("business_hours")
   private String businessHours;
   @ApiModelProperty(value = "", name = "shopId", required = false)
      @TableField("shop_id")
   private String shopId;
   @ApiModelProperty(value = "", name = "createDate", required = false)
      @TableField("create_date")
   private LocalDateTime createDate;
   @ApiModelProperty(value = "", name = "updateDate", required = false)
      @TableField("update_date")
   private LocalDateTime updateDate;
    /**
     * 省
     */
   @ApiModelProperty(value = "省", name = "provinceId", required = false)
      @TableField("province_id")
   private Integer provinceId;
   @ApiModelProperty(value = "", name = "cityId", required = false)
      @TableField("city_id")
   private Integer cityId;
    /**
     * 区
     */
   @ApiModelProperty(value = "区", name = "districtId", required = false)
      @TableField("district_id")
   private Integer districtId;
   @ApiModelProperty(value = "", name = "status", required = false)
      @TableField("status")
   private Integer status;


   public String getId() {
      return id;
   }

   public void setId(String id) {
         this.id = id;
   }
   public String getMerchantId() {
      return merchantId;
   }

   public void setMerchantId(String merchantId) {
         this.merchantId = merchantId;
   }
   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
         this.address = address;
   }
   public String getTelephone() {
      return telephone;
   }

   public void setTelephone(String telephone) {
         this.telephone = telephone;
   }
   public String getBranchName() {
      return branchName;
   }

   public void setBranchName(String branchName) {
         this.branchName = branchName;
   }
   public String getBusinessHours() {
      return businessHours;
   }

   public void setBusinessHours(String businessHours) {
         this.businessHours = businessHours;
   }
   public String getShopId() {
      return shopId;
   }

   public void setShopId(String shopId) {
         this.shopId = shopId;
   }
   public LocalDateTime getCreateDate() {
      return createDate;
   }

   public void setCreateDate(LocalDateTime createDate) {
         this.createDate = createDate;
   }
   public LocalDateTime getUpdateDate() {
      return updateDate;
   }

   public void setUpdateDate(LocalDateTime updateDate) {
         this.updateDate = updateDate;
   }
   public Integer getProvinceId() {
      return provinceId;
   }

   public void setProvinceId(Integer provinceId) {
         this.provinceId = provinceId;
   }
   public Integer getCityId() {
      return cityId;
   }

   public void setCityId(Integer cityId) {
         this.cityId = cityId;
   }
   public Integer getDistrictId() {
      return districtId;
   }

   public void setDistrictId(Integer districtId) {
         this.districtId = districtId;
   }
   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
         this.status = status;
   }

   @Override
   public String toString() {
      return "MerchantBranchEntity{" +
         "id=" + id +
         ", merchantId=" + merchantId +
         ", address=" + address +
         ", telephone=" + telephone +
         ", branchName=" + branchName +
         ", businessHours=" + businessHours +
         ", shopId=" + shopId +
         ", createDate=" + createDate +
         ", updateDate=" + updateDate +
         ", provinceId=" + provinceId +
         ", cityId=" + cityId +
         ", districtId=" + districtId +
         ", status=" + status +
         "}";
   }
}
