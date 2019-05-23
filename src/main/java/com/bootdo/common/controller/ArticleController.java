package com.bootdo.common.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bootdo.common.domain.AchievementDO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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

import com.bootdo.common.domain.ArticleDO;
import com.bootdo.common.domain.CategoryDO;
import com.bootdo.common.domain.FileDO;
import com.bootdo.common.service.ArticleDataService;
import com.bootdo.common.service.ArticleService;
import com.bootdo.common.service.CategoryService;
import com.bootdo.common.utils.PageUtils;
import com.bootdo.common.utils.Query;
import com.bootdo.common.utils.R;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.mia.domain.ExperevaluateDO;
import com.bootdo.mia.service.ExperevaluateService;
import com.bootdo.system.domain.UserDO;
import com.bootdo.system.shiro.LoginUser;

/**
 * 文章表
 *
 * @author douenhu
 * @email douenhu.hi@163.com
 * @date 2018-11-01 18:16:31
 */

@Controller
@RequestMapping("/common/article")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleDataService articleDataService;
    @Autowired
    private ExperevaluateService experevaluateService;
    @Autowired
    private CategoryService categoryService;

    @RequestMapping()
    @RequiresPermissions("common:article:article")
    String Article() {
        return "common/article/article";
    }

    @ResponseBody
    @GetMapping("/list")
    @RequiresPermissions("common:article:article")
    public PageUtils list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/add")
    @RequiresPermissions("common:article:add")
    String add(ArticleDO article, Model model) {

        article.setIsTop("0");
        article.setDelFlag("1");
        article.setIsSyncAdvert("0");
        article.setType("1");
        model.addAttribute("article", article);

        //加载文章分类
        if (article.getCategoryId() != null) {

            CategoryDO category = categoryService.get(Integer.valueOf(article.getCategoryId()));
            article.setCategoryName(category.getName());
            if ("videoList".equals(category.getModule())) {

                return "common/article/addVideo";
            }
            if ("achievementList".equals(category.getModule())) {

                return "common/article/addAchievement";
            }
        }
        return "common/article/add";
    }

    @GetMapping("/edit/{id}")
    @RequiresPermissions("common:article:edit")
    String edit(@PathVariable("id") Integer id, Model model) {

        ArticleDO article = articleService.get(id);
        model.addAttribute("article", article);

        //获取附件信息
        FileDO fileObj = new FileDO();
        if (StringUtils.isNotEmpty(article.getImage())) {
            fileObj = sysFileService.get(Long.parseLong(article.getImage()));
        }
        model.addAttribute("fileObj", fileObj);

        // 附件
        List<FileDO> fileList = new ArrayList<FileDO>();
        if (StringUtils.isNotEmpty(article.getAttachment())) {
            String[] imageArray = article.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                FileDO fileObject = sysFileService.get(Long
                        .parseLong(imageArray[i]));
                if (fileObject != null) {
                    fileList.add(fileObject);
                }
            }
        }
        model.addAttribute("fileList", fileList);

        //加载文章分类

        CategoryDO category = categoryService.get(Integer.valueOf(article.getCategoryId()));
		/*if("videoList".equals(category.getModule())) {

			return "common/article/addVideo";
		}*/
        if ("achievementList".equals(category.getModule())) {

            AchievementDO achievementDO = articleService.getAchievement(id);
            BeanUtils.copyProperties(article, achievementDO);
            model.addAttribute("article", achievementDO);
            return "common/article/editAchievement";
        }
        return "common/article/edit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/save")
    @RequiresPermissions("common:article:add")
    public R save(ArticleDO article, MultipartFile file, HttpServletRequest request, MultipartFile[] files) {

        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (null != fileDo) {
                article.setImage(fileDo.getId().toString());
            }
        }
        article.setHits(0);
        article.setCreateBy(ShiroUtils.getUserId().intValue());
        article.setCreateTime(new Date());
        article.setPublishTime(new Date());

        // 保存附件
        String image = "";
        if (file != null && files.length > 0) {
            for (MultipartFile f : files) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }
        ArticleDO advert = new ArticleDO();
        //是否同步到轮播图
        if ("1".equals(article.getIsSyncAdvert())) {
            advert = this.syncAdvert(article, file, files, request);
        }
        article.setType(null);
        if (articleService.save(article) > 0) {
            if ("1".equals(article.getIsSyncAdvert())) {
                articleService.save(advert);
            }
            return R.ok();
        }
        return R.error();
    }

    @ResponseBody
    @PostMapping("/saveAchievement")
    @RequiresPermissions("common:article:add")
    public R saveAchievement(AchievementDO article, MultipartFile file, HttpServletRequest request, MultipartFile[] files) {

        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (null != fileDo) {
                article.setImage(fileDo.getId().toString());
            }
        }
        article.setHits(0);
        article.setCreateBy(ShiroUtils.getUserId().intValue());
        article.setCreateTime(new Date());
        article.setPublishTime(new Date());

        // 保存附件
        String image = "";
        if (file != null && files.length > 0) {
            for (MultipartFile f : files) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }
        ArticleDO advert = new ArticleDO();
        //是否同步到轮播图
        if ("1".equals(article.getIsSyncAdvert())) {
            advert = this.syncAdvert(article, file, files, request);
        }
        article.setType(null);
        if (articleService.saveAchievement(article) > 0) {
            if ("1".equals(article.getIsSyncAdvert())) {
                articleService.save(advert);
            }
            return R.ok();
        }
        return R.error();
    }

    public ArticleDO syncAdvert(ArticleDO article, MultipartFile file, MultipartFile[] files, HttpServletRequest request) {

        ArticleDO advert = new ArticleDO();
        BeanUtils.copyProperties(article, advert);
        advert.setCategoryId(3);
        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (null != fileDo) {
                advert.setImage(fileDo.getId().toString());
            }
        }
        // 保存附件
        String image = "";
        if (file != null && files.length > 0) {
            for (MultipartFile f : files) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            advert.setAttachment(image.substring(0, image.length() - 1));
        }

        return advert;
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/update")
    @RequiresPermissions("common:article:edit")
    public R update(ArticleDO article, MultipartFile file, HttpServletRequest request, String fileId, String isDel, MultipartFile[] files) {

        ArticleDO oldArticle = articleService.get(article.getId());
        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (null != fileDo) {
                article.setImage(fileDo.getId().toString());
                //删除原来上传的附件
                if (StringUtils.isNotEmpty(oldArticle.getImage())) {
                    this.deleteFile(Long.parseLong(oldArticle.getImage()));
                }
            } else {
                article.setImage(oldArticle.getImage());
            }

        }

        // 附件
        String image = "";
        if (StringUtils.isNotEmpty(fileId) && StringUtils.isNotEmpty(isDel)) {
            String[] fileIdArray = fileId.split(",");
            String[] isDelArray = isDel.split(",");
            for (int i = 0; i < isDelArray.length; i++) {
                if ("0".equals(isDelArray[i])) {
                    image += fileIdArray[i] + ",";
                } else {
                    this.deleteFile(Long.parseLong(fileIdArray[i]));
                }
            }
        }
        if (file != null && files.length > 0) {
            for (MultipartFile f : files) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }
        articleService.update(article);
        return R.ok();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/updateAchievement")
    @RequiresPermissions("common:article:edit")
    public R updateAchievement(AchievementDO article, MultipartFile file, HttpServletRequest request,
                               String fileId, String isDel, MultipartFile[] files) {

        ArticleDO oldArticle = articleService.get(article.getId());
        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (null != fileDo) {
                article.setImage(fileDo.getId().toString());
                //删除原来上传的附件
                if (StringUtils.isNotEmpty(oldArticle.getImage())) {
                    this.deleteFile(Long.parseLong(oldArticle.getImage()));
                }
            } else {
                article.setImage(oldArticle.getImage());
            }

        }

        // 附件
        String image = "";
        if (StringUtils.isNotEmpty(fileId) && StringUtils.isNotEmpty(isDel)) {
            String[] fileIdArray = fileId.split(",");
            String[] isDelArray = isDel.split(",");
            for (int i = 0; i < isDelArray.length; i++) {
                if ("0".equals(isDelArray[i])) {
                    image += fileIdArray[i] + ",";
                } else {
                    this.deleteFile(Long.parseLong(fileIdArray[i]));
                }
            }
        }
        if (file != null && files.length > 0) {
            for (MultipartFile f : files) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }
        articleService.updateAchievement(article);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/remove")
    @ResponseBody
    @RequiresPermissions("common:article:remove")
    public R remove(Integer id) {
        ArticleDO article = articleService.get(id);
        if (articleService.remove(id) > 0) {
            //刪除附件
            if (StringUtils.isNotEmpty(article.getImage())) {
                this.deleteFile(Long.parseLong(article.getImage()));
            }
            if (null != article.getAttachment()) {
                String[] imageArray = article.getAttachment().split(",");
                for (int i = 0; i < imageArray.length; i++) {
                    this.deleteFile(Long.parseLong(imageArray[i]));
                }
            }
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/batchRemove")
    @ResponseBody
    @RequiresPermissions("common:article:batchRemove")
    public R remove(@RequestParam("ids[]") Integer[] ids) {
        for (int i = 0; i < ids.length; i++) {
            ArticleDO article = articleService.get(ids[i]);
            //刪除附件
            if (StringUtils.isNotEmpty(article.getImage())) {
                this.deleteFile(Long.parseLong(article.getImage()));
            }
            String[] imageArray = article.getAttachment().split(",");
            for (int j = 0; j < imageArray.length; j++) {
                this.deleteFile(Long.parseLong(imageArray[j]));
            }
        }
        articleService.batchRemove(ids);
        return R.ok();
    }

    /**
     * 导出
     */
    @RequestMapping("/exportExcel")
    @RequiresPermissions("common:article:add")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response, ServletOutputStream out) {

        //查询列表数据
        articleService.exportExcel(request, response, params, out);

        return;
    }

    @GetMapping("/importView")
    @RequiresPermissions("common:article:add")
    String importView() {
        return "common/article/importExcel";
    }

    /**
     * 导入
     */
    @ResponseBody
    @PostMapping("/importExcel")
    @RequiresPermissions("common:article:add")
    public R importExcel(@RequestParam("file") MultipartFile file) {
        String fileName = file.getOriginalFilename();
        try {
            articleService.importExcel(fileName, file);
        } catch (Exception e) {
            return R.error();
        }
        return R.ok();
    }

    /***************************************************新闻动态接口******************************************************/
    @ResponseBody
    @GetMapping("/front/list")
    @RequiresPermissions("common:news:news")
    public PageUtils newslist(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/front/newsEdit/{id}")
    @RequiresPermissions("common:news:edit")
    String news(@PathVariable("id") Integer id, Model model) {
        ArticleDO article = articleService.get(id);
        model.addAttribute("article", article);
        return null;
    }

    /***************************************************通知公告接口******************************************************/
    @ResponseBody
    @GetMapping("/front/notice")
    @RequiresPermissions("common:notice:notice")
    public PageUtils noticelist(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/front/noticeEdit/{id}")
    @RequiresPermissions("common:notice:edit")
    String notice(@PathVariable("id") Integer id, Model model) {
        ArticleDO article = articleService.get(id);
        model.addAttribute("article", article);
        return null;
    }

    /***************************************************推荐文章接口******************************************************/
    @ResponseBody
    @GetMapping("/front/articleRecommendList")
    @RequiresPermissions("common:articleRecommend:articleRecommend")
    public PageUtils articleRecommendlist(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/front/articleRecommendEdit/{id}")
    @RequiresPermissions("common:articleRecommend:edit")
    String articleRecommendedit(@PathVariable("id") Integer id, Model model) {
        ArticleDO article = articleService.get(id);
        model.addAttribute("article", article);



        return null;
    }


    /***************************************************新闻资讯******************************************************/
    @GetMapping("/news")
    @RequiresPermissions("common:news:news")
    String News() {
        return "common/article/news";
    }

    @ResponseBody
    @GetMapping("/newsList")
    @RequiresPermissions("common:news:news")
    public PageUtils newsList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/newsAdd")
    @RequiresPermissions("common:news:add")
    String newsAdd(ArticleDO article, Model model) {
        article.setIsTop("0");
        article.setDelFlag("1");
        model.addAttribute("article", article);
        return "common/article/newsAdd";
    }

    @GetMapping("/newsEdit/{id}")
    @RequiresPermissions("common:news:edit")
    String newsEdit(@PathVariable("id") Integer id, Model model) {
        ArticleDO article = articleService.get(id);
        model.addAttribute("article", article);

        // 附件
        List<FileDO> fileList = new ArrayList<FileDO>();
        if (StringUtils.isNotEmpty(article.getAttachment())) {
            String[] imageArray = article.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
                if (fileObj != null) {
                    fileList.add(fileObj);
                }
            }
        }
        model.addAttribute("fileList", fileList);

        return "common/article/newsEdit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/newsSave")
    @RequiresPermissions("common:news:add")
    public R newsSave(ArticleDO article, MultipartFile[] file, HttpServletRequest request) {
        UserDO user = ShiroUtils.getUser().getUser();
        article.setHits(0);
        article.setCreateBy(Integer.parseInt(user.getUserId().toString()));
        article.setCreateTime(new Date());
        article.setPublishTime(new Date());
        //保存附件
        String image = "";
        if (file != null && file.length > 0) {
            for (MultipartFile f : file) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }

        if (articleService.save(article) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/newsUpdate")
    @RequiresPermissions("common:news:edit")
    public R newsUpdate(ArticleDO article, String fileId, String isDel, MultipartFile[] file, HttpServletRequest request) {
        // 附件
        String image = "";
        if (StringUtils.isNotEmpty(fileId) && StringUtils.isNotEmpty(isDel)) {
            String[] fileIdArray = fileId.split(",");
            String[] isDelArray = isDel.split(",");
            for (int i = 0; i < isDelArray.length; i++) {
                if ("0".equals(isDelArray[i])) {
                    image += fileIdArray[i] + ",";
                } else {
                    this.deleteFile(Long.parseLong(fileIdArray[i]));
                }
            }
        }
        if (file != null && file.length > 0) {
            for (MultipartFile f : file) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }

        articleService.update(article);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/newsRemove")
    @ResponseBody
    @RequiresPermissions("common:news:remove")
    public R newsRemove(Integer id) {
        ArticleDO article = articleService.get(id);
        if (articleService.remove(id) > 0) {
            //刪除附件
            if (StringUtils.isNotEmpty(article.getImage())) {
                this.deleteFile(Long.parseLong(article.getImage()));
            }
            String[] imageArray = article.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                this.deleteFile(Long.parseLong(imageArray[i]));
            }
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/newsBatchRemove")
    @ResponseBody
    @RequiresPermissions("common:news:batchRemove")
    public R newsBatchRemove(@RequestParam("ids[]") Integer[] ids) {
        for (int i = 0; i < ids.length; i++) {
            ArticleDO article = articleService.get(ids[i]);
            //刪除附件
            if (StringUtils.isNotEmpty(article.getImage())) {
                this.deleteFile(Long.parseLong(article.getImage()));
            }
            String[] imageArray = article.getAttachment().split(",");
            for (int j = 0; j < imageArray.length; j++) {
                this.deleteFile(Long.parseLong(imageArray[j]));
            }
        }
        articleService.batchRemove(ids);
        return R.ok();
    }

    /**
     * 是否置顶
     */
    @ResponseBody
    @PostMapping("/newsTopOrDown")
    @RequiresPermissions("common:news:edit")
    public R newsTopOrDown(ArticleDO article) {
        ArticleDO articleDO = articleService.get(article.getId());
        articleDO.setIsTop(article.getIsTop());
        if (articleService.update(article) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /***************************************************通知公告******************************************************/
    @GetMapping("/notice")
    @RequiresPermissions("common:notice:notice")
    String Notice() {
        return "common/article/notice";
    }

    @ResponseBody
    @GetMapping("/noticeList")
    @RequiresPermissions("common:notice:notice")
    public PageUtils noticeList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/noticeAdd")
    @RequiresPermissions("common:notice:add")
    String noticeAdd(ArticleDO article, Model model) {
        article.setIsTop("0");
        article.setDelFlag("1");
        model.addAttribute("article", article);
        return "common/article/noticeAdd";
    }

    @GetMapping("/noticeEdit/{id}")
    @RequiresPermissions("common:notice:edit")
    String noticeEdit(@PathVariable("id") Integer id, Model model) {
        ArticleDO article = articleService.get(id);
        model.addAttribute("article", article);

        // 附件
        List<FileDO> fileList = new ArrayList<FileDO>();
        if (StringUtils.isNotEmpty(article.getAttachment())) {
            String[] imageArray = article.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
                if (fileObj != null) {
                    fileList.add(fileObj);
                }
            }
        }
        model.addAttribute("fileList", fileList);
        return "common/article/noticeEdit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/noticeSave")
    @RequiresPermissions("common:notice:add")
    public R noticeSave(ArticleDO article, MultipartFile[] file, HttpServletRequest request) {
        UserDO user = ShiroUtils.getUser().getUser();
        article.setHits(0);
        article.setCreateBy(Integer.parseInt(user.getUserId().toString()));
        article.setCreateTime(new Date());
        article.setPublishTime(new Date());

        // 保存附件
        String image = "";
        if (file != null && file.length > 0) {
            for (MultipartFile f : file) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }

        if (articleService.save(article) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/noticeUpdate")
    @RequiresPermissions("common:notice:edit")
    public R noticeUpdate(ArticleDO article, String fileId, String isDel, MultipartFile[] file, HttpServletRequest request) {

        // 附件
        String image = "";
        if (StringUtils.isNotEmpty(fileId) && StringUtils.isNotEmpty(isDel)) {
            String[] fileIdArray = fileId.split(",");
            String[] isDelArray = isDel.split(",");
            for (int i = 0; i < isDelArray.length; i++) {
                if ("0".equals(isDelArray[i])) {
                    image += fileIdArray[i] + ",";
                } else {
                    this.deleteFile(Long.parseLong(fileIdArray[i]));
                }
            }
        }
        if (file != null && file.length > 0) {
            for (MultipartFile f : file) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }

        articleService.update(article);
		/*ArticleDataDO articleData = new ArticleDataDO();
		articleData.setId(article.getId());
		articleData.setCopyfrom(article.getCopyfrom());
		articleData.setContent(article.getContent());
		articleDataService.update(articleData);*/
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/noticeRemove")
    @ResponseBody
    @RequiresPermissions("common:notice:remove")
    public R noticeRemove(Integer id) {
        ArticleDO article = articleService.get(id);
        if (articleService.remove(id) > 0) {
            //刪除附件
            if (StringUtils.isNotEmpty(article.getImage())) {
                this.deleteFile(Long.parseLong(article.getImage()));
            }
            String[] imageArray = article.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                this.deleteFile(Long.parseLong(imageArray[i]));
            }
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/noticeBatchRemove")
    @ResponseBody
    @RequiresPermissions("common:notice:batchRemove")
    public R noticeBatchRemove(@RequestParam("ids[]") Integer[] ids) {
        for (int i = 0; i < ids.length; i++) {
            ArticleDO article = articleService.get(ids[i]);
            //刪除附件
            if (StringUtils.isNotEmpty(article.getImage())) {
                this.deleteFile(Long.parseLong(article.getImage()));
            }
            String[] imageArray = article.getAttachment().split(",");
            for (int j = 0; j < imageArray.length; j++) {
                this.deleteFile(Long.parseLong(imageArray[j]));
            }
        }
        articleService.batchRemove(ids);
        return R.ok();
    }


    /***************************************************打分专家******************************************************/
    @GetMapping("/articleReviewScore")
    @RequiresPermissions("common:articleReviewScore:articleReviewScore")
    String expertScore() {
        return "common/articleReviewScore/article";
    }

    @ResponseBody
    @GetMapping("/articleReviewScoreList")
    @RequiresPermissions("common:articleReviewScore:articleReviewScore")
    public PageUtils articleReviewScoreList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        params.put("isPublish", "1");
        params.put("status", "1");
        params.put("articleType", "edit");
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/articleReviewScoreEdit/{id}")
    @RequiresPermissions("common:articleReviewScore:edit")
    String articleReviewScoreEdit(@PathVariable("id") Integer id, Model model) {

        ArticleDO article = articleService.get(id);
        AchievementDO achievementDO = articleService.getAchievement(id);
        BeanUtils.copyProperties(article, achievementDO);
        model.addAttribute("article", achievementDO);

        // 附件
        List<FileDO> fileList = new ArrayList<>();
        if (com.bootdo.common.utils.StringUtils.isNotEmpty(article.getAttachment())) {
            String[] imageArray = article.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
                if (fileObj != null) {
                    fileList.add(fileObj);
                }
            }
        }
        model.addAttribute("fileList", fileList);
        return "common/articleReviewScore/edit";
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/articleReviewScoreUpdate")
    @RequiresPermissions("common:articleReviewScore:edit")
    public R articleReviewScoreUpdate(ArticleDO article) {
        //判断是否选择专家
        if (!StringUtils.isNotEmpty(article.getExpertIds())) {
            return R.error("请选择导师");
        }
        articleService.update(article);
        //循环添加专家评价信息
        String[] expertIdArray = article.getExpertIds().split(",");
        ExperevaluateDO experevaluate = null;
        for (int i = 0; i < expertIdArray.length; i++) {
            experevaluate = new ExperevaluateDO();
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("articleId", article.getId());
            params.put("expertId", expertIdArray[i]);
            int count = experevaluateService.count(params);

            if (count > 0) {
                return R.error("该专家已存在!");
            } else {
                experevaluate.setArticleId(article.getId());
                experevaluate.setExpertId(Integer.parseInt(expertIdArray[i]));
                experevaluateService.save(experevaluate);
            }
        }
        return R.ok();
    }

    /**
     * @param ids 多个字符串
     * @return
     * @Description: 打分页面初始化
     * @author: 窦恩虎
     * @date 2018年7月29日 上午9:00:07
     */
    @GetMapping("/articleReviewScoreCheckInit")
    @RequiresPermissions("common:articleReviewScore:edit")
    String articleReviewScoreCheckInit(@RequestParam("ids") String ids, Model model) {
        model.addAttribute("ids", ids);
        return "common/articleReviewScore/articleReviewCheckInfo";
    }

    /**
     * @return
     * @Description: 打分功能
     * @author: 窦恩虎
     * @date 2018年7月29日 上午9:00:07
     */
    @ResponseBody
    @RequestMapping("/articleReviewScoreUpdateCheck")
    @RequiresPermissions("common:articleReviewScore:edit")
    public R updateScoreCheck(ArticleDO article) {

		/*if(articleService.articleReviewBatchCheck(article)>0){
			return R.ok();
		}*/
        //判断是否选择专家
        if (!StringUtils.isNotEmpty(article.getExpertIds())) {
            return R.error("请选择导师");
        }
        articleService.articleReviewBatchCheck(article);
        //循环添加专家评价信息
        String[] articleArray = article.getIds().split(",");
        String[] expertIdArray = article.getExpertIds().split(",");
        ExperevaluateDO experevaluate = null;
        for (int i = 0; i < articleArray.length; i++) {
            for (int j = 0; j < expertIdArray.length; j++) {
                experevaluate = new ExperevaluateDO();
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("articleId", articleArray[i]);
                params.put("expertId", expertIdArray[j]);
                int count = experevaluateService.count(params);
                if (count > 0) {
                    return R.error("该专家已存在!");
                } else {
                    experevaluate.setArticleId(Integer.parseInt(articleArray[i]));
                    experevaluate.setExpertId(Integer.parseInt(expertIdArray[j]));
                    experevaluateService.save(experevaluate);
                }
            }
        }
        return R.ok();
    }

    /***************************************************文章审核******************************************************/
    @GetMapping("/articleReview")
    @RequiresPermissions("common:articleReview:articleReview")
    String articleReview() {
        return "common/article/articleReview";
    }

    @ResponseBody
    @GetMapping("/articleReviewList")
    @RequiresPermissions("common:articleReview:articleReview")
    public PageUtils articleReviewList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.articleReviewlist(query);
        int total = articleService.articleReviewCount(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/articleReviewAdd")
    @RequiresPermissions("common:articleReview:add")
    String articleReviewAdd(ArticleDO article, Model model) {
        article.setIsTop("0");
        article.setDelFlag("1");
        model.addAttribute("article", article);
        return "common/article/noticeAdd";
    }

    @GetMapping("/articleReviewEdit/{id}")
    @RequiresPermissions("common:articleReview:edit")
    String articleReviewEdit(@PathVariable("id") Integer id, Model model) {

        Map params = new HashMap();
        params.put("id", id);
        Query query = new Query(params);
        List<AchievementDO> achievementList = articleService.listAchievement(query);
        //ArticleDO article = articleService.get(id);
        if (achievementList.size() > 0) {

            ArticleDO article = achievementList.get(0);
            model.addAttribute("article", achievementList.get(0));
            // 附件
            List<FileDO> fileList = new ArrayList<>();
            if (com.bootdo.common.utils.StringUtils.isNotEmpty(article.getAttachment())) {
                String[] imageArray = article.getAttachment().split(",");
                for (int i = 0; i < imageArray.length; i++) {
                    FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
                    if (fileObj != null) {
                        fileList.add(fileObj);
                    }
                }
            }
            model.addAttribute("fileList", fileList);
        }
        return "common/article/articleReviewEdit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/articleReviewSave")
    @RequiresPermissions("common:articleReview:add")
    public R articleReviewSave(ArticleDO article) {
        UserDO user = ShiroUtils.getUser().getUser();
        article.setHits(0);
        article.setCreateBy(Integer.parseInt(user.getUserId().toString()));
        article.setCreateTime(new Date());
        if (articleService.save(article) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/articleReviewUpdate")
    @RequiresPermissions("common:articleReview:edit")
    public R articleReviewUpdate(ArticleDO article) {
        LoginUser loginUser = ShiroUtils.getUser();
        article.setAuditDate(new Date());
        article.setAuditBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
        if ("-1".equals(article.getStatus())) {
            //未通过
            article.setIsPublish("0");

        }/*else if("1".equals(article.getStatus())){
			//通过
			//判断是否选择专家
			if(!StringUtils.isNotEmpty(article.getExpertIds())){
				return R.error("请选择导师");
			}
		}*/
        articleService.update(article);
		/*//循环添加专家评价信息
		if("1".equals(article.getStatus())){
			String[] expertIdArray = article.getExpertIds().split(",");
			ExperevaluateDO experevaluate = null;
			for(int i=0; i<expertIdArray.length; i++){
				experevaluate = new ExperevaluateDO();
				experevaluate.setArticleId(article.getId());
				experevaluate.setExpertId(Integer.parseInt(expertIdArray[i]));
				experevaluateService.save(experevaluate);
			}
		}*/
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/articleReviewRemove")
    @ResponseBody
    @RequiresPermissions("common:articleReview:remove")
    public R articleReviewRemove(Integer id) {
        if (articleService.remove(id) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/articleReviewBatchRemove")
    @ResponseBody
    @RequiresPermissions("common:articleReview:batchRemove")
    public R articleReviewBatchRemove(@RequestParam("ids[]") Integer[] ids) {
        articleService.batchRemove(ids);
        return R.ok();
    }

    /**
     * @param ids 多个字符串
     * @return
     * @Description: 审核页面初始化
     * @author: 窦恩虎
     * @date 2018年7月29日 上午9:00:07
     */
    @GetMapping("/articleReviewCheckInit")
    @RequiresPermissions("common:articleReview:edit")
    String articleReviewCheckInit(@RequestParam("ids") String ids, Model model) {
        model.addAttribute("ids", ids);
        return "common/article/articleReviewCheckInfo";
    }

    /**
     * @Description: 审核功能
     * @author: 窦恩虎
     * @date 2018年7月29日 上午9:00:07
     */
    @ResponseBody
    @RequestMapping("/articleReviewUpdateCheck")
    @RequiresPermissions("common:articleReview:edit")
    public R updateCheck(ArticleDO article) {
        if (articleService.articleReviewBatchScoreCheck(article) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /***************************************************推荐文章******************************************************/
    @GetMapping("/articleRecommend")
    @RequiresPermissions("common:articleRecommend:articleRecommend")
    String articleRecommend() {
        return "common/article/articleRecommend";
    }

    @ResponseBody
    @GetMapping("/articleRecommendList")
    @RequiresPermissions("common:articleRecommend:articleRecommend")
    public PageUtils articleRecommendList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        params.put("isPublish", "1");
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/articleRecommendEdit/{id}")
    @RequiresPermissions("common:articleRecommend:edit")
    String articleRecommendEdit(@PathVariable("id") Integer id, Model model) {

        ArticleDO article = articleService.get(id);
        AchievementDO achievementDO = articleService.getAchievement(id);
        BeanUtils.copyProperties(article, achievementDO);
        model.addAttribute("article", achievementDO);
        // 附件
        List<FileDO> fileList = new ArrayList<FileDO>();
        if (StringUtils.isNotEmpty(article.getAttachment())) {
            String[] imageArray = article.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                FileDO fileObj = sysFileService.get(Long.parseLong(imageArray[i]));
                if (fileObj != null) {
                    fileList.add(fileObj);
                }
            }
        }
        model.addAttribute("fileList", fileList);
        return "common/article/articleRecommendEdit";
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/articleRecommendUpdate")
    @RequiresPermissions("common:articleRecommend:edit")
    public R articleRecommendUpdate(ArticleDO article) {
        if ("1".equals(article.getIsRecommend())) {
            article.setRecommendDate(new Date());
        }
        if ("1".equals(article.getIsTop())) {
            article.setTopDate(new Date());
        }
        articleService.update(article);
        return R.ok();
    }

    /**
     * 推荐操作
     *
     * @param id
     * @param type 1是否推荐 2是否置顶
     * @param val
     * @return
     */
    @ResponseBody
    @RequestMapping("/recommendOperation")
    @RequiresPermissions("common:articleRecommend:edit")
    public R recommendOperation(String id, String type, String val) {
        ArticleDO article = articleService.get(Integer.parseInt(id));
        if ("1".equals(type)) {
            article.setIsRecommend(val);
            if ("1".equals(val)) {
                article.setRecommendDate(new Date());
            }
        } else if ("2".equals(type)) {
            article.setIsTop(val);
            if ("1".equals(val)) {
                article.setTopDate(new Date());
            }
        }
        articleService.update(article);
        return R.ok();
    }

    /***************************************************广告管理******************************************************/
    @GetMapping("/advert")
    @RequiresPermissions("common:advert:advert")
    String advert() {
        return "common/article/advert";
    }

    @ResponseBody
    @GetMapping("/advertList")
    @RequiresPermissions("common:advert:advert")
    public PageUtils advertList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.list(query);
        int total = articleService.count(query);
        PageUtils pageUtils = new PageUtils(articleList, total);
        return pageUtils;
    }

    @GetMapping("/advertAdd")
    @RequiresPermissions("common:advert:add")
    String advertAdd(ArticleDO article, Model model) {
        article.setType("1");
        model.addAttribute("article", article);
        return "common/article/advertAdd";
    }

    @GetMapping("/advertEdit/{id}")
    @RequiresPermissions("common:advert:edit")
    String advertEdit(@PathVariable("id") Integer id, Model model) {
        ArticleDO article = articleService.get(id);
        model.addAttribute("article", article);
        //获取附件信息
        FileDO fileObj = new FileDO();
        if (StringUtils.isNotEmpty(article.getImage())) {
            fileObj = sysFileService.get(Long.parseLong(article.getImage()));
        }
        model.addAttribute("fileObj", fileObj);

        // 附件
        List<FileDO> fileList = new ArrayList<FileDO>();
        if (StringUtils.isNotEmpty(article.getAttachment())) {
            String[] imageArray = article.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                FileDO fileObject = sysFileService.get(Long.parseLong(imageArray[i]));
                if (fileObject != null) {
                    fileList.add(fileObject);
                }
            }
        }
        model.addAttribute("fileList", fileList);
        return "common/article/advertEdit";
    }

    /**
     * 保存
     */
    @ResponseBody
    @PostMapping("/advertSave")
    @RequiresPermissions("common:advert:add")
    public R advertSave(MultipartFile file, HttpServletRequest request, ArticleDO article, MultipartFile[] files) {
        LoginUser loginUser = ShiroUtils.getUser();
        article.setHits(0);
        article.setCreateTime(new Date());
        article.setCreateBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
        article.setDelFlag("1");
        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (null != fileDo) {
                article.setImage(fileDo.getId().toString());
            }
        }

        // 保存附件
        String image = "";
        if (file != null && files.length > 0) {
            for (MultipartFile f : files) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }
        if (articleService.save(article) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /**
     * 修改
     */
    @ResponseBody
    @RequestMapping("/advertUpdate")
    @RequiresPermissions("common:advert:edit")
    public R advertUpdate(MultipartFile file, HttpServletRequest request, ArticleDO article, String fileId, String isDel, MultipartFile[] files) {
        ArticleDO oldArticle = articleService.get(article.getId());
        if (file != null) {
            FileDO fileDo = this.uploadFile(file, request);
            if (null != fileDo) {
                article.setImage(fileDo.getId().toString());
                //删除原来上传的附件
                if (StringUtils.isNotEmpty(oldArticle.getImage())) {
                    this.deleteFile(Long.parseLong(oldArticle.getImage()));
                }
            } else {
                article.setImage(oldArticle.getImage());
            }
        }

        // 附件
        String image = "";
        if (StringUtils.isNotEmpty(fileId) && StringUtils.isNotEmpty(isDel)) {
            String[] fileIdArray = fileId.split(",");
            String[] isDelArray = isDel.split(",");
            for (int i = 0; i < isDelArray.length; i++) {
                if ("0".equals(isDelArray[i])) {
                    image += fileIdArray[i] + ",";
                } else {
                    this.deleteFile(Long.parseLong(fileIdArray[i]));
                }
            }
        }
        if (file != null && files.length > 0) {
            for (MultipartFile f : files) {
                FileDO fileDo = this.uploadFile(f, request);
                if (null != fileDo) {
                    image += fileDo.getId() + ",";
                }
            }
        }
        if (StringUtils.isNotEmpty(image)) {
            article.setAttachment(image.substring(0, image.length() - 1));
        }
		/*if(articleService.update(article)>0){
			
			ArticleDataDO articleData = articleDataService.get(article.getId());
			articleData.setCopyfrom(article.getCopyfrom());
			articleDataService.update(articleData);
			
		}*/
        articleService.update(article);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/advertRemove")
    @ResponseBody
    @RequiresPermissions("common:advert:remove")
    public R advertRemove(Integer id) {
        ArticleDO oldArticle = articleService.get(id);
        if (articleService.remove(id) > 0) {
            //删除原来上传的附件
            if (StringUtils.isNotEmpty(oldArticle.getImage())) {
                this.deleteFile(Long.parseLong(oldArticle.getImage()));
            }
            String[] imageArray = oldArticle.getAttachment().split(",");
            for (int i = 0; i < imageArray.length; i++) {
                this.deleteFile(Long.parseLong(imageArray[i]));
            }
            return R.ok();
        }
        return R.error();
    }

    /**
     * 删除
     */
    @PostMapping("/advertBatchRemove")
    @ResponseBody
    @RequiresPermissions("common:advert:batchRemove")
    public R advertBatchRemove(@RequestParam("ids[]") Integer[] ids) {
        for (Integer id : ids) {
            ArticleDO article = articleService.get(id);
            if (StringUtils.isNotEmpty(article.getImage())) {
                this.deleteFile(Long.parseLong(article.getImage()));
            }
            String[] imageArray = article.getAttachment().split(",");
            for (int j = 0; j < imageArray.length; j++) {
                this.deleteFile(Long.parseLong(imageArray[j]));
            }
        }
        articleService.batchRemove(ids);
        return R.ok();
    }

    /***************************************************评荐文章******************************************************/
    @GetMapping("/articleComments")
    @RequiresPermissions("common:articleComments:articleComments")
    String articleComments() {
        return "common/article/articlecomments";
    }

    @ResponseBody
    @GetMapping("/articleCommentsList")
    @RequiresPermissions("common:articleComments:articleComments")
    public PageUtils articleCommentsList(@RequestParam Map<String, Object> params) {

        //查询列表数据
        if (!params.containsKey("specialty")) {
            params.put("specialty", "");
        }
        Query query = new Query(params);
        List<ArticleDO> articleList = articleService.articleCommentsList(query);
        List<ArticleDO> articleListDO = new ArrayList<>();
        for (ArticleDO articleDO : articleList) {

            articleDO.setRanking(articleList.indexOf(articleDO) + 1);
            articleListDO.add(articleDO);
        }
        int total = articleService.articleCommentsCount(query);
        PageUtils pageUtils = new PageUtils(articleListDO, total);
        return pageUtils;
    }

    @GetMapping("/articleCommentsDetail")
    @RequiresPermissions("common:articleComments:articleCommentsDetail")
    String articleCommentsDetail() {
        return "common/article/articlecommentsDetail";
    }

    @ResponseBody
    @GetMapping("/articleCommentsDetailList")
    @RequiresPermissions("common:articleComments:articleCommentsDetail")
    public PageUtils articleCommentsDetailList(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<ExperevaluateDO> experevaluateList = experevaluateService.detailList(query);
        int total = experevaluateService.detailCount(query);
        PageUtils pageUtils = new PageUtils(experevaluateList, total);
        return pageUtils;
    }


    /**
     * 导出
     */
    @ResponseBody
    @RequestMapping("/articleCommentsExportExcel")
    @RequiresPermissions("common:articleComments:articleComments")
    public void articleCommentsExportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request,
                                           HttpServletResponse response, ServletOutputStream out) {
        params.put("categoryId", 4);
        params.put("status", "1");
        params.put("isPublish", "1");
        // 查询列表数据
        articleService.articleCommentsExportExcel(request, response, params, out);
    }
}
