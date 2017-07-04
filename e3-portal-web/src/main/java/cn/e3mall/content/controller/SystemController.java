package cn.e3mall.content.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.common.po.AdResult;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.manager.po.TbContent;

/**
 * 门户表现层
 * 
 */
@Controller
public class SystemController {
	
	@Autowired
	private ContentService service;
	
	@Value("${AD1_CATEGORY_ID}")
	private Long AD1_CATEGORY_ID;
	
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	
	@Value("${AD1_HEIGHTB}")
	private Integer AD1_HEIGHTB;
	
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	
	@Value("${AD1_WIDTHB}")
	private Integer AD1_WIDTHB;
	
	@RequestMapping("/")
	public String showIndexPage(ModelMap map) {
		List<TbContent> contentList = service.getContentList(AD1_CATEGORY_ID);
		List<AdResult> results = new ArrayList<>();
		AdResult result;
		for (TbContent tbContent : contentList) {
			result = new AdResult();
			result.setAlt(tbContent.getTitle());
			result.setHeight(AD1_HEIGHT);
			result.setHeightB(AD1_HEIGHTB);
			result.setWidth(AD1_WIDTH);
			result.setWidthB(AD1_WIDTHB);
			result.setSrc(tbContent.getPic());
			result.setSrcB(tbContent.getPic2());
			result.setHref(tbContent.getUrl());
			results.add(result);
		}
		map.addAttribute("ad1", JsonUtils.objectToJson(results));
		return "index";
	}
	
}
