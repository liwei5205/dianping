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
 * 
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */
 

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_deal_info")
@ApiModel(value = "", description = "")
public class DealInfoEntity extends Model<DealInfoEntity> {

    private static final long serialVersionUID = 1L;

   @ApiModelProperty(value = "", name = "id", required = false)
      @TableId(value = "id", type = IdType.AUTO)
   private String id;
   @ApiModelProperty(value = "", name = "dealId", required = false)
      @TableField("deal_id")
   private String dealId;
   @ApiModelProperty(value = "", name = "dealInfo", required = false)
      @TableField("deal_info")
   private String dealInfo;
   @ApiModelProperty(value = "", name = "userNotes", required = false)
      @TableField("user_notes")
   private String userNotes;


   public String getId() {
      return id;
   }

   public void setId(String id) {
         this.id = id;
   }
   public String getDealId() {
      return dealId;
   }

   public void setDealId(String dealId) {
         this.dealId = dealId;
   }
   public String getDealInfo() {
      return dealInfo;
   }

   public void setDealInfo(String dealInfo) {
         this.dealInfo = dealInfo;
   }
   public String getUserNotes() {
      return userNotes;
   }

   public void setUserNotes(String userNotes) {
         this.userNotes = userNotes;
   }

   @Override
   public String toString() {
      return "DealInfoEntity{" +
         "id=" + id +
         ", dealId=" + dealId +
         ", dealInfo=" + dealInfo +
         ", userNotes=" + userNotes +
         "}";
   }
}
