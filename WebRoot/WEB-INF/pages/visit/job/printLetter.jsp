<%@ page pageEncoding="UTF-8"%>
<%@ page language="java" import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://com.miaxis.www/customer" prefix="miaxis"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title></title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/plug-in/easyui1.3.2/themes/gray/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/plug-in/easyui1.3.2/plugins/my97.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/plug-in/easyui1.3.2/themes/icon.css"/>
	
	<link rel="stylesheet" href="<%=basePath%>/plug-in/poshytip-1.2/tip-yellow/tip-yellow.css" type="text/css" />

	<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/easyui1.3.2/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/easyui1.3.2/easyui_ext.js"></script> 
	<script type="text/javascript" src="<%=basePath%>/plug-in/easyui1.3.2/easyui-lang-zh_CN.js"></script>
	
	<script type="text/javascript" src="<%=basePath%>/plug-in/My97DatePicker/WdatePicker.js"></script>  
	<script type="text/javascript" src="<%=basePath%>/plug-in/easyui1.3.2/plugins/jquery.my97.js"></script>
	
	<script type="text/javascript" src="<%=basePath%>/plug-in/easyui1.3.2/plugins/jquery.validatebox.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/poshytip-1.2/jquery.poshytip.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/lhgdialog/lhgdialog.min.js?skin=chrome"></script>
	<script type="text/javascript" src="<%=basePath%>/js/common.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/jquery.jqprint-0.3.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/teachlog.css" />
  </head>
	

	<body>
		<input id="id" type="hidden" value="" />
		<DIV id="totalPage">
			<%@ include file="/printbar.jsp"%>
			<br /><br /><br /><br /><br /><br /><br /><br /><br /><br />
			<div style="font-size:30px;text-align:center;margin-bottom:60px">介绍信</div>
			<div style="font-size:20px;margin-bottom:20px;margin-left: 100px">${jobBank}：</div>
			<div style="font-size:20px;margin-bottom:20px;margin-left: 140px"> 兹介绍${personName}同志（${personNum}人）身份证号 ${personIdnum} 前来  </div>
			<div style="font-size:20px;margin-bottom:80px;margin-left: 100px"> 你处,联系${jobContent}事宜。请接洽。  </div>
			<div  style="font-size:20px;margin-bottom:20px;margin-left: 500px">${jobDate}</div>  
		</DIV>
	</body>
</html>
