package com.bootdo.mia.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
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
import com.bootdo.mia.domain.MemberDO;
import com.bootdo.mia.service.MemberService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.service.UserService;

/**
 * 1) 用于记录会员信息。
 * 2) 通过前端用户注册产生记录。
 * 3) 该实体主要由会员登录、注册、发表文章
 *
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:36
 */

@Controller
@RequestMapping("/mia/member")
public class MemberController extends BaseController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;

    @RequestMapping()
    @RequiresPermissions("mia:member:member")
    String Member() {
        return "mia/member/member";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("mia:member:member")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<MemberDO> memberList = memberService.list(query);
        int total = memberService.count(query);
        PageUtils pageUtils = new PageUtils(memberList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("mia:member:add")
    String add() {
        return "mia/member/add";
    }

    @GetMapping("/edit/{memId}")
    @RequiresPermissions("mia:member:edit")
    String edit(@PathVariable("memId") Integer memId, Model model) {

        MemberDO member = memberService.get(memId);
        model.addAttribute("member", member);
        //获取附件信息
        FileDO fileObj = new FileDO();
        if (StringUtils.isNotEmpty(member.getHeadImg())) {
            fileObj = sysFileService.get(Long.parseLong(member.getHeadImg()));
        }
        model.addAttribute("fileObj", fileObj);
        return "mia/member/edit";
    }

    /***
     * 重置密码
     * @param user
     * @return
     */
    @GetMapping("/resetpwd/{memId}")
    @RequiresPermissions("mia:member:edit")
    public String resetpwd(@PathVariable("memId") Integer memId, Model model) {
        MemberDO member = memberService.get(memId);
        model.addAttribute("member", member);
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

        return "mia/member/resetpwd";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("mia:member:add")
    public R save(MultipartFile file, HttpServletRequest request, MemberDO member) {
        member.setCreateDate(new Date());
        member.setLastTime(new Date());
        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (null != fileDo) {
                member.setHeadImg(fileDo.getId().toString());
            }
        }
        if (memberService.save(member) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("mia:member:edit")
    public R update(MultipartFile file, HttpServletRequest request, MemberDO member) {
        MemberDO oldMember = memberService.get(member.getMemId());
        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (fileDo != null) {
                member.setHeadImg(fileDo.getId().toString());
                //删除原来上传的照片
                if (StringUtils.isNotEmpty(oldMember.getHeadImg())) {
                    this.deleteFile(Long.parseLong(oldMember.getHeadImg()));
                }
            } else {
                member.setHeadImg(oldMember.getHeadImg());
            }
        }
        if (memberService.update(member) > 0) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("busId", member.getMemId());
            map.put("fType", new String[]{"M"});
            List<UserDO> userlist = userService.list(map);
            for (UserDO userDO : userlist) {
                if ("0".equals(member.getStatus())) {
                    userDO.setStatus(1);
                } else {
                    userDO.setStatus(0);
                }
                /*userDO.setUsername(member.getPhone());*/
                userService.update(userDO);
            }
        }
        return R.ok();
    }

    /***
     * 修改密码
     * @param user
     * @return
     */
    @ResponseBody
    @RequestMapping("/resetpwdupdate")
    @RequiresPermissions("mia:member:edit")
    public R resetpwdUpdate(MemberDO member) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("busId", member.getMemId());
        map.put("fType", new String[]{"M"});
        List<UserDO> userlist = userService.list(map);
        if (userlist != null && userlist.size() > 0) {
            UserDO userDO = userlist.get(0);
            userDO.setPassword(MD5Utils.encrypt(userDO.getUsername(), member.getPassword()));
            userService.update(userDO);

            if (userService.update(userDO) > 0) {
                return R.ok();
            }
        }

        return R.error();
    }

    /**
     * @param ids 多个字符串
     * @return
     * @Description: 审核页面初始化
     * @author: 窦恩虎
     * @date 2018年7月29日 上午9:00:07
     */
    @GetMapping("/memberCheckInit")
    @RequiresPermissions("mia:member:edit")
    String articleReviewCheckInit(@RequestParam("ids") String ids, Model model) {
        model.addAttribute("ids", ids);
        return "mia/member/memberCheckInfo";
    }

    /**
     * 审核
     */
    @ResponseBody
    @RequestMapping("/memberUpdateCheck")
    @RequiresPermissions("mia:member:edit")
    public R memberUpdateCheck(MemberDO member) {
        if (memberService.memberUpdateCheck(member) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("mia:member:remove")
    public R remove(Integer memId) {
        //判断是否可以删除
        if (memberService.checkRemove(memId)) {
            if (memberService.remove(memId) > 0) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("busId", memId);
                map.put("fType", "M");
                userService.removeByParam(map);
                return R.ok();
            }
            return R.error();
        } else {
            return R.error("会员发表的文章已经审核通过，不允许删除！");
        }

    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("mia:member:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] memIds) {
        memberService.batchRemove(memIds);
        return R.ok();
    }

    /**
     * 导出
     */
    @RequestMapping("/exportExcel")
    @RequiresPermissions("mia:member:add")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response, ServletOutputStream out) {

        //查询列表数据
        memberService.exportExcel(request, response, params, out);

        return;
    }

    @GetMapping("/importView")
    @RequiresPermissions("mia:member:add")
    String importView() {
        return "mia/member/importExcel";
    }

    /**
     * 导入
     */
    @ResponseBody
    @PostMapping("/importExcel")
    @RequiresPermissions("mia:member:add")
    public R importExcel(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            memberService.importExcel(fileName, file);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }
}
