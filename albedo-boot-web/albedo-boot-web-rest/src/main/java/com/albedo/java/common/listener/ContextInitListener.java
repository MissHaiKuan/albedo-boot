package com.albedo.java.common.listener;

import com.albedo.java.common.base.BaseInit;
import com.albedo.java.common.base.BaseInterface;
import com.albedo.java.util.PublicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by lijie on 2017/5/17.
 *
 * @author 837158334@qq.com
 */

public class ContextInitListener implements ApplicationListener<ContextRefreshedEvent> {

    private static Logger _log = LoggerFactory.getLogger(ContextInitListener.class);


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
//        //初始化权限数据
//        ((InvocationSecurityMetadataSourceService) SpringContextHolder.
//                getBean("invocationSecurityMetadataSourceService")).afterPropertiesSet();
//        //初始化freeMarker引擎数据
//        SpringContextHolder.getBean(FreeMarkerConfig.class).afterPropertiesSet();
//        //初始化任务
//        SpringContextHolder.getBean(TaskScheduleJobExcutorService.class).afterPropertiesSet();


        // root application context
        if (null == contextRefreshedEvent.getApplicationContext().getParent()) {
            _log.debug(">>>>> spring初始化完毕 <<<<<");
            // spring初始化完毕后，通过反射调用所有使用BaseInit注解的afterPropertiesSet方法
            Map<String, Object> baseServices = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BaseInit.class);
            if (PublicUtil.isNotEmpty(baseServices)) {
                for (Object service : baseServices.values()) {
                    _log.debug(">>>>> {}.afterPropertiesSet()", service.getClass().getName());
                    try {
                        Method initMapper = service.getClass().getMethod("afterPropertiesSet");
                        initMapper.invoke(service);
                    } catch (Exception e) {
                        _log.error("初始化BaseInit的afterPropertiesSet方法异常{}", e);
                        e.printStackTrace();
                    }
                }
            }

            // 系统入口初始化
            Map<String, BaseInterface> baseInterfaceBeans = contextRefreshedEvent.getApplicationContext().getBeansOfType(BaseInterface.class);
            if (PublicUtil.isNotEmpty(baseInterfaceBeans)) {
                for (Object service : baseInterfaceBeans.values()) {
                    _log.debug(">>>>> {}.init()", service.getClass().getName());
                    try {
                        Method init = service.getClass().getMethod("init");
                        init.invoke(service);
                    } catch (Exception e) {
                        _log.error("初始化BaseInterface的init方法异常{}", e);
                        e.printStackTrace();
                    }
                }
            }


        }


    }


}
