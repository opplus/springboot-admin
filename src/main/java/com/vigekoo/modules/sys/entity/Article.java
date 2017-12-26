package com.vigekoo.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author oplus
 * @Description: TODO(文章)
 * @date 2017-11-30 15:35:54
 */
public class Article implements Serializable {
	
	//
	private Integer id;
	//标题
	private String title;
	//预览图
	private String image;
	//正文
	private String content;
	//创建时间
	private Date createTime;
	//最近一次修改时间
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
	 * 设置：标题
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * 获取：标题
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置：预览图
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * 获取：预览图
	 */
	public String getImage() {
		return image;
	}
	/**
	 * 设置：正文
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取：正文
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：最近一次修改时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：最近一次修改时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}

}
