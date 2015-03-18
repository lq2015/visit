<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<style type="text/css">
.driverlist {
	width: 95%;margin-left: 20px
}

.driverlist .tit {
	height: 60px;
	font-size: 16px;
	font-family: "微软雅黑";
	line-height: 60px;
	text-align: center;
	font-weight: bold;
}

.driverlist .tit1 {
	height: 20px;
	pading: 0 10px;
	font-size: 14px;
	line-height: 20px;
	font-family: "微软雅黑";
	text-align: left;
	font-weight: bold;
}

.driverlist .con {
	padding: 5px 10px;
}

.driverlist .con .tits {
	font-size: 12px;
	font-family: "微软雅黑";
	font-weight: bold;
	height: 30px;
	line-height: 30px;
}

.driverlist .con .cons {
	font-size: 12px;
	line-height: 20px;
	padding: 10px 0;
}

.driverlist .con .cons a {
	color: #0070C0;
	font-weight: bold;
}

.dlist li {
	font-size: 14px;
	margin: 15px 0 5px 5px;
}
</style>
<body style="background-color: #E1EFFA;">
  <div class="driverlist">
    <div class="tit">系统驱动程序说明</div>
    <div class="tit1">使用本系统需要安装以下程序：</div>
    <br/>
    <div class="tit1">一、中正MR300驱动程序</div>
    <div class="con">
      <div class="cons">
        &nbsp;&nbsp;&nbsp;&nbsp;此程序是浙江中正自主研发生产,支持读取二代身份证、IC卡和采集指纹数据。 <br /> <a href="<%=basePath%>/download/ocx-fj.rar">点击此处</a>下载中正MR300驱动程序并安装。
      </div>
    </div>
    <div class="tit1">二、 打印控件</div>
    <div class="con">
      <div class="cons">
        &nbsp;&nbsp;&nbsp;&nbsp;此打印控件，主要是用于打印学员信息。如果您在使用本系统的过程中，发现无法打印学员信息的问题，可能就是因为没有安装此打印控件。正常情况下，如果您未安装此控件，在您使用本系统时浏览器会自动提示安装，但如果您的IE浏览器安全级别设置较高，可能会拦截该提示。为了确保您能够正常使用打印功能，请您手动安装本控件。<br />
        <b>1.操作系统32位适用：</b><a href="<%=basePath%>/download/install_lodop32.exe">点击此处</a>下载打印控件安装程序并安装。<br />
        <b>2.操作系统64位适用：</b><a href="<%=basePath%>/download/install_lodop64.exe">点击此处</a>下载打印控件安装程序并安装。<br />
      </div>
    </div>
    
    <div class="tit1">三、 IE8浏览器</div>
    <div class="con">
      <div class="cons">
        &nbsp;&nbsp;&nbsp;&nbsp;本系统使用的所有驱动和接口程序在IE8浏览器下兼容性是最好的，低版本及高版本的IE对接口的兼容性存在不足，使用时容易出现问题。所以，建议您将驾校的浏览器统一升级成IE8浏览器，并使用IE8浏览器来使用本系统。 <br /> <a
          href="http://download.microsoft.com/download/1/6/1/16174D37-73C1-4F76-A305-902E9D32BAC9/IE8-WindowsXP-x86-CHS.exe">点击此处</a>下载IE8浏览器安装程序并安装。
      </div>
    </div>
    <!-- 
   <ul class="dlist">
	   <li><a href="driver/huateng_ocx.exe">二代证-华腾永泰OCX接口 v1.7.4</a></li>
	   <li><a href="driver/usb_driver.exe">二代证读验机具USB驱动</a></li>
	   <li><a href="driver/zwy_driver.zip">指纹仪驱动</a></li>
	    <li><a href="http://download.microsoft.com/download/1/6/1/16174D37-73C1-4F76-A305-902E9D32BAC9/IE8-WindowsXP-x86-CHS.exe">IE8浏览器</a></li>
	   <li><a href="install_lodop.exe">打印控件</a> </li>
   </ul>-->
  </div>
</body>
</html>
