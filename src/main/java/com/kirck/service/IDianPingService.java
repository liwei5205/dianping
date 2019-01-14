package com.kirck.service;

import java.util.List;

import com.kirck.entity.MerchantBranchEntity;
import com.kirck.entity.MerchantDealEntity;

public interface IDianPingService {

	/**
	 * 根据团购网站查询团购信息
	 * 
	 * @param dealId
	 * @return
	 */
	MerchantDealEntity findDealInfo(String dealId);

	/**
	 * 更新团购基本信息
	 * 
	 * @param merchantDeal
	 */
	void updateMerchantDeal(MerchantDealEntity merchantDeal);

	/**
	 * 批量存储分店信息
	 * 
	 * @param mbs
	 */
	void saveMerchantBranch(List<MerchantBranchEntity> mbs);

	/**
	 * 存储团购分店信息
	 * 
	 * @param id
	 * @param mbIds
	 */
	void saveBranchDeal(String id, List<String> mbIds);

	MerchantBranchEntity findMerchantBranch(String shopId);

	/**
	 * 批量保存
	 * 
	 * @param merchantDeals
	 */
	void saveOrUpdate(List<MerchantDealEntity> merchantDeals);

	/**
	 * 获取上次插入的ids
	 * @return
	 */
	List<String> getLastDealIds();

}
