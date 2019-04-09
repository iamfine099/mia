package com.bootdo.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.common.domain.ArticleDataDO;
import com.bootdo.common.service.ArticleDataService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 文章数据表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */
 
@Controller
@RequestMapping("/common/articleData")
public class ArticleDataController {
	@Autowired
	private ArticleDataService articleDataService;
	
	@RequestMapping()
	@RequiresPermissions("common:articleData:articleData")
	String ArticleData(){
	    return "common/articleData/articleData";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:articleData:articleData")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ArticleDataDO> articleDataList = articleDataService.list(query);
		int total = articleDataService.count(query);
		PageUtils pageUtils = new PageUtils(articleDataList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("common:articleData:add")
	String add(){
	    return "common/articleData/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("common:articleData:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		ArticleDataDO articleData = articleDataService.get(id);
		model.addAttribute("articleData", articleData);
	    return "common/articleData/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:articleData:add")
	public R save( ArticleDataDO articleData){
		if(articleDataService.save(articleData)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:articleData:edit")
	public R update( ArticleDataDO articleData){
		articleDataService.update(articleData);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:articleData:remove")
	public R remove( Integer id){
		if(articleDataService.remove(id)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:articleData:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		articleDataService.batchRemove(ids);
		return R.ok();
	}
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	@RequiresPermissions("common:articleData:add")
	public void exportExcel(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response,  ServletOutputStream out){
	
		//查询列表数据
		articleDataService.exportExcel(request, response, params, out);
		
		return ;
	}
	@GetMapping("/importView")
	@RequiresPermissions("common:articleData:add")
	String importView(){
	    return "common/articleData/importExcel";
	}
	/**
	 * 导入
	 */
	@ResponseBody
	@PostMapping("/importExcel")
	@RequiresPermissions("common:articleData:add")
	public R importExcel(@RequestParam("file") MultipartFile file) {
	        String fileName = file.getOriginalFilename();
	        try {
	           articleDataService.importExcel(fileName, file);
	        } catch (Exception e) {
	        	 return R.error();
	        }
	        return R.ok();
	}
}
