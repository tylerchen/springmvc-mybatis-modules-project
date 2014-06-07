/*******************************************************************************
 * Copyright (c) 2014-5-20 @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> - initial API and implementation
 ******************************************************************************/
package com.foreveross.infra.util.groovy;

import java.io.File;
import java.io.FileFilter;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author <a href="mailto:iffiff1@hotmail.com">Tyler Chen</a> 
 * @since 2014-5-20
 */
public class GroovyFactory implements ApplicationContextAware {

	private String directory;

	private static final Timer timer = new Timer();
	private static ApplicationContext context = null;
	private static final Map<String, File> groovyBeanMap = new HashMap<String, File>(
			1024);

	public String getDirectory() {
		return directory;
	}

	public void setDirectory(String directory) {
		this.directory = directory;
	}

	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		{
			if (GroovyFactory.context == null) {
				GroovyFactory.context = context;
			}
			timer.schedule(new TimerTask() {
				public void run() {
					Map<String, File> temp = new HashMap<String, File>(1024);
					String realDirectory = getDirectory();
					File[] files = new File(realDirectory)
							.listFiles(new FileFilter() {
								public boolean accept(File pathname) {
									return pathname.getName().endsWith(
											".groovy");
								}
							});
					for (File file : files) {
						String beanName = file.getName().replace(".groovy", "");
						temp.put(beanName, file);
					}
					for (Entry<String, File> entry : groovyBeanMap.entrySet()) {
						if (!temp.containsKey(entry)) {
							removeBeanDefinition(entry.getKey(), entry
									.getValue());
						}
					}
					for (Entry<String, File> entry : temp.entrySet()) {
						if (!groovyBeanMap.containsKey(entry)) {
							registerBeanDefinition(entry.getKey(), entry
									.getValue());
						}
					}
					groovyBeanMap.clear();
					groovyBeanMap.putAll(temp);
				}
			}, 3000, 1000);
		}
		//		// 只有这个对象才能注册bean到spring容器  
		//		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context
		//				.getAutowireCapableBeanFactory();
		//
		//		// 因为spring会自动将xml解析成BeanDefinition对象然后进行实例化，这里我们没有用xml，所以自己定义BeanDefinition  
		//		// 这些信息跟spring配置文件的方式差不多，只不过有些东西lang:groovy标签帮我们完成了  
		//		final String refreshCheckDelay = "org.springframework.scripting.support.ScriptFactoryPostProcessor.refreshCheckDelay";
		//		final String language = "org.springframework.scripting.support.ScriptFactoryPostProcessor.language";
		//
		//		File root = new File(directory);
		//		String realDirectory = directory;
		//
		//		File[] files = new File(realDirectory).listFiles(new FileFilter() {
		//			public boolean accept(File pathname) {
		//				return pathname.getName().endsWith(".groovy") ? true : false;
		//			}
		//		});
		//		for (File file : files) {
		//			try {
		//				GenericBeanDefinition bd = new GenericBeanDefinition();
		//				bd
		//						.setBeanClassName("org.springframework.scripting.groovy.GroovyScriptFactory");
		//				// 刷新时间  
		//				bd.setAttribute(refreshCheckDelay, 500);
		//				// 语言脚本  
		//				bd.setAttribute(language, "groovy");
		//				// 文件目录  
		//				bd.getConstructorArgumentValues().addIndexedArgumentValue(0,
		//						"file:" + file.getPath());
		//				// 注册到spring容器  
		//				String beanName = file.getName().replace(".groovy", "");
		//				beanFactory.registerBeanDefinition(beanName, bd);
		//				System.out.println("Bean:" + beanFactory.getBean(beanName));
		//			} catch (Exception e) {
		//				e.printStackTrace();
		//			}
		//		}
	}

	public void registerBeanDefinition(String groovyBeanName, File file) {
		// 只有这个对象才能注册bean到spring容器  
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context
				.getAutowireCapableBeanFactory();
		// 因为spring会自动将xml解析成BeanDefinition对象然后进行实例化，这里我们没有用xml，所以自己定义BeanDefinition  
		// 这些信息跟spring配置文件的方式差不多，只不过有些东西lang:groovy标签帮我们完成了  
		final String refreshCheckDelay = "org.springframework.scripting.support.ScriptFactoryPostProcessor.refreshCheckDelay";
		final String language = "org.springframework.scripting.support.ScriptFactoryPostProcessor.language";
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd
				.setBeanClassName("org.springframework.scripting.groovy.GroovyScriptFactory");
		// 刷新时间  
		bd.setAttribute(refreshCheckDelay, 500);
		// 语言脚本  
		bd.setAttribute(language, "groovy");
		// 文件目录  
		bd.getConstructorArgumentValues().addIndexedArgumentValue(0,
				"file:" + file.getPath());
		// 注册到spring容器  
		bd.setAutowireMode(4);
		beanFactory.registerBeanDefinition(groovyBeanName, bd);
	}

	public void removeBeanDefinition(String groovyBeanName, File file) {
		// 只有这个对象才能注册bean到spring容器  
		DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) context
				.getAutowireCapableBeanFactory();
		beanFactory.removeBeanDefinition(groovyBeanName);
	}
}