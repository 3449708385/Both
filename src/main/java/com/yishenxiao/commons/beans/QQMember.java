package com.yishenxiao.commons.beans;

public class QQMember {
    private Integer id;

    private Long groupId;

    private Long userId;

    private String nickname;

    private String card;

    private String sex;

    private Integer age;

    private String area;

    private String role;

    private String unfriendly;

    private Long joinTime;

    private String level;

    private Integer isdelete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card == null ? null : card.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role == null ? null : role.trim();
    }

    public String getUnfriendly() {
        return unfriendly;
    }

    public void setUnfriendly(String unfriendly) {
        this.unfriendly = unfriendly == null ? null : unfriendly.trim();
    }

    public Long getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Long joinTime) {
        this.joinTime = joinTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level == null ? null : level.trim();
    }

    public Integer getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

	@Override
	public String toString() {
		return "QQMember [id=" + id + ", groupId=" + groupId + ", userId=" + userId + ", nickname=" + nickname
				+ ", card=" + card + ", sex=" + sex + ", age=" + age + ", area=" + area + ", role=" + role
				+ ", unfriendly=" + unfriendly + ", joinTime=" + joinTime + ", level=" + level + ", isdelete="
				+ isdelete + "]";
	}
}