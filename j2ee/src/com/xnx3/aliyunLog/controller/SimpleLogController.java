package com.xnx3.aliyunLog.controller;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.exception.LogException;
import com.xnx3.StringUtil;
import com.xnx3.j2ee.util.IpUtil;
import com.xnx3.net.AliyunLogPageUtil;
import com.xnx3.net.AliyunLogUtil;

/**
 * 简单的日志服务使用
 * @author 管雷鸣
 */
@Controller
@RequestMapping("/simple/")
public class SimpleLogController{
	
	public static AliyunLogUtil actionLog;
	static{
		//这里填上你的阿里云日志服务相关的参数信息，参数获取可以参考  http://www.guanleiming.com/1726.html
		actionLog = new AliyunLogUtil("endpoint...", "accessKeyId...", "accessKeySecret...", "project...", "logstore...");
	}
	
	/**
	 * 增加一条演示的日志记录
	 */
	@RequestMapping("addLog")
	public String addLog(HttpServletRequest request, Model model) throws LogException{
		LogItem logItem = actionLog.newLogItem();
		logItem.PushBack("name", "管雷鸣");
		logItem.PushBack("age","25");
		logItem.PushBack("randomString",StringUtil.getRandomAZ(6));	//填充随机六位数的字符串，容易区分条数
		
		//将其立即保存到阿里云日志服务中去
		actionLog.save("zhuti", IpUtil.getIpAddress(request), logItem);
		
		return "simple/tishi";
	}
	
	
	/**
	 * 读取日志列表，分页展示演示出来
	 */
	@RequestMapping("list")
	public String list(HttpServletRequest request, Model model) throws LogException{
		AliyunLogPageUtil log = new AliyunLogPageUtil(actionLog);
		JSONArray jsonArray = log.list("", "", true, 10, request);
		model.addAttribute("list", jsonArray);
		model.addAttribute("page", log.getPage());
		return "simple/list";
	}
	
}
