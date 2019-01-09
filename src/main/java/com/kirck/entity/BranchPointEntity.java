package com.kirck.entity;

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
 * 分店得分
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */
 

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_branch_point")
@ApiModel(value = "分店得分", description = "分店得分")
public class BranchPointEntity extends Model<BranchPointEntity> {

    private static final long serialVersionUID = 1L;

   @ApiModelProperty(value = "", name = "id", required = false)
      @TableId(value = "id", type = IdType.AUTO)
   private String id;
    /**
     * 分店id
     */
   @ApiModelProperty(value = "分店id", name = "branchId", required = false)
      @TableField("branch_id")
   private String branchId;
    /**
     * 人均
     */
   @ApiModelProperty(value = "人均", name = "avgPrice", required = false)
      @TableField("avg_price")
   private Integer avgPrice;
    /**
     * 口味
     */
   @ApiModelProperty(value = "口味", name = "taste", required = false)
      @TableField("taste")
   private Double taste;
    /**
     * 环境
     */
   @ApiModelProperty(value = "环境", name = "environment", required = false)
      @TableField("environment")
   private Double environment;
    /**
     * 服务
     */
   @ApiModelProperty(value = "服务", name = "service", required = false)
      @TableField("service")
   private Double service;


   public String getId() {
      return id;
   }

   public void setId(String id) {
         this.id = id;
   }
   public String getBranchId() {
      return branchId;
   }

   public void setBranchId(String branchId) {
         this.branchId = branchId;
   }
   public Integer getAvgPrice() {
      return avgPrice;
   }

   public void setAvgPrice(Integer avgPrice) {
         this.avgPrice = avgPrice;
   }
   public Double getTaste() {
      return taste;
   }

   public void setTaste(Double taste) {
         this.taste = taste;
   }
   public Double getEnvironment() {
      return environment;
   }

   public void setEnvironment(Double environment) {
         this.environment = environment;
   }
   public Double getService() {
      return service;
   }

   public void setService(Double service) {
         this.service = service;
   }

   @Override
   public String toString() {
      return "BranchPointEntity{" +
         "id=" + id +
         ", branchId=" + branchId +
         ", avgPrice=" + avgPrice +
         ", taste=" + taste +
         ", environment=" + environment +
         ", service=" + service +
         "}";
   }
}
