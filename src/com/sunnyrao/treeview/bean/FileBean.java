package com.sunnyrao.treeview.bean;

import com.sunnyrao.treeview.utils.annotation.TreeNodeId;
import com.sunnyrao.treeview.utils.annotation.TreeNodeLabel;
import com.sunnyrao.treeview.utils.annotation.TreeNodePid;

public class FileBean {

	public FileBean(int id, int pId, String label) {
		super();
		this.id = id;
		this.pId = pId;
		this.label = label;
	}

	@TreeNodeId
	private int id;

	@TreeNodePid
	private int pId;

	@TreeNodeLabel
	private String label;

	private String desc;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpId() {
		return pId;
	}

	public void setpId(int pId) {
		this.pId = pId;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
