package cn.e3mall.manager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.e3mall.manager.mapper.TbItemMapper;
import cn.e3mall.manager.po.TbItem;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper tbItemMapper;
	
	@Override
	public TbItem queryItemById(Long id) {
		return tbItemMapper.selectByPrimaryKey(id);
	}

}
