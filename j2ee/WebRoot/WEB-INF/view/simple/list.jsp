<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html lang="en">
<head>
<title>演示日志分页列表</title>
<script src="http://res.weiunity.com/js/jquery-2.1.4.js"></script>
<!-- layer 、 layui -->
<script src="http://res.weiunity.com/layer/layer.js"></script>
<link rel="stylesheet" href="http://res.weiunity.com/layui/css/layui.css">
<script src="http://res.weiunity.com/layui/layui.js"></script>
<body>

<br/> 
 
<!-- 表单搜索 -->
<form method="get" class="layui-form">
	<div class="layui-form-item">
		<label class="layui-form-label" style="width: auto;">
			关键词
			<i class="layui-icon" style="border: 1px solid #e2e2e2; padding:3px;border-radius: 30px; color: #626262;font-size: 12px; cursor: pointer;" onclick="window.open('https://help.aliyun.com/document_detail/29060.html');">&#xe607;</i>
		</label>
 		<div class="layui-input-inline">
			<input type="text" name="queryString" placeholder="请输入关键词" autocomplete="off" class="layui-input" value="<%=request.getParameter("queryString")==null? "":request.getParameter("queryString")  %>">
		</div>
		
		<input class="layui-btn" type="submit" value="搜索"/>
	</div>
</form>  
  
<!-- 列表数据 -->  
<table class="layui-table">
	<thead>
		<tr>
			<th>姓名</th>
			<th>年龄</th>
			<th>6位字符</th>
			<th>操作时间</th>
		</tr> 
	</thead>
	<tbody>
		<c:forEach items="${list}" var="log">
			<tr>
				<td>${log.name }</td>
				<td>${log.age }</td>
				<td>${log.randomString }</td>
				<td>${log.logtime }</td>
			</tr>
		</c:forEach>	
	</tbody>
</table>  

<br/>
<!-- 通用分页跳转 -->
<jsp:include page="../include/page.jsp">
	<jsp:param name="page" value="${page }"/>
</jsp:include>

</body>
</html>