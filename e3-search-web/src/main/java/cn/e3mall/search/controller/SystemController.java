package cn.e3mall.search.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.common.po.SearchResult;
import cn.e3mall.search.service.SearchService;

/**
 * 搜索表现层
 */
@Controller
public class SystemController {
	
	@Autowired
	private SearchService service;
	
	@Value("${PAGE_ROWS}")
	private Integer PAGE_ROWS;
	
	@RequestMapping("/search")
	public String showSearchPage(Model model, @RequestParam(value="q")String keyword, @RequestParam(defaultValue="1")Integer page) throws Exception {
		if(StringUtils.isNoneBlank(keyword)) {
			keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		}
		SearchResult searchResults = service.getSearchResults(page, PAGE_ROWS, keyword);
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", searchResults.getTotalPages());
		model.addAttribute("page", page);
		model.addAttribute("recourdCount", searchResults.getRecourdCount());
		model.addAttribute("itemList", searchResults.getItemList());
		return "search";
	}
}
