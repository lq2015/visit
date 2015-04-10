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
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" ></meta>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<title>内外服务人员登记核查系统 </title>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/plug-in/easyui1.3.2/themes/gray/easyui.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/plug-in/easyui1.3.2/plugins/my97.css"/>
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/plug-in/easyui1.3.2/themes/icon.css"/>
	
	<link rel="stylesheet" href="<%=basePath%>/plug-in/poshytip-1.2/tip-yellow/tip-yellow.css" type="text/css" />
	<link rel="stylesheet" type="text/css" href="<%=basePath%>/css/main.css"/>
	
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
	<script type="text/javascript" src="<%=basePath%>/js/json2.min.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/jquery.form.js"></script>
	<script type="text/javascript" src="<%=basePath%>/plug-in/jquery/jquery.jqprint-0.3.js"></script>
	<%-- <%@ include file="/WEB-INF/pages/lodopHeader.jsp"%> --%>
  </head>
	