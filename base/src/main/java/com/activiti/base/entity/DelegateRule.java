package com.activiti.base.entity;

import java.io.Serializable;

/**
 * 委托规则
 *
 */
public class DelegateRule implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6982625425057579427L;
	
	//规则id
	private String ruleId;
	//规则名称
	private String ruleName;
	//规则key
	private String ruleKey;
	//值
	private String ruleValue;
	//类型
	private String ruleType;
	//运算符
	private String ruleOperator;
	//委托记录id
	private String delegateId;
	
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	public String getRuleKey() {
		return ruleKey;
	}
	public void setRuleKey(String ruleKey) {
		this.ruleKey = ruleKey;
	}
	public String getRuleValue() {
		return ruleValue;
	}
	public void setRuleValue(String ruleValue) {
		this.ruleValue = ruleValue;
	}
	public String getRuleType() {
		return ruleType;
	}
	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}
	public String getRuleOperator() {
		return ruleOperator;
	}
	public void setRuleOperator(String ruleOperator) {
		this.ruleOperator = ruleOperator;
	}
	public String getDelegateId() {
		return delegateId;
	}
	public void setDelegateId(String delegateId) {
		this.delegateId = delegateId;
	}

}
