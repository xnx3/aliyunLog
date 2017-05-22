# aliyunLog
程序中需时刻记录日志，同时，又不能让其造成数据堆积，可采用阿里云日志服务。<br/>
记录日志后可设置几天或几周有效期，超过有效期后自动转为文件存储起来并不会丢失。同时还可以将其导入MaxCompute进行大数据分析！<br/>
整理所得，将日志服务快速接入项目中使用，全程5～20分钟内接入完成接入，实现日志的添加跟获取。
<br/>
## 1 准备工作
阿里云日志服务控制台： <a href="https://sls.console.aliyun.com">https://sls.console.aliyun.com</a> 增加一个项目(Project)，然后再在其中增加一个日志库(logStore)<br/>
具体步骤可参考：<a href="http://www.guanleiming.com/1726.html">http://www.guanleiming.com/1726.html</a><br/>
<br/>
## 2 快速上手，2分钟内跑通项目
##### 2.1 创建项目(Java Project)
可选 a. 将 <a href="https://github.com/xnx3/aliyunLog/tree/master/j2se">j2seSource文件夹内的项目</a> 直接导入到Eclipse中进行调试<br/>
可选 b. 其他编辑器，或自己手动创建项目，需导入 <a href="https://github.com/xnx3/aliyunLog/tree/master/j2se/lib">/j2se/lib/</a> 文件夹中的jar包<br/>
##### 2.2 修改调试类，调试运行
<a href="https://github.com/xnx3/aliyunLog/blob/master/j2se/src/com/xnx3/aliyunLog/SimpleExample.java">/j2se/src/com/xnx3/aliyunLog/SimpleExample.java</a> 修改以下静态工厂中代码，填写你步骤1中阿里云的相关参数
````Java
static{
    //这里填上你的阿里云日志服务相关的参数信息
    actionLog = new AliyunLogUtil("endpoint...", "accessKeyId...", "accessKeySecret...", "project...", "logstore...");
}
````
修改完这五个参数后，直接运行，即可看到效果。此时，日志的写入、读取已操作完毕！<br/>
<br/>

## 3 加入Web项目使用
##### 3.1 准备工作：将步骤2.1中所用的jar包加入到你的web项目中; 查看示例：<a href="https://github.com/xnx3/aliyunLog/blob/master/j2ee">/j2ee</a>
##### 3.2 写入日志，可参考步骤2.2中， SimpleExample 类的 simpleAdd() 方法
##### 3.2 读取保存的日志列表并在页面上显示
3.2.1 控制器Controller中，增加列表页入口
````Java
public static AliyunLogUtil actionLog;
static{
    //这里填上你的阿里云日志服务相关的参数信息
    actionLog = new AliyunLogUtil("endpoint...", "accessKeyId...", "accessKeySecret...", "project...", "logstore...");
}
    
@RequestMapping("list")
public String list(HttpServletRequest request, Model model) throws LogException{
    AliyunLogPageUtil log = new AliyunLogPageUtil(actionLog);
    JSONArray jsonArray = log.list("", "", true, 10, request);
    model.addAttribute("list", jsonArray);
    model.addAttribute("page", log.getPage());
    return "simple/list";
}
````
3.2.2 视图view中，增加两个页面<br/>
>   1. <a href="https://github.com/xnx3/aliyunLog/blob/master/j2ee/WebRoot/WEB-INF/view/simple/list.jsp">list.jsp</a> (列表页，将数据列出展示)<br/>
>   2. <a href="https://github.com/xnx3/aliyunLog/blob/master/j2ee/WebRoot/WEB-INF/view/include/page.jsp">page.jsp</a> (底部的分页功能，显示上一页、下一页、首页、尾页等跳转)<br/>
添加完毕后，重启项目，访问控制器中增加的list方法，看看效果(看一下list.jsp中include标签引用page.jsp的路径，别路径错了)<br/>
至此，日志服务集成到项目中完毕！

## 4 扩展
##### 4.1 jsp页面中增加将时间戳转化为时间描述的tld标签
在 步骤3.2 中，列表页调出数据，显示的日志存储时间 ${log.logtime} 是10位时间戳，可将其转化为能阅读的时间显示。<br/>
引入jar包 <a href="https://github.com/xnx3/xnx3_tld/raw/master/xnx3_tld.jar">xnx3_tld.jar(点击下载)</a> | <a href="https://github.com/xnx3/xnx3_tld">xnx3_tld使用说明</a>

## 5 预览效果
![](http://www.xnx3.com/d/file/doc/j2se_util/20170522/138e051efdc1180d253980ce083c608a.png)



