package com.bootdo.common.service.impl;

import com.bootdo.common.domain.AchievementDO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bootdo.common.dao.ArticleDao;
import com.bootdo.common.dao.ArticleDataDao;
import com.bootdo.common.domain.ArticleDO;
import com.bootdo.common.domain.ArticleDataDO;
import com.bootdo.common.service.ArticleService;
import com.bootdo.common.utils.ShiroUtils;
import com.bootdo.common.utils.excel.ImportExcel;
import com.bootdo.mia.dao.ExperevaluateDao;
import com.bootdo.mia.domain.ExperevaluateDO;
import com.bootdo.system.shiro.LoginUser;

import org.springframework.web.multipart.MultipartFile;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.TemplateExportParams;



@Service
public class ArticleServiceImpl extends BaseServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleDataDao articleDataDao;
	@Autowired
	private ExperevaluateDao experevaluateDao;

	@Override
	public ArticleDO get(Integer id){
		return articleDao.get(id);
	}

    @Override
    public AchievementDO getAchievement(Integer id){

        return articleDataDao.getAchievement(id);
    }

	@Override
	public List<ArticleDO> list(Map<String, Object> map){
		return articleDao.list(map);
	}

	@Override
	public List<ArticleDO> articleCommentsList(Map<String, Object> map){
		return articleDao.articleCommentsList(map);
	}

	@Override
	public List<ArticleDO> expertRecommendList(Map<String, Object> map){
		return articleDao.expertRecommendList(map);
	}

	@Override
	public List<ArticleDO> photolist(Map<String, Object> map){
		return articleDao.photolist(map);
	}

	@Override
	public int count(Map<String, Object> map){
		return articleDao.count(map);
	}

	@Override
	public int expertRecommendCount(Map<String, Object> map){
		return articleDao.expertRecommendCount(map);
	}

	@Override
	public int articleCommentsCount(Map<String, Object> map){
		return articleDao.articleCommentsCount(map);
	}

	@Override
	public int save(ArticleDO article){
		if(articleDao.save(article) > 0){
			ArticleDataDO articleData = new ArticleDataDO();
			articleData.setId(article.getId());
			articleData.setCopyfrom(article.getCopyfrom());
			articleData.setContent(article.getContent());
			return articleDataDao.save(articleData);
		}
		return 0;
	}

	@Override
	public int saveAchievement(AchievementDO article){

		Integer count = 0;
		// 1 保存文章
		articleDao.save(article);

		// 2 保存文章数据
		ArticleDataDO articleData = new ArticleDataDO();
		articleData.setId(article.getId());
		articleData.setCopyfrom(article.getCopyfrom());
		articleData.setContent(article.getContent());
		count = articleDataDao.save(articleData);

		// 3 保存成果其他信息
		articleDataDao.saveAchievement(article);
		return count;
	}

	@Override
	public int update(ArticleDO article){

		articleDao.update(article);

		ArticleDataDO articleDataDO = new ArticleDataDO();
		articleDataDO.setId(article.getId());
		articleDataDO.setContent(article.getContent());
		articleDataDO.setCopyfrom(article.getCopyfrom());
		return articleDataDao.update(articleDataDO);
	}

	@Override
	public int updateAchievement(AchievementDO article){

		Integer count = 0;
		articleDao.update(article);

		ArticleDataDO articleDataDO = new ArticleDataDO();
		articleDataDO.setId(article.getId());
		articleDataDO.setContent(article.getContent());
		articleDataDO.setCopyfrom(article.getCopyfrom());
		count = articleDataDao.update(articleDataDO);

		articleDataDao.updateAchievement(article);

		return count;
	}

	@Override
	public int remove(Integer id){
		if(articleDao.remove(id) > 0){
			articleDataDao.remove(id);
		}
		return 1;
	}

	@Override
	public int batchRemove(Integer[] ids){
		articleDao.batchRemove(ids);
		articleDataDao.batchRemove(ids);
		return 1;

	}
	/**
	 * 职位类别导出EXCEL
	 * @param request
	 * @param response
	 * @param out
	 */
	public void exportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			ServletOutputStream out) {
		TemplateExportParams params = new TemplateExportParams("templates/doc/Article.xls");
		List<ArticleDO> list = articleDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "Article"+new Date().getTime()/1000+".xls";
			response.setContentType("application/octet-stream");
            response.setHeader("name", fileName);
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setDateHeader("Expires", 0);
            response.setHeader("Content-disposition","attachment; filename=\""+URLEncoder.encode(fileName, "UTF-8")+ "\"");
			 workbook.write(out);
			 workbook.close();
			 out.flush();
			 out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/**
	 * 导入excel
	 * @param fileName
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public void importExcel(String fileName, MultipartFile file) throws Exception {

			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ArticleDO> list = ei.getDataList(ArticleDO.class);
			//
			for (ArticleDO article : list) {

				if(article != null) {
					 articleDao.save( article);
				}

			}
	}

	@Override
	public List<ArticleDO> articleListForCategoryId(Integer categoryId) {
		return articleDao.articleListForCategoryId(categoryId);
	}

	@Override
	public List<ArticleDO> getRoundList() {
		return articleDao.getRoundList();
	}

	@Override
	public int articleReviewBatchCheck(ArticleDO article) {
		LoginUser loginUser = ShiroUtils.getUser();
		if("-1".equals(article.getStatus()) || "0".equals(article.getStatus())){
			//未通过
			article.setIsPublish("0");

		}else{
			article.setIsPublish("1");
		}
		article.setAuditBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		if(articleDao.articleReviewBatchCheck(article) > 0){
			if("1".equals(article.getStatus())){
				String[] articleArray = article.getIds().split(",");
				String[] expertIdArray = article.getExpertIds().split(",");
				ExperevaluateDO experevaluate = null;
				for(int i=0; i<articleArray.length; i++){
					for(int j=0; j<expertIdArray.length; j++){
						experevaluate = new ExperevaluateDO();
						experevaluate.setArticleId(Integer.parseInt(articleArray[i]));
						experevaluate.setExpertId(Integer.parseInt(expertIdArray[j]));
						experevaluateDao.save(experevaluate);
					}
				}
			}
		}
		return 1;
	}

	@Override
	public int articleReviewBatchScoreCheck(ArticleDO article) {
		LoginUser loginUser = ShiroUtils.getUser();
		if("-1".equals(article.getStatus()) || "0".equals(article.getStatus())){
			//未通过
			article.setIsPublish("0");

		}else{
			article.setIsPublish("1");
		}
		article.setAuditBy(Integer.parseInt(loginUser.getUser().getUserId().toString()));
		if(articleDao.articleReviewBatchCheck(article) > 0){

		}
		return 1;
	}

	/**
	 * 评荐文章导出
	 */
	@Override
	public void articleCommentsExportExcel(HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> map,
			ServletOutputStream out) {
		TemplateExportParams params = new TemplateExportParams(bootdoConfig.getUploadPath()+"templates/doc/ArticleComments.xls");
		List<ArticleDO> list = articleDao.list(map);
		Map<String, Object> map1 = new HashMap<String, Object>();
		map1.put("maplist", list);
		Workbook workbook = ExcelExportUtil.exportExcel(params,map1);
		try {
			String fileName = "ArticleComments"+new Date().getTime()/1000+".xls";
			response.setContentType("application/octet-stream");
            response.setHeader("name", fileName);
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setDateHeader("Expires", 0);
            response.setHeader("Content-disposition","attachment; filename=\""+URLEncoder.encode(fileName, "UTF-8")+ "\"");
			 workbook.write(out);
			 workbook.close();
			 out.flush();
			 out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArticleDO prevArticle(Map<String, Object> map) {
		return articleDao.prevArticle(map);
	}

	@Override
	public ArticleDO nextArticle(Map<String, Object> map) {
		return articleDao.nextArticle(map);
	}

	@Override
	public List<ArticleDO> draftslist(Map<String, Object> map) {
		return articleDao.draftslist(map);
	}

	@Override
	public List<ArticleDO> myarticlelist(Map<String, Object> map) {
		return articleDao.myarticlelist(map);
	}

	@Override
	public List<ArticleDO> articleScoreList(Map<String, Object> map) {
		return articleDao.articleScoreList(map);
	}

	@Override
	public int articleScoreListCount(Map<String, Object> map) {
		return articleDao.articleScoreListCount(map);
	}

	@Override
	public List<ArticleDO> experevaluateList(Map<String, Object> map) {
		return articleDao.experevaluateList(map);
	}

	@Override
	public int experevaluateListCount(Map<String, Object> map) {
		return articleDao.experevaluateListCount(map);
	}

	@Override
	public List<ArticleDO> articleReviewlist(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return articleDao.articleReviewlist(map);
	}

	@Override
	public int articleReviewCount(Map<String, Object> map){
		return articleDao.articleReviewCount(map);
	}
}
