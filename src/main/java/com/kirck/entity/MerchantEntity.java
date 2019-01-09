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
 * 商户表
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */
 

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_merchant")
@ApiModel(value = "商户表", description = "商户表")
public class MerchantEntity extends Model<MerchantEntity> {

    private static final long serialVersionUID = 1L;

   @ApiModelProperty(value = "", name = "id", required = false)
      @TableId(value = "id", type = IdType.AUTO)
   private String id;
    /**
     * 商户名
     */
   @ApiModelProperty(value = "商户名", name = "merchantName", required = false)
      @TableField("merchant_name")
   private String merchantName;
   @ApiModelProperty(value = "", name = "shortName", required = false)
      @TableField("short_name")
   private String shortName;
    /**
     * 商户类型
     */
   @ApiModelProperty(value = "商户类型", name = "type", required = false)
      @TableField("type")
   private Integer type;
    /**
     * 商户风格
     */
   @ApiModelProperty(value = "商户风格", name = "style", required = false)
      @TableField("style")
   private Integer style;
    /**
     * 商户介绍
     */
   @ApiModelProperty(value = "商户介绍", name = "notes", required = false)
      @TableField("notes")
   private String notes;
   @ApiModelProperty(value = "", name = "brandId", required = false)
      @TableField("brand_id")
   private String brandId;
   @ApiModelProperty(value = "", name = "createDate", required = false)
      @TableField("create_date")
   private LocalDateTime createDate;
   @ApiModelProperty(value = "", name = "updateDate", required = false)
      @TableField("update_date")
   private LocalDateTime updateDate;
   @ApiModelProperty(value = "", name = "status", required = false)
      @TableField("status")
   private Integer status;


   public String getId() {
      return id;
   }

   public void setId(String id) {
         this.id = id;
   }
   public String getMerchantName() {
      return merchantName;
   }

   public void setMerchantName(String merchantName) {
         this.merchantName = merchantName;
   }
   public String getShortName() {
      return shortName;
   }

   public void setShortName(String shortName) {
         this.shortName = shortName;
   }
   public Integer getType() {
      return type;
   }

   public void setType(Integer type) {
         this.type = type;
   }
   public Integer getStyle() {
      return style;
   }

   public void setStyle(Integer style) {
         this.style = style;
   }
   public String getNotes() {
      return notes;
   }

   public void setNotes(String notes) {
         this.notes = notes;
   }
   public String getBrandId() {
      return brandId;
   }

   public void setBrandId(String brandId) {
         this.brandId = brandId;
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
   public Integer getStatus() {
      return status;
   }

   public void setStatus(Integer status) {
         this.status = status;
   }

   @Override
   public String toString() {
      return "MerchantEntity{" +
         "id=" + id +
         ", merchantName=" + merchantName +
         ", shortName=" + shortName +
         ", type=" + type +
         ", style=" + style +
         ", notes=" + notes +
         ", brandId=" + brandId +
         ", createDate=" + createDate +
         ", updateDate=" + updateDate +
         ", status=" + status +
         "}";
   }
}
