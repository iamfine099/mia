package com.bootdo.common.config;

public class Constant {
    //演示系统账户
    public static String DEMO_ACCOUNT = "test";
    //自动去除表前缀
    public static String AUTO_REOMVE_PRE = "true";
    //停止计划任务
    public static String STATUS_RUNNING_STOP = "stop";
    //开启计划任务
    public static String STATUS_RUNNING_START = "start";
    //通知公告阅读状态-未读
    public static String OA_NOTIFY_READ_NO = "0";
    //通知公告阅读状态-已读
    public static int OA_NOTIFY_READ_YES = 1;
    //部门根节点id
    public static Long DEPT_ROOT_ID = 0l;
    //缓存方式
    public static String CACHE_TYPE_REDIS ="redis";

    public static String LOG_ERROR = "error";
    
    /**
     * 用户类型
     */
    public static String 	USER_TYPE_STUDENT = "S";
    public static String 	USER_TYPE_EMPLOYER = "E";
    public static String 	USER_TYPE_COUNSELLOR = "C";
    public static String 	USER_TYPE_PLACEMENT_OFFICE = "P"; //Placement Office
    public static String 	USER_TYPE_ADMIN = "A"; //Administrator
    
    /**
     * 角色类型
     */
    public static Long 	ROLE_TYPE_JYZDZX = (long) 61; //就业指导中心
    public static Long 	ROLE_TYPE_FDY = (long) 60; //辅导员
    
	/**
	 * 显示/隐藏
	 */
	public static final String SHOW = "1";
	public static final String HIDE = "0";
	

	/**
	 * 职位类型 f_post_type 若为实习职位则为"I"，若为就业职位则为"E"
	 */
	public static final String BROWSING_POST_TYPE_INTERSHIP= "I";
	public static final String BROWSING_POST_TYPE_EMPLOYMENT= "E";
	
	/**
	 * 添加文章时 不显示栏目
	 */
	public static final int CATEGORY_ADVERT_BANNER = 3; //广告轮播
	public static final int CATEGORY_MEM_ARTICLE = 4; //会员文章
	public static final int CATEGORY_RECOMMEND_ARTICLE = 8; //推荐文章
	public static final int CATEGORY_MEM_MIA = 9; //协会会员
	
    
}
