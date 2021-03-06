package com.kirck.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kirck.entity.BranchDealEntity;

/**
 * <p>
 * 分店团购关系表 Mapper 接口
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */
@Mapper
public interface BranchDealMapper extends BaseMapper<BranchDealEntity> {

}
