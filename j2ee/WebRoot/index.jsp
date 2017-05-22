<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>aliyunLog测试</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  
    <div>
    	简单演示：<br/>
		<a href="simple/addLog.do">立即增加一条日志到阿里云日志服务中</a> | <a href="simple/list.do">日志列表</a>
    </div>
      
    <div>
    	进阶演示：<br/>
		<a href="extend/addLog.do">增加一条日志到缓存中(缓存达到指定条数后自动提交到阿里云日志服务)</a> | <a href="extend/list.do">日志列表</a>
    </div>
    
  </body>
</html>
