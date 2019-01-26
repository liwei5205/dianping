package com.kirck.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kirck.entity.MerchantDealEntity;

/**
 * <p>
 * 商户团购表 Mapper 接口
 * </p>
 *
 * @author kirck007
 * @since 2019-01-09
 */
@Mapper
public interface MerchantDealMapper extends BaseMapper<MerchantDealEntity> {

	void batchInsert(@Param("merchantDeals")List<MerchantDealEntity> merchantDeals);

	Set<String> getLastDealIds();

}
