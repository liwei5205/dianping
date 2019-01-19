package com.kirck.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 国家省市区表
 * </p>
 *
 * @author kirck007
 * @since 2019-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_sys_area")
public class Area extends Model<Area> {

    private static final long serialVersionUID = 1L;

        /**
     * 主键Id
     */
        @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

        /**
     * 类型 0：国家  , 1：省 , 2：市 , 3: 区
     */
        @TableField("type")
        private Integer type;

        /**
     * 标签
     */
        @TableField("label")
        private String label;

        /**
     * 描述
     */
        @TableField("description")
         private String description;

        /**
     * 排序begin 0
     */
        @TableField("sort")
         private Integer sort;

        /**
     * 行政区域Id
     */
         @TableField("area_iD")
    private Integer areaID;

        /**
     * 上级Id
     */
        @TableField("pid")
        private Integer pid;

        /**
     * 邮编
     */
        @TableField("zip")
        private String zip;

    @TableField("abbreviation")
    private String abbreviation;
    
    @TableField("phone_code")
    private String phoneCode;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public String getLabel() {
		return label;
	}


	public void setLabel(String label) {
		this.label = label;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getSort() {
		return sort;
	}


	public void setSort(Integer sort) {
		this.sort = sort;
	}


	public Integer getAreaID() {
		return areaID;
	}


	public void setAreaID(Integer areaID) {
		this.areaID = areaID;
	}


	public Integer getPid() {
		return pid;
	}


	public void setPid(Integer pid) {
		this.pid = pid;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String getAbbreviation() {
		return abbreviation;
	}


	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}


	public String getPhoneCode() {
		return phoneCode;
	}


	public void setPhoneCode(String phoneCode) {
		this.phoneCode = phoneCode;
	}
    
    
}
