package com.bootdo.mia.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bootdo.common.domain.DictDO;
import com.bootdo.common.utils.DictUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
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
import org.springframework.web.multipart.MultipartFile;

import com.bootdo.common.controller.BaseController;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.utils.MD5Utils;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.mia.domain.ExpertDO;
import com.bootdo.mia.service.ExpertService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;

/**
 * 1)用于记录专家信息。
 * 2)通过后台添加产生记录。
 * 3)该实体主要由专家登录、专家管理等业务使用
 *
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:34
 */

@Controller
@RequestMapping("/mia/expert")
public class ExpertController extends BaseController {
    @Autowired
    private ExpertService expertService;
    @Autowired
    private UserService userService;


    @RequestMapping()
    @RequiresPermissions(value = {"mia:expert:expert", "common:articleReview:edit"}, logical = Logical.OR)
    String Expert() {
        return "mia/expert/expert";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("mia:expert:expert")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ExpertDO> expertList = expertService.list(query);
        int total = expertService.count(query);
        PageUtils pageUtils = new PageUtils(expertList, total);
        return pageUtils;
    }
	/*@ResponseBody
	@GetMapping("/statusList")
	@RequiresPermissions("common:status:status")
	public PageUtils newsList(@RequestParam Map<String, Object> params){
		//查询列表数据
        Query query = new Query(params);
		List<ExpertDO> articleList = expertService.list(query);
		int total = expertService.count(query);
		PageUtils pageUtils = new PageUtils(articleList, total);
		return pageUtils;
	}*/

    @GetMapping("/add")
    @RequiresPermissions("mia:expert:add")
    String add() {
        return "mia/expert/add";
    }

    @GetMapping("/edit/{expertId}")
    @RequiresPermissions("mia:expert:edit")
    String edit(@PathVariable("expertId") Integer expertId, Model model) {

        ExpertDO expert = expertService.get(expertId);


        model.addAttribute("expert", expert);
        //获取附件信息
        FileDO fileObj = new FileDO();
        if (StringUtils.isNotEmpty(expert.getHeadImg())) {
            fileObj = sysFileService.get(Long.parseLong(expert.getHeadImg()));
        }
        model.addAttribute("fileObj", fileObj);
        return "mia/expert/edit";
    }

