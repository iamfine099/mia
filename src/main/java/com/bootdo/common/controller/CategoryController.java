package com.bootdo.common.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.common.domain.CategoryDO;
import com.bootdo.common.domain.Tree;
import com.bootdo.common.service.CategoryService;
import com.bootdo.common.utils.R;

/**
 * 栏目表
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-07-03 13:58:50
 */
 
@Controller
@RequestMapping("/common/category")
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping()
	@RequiresPermissions("common:category:category")
	String Category(){
	    return "common/category/category";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("common:category:category")
	public List<CategoryDO> list(@RequestParam Map<String, Object> params){
		//查询列表数据
		List<CategoryDO> categoryList = categoryService.list(params);
		return categoryList;
	}
	
	@GetMapping("/add/{pId}")
	@RequiresPermissions("common:category:add")
	String add(@PathVariable("pId") int pId, Model model,CategoryDO category){
		model.addAttribute("pId", pId);
		if (pId == 0) {
			model.addAttribute("pName", "总栏目");
		} else {
			model.addAttribute("pName", categoryService.get(pId).getName());
		}
		/**
		 * 默认值
		 */
		category.setInMenu("1");
		category.setDelFlag("1");
		model.addAttribute("category", category);
	    return "common/category/add";
	}

	@GetMapping("/edit/{id}")
	@RequiresPermissions("common:category:edit")
	String edit(@PathVariable("id") Integer id,Model model){
		CategoryDO category = categoryService.get(id);
		model.addAttribute("category", category);
		
		if(category.getParentId() == 0) {
			model.addAttribute("parentName", "无");
		}else {
			CategoryDO parCategory = categoryService.get(category.getParentId());
			model.addAttribute("parentName", parCategory.getName());
		}
	    return "common/category/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("common:category:add")
	public R save( CategoryDO category){
		if(categoryService.save(category)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("common:category:edit")
	public R update( CategoryDO category){
		categoryService.update(category);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("common:category:remove")
	public R remove( Integer id){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", id);
		if(categoryService.count(map)>0) {
			return R.error(1, "包含下级栏目,不允许修改");
		}
		
		if(categoryService.checkCategoryHasArticle(id)) {
			if (categoryService.remove(id) > 0) {
				return R.ok();
			}
		}else {
			return R.error(1, "栏目包含文章,不允许修改");
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("common:category:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] ids){
		categoryService.batchRemove(ids);
		return R.ok();
	}
	
	@GetMapping("/tree")
	@ResponseBody
	public Tree<CategoryDO> tree() {
		Tree<CategoryDO> tree = new Tree<CategoryDO>();
		tree = categoryService.getTree();
		return tree;
	}
	
	@GetMapping("/treeView")
	String treeView() {
		return "common/category/categoryTree";
	}
}
