package com.vigekoo.modules.sys.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author oplus
 * @Description: TODO(兵器)
 * @date 2017-12-19 13:59:34
 */
public class Generals implements Serializable {
	
	//
	private Integer id;
	//
	private String name;
	//
	private String image;
	//
	private String pcImage;
	//
	private String video;
	//
	private String previewImage;
	//
	private String previewVideo;
	//
	private String intro;
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
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取：
	 */
	public String getName() {
		return name;
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
	 * 设置：
	 */
	public void setPcImage(String pcImage) {
		this.pcImage = pcImage;
	}
	/**
	 * 获取：
	 */
	public String getPcImage() {
		return pcImage;
	}
	/**
	 * 设置：
	 */
	public void setVideo(String video) {
		this.video = video;
	}
	/**
	 * 获取：
	 */
	public String getVideo() {
		return video;
	}
	/**
	 * 设置：
	 */
	public void setPreviewImage(String previewImage) {
		this.previewImage = previewImage;
	}
	/**
	 * 获取：
	 */
	public String getPreviewImage() {
		return previewImage;
	}
	/**
	 * 设置：
	 */
	public void setPreviewVideo(String previewVideo) {
		this.previewVideo = previewVideo;
	}
	/**
	 * 获取：
	 */
	public String getPreviewVideo() {
		return previewVideo;
	}
	/**
	 * 设置：
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}
	/**
	 * 获取：
	 */
	public String getIntro() {
		return intro;
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
