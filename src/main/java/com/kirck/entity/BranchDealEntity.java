package com.kirck.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 分店团购关系表
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_branch_deal")
@ApiModel(value = "分店团购关系表", description = "分店团购关系表")
public class BranchDealEntity extends Model<BranchDealEntity> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "", name = "branchId", required = false)
	@TableField("branch_id")
	private String branchId;
	@ApiModelProperty(value = "", name = "dealId", required = false)
	@TableField("deal_id")
	private String dealId;
	@ApiModelProperty(value = "", name = "status", required = false)
	@TableField("status")
	private Integer status;

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getDealId() {
		return dealId;
	}

	public void setDealId(String dealId) {
		this.dealId = dealId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "BranchDealEntity{" + "branchId=" + branchId + ", dealId=" + dealId + ", status=" + status + "}";
	}
}
