package com.tarena.dao;
import java.util.List;

import com.tarena.entity.Main;
/**
 * @author imenger.cn
 */
public interface MainMapper {
	
	/*今日动态*/
	/**
	 * 注册用户
	 */
	public int getUserCount();
	/**
	 * 上传视频
	 */
	public int uploadVideo();
	
	/**
	 * 收藏视频
	 */
	public int collectVideo();
	
	/**
	 * 购买视频
	 */
	public int buyVideo();
	
	/**
	 * 最新活动
	 */
	public int newActivity();
	
	/**
	 * 最新评论
	 */
	public int newComment();
	
	
	
	
	


//	/*视频统计图*/
//	/**
//	 * JAVA所占比例
//	 */
//	public int javaStatistics();
//	/**
//	 * IOS所占比例
//	 */
//	public int iosStatistics();
//	/**
//	 * Android所占比例
//	 */
//	public int androidStatistics();
//	/**
//	 * NET所占比例
//	 */
//	public int netStatistics();
//	/**
//	 * UID所占比例
//	 */
//	public int uidStatistics();
//	/**
//	 * 网络营销所占比例
//	 */
//	public int webStatistics();
//	/**
//	 * C++所占比例
//	 */
//	public int cStatistics();
//	/**
//	 * 软件测试所占比例
//	 */
//	public int softStatistics();
//	/**
//	 * PHP所占比例
//	 */
//	public int phpStatistics();
//	/**
//	 * 大数据所占比例
//	 */
//	public int dataStatistics();
//	/**
//	 * wen前端所占比例
//	 */
//	public int htmlStatistics();
//	/**
//	 * Unity3D所占比例
//	 */
//	public int unityStatistics();
//	/**
//	 * 智能硬件所占比例
//	 */
//	public int hardStatistics();
//	
//	
//	
//	
	
	/*用户增长率*/
	/**
	 * 月环比
	 */
	public int userMonthBasis();
	/**
	 * 月同比
	 */
	public int userSameMonth();
	/**
	 * 季环比
	 */
	public int userQuarterBasis();
	/**
	 * 季同比
	 */
	public int userSameQuarter();
	/*视频增长率*/
	/**
	 * 月环比
	 */
	public int videoMonthBasis();
	/**
	 * 月同比
	 */
	public int videoSameMonth();
	/**
	 * 季环比
	 */
	public int videoQuarterBasis();
	/**
	 * 季同比
	 */
	public int videoSameQuarter();
	
	
	
	
	
	/**
	 * 视频收藏排行
	 */
	public List<Main> collectionVideoOrder();
	
	/**
	 * 视频购买排行
	 */
	public List<Main> purchasedVideoOrder();
}
