package cn.e3mall.manager.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.common.utils.FastDFSClient;

/**
 * 图片上传表现层
 * 
 */
@RequestMapping("/pic")
@Controller
public class PicController {

	@Value("${baseImageUrl}")
	private String baseImageUrl;
		
	@RequestMapping("/upload")
	@ResponseBody
	public String upload(MultipartFile uploadFile) {
		Map<String, Object> map = new HashMap<>();

		// 文件上传校验
		if (uploadFile == null) {
			map.put("error", 1);
			map.put("message", "上传文件不能为空");

			return JsonUtils.objectToJson(map);
		}

		// 获取上传文件的原始名称
		String originalFilename = uploadFile.getOriginalFilename();
		// 校验上传文件名称
		if (StringUtils.isBlank(originalFilename)) {
			map.put("error", 1);
			map.put("message", "上传文件名称不能为空");

			return JsonUtils.objectToJson(map);
		}

		// 获取上传文件扩展名(不带点)
		String extName = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

		// 校验上传文件格式
		if (!extName.toUpperCase().equals("JPG")) {
			map.put("error", 1);
			map.put("message", "上传文件格式必须为JPG");

			return JsonUtils.objectToJson(map);
		}

		// 通过FastDFSClient工具类实现图片上传
		try {
			// 获取上传图片的相对路径
			String imgUrl = FastDFSClient.uploadFile(uploadFile.getBytes(), extName);

			// 封装完整图片URL
			String url = baseImageUrl + imgUrl;

			map.put("error", 0);
			map.put("url", url);

			return JsonUtils.objectToJson(map);

		} catch (Exception e) {
			e.printStackTrace();
			map.put("error", 1);
			map.put("message", "上传失败");

			return JsonUtils.objectToJson(map);
		}

	}
}
