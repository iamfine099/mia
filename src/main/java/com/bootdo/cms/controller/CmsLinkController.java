package com.bootdo.cms.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bootdo.cms.domain.CmsLinkDO;
import com.bootdo.cms.service.CmsLinkService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 友情链接
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-25 09:53:54
 */
 
@Controller
@RequestMapping("/cms/cmsLink")
public class CmsLinkController {
	@Autowired
	private CmsLinkService cmsLinkService;
	
	@RequestMapping()
	@RequiresPermissions("cms:cmsLink:cmsLink")
	String CmsLink(){
	    return "cms/cmsLink/cmsLink";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("cms:cmsLink:cmsLink")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CmsLinkDO> cmsLinkList = cmsLinkService.list(query);
		int total = cmsLinkService.count(query);
		PageUtils pageUtils = new PageUtils(cmsLinkList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("cms:cmsLink:add")
	String add(){
	    return "cms/cmsLink/add";
	}

	@GetMapping("/edit/{fId}")
	@RequiresPermissions("cms:cmsLink:edit")
	String edit(@PathVariable("fId") Integer fId,Model model){
		CmsLinkDO cmsLink = cmsLinkService.get(fId);
		model.addAttribute("cmsLink", cmsLink);
	    return "cms/cmsLink/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("cms:cmsLink:add")
	public R save(CmsLinkDO cmsLink){
		if(cmsLinkService.save(cmsLink)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("cms:cmsLink:edit")
	public R update(CmsLinkDO cmsLink){
		cmsLinkService.update(cmsLink);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("cms:cmsLink:remove")
	public R remove( Integer fId){
		if(cmsLinkService.remove(fId)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("cms:cmsLink:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] fIds){
		cmsLinkService.batchRemove(fIds);
		return R.ok();
	}
}
