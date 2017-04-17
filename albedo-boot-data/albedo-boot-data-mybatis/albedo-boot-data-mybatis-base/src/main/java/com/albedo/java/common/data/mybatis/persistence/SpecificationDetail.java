package com.albedo.java.common.data.mybatis.persistence;

import com.albedo.java.util.PublicUtil;
import com.albedo.java.util.domain.Order;
import com.albedo.java.util.domain.QueryCondition;
import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@SuppressWarnings("unchecked")
public class SpecificationDetail<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static Logger logger = LoggerFactory.getLogger(SpecificationDetail.class);
	/**
	 * 连接条件
	 */
	public enum Condition {
		AND, OR
	}

	/** 属性分隔符 */
	private static final String PROPERTY_SEPARATOR = ".";

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	/**
	 * and条件

	 */
	private List<QueryCondition> andQueryConditions = Lists.newArrayList();

	public List<QueryCondition> getAndQueryConditions() {
		return andQueryConditions;
	}

	public void setAndQueryConditions(List<QueryCondition> andQueryConditions) {
		this.andQueryConditions = andQueryConditions;
	}

	public List<QueryCondition> getOrQueryConditions() {
		return orQueryConditions;
	}

	public void setOrQueryConditions(List<QueryCondition> orQueryConditions) {
		this.orQueryConditions = orQueryConditions;
	}

	/**
	 * or条件
	 */
	private List<QueryCondition> orQueryConditions = Lists.newArrayList();
	/**
	 * 排序属性
	 */
	private List<Order> orders = Lists.newArrayList();

	/**
	 * 添加一个and条件
	 *
	 * @param condition
	 *            该条件
	 * @return 链式调用
	 */
	public SpecificationDetail<T> and(QueryCondition condition) {
		this.andQueryConditions.add(condition);
		return this;
	}

	public SpecificationDetail<T> andAll(String queryConditionJson) {
		List<QueryCondition> list = null;
		if (PublicUtil.isNotEmpty(queryConditionJson)) {
			try {
				list = JSONArray.parseArray(queryConditionJson, QueryCondition.class);
			} catch (Exception e) {
				logger.warn(PublicUtil.toAppendStr("queryCondition[", queryConditionJson,
						"] is not json or other error", e.getMessage()));
			}
		}
		if (list == null) list = Lists.newArrayList();
		this.andQueryConditions.addAll(list);
		return this;
	}

	/**
	 * 添加多个and条件
	 *
	 * @param condition
	 *            该条件
	 * @return 链式调用
	 */
	public SpecificationDetail<T> and(QueryCondition... condition) {
		this.andQueryConditions.addAll(Arrays.asList(condition));
		return this;
	}
	public SpecificationDetail<T> andAll(Collection<QueryCondition> conditions) {
		this.andQueryConditions.addAll(conditions);
		return this;
	}
	/**
	 * 添加一个or条件
	 *
	 * @param condition 该条件
	 * @return 链式调用
	 */
	public SpecificationDetail<T> or(QueryCondition condition) {
		this.orQueryConditions.add(condition);
		return this;
	}

	/**
	 * 添加多个or条件
	 *
	 * @param condition
	 *            该条件
	 * @return 链式调用
	 */
	public SpecificationDetail<T> or(QueryCondition... condition) {
		this.orQueryConditions.addAll(Arrays.asList(condition));
		return this;
	}
	public SpecificationDetail<T> orAll(Collection<QueryCondition> conditions) {
		this.orQueryConditions.addAll(conditions);
		return this;
	}

	public SpecificationDetail<T> orAll(String queryConditionJson) {
		List<QueryCondition> list = null;
		if (PublicUtil.isNotEmpty(queryConditionJson)) {
			try {
				list = JSONArray.parseArray(queryConditionJson, QueryCondition.class);
			} catch (Exception e) {
				logger.warn(PublicUtil.toAppendStr("queryCondition[", queryConditionJson,
						"] is not json or other error", e.getMessage()));
			}
		}
		if (list == null) list = Lists.newArrayList();
		this.orQueryConditions.addAll(list);
		return this;
	}

	/**
	 * 升序字段
	 *
	 * @param property
	 *            该字段对应变量名
	 * @return 链式调用
	 */
	public SpecificationDetail<T> orderASC(String... property) {
		if(PublicUtil.isNotEmpty(property)){
			for (int i = 0; i < property.length; i++) {
				this.orders.add(Order.asc(property[i]));
			}
		}
		return this;
	}

	/**
	 * 降序字段
	 *
	 * @param property
	 *            该字段对应变量名
	 * @return 链式调用
	 */
	public SpecificationDetail<T> orderDESC(String... property) {
		if(PublicUtil.isNotEmpty(property)){
			for (int i = 0; i < property.length; i++) {
				this.orders.add(Order.desc(property[i]));
			}
		}
		return this;
	}

	/**
	 * 清除所有条件
	 * 
	 * @return 该实例
	 */
	public SpecificationDetail<T> clearAll() {
		if (!this.andQueryConditions.isEmpty())
			this.andQueryConditions.clear();
		if (!this.orQueryConditions.isEmpty())
			this.orQueryConditions.clear();
		if (!this.orders.isEmpty())
			this.orders.clear();
		return this;
	}

	/**
	 * 清除and条件
	 * 
	 * @return 该实例
	 */
	public SpecificationDetail<T> clearAnd() {
		if (!this.andQueryConditions.isEmpty())
			this.andQueryConditions.clear();
		return this;
	}

	/**
	 * 清除or条件
	 * 
	 * @return 该实例
	 */
	public SpecificationDetail<T> clearOr() {
		if (!this.orQueryConditions.isEmpty())
			this.andQueryConditions.clear();
		return this;
	}

	/**
	 * 清除order条件
	 *
	 * @return 该实例
	 */
	public SpecificationDetail<T> clearOrder() {
		if (!this.orders.isEmpty())
			this.orders.clear();
		return this;
	}
}
