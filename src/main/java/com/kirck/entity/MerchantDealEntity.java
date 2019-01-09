package com.kirck.entity;

import java.math.BigDecimal;
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
 * 商户团购表
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */
 

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_merchant_deal")
@ApiModel(value = "商户团购表", description = "商户团购表")
public class MerchantDealEntity extends Model<MerchantDealEntity> {

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
    /**
     * 标题
     */
   @ApiModelProperty(value = "标题", name = "dealTitle", required = false)
      @TableField("deal_title")
   private String dealTitle;
    /**
     * 折扣价
     */
   @ApiModelProperty(value = "折扣价", name = "price", required = false)
      @TableField("price")
   private BigDecimal price;
    /**
     * 原价
     */
   @ApiModelProperty(value = "原价", name = "storePrice", required = false)
      @TableField("store_price")
   private BigDecimal storePrice;
    /**
     * 网址id
     */
   @ApiModelProperty(value = "网址id", name = "dianpingUrlId", required = false)
	@TableField("dianping_url_id")
   private String dianpingUrlId;
    /**
     * 创建时间
     */
   @ApiModelProperty(value = "创建时间", name = "createDate", required = false)
      @TableField("create_date")
   private LocalDateTime createDate;


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
   public String getDealTitle() {
      return dealTitle;
   }

   public void setDealTitle(String dealTitle) {
         this.dealTitle = dealTitle;
   }
   public BigDecimal getPrice() {
      return price;
   }

   public void setPrice(BigDecimal price) {
         this.price = price;
   }
   public BigDecimal getStorePrice() {
      return storePrice;
   }

   public void setStorePrice(BigDecimal storePrice) {
         this.storePrice = storePrice;
   }
   public String getDianpingUrlId() {
      return dianpingUrlId;
   }

   public void setDianpingUrlId(String dianpingUrlId) {
         this.dianpingUrlId = dianpingUrlId;
   }
   public LocalDateTime getCreateDate() {
      return createDate;
   }

   public void setCreateDate(LocalDateTime createDate) {
         this.createDate = createDate;
   }

   @Override
   public String toString() {
      return "MerchantDealEntity{" +
         "id=" + id +
         ", merchantId=" + merchantId +
         ", dealTitle=" + dealTitle +
         ", price=" + price +
         ", storePrice=" + storePrice +
         ", dianpingUrlId=" + dianpingUrlId +
         ", createDate=" + createDate +
         "}";
   }
}
