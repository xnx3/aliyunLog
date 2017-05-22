package com.xnx3.aliyunLog;

import java.util.ArrayList;
import com.aliyun.openservices.log.common.LogItem;
import com.aliyun.openservices.log.common.QueriedLog;
import com.aliyun.openservices.log.exception.LogException;
import com.xnx3.DateUtil;
import com.xnx3.StringUtil;
import com.xnx3.net.AliyunLogUtil;

/**
 * 简单使用阿里云日志服务例子
 * @author 管雷鸣
 */
public class SimpleExample {
	
	public static AliyunLogUtil actionLog;
	static{
		//这里填上你的阿里云日志服务相关的参数信息，参数获取可以参考  http://www.guanleiming.com/1726.html
//		actionLog = new AliyunLogUtil("endpoint...", "accessKeyId...", "accessKeySecret...", "project...", "logstore...");
		actionLog = new AliyunLogUtil("cn-hongkong.log.aliyuncs.com", "LTAIWkg9Fx3esi82", "qICXH2PeVIWXb7IX3uuxQMLpWIMC9H", "requestlog", "fangwen");
	}
	
	public static void main(String[] args) throws LogException {
		//可用此判断是否连接成功
		simpleTest();
		
		//简单添加日志示例：向日志服务中实时存入一条日志（保存后并不是立即能取出来，得等个一秒钟才能取到）
		simpleAdd();
		
		//简单获取日志示例：获取当前7天内，最新的100条日志列表
		simpleList();
		
		//扩展添加日志示例，使用缓存
		extendAdd_1();
	}
	
	/**
	 * 简单测试，看一下当前日志项目里面有几个日志库，作为测试配置得参数是否正确、是否能使用
	 * @throws LogException
	 */
	public static void simpleTest() throws LogException{
		ArrayList<String> storeList = actionLog.getLogStore();
		System.out.println("找到了"+storeList.size()+"个日志库："+storeList.toString());
		if(storeList.size() == 0){
			System.out.println("请在日志项目中添加一个日志库（logStore）");
		}
	}
	
	/**
	 * 向日志服务中实时存入一条日志（保存后并不是立即能取出来，得等个一秒钟才能取到）
	 * @throws LogException 
	 */
	public static void simpleAdd() throws LogException{
		LogItem logItem = actionLog.newLogItem();
		logItem.PushBack("name", "管雷鸣");
		logItem.PushBack("age","25");
		logItem.PushBack("randomString",StringUtil.getRandomAZ(6));	//填充随机六位数的字符串，容易区分条数
		
		//将其立即联网保存到阿里云日志服务中去
		actionLog.save("zhuti", "", logItem);
	}
	
	/**
	 * 获取当前7天内，最新的100条日志列表
	 * @throws LogException
	 */
	public static void simpleList() throws LogException{
		int endTime = DateUtil.timeForUnix10();	//结束时间为当前时间
		ArrayList<QueriedLog> qList = actionLog.queryList("", "", endTime - (7*24*60*60), endTime, 0, 100, true);
		for (int i = 0; i < qList.size(); i++) {
			System.out.println(qList.get(i).GetLogItem().ToJsonString());
		}
	}
	
	
	/**
	 * 日志服务钟开启缓存，设定缓存得数量为100，当日志缓存到100条时，才回将缓存中得日志提交到阿里云得日志服务中去。
	 * <br/>（保存后并不是立即能取出来，得等个一秒钟才能取到）
	 * @throws LogException 
	 */
	public static void extendAdd_1() throws LogException{
		actionLog.setCacheLogMaxNumber(100);
		
		/*
		 * 存储了二百条数据，其实只是联网提交了两次。当达到最大缓存条数100条时，才会联网提交
		 */
		
		for (int i = 0; i < 222; i++) {
			LogItem logItem = actionLog.newLogItem();
			logItem.PushBack("name", "管雷鸣");
			logItem.PushBack("age",""+i);
			actionLog.cacheLog(logItem);
		}
	}
	
}
