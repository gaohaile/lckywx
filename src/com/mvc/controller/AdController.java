package com.mvc.controller;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.mvc.entiy.Ad;
import com.mvc.service.AdService;
import com.utils.SessionUtil;
import com.utils.StringUtil;
import net.sf.json.JSONObject;

/**
 * 
 * 广告
 * 
 * @author ghl
 * @date   2017年8月15日
 */
@Controller
@RequestMapping("/ad")
public class AdController {
	@Autowired
	AdService  adService;
	/**
	 * 添加，修改广告
	 * 
	 * @param request
	 * @param session
	 * @return  ad对象
	 * @throws ParseException
	 */
	@RequestMapping("/addAd.do")
	public @ResponseBody String addAd(HttpServletRequest request, HttpSession session) {
		JSONObject jsonObject= JSONObject.fromObject(request.getParameter("ad"));
		Ad ad= new  Ad();
		if (jsonObject.containsKey("ad_type")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_type"))){
			ad.setAd_type(Integer.parseInt(jsonObject.getString("ad_type")));
			}
		}
		if (jsonObject.containsKey("ad_name")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_name"))){
				ad.setAd_name(jsonObject.getString("ad_name"));
			}
		}
		if (jsonObject.containsKey("ad_tel")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_tel"))){
				ad.setAd_tel(jsonObject.getString("ad_tel"));
			}	
		}
		if (jsonObject.containsKey("ad_title")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_title"))){
				ad.setAd_title(jsonObject.getString("ad_title"));
			}
		}
	/*	if (jsonObject.containsKey("ad_pic_path")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_pic_path"))){
				ad.setAd_pic_path(jsonObject.getString("ad_pic_path"));
			}
		}*/
		if (jsonObject.containsKey("ad_remark")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_remark"))){
				ad.setAd_remark(jsonObject.getString("ad_remark"));
			}else{
				ad.setAd_remark("");
			}
		}
		if (jsonObject.containsKey("ad_content")){
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_content"))){
				ad.setAd_content(jsonObject.getString("ad_content"));
			}
		}
		Date getDate = new Date();
		ad.setAd_stime(getDate);
		ad.setIs_delete(true);
		ad.setAd_state(0);
		String openid = SessionUtil.getOpenid(request);
		ad.setOpen_id(openid);
		Ad result = null;
		if (jsonObject.containsKey("ad_id")) {
			if (StringUtil.strIsNotEmpty(jsonObject.getString("ad_id"))){
				ad.setAd_id(Integer.valueOf(jsonObject.getString("ad_id")));
				result = adService.saveAd(ad);// 修改广告	
			}
		} else {
			result = adService.saveAd(ad);// 添加广告
		}
		JSONObject limit = new JSONObject();
		limit.put("result", result);
		return limit.toString(); 
	}

	/**
	 * 广告查询
	 * 根据类型查询 若为空则返回全部类型广告，否则返回相应类型广告
	 * @param request
	 * @param session
	 * @return  list
	 */
	@RequestMapping("/selectAdver.do")
	public @ResponseBody String selectAdver(HttpServletRequest request, HttpSession session){
		String adType;
		List<Ad> list ;
		if(request.getParameter("adType") != null){
		adType= JSONObject.fromObject(request.getParameter("adType")).getString("ad_type");
		list = adService.finAdByType(Integer.parseInt(adType));
		}else{
			list = adService.finAdAlls();
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}

	/**
	 * 广告删除根据广告id
	 * @param request
	 * @return true false
	 * 
	 */
	@RequestMapping("/deleteAd.do")
	public @ResponseBody String deleteAd (HttpServletRequest request){
		Integer ad_id = Integer.parseInt(request.getParameter("adId"));
		Boolean flag = adService.deleteAd(ad_id);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("flag", flag);
		return jsonObject.toString();
	}
	
	/**
	 * 根据id查找广告list
	 * @param request
	 * @return list
	 * 
	 * */
	@RequestMapping("/selectAdverInfo.do")
	public @ResponseBody String selectAdverInfo (HttpServletRequest request){
		Ad list;
		String adId = request.getParameter("ad_id");
		list = adService.selectAdverInfo(adId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
		
	}
	/**
	 * 根据openId查找广告
	 * @param request
	 * @return list
	 */
	@RequestMapping("/myPlace.do")
	public @ResponseBody String myPlace (HttpServletRequest request){
		String openId = SessionUtil.getOpenid(request);
		List<Ad> list = adService.findMyPlaceAd(openId);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("list", list);
		return jsonObject.toString();
	}
	
}
