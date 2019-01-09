package com.kirck.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kirck.entity.MerchantBranchEntity;

/**
 * <p>
 * 分店表 Mapper 接口
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */
@Mapper
public interface MerchantBranchMapper extends BaseMapper<MerchantBranchEntity> {

}
