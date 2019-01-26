package com.kirck.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kirck.commen.TypeConstants;
import com.kirck.entity.Area;
import com.kirck.entity.BranchDealEntity;
import com.kirck.entity.MerchantBranchEntity;
import com.kirck.entity.MerchantDealEntity;
import com.kirck.mapper.AreaMapper;
import com.kirck.mapper.BranchDealMapper;
import com.kirck.mapper.MerchantBranchMapper;
import com.kirck.mapper.MerchantDealMapper;
import com.kirck.service.AbstractService;
import com.kirck.service.IDianPingService;
import com.kirck.utils.LocalDateUtils;

@Service("dianPingService")
public class DianPingServiceImpl extends AbstractService implements IDianPingService {

	@Autowired
	private MerchantDealMapper merchantDealMapper;

	@Autowired
	private MerchantBranchMapper merchantBranchMapper;

	@Autowired
	private BranchDealMapper branchDealMapper;

	@Autowired
	private AreaMapper areaMapper;

	@Override
	public MerchantDealEntity findDealInfo(String dealId) {
		return merchantDealMapper.selectOne(new QueryWrapper<MerchantDealEntity>().eq("dianping_url_id", dealId));
	}

	@Override
	@Transactional
	public void updateMerchantDeal(MerchantDealEntity merchantDeal) {
		merchantDealMapper.updateById(merchantDeal);
	}

	@Override
	@Transactional
	public void saveMerchantBranch(List<MerchantBranchEntity> mbs) {
		for (MerchantBranchEntity temp : mbs) {
			temp.setCreateDate(LocalDateTime.now());
			temp.setStatus(TypeConstants.Status.NORMAL);
			merchantBranchMapper.insert(temp);
		}
	}

	@Override
	public void saveBranchDeal(String dealId, List<String> mbIds) {
		// 需要重复校验
		for (String mbId : mbIds) {
			BranchDealEntity branchDeal = new BranchDealEntity();
			branchDeal.setDealId(dealId);
			branchDeal.setBranchId(mbId);
			branchDeal.setStatus(TypeConstants.Status.NORMAL);
			branchDealMapper.insert(branchDeal);
		}
	}

	@Override
	public MerchantBranchEntity findMerchantBranch(String shopId) {
		return merchantBranchMapper.selectOne(new QueryWrapper<MerchantBranchEntity>().eq("shop_id", shopId));
	}

	@Override
	public void saveOrUpdate(List<MerchantDealEntity> merchantDeals) {
		merchantDealMapper.batchInsert(merchantDeals);
	}

	@Override
	public Set<String> getLastDealIds() { // 获取最后一条的时间
		return merchantDealMapper.getLastDealIds();
	}

	@Override
	public void insertArea(List<Area> list) {
		for (Area area : list) {
			areaMapper.insert(area);
		}
	}

}
