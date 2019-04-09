package com.bootdo.mia.controller;

import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.mia.domain.CommentDO;
import com.bootdo.mia.domain.ExpertDO;
import com.bootdo.mia.service.CommentService;
import com.bootdo.system.shiro.LoginUser;
import com.bootdo.common.domain.ArticleDO;
import com.bootdo.common.service.ArticleService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;

/**
 * 1)用于记录评论。
2)通过专家对文章评论产生记录。
3)该实体主要由评论审核、评论推荐等业务使
 * 
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:33
 */
 
@Controller
@RequestMapping("/mia/comment")
public class CommentController {
	@Autowired
	private CommentService commentService;
	@Autowired
	private ArticleService articleService;
	
	@RequestMapping()
	@RequiresPermissions("mia:comment:comment")
	String Comment(){
	    return "mia/comment/comment";
	}
	
	@ResponseBody
	@GetMapping("/list")
	@RequiresPermissions("mia:comment:comment")
	public PageUtils list(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total);
		return pageUtils;
	}
	
	@GetMapping("/add")
	@RequiresPermissions("mia:comment:add")
	String add(){
	    return "mia/comment/add";
	}

	@GetMapping("/edit/{cId}")
	@RequiresPermissions("mia:comment:edit")
	String edit(@PathVariable("cId") Integer cId,Model model){
		CommentDO comment = commentService.get(cId);
		model.addAttribute("comment", comment);
	    return "mia/comment/edit";
	}
	
	/**
	 * 保存
	 */
	@ResponseBody
	@PostMapping("/save")
	@RequiresPermissions("mia:comment:add")
	public R save( CommentDO comment){
		if(commentService.save(comment)>0){
			return R.ok();
		}
		return R.error();
	}
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/update")
	@RequiresPermissions("mia:comment:edit")
	public R update( CommentDO comment){
		commentService.update(comment);
		return R.ok();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/remove")
	@ResponseBody
	@RequiresPermissions("mia:comment:remove")
	public R remove( Integer cId){
		if(commentService.remove(cId)>0){
		return R.ok();
		}
		return R.error();
	}
	
	/**
	 * 删除
	 */
	@PostMapping( "/batchRemove")
	@ResponseBody
	@RequiresPermissions("mia:comment:batchRemove")
	public R remove(@RequestParam("ids[]") Integer[] cIds){
		commentService.batchRemove(cIds);
		return R.ok();
	}
	/**
	 * 导出
	 */
	@RequestMapping("/exportExcel")
	@RequiresPermissions("mia:comment:add")
	public void exportExcel(@RequestParam Map<String, Object> params,HttpServletRequest request, HttpServletResponse response,  ServletOutputStream out){
	
		//查询列表数据
		commentService.exportExcel(request, response, params, out);
		
		return ;
	}
	@GetMapping("/importView")
	@RequiresPermissions("mia:comment:add")
	String importView(){
	    return "mia/comment/importExcel";
	}
	/**
	 * 导入
	 */
	@ResponseBody
	@PostMapping("/importExcel")
	@RequiresPermissions("mia:comment:add")
	public R importExcel(@RequestParam("file") MultipartFile file) {
	        String fileName = file.getOriginalFilename();
	        try {
	           commentService.importExcel(fileName, file);
	        } catch (Exception e) {
	        	 return R.error();
	        }
	        return R.ok();
	}
	
	/***************************************************评论审核******************************************************/
	@RequestMapping("/commentAudit")
	@RequiresPermissions("mia:commentAudit:commentAudit")
	String commentAudit(){
	    return "mia/comment/commentAudit";
	}
	
	@ResponseBody
	@GetMapping("/commentAuditList")
	@RequiresPermissions("mia:commentAudit:commentAudit")
	public PageUtils commentAuditList(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total);
		return pageUtils;
	}
	
	@GetMapping("/commentAuditEdit/{cId}")
	@RequiresPermissions("mia:commentAudit:edit")
	String commentAuditEdit(@PathVariable("cId") Integer cId,Model model){
		CommentDO comment = commentService.get(cId);
		model.addAttribute("comment", comment);
	    return "mia/comment/commentAuditEdit";
	}
	
	/**
	 * 修改
	 */
	@ResponseBody
	@RequestMapping("/commentAuditUpdate")
	@RequiresPermissions("mia:commentAudit:edit")
	public R commentAuditUpdate( CommentDO comment){
		LoginUser loginUser = ShiroUtils.getUser();
		comment.setAuditBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		comment.setAuditDate(new Date());
		commentService.update(comment);
		return R.ok();
	}
	
	/**
	 * 
	 * @Description:  审核页面初始化
	 * @param ids 多个字符串
	 * @return  
	 * @author: 窦恩虎
	 * @date 2018年7月29日 上午9:00:07
	 */
	@GetMapping("/commentAuditCheckInit")
	@RequiresPermissions("common:articleReview:edit")
	String commentAuditCheckInit(@RequestParam("ids") String ids,Model model){
		model.addAttribute("ids", ids);
	    return "mia/comment/commentAuditCheckInfo";
	}
	
	/**
	 * 
	 * @Description:  审核功能
	 * @param employer
	 * @return  
	 * @author: 窦恩虎
	 * @date 2018年7月29日 上午9:00:07
	 */
	@ResponseBody
	@RequestMapping("/commentAuditUpdateCheck")
	@RequiresPermissions("common:articleReview:edit")
	public R commentAuditUpdateCheck( CommentDO comment){
		if(commentService.commentAuditUpdateCheck(comment)>0){
			return R.ok();
		}
		return R.error();
	}
	
	/***************************************************评论推荐******************************************************/
	@RequestMapping("/commentRecommend")
	@RequiresPermissions("mia:commentRecommend:commentRecommend")
	String commentRecommend(){
	    return "mia/comment/commentRecommend";
	}
	
	@ResponseBody
	@GetMapping("/commentRecommendList")
	@RequiresPermissions("mia:commentRecommend:commentRecommend")
	public PageUtils commentRecommendList(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<CommentDO> commentList = commentService.list(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total);
		return pageUtils;
	}
	
	/**
	 * 查看文章
	 * @param id
	 * @param model
	 * @return
	 */
	@GetMapping("/selArticle/{id}")
	@RequiresPermissions("mia:commentRecommend:commentRecommend")
	String selArticle(@PathVariable("id") Integer id,Model model){
		ArticleDO article = articleService.get(id);
		model.addAttribute("article", article);
	    return "mia/comment/articleInfo";
	}
	
	/**
	 * 推荐操作
	 * @param id
	 * @param val
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/recommendOperation")
	@RequiresPermissions("mia:commentRecommend:edit")
	public R recommendOperation( String id, String val){
		CommentDO comment = commentService.get(Integer.parseInt(id));
		comment.setIsRecommend(val);
		commentService.update(comment);
		return R.ok();
	}
	/********************************************接口*******************************************************/
	//查询精彩评论列表
	@ResponseBody
	@RequestMapping("/front/selectcomment")
	public PageUtils employmentList(@RequestParam Map<String, Object> params) {
		Query query = new Query(params);
		List<CommentDO> commentList = commentService.recommendlist(query);
		int total = commentService.count(query);
		PageUtils pageUtils = new PageUtils(commentList, total);
		return pageUtils;
	}
	//专家详情
		@RequestMapping("/front/comment")
		public String frontcomment(String cId, Model model){
		    return "front//commentDetails";
		}
}
