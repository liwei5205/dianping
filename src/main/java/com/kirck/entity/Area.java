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
        @TableId(value = "id", type = IdType.UUID)
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


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
