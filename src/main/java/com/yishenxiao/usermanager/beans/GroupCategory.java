package com.yishenxiao.usermanager.beans;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class GroupCategory implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    private String groupcategoryname;

    private Date createtime;

    private Date updatetime;

    private String classificationdescribe;

    private Integer rdindex;

    private Integer state;
    
    private List<Group> groupList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupcategoryname() {
        return groupcategoryname;
    }

    public void setGroupcategoryname(String groupcategoryname) {
        this.groupcategoryname = groupcategoryname == null ? null : groupcategoryname.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public String getClassificationdescribe() {
        return classificationdescribe;
    }

    public void setClassificationdescribe(String classificationdescribe) {
        this.classificationdescribe = classificationdescribe == null ? null : classificationdescribe.trim();
    }

    public Integer getRdindex() {
        return rdindex;
    }

    public void setRdindex(Integer rdindex) {
        this.rdindex = rdindex;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

	public List<Group> getGroupList() {
		return groupList;
	}

	public void setGroupList(List<Group> groupList) {
		this.groupList = groupList;
	}

	@Override
	public String toString() {
		return "GroupCategory [id=" + id + ", groupcategoryname=" + groupcategoryname + ", createtime=" + createtime
				+ ", updatetime=" + updatetime + ", classificationdescribe=" + classificationdescribe + ", rdindex="
				+ rdindex + ", state=" + state + ", groupList=" + groupList + "]";
	}
}