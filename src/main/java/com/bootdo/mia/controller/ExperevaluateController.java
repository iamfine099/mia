package com.bootdo.mia.controller;

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

import com.bootdo.mia.domain.ExperevaluateDO;
import com.bootdo.mia.service.ExperevaluateService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;

/**
 * 1)用于记录专家对文章的推荐和评分。
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:35
 */
 
@Controller
@RequestMapping("/mia/experevaluate")
public class ExperevaluateController {
	@Autowired
	private ExperevaluateService experevaluateService;
	
	@RequestMapping()
	@RequiresPermissions("mia:experevaluate:experevaluate")
	String Experevaluate(){
	    return "mia/experevaluate/experevaluate";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("mia:experevaluate:experevaluate")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ExperevaluateDO> experevaluateList = experevaluateService.list(query);
		int total = experevaluateService.count(query);
		PageUtils pageUtils = new PageUtils(experevaluateList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("mia:experevaluate:add")
	String add(){
	    return "mia/experevaluate/add";
	}

	@GetMapping("/edit/{eetId}")
	@RequiresPermissions("mia:experevaluate:edit")
	String edit(@PathVariable("eetId") Integer eetId,Model model){
		ExperevaluateDO experevaluate = experevaluateService.get(eetId);
		model.addAttribute("experevaluate", experevaluate);
	    return "mia/experevaluate/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("mia:experevaluate:add")
	public R save( ExperevaluateDO experevaluate){
		if(experevaluateService.save(experevaluate)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mia:experevaluate:edit")
	public R update( ExperevaluateDO experevaluate){
		experevaluateService.update(experevaluate);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("mia:experevaluate:remove")
	public R remove( Integer eetId){
		if(experevaluateService.remove(eetId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("mia:experevaluate:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] eetIds){
		experevaluateService.batchRemove(eetIds);
		return R.ok();
	}
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	@RequiresPermissions("mia:experevaluate:add")
	public void exportExcel(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response,  ServletOutputStream out){
	
		//查询列表数据
		experevaluateService.exportExcel(request, response, params, out);
		
		return ;
	}
	@GetMapping("/importView")
	@RequiresPermissions("mia:experevaluate:add")
	String importView(){
	    return "mia/experevaluate/importExcel";
	}
	/**
	 * 导入
	 */
	@ResponseBody
	@PostMapping("/importExcel")
	@RequiresPermissions("mia:experevaluate:add")
	public R importExcel(@RequestParam("file") MultipartFile file) {
	        String fileName = file.getOriginalFilename();
	        try {
	           experevaluateService.importExcel(fileName, file);
	        } catch (Exception e) {
	        	 return R.error();
	        }
	        return R.ok();
	}
}
