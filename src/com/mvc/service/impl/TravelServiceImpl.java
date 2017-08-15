/**
 * @Title: TravelServiceImpl.java
 * @Package com.mvc.service.impl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 */
package com.mvc.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mvc.entiy.Travel;
import com.mvc.entiy.TravelTrade;
import com.mvc.repository.TravelRepository;
import com.mvc.repository.TravelTradeRepository;
import com.mvc.service.TravelService;

/**
 * @ClassName: TravelServiceImpl
 * @Description: TODO
 * @author ycj
 * @date 2017年8月14日 上午11:52:49 
 * 
 *
 */
@Service("travelServiceImpl")
public class TravelServiceImpl implements TravelService{
	@Autowired
	TravelRepository travelRepository;
	@Autowired
	TravelTradeRepository travelTradeRepository;
	//按出发日期查询旅游信息
	@Override
	public List<Travel> findTravelAlls(String useDate) {
		return travelRepository.findByUsertime(useDate);
	}
	//按成人票价查询旅游信息
	@Override
	public List<Travel> findTravelAlls1(String usePrice) {
		return travelRepository.findByUserprice(usePrice);
	}
	//旅游交易
	@Override
	public boolean saveTravelTrade(TravelTrade travelTrade) {
		TravelTrade result =travelTradeRepository.saveAndFlush(travelTrade);
		if (result.getTrtr_id() != null)
			return true;
		else
			return false;
	}

}