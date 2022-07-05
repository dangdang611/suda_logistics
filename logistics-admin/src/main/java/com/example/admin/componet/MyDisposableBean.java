package com.example.admin.componet;

import com.example.system.componet.RedisOperator;
import com.example.system.controller.SystemIndexContorller;
import com.example.system.exception.OperationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// 项目销毁的时候执行的方法
@Component
public class MyDisposableBean implements DisposableBean{

	@Autowired
	private RedisOperator redisOperator;
 
	@Override
	public void destroy() throws Exception {

		redisOperator.set("today_page_view",SystemIndexContorller.pageView);//将今天的浏览量存入
		redisOperator.set("total_page_view",SystemIndexContorller.totalView);//保存现在的总浏览量
		
	}
 
}