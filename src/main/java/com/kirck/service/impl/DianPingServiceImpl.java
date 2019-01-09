package com.kirck.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kirck.entity.MerchantDealEntity;
import com.kirck.mapper.MerchantDealMapper;
import com.kirck.service.AbstractService;
import com.kirck.service.IDianPingService;

@Service("dianPingService")
public class DianPingServiceImpl extends AbstractService implements IDianPingService {

	@Autowired
	private MerchantDealMapper merchantDealMapper;
	@Override
	public MerchantDealEntity findDealInfo(String dealId) {
		return merchantDealMapper.selectOne(new QueryWrapper<MerchantDealEntity>().eq("dianping_url_id", dealId));
	}

}
