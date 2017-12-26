package com.vigekoo.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author oplus
 * @Description: TODO(轮播图)
 * @date 2017-11-30 15:35:54
 */
public class Carousel implements Serializable {
	
	//
	private Integer id;
	//
	private String title;
	//
	private String image;
	//pc端图片
	private String pcImage;
	//0:图片1：视频2：问卷3：链接4:测试电脑配置
	private Integer type;
	//
	private String content;
	//
	private Date createTime;
	//
	private Date updateTime;

	/**
	 * 设置：
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	/**
	 * 获取：
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置：
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：pc端图片
	 */
	public void setPcImage(String pcImage) {
		this.pcImage = pcImage;
	}
	/**
	 * 获取：pc端图片
	 */
	public String getPcImage() {
		return pcImage;
	}
	/**
	 * 设置：0:图片1：视频2：问卷3：链接4:测试电脑配置
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取：0:图片1：视频2：问卷3：链接4:测试电脑配置
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置：
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

}
