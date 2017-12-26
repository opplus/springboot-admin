package com.vigekoo.modules.sys.entity;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author oplus
 * @Description: TODO(角色)
 * @date 2017-6-23 15:07
 */
public class SysRole implements Serializable {
	
	/**
	 * 角色ID
	 */
	private Long id;

	/**
	 * 角色名称
	 */
	@NotBlank(message="角色名称不能为空")
	private String name;

	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 创建者ID
	 */
	private Long createUserId;
	
	private List<Long> menuIdList;
	
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 设置：
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 获取：
	 * @return Long
	 */
	public Long getId() {
		return id;
	}
	
	/**
	 * 设置：角色名称
	 * @param name 角色名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取：角色名称
	 * @return String
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * 设置：备注
	 * @param remark 备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 获取：备注
	 * @return String
	 */
	public String getRemark() {
		return remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public List<Long> getMenuIdList() {
		return menuIdList;
	}

	public void setMenuIdList(List<Long> menuIdList) {
		this.menuIdList = menuIdList;
	}
	
}
