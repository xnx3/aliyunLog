package com.xnx3.aliyunLog.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.exception.LogException;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.Page;
import com.xnx3.net.AliyunLogPageUtil;
import com.xnx3.net.AliyunLogUtil;

/**
 * 日志使用扩展。
 * 1.日志加入缓存，达到一定条数后再提交到日志服务
 * 2.日志列表
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/extend/")
public class ExtendLogController{
	
	public static AliyunLogUtil actionLog;
	static{
		//这里填上你的阿里云日志服务相关的参数信息  参数获取可以参考  http://www.guanleiming.com/1726.html
		actionLog = new AliyunLogUtil("endpoint...", "accessKeyId...", "accessKeySecret...", "project...", "logstore...");
		
		//设置缓存的日志条数。当达到这个条数时，才回将当前缓存的日志存入阿里云日志服务。最大支持4096
		actionLog.setCacheLogMaxNumber(5);
	}
	
	/**
	 * 增加一条演示的日志记录到缓存中。当缓存中的日志达到一定的条数后，才会自动联网存入阿里云的日志服务中
	 */
	@RequestMapping("addLog")
	public String addLog(HttpServletRequest request, Model model) throws LogException{
		LogItem logItem = actionLog.newLogItem();
		logItem.PushBack("name", "管雷鸣");
		logItem.PushBack("age","25");
		logItem.PushBack("randomString",StringUtil.getRandomAZ(6));	//填充随机六位数的字符串，容易区分条数
		
		//将其加入缓存中。当缓存中的日志达到设置的  actionLog.setCacheLogMaxNumber(5); 条数时，会自动联网存入阿里云的日志服务中，提高程序性能
		actionLog.cacheLog(logItem);
		
		/**
		 * 如果程序要中途中止，或者tomcat遇到什么意外关闭了等等意外，此时缓存中的日志还未达到保存的触发条数时，可以通过 actionLog.cacheCommit(); 手动触发提交缓存中的日志保存到阿里云的日志服务
		 */
		
		return "extend/tishi";
	}
	
	
	/**
	 * 读取日志列表，分页展示演示出来
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) throws LogException{
		AliyunLogPageUtil log = new AliyunLogPageUtil(actionLog);
		
		//得到当前页面的列表数据
		JSONArray jsonArray = log.list("", "", true, 10, request);
		
		//得到当前页面的分页相关数据（必须在执行了list方法获取列表数据之后，才能调用此处获取到分页）
		Page page = log.getPage();
		//设置分页，出现得上几页、下几页跳转按钮的个数
		page.setListNumber(2);
		
		model.addAttribute("list", jsonArray);
		model.addAttribute("page", page);
		return "extend/list";
	}
	
}
