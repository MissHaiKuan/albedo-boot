package com.albedo.java.common.event.listener;

import com.albedo.java.common.event.model.ParameterUpdate;
import org.springframework.context.ApplicationEvent;

/**
 * 登录事件
 *
 * @author somewhere
 * @date 2020年03月18日17:22:55
 */
public class ParameterUpdateEvent extends ApplicationEvent {
	public ParameterUpdateEvent(ParameterUpdate source) {
		super(source);
	}
}