    /***
     * 重置密码
     * @return
     */
    @GetMapping("/resetpwd/{expertId}")
    @RequiresPermissions("mia:expert:edit")
    public String resetpwd(@PathVariable("expertId") Integer expertId, Model model) {
        ExpertDO expert = expertService.get(expertId);
        model.addAttribute("expert", expert);
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("busId", expert.getExpertId());
		map.put("fType", new String[]{"E"});
		List<UserDO> userlist = userService.list(map);
		for (UserDO userDO : userlist) {
			
			userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), "666666"));
			userService.update(userDO);
			
			if(userService.update(userDO)>0){
				return R.ok();
			}
		}*/

        return "mia/expert/resetpwd";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("mia:expert:add")
    public R save(MultipartFile file, HttpServletRequest request, ExpertDO expert) {

        expert.setSpecialty(request.getParameter("sp_id"));

        Map validValues = DictUtils.getDictList("specialty").stream().filter(a -> a.getParentId() != null).
                collect(Collectors.toMap(DictDO::getValue, DictDO::getValue));
        String[] specialtys = expert.getSpecialty().split(",");
        List specialtyList = new ArrayList();
        for (String specialty : specialtys) {
            if (validValues.containsKey(specialty)) {

                specialtyList.add(specialty);
            }
        }
        expert.setSpecialty(StringUtils.join(specialtyList, ","));

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("username", expert.getPhone());
        int total = userService.count(param);
        if (total != 0) {

            return R.error("手机号不能重复！");
        } else {
            expert.setCreateDate(new Date());
            expert.setLastTime(new Date());
            String mess = beanValidatorDO(expert);
            if (mess != null) {
                return R.error(mess);
            }
            if (file != null) {
                FileDO fileDo = this.uploadFile(file, request);
                if (null != fileDo) {
                    expert.setHeadImg(fileDo.getId().toString());
                }
            }
            if (expertService.save(expert) > 0) {
                return R.ok();
            }
            return R.ok();
        }

    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("mia:expert:edit")
    public R update(MultipartFile file, HttpServletRequest request, ExpertDO expert) {

        expert.setSpecialty(request.getParameter("sp_id"));

        ExpertDO oldexpert = expertService.get(expert.getExpertId());
		/*Map<String, Object> param = new HashMap<String, Object>();
		param.put("username", expert.getPhone());
		List<UserDO> userList = userService.list(param);		
		int total = userService.count(param);
			if(userList.get(0).getBusId().equals(expert.getExpertId())&& userList.get(0).getfType()=="E" ){
				
			}
			
			else{
			if(total!=0){
				
				 return R.error("手机号已经存在！");
			}
			else{*/
        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (fileDo != null) {
                expert.setHeadImg(fileDo.getId().toString());
                //删除原来上传的照片
                if (StringUtils.isNotEmpty(oldexpert.getHeadImg())) {
                    this.deleteFile(Long.parseLong(oldexpert.getHeadImg()));
                }
            } else {
                expert.setHeadImg(oldexpert.getHeadImg());
            }

        }
        if (expertService.update(expert) > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("busId", expert.getExpertId());
            map.put("fType", new String[]{"E"});
            List<UserDO> userlist = userService.list(map);
            for (UserDO userDO : userlist) {
                if ("0".equals(expert.getStatus())) {
                    userDO.setStatus(1);
                } else {
                    userDO.setStatus(0);
                }
                /*userDO.setUsername(expert.getPhone());*/
                userService.update(userDO);
            }
        }
		/*	}
		return R.ok();
		}*/
        return R.ok();
    }

    /***
     * 修改密码
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetpwdupdate")
    @RequiresPermissions("mia:expert:edit")
    public R resetpwdUpdate(ExpertDO expert) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("busId", expert.getExpertId());
        map.put("fType", new String[]{"E"});
        List<UserDO> userlist = userService.list(map);
        if (userlist != null && userlist.size() > 0) {
            UserDO userDO = userlist.get(0);
            userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), expert.getPassword()));
            userService.update(userDO);

            if (userService.update(userDO) > 0) {
                return R.ok();
            }
        }

        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("mia:expert:remove")
    public R remove(Integer expertId) {
        if (expertService.remove(expertId) > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("busId", expertId);
            map.put("fType", "E");
            userService.removeByParam(map);
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("mia:expert:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] expertIds) {
        if (expertService.batchRemove(expertIds) > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("busIds", expertIds);
            map.put("fType", "E");
            userService.removeByParam(map);

            return R.ok();
        }
        return R.error();
    }

    /**
     * 导出
     */
    @RequestMapping("/exportExcel")
    @RequiresPermissions("mia:expert:add")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response, ServletOutputStream out) {

        //查询列表数据
        expertService.exportExcel(request, response, params, out);

        return;
    }

    @GetMapping("/importView")
    @RequiresPermissions("mia:expert:add")
    String importView() {
        return "mia/expert/importExcel";
    }

    /**
     * 导入
     */
    @ResponseBody
    @PostMapping("/importExcel")
    @RequiresPermissions("mia:expert:add")
    public R importExcel(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            expertService.importExcel(fileName, file);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    //选择打分导师
    @GetMapping("selectexpert")
    @RequiresPermissions("common:articleReview:edit")
    String selectexpert() {
        return "mia/expert/selectexpert";
    }

    /********************************************接口*******************************************************/
    //查询专家列表
    @ResponseBody
    @RequestMapping("/front/selectexpert")
    public PageUtils employmentList(@RequestParam Map<String, Object> params) {
        Query query = new Query(params);
        List<ExpertDO> expertList = expertService.list(query);
        int total = expertService.count(query);
        PageUtils pageUtils = new PageUtils(expertList, total);
        return pageUtils;
    }
}
