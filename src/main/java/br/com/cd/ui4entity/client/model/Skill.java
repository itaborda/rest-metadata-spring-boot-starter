package br.com.cd.ui4entity.client.model;

public class Skill {

	private Integer id;
	private String skill;
	private Integer average;

	public Skill(Integer id, String skill, Integer average) {
		this.id = id;
		this.skill = skill;
		this.average = average;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSkill() {
		return skill;
	}

	public void setSkill(String skill) {
		this.skill = skill;
	}

	public Integer getAverage() {
		return average;
	}

	public void setAverage(Integer average) {
		this.average = average;
	}

}