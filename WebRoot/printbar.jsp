<%@ page pageEncoding="UTF-8"%>
<style media=print>
.Noprint {
	display: none;
}

.PageNext {
	page-break-after: always;
}
</style>
<style>

li {
	list-style: none;
}

img {
	border: none;
}

em {
	font-style: normal;
}

a {
	color: #555;
	text-decoration: none;
	outline: none;
	blr: this .       onFocus =       this .       blur();
}

a:hover {
	color: #000;
	text-decoration: underline;
}

.bnav {
	text-align: left;
	height: 25px;
	overflow: hidden;
	width: 230px;
	line-height: 25px;
	background: #fff;
	margin: 0 1%;
	border: #0066CC 1px solid;
	border-bottom: none;
	z-index: 999;
	position: fixed;
	bottom: 0;
	right: 0;
	_position: absolute; /* for IE6 */
	_top: expression(documentElement .       scrollTop +         documentElement .
		  
		   clientHeight-this .       offsetHeight); /* for IE6 */
	overflow: visible;
}

.closeprint {
	position: absolute;
	right: 5px;
	height: 25px;
	width: 16px;
	text-indent: -9999px;
	padding-left: 10px;
}

.closeprint a {
	background: url(${pageContext.request.contextPath}/images/close.gif) no-repeat center;
	width: 16px;
	display: block;
}

.bnav2 {
	height: 24px;
	line-height: 24px;
	margin: 1px;
	margin-bottom: 0;
	background: #E5E5E5;
}

.bnav .s1 {
	position: absolute;
	left: 10px;
}

.bnav .s1 img {
	padding-top: 3px;
	margin-right: 7px;
}

.bnav .s2 {
	position: absolute;
	right: 30px;
	color: #888;
}

.bnav .s2 span {
	padding-right: 10px;
}

.bnav .s2 a {
	margin: 0 6px;
}

.bnav3 {
	height: 25px;
	width: 24px;
	text-align: center;
	line-height: 25px;
	margin: 0 1%;
	background: #fff;
	padding-left: 6px;
	border: #0066CC 1px solid;
	border-bottom: none;
	z-index: 999;
	position: fixed;
	bottom: 0;
	right: 0;
	_position: absolute; /* for IE6 */
	_top: expression(documentElement .       scrollTop +         documentElement .
		  
		   clientHeight-this .       offsetHeight); /* for IE6 */
	overflow: visible;
}

.bnav3 a {
	background: url(${pageContext.request.contextPath}/images/open.gif) no-repeat center;
	display: block;
	height: 25px;
	width: 16px;
	text-indent: -5000px;
}
</style>
<OBJECT id=WebBrowser classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2
	height=0 width=0>
</OBJECT>   
<div class="Noprint">
	<div class="bnav openClose">
		<div class="bnav2">
			<span class="s1"></span>
			<span class="s2"> <span
				style="background: url(${pageContext.request.contextPath}/images/zj.gif) no-repeat; padding-left: 5px;"></span><a
				href="#" onclick="setpage()" title="设置打印页面" >页面设置</a>| <span
				style="background: url(${pageContext.request.contextPath}/images/dy.gif) no-repeat; padding-left: 4px;"></span><a
				href="#" onclick="pageprint()" title="打印" >打印</a>| <span
				style="background: url(${pageContext.request.contextPath}/images/exit.gif) no-repeat; padding-left: 4px;"></span><a
				href="#" onclick="closewindow()" title="关闭窗口" >关闭</a> |</span>
			<span class="closeprint"> <a href="javascript:void(0)"
				onclick="closeNav()" title="关闭工具条">关闭工具条</a> </span>
		</div>
	</div>
	<div class="bnav3 openClose" style="display: none;">
		<a href="javascript:void(0)" onclick="showNav()" title="打开工具条">打开</a>
	</div>
</div>

<script type="text/javascript">
	function closewindow(){
		window.opener=null;
		window.open('','_self');
		window.close();
	}
	
	function pageprint(){
		document.all.WebBrowser.ExecWB(6,1)
	}
	
	function setpage(){
		document.all.WebBrowser.ExecWB(8,1)
	}
	
	function showNav(){
		$(".openClose").toggle();
	}
	
	function closeNav(){
		$(".openClose").toggle();
	}
	
    var HKEY_Root, HKEY_Path, HKEY_Key;
    HKEY_Root = "HKEY_CURRENT_USER";
    HKEY_Path = "\\Software\\Microsoft\\Internet Explorer\\PageSetup\\";
    
    //设置网页打印的页眉页脚为空
    function PageSetup_Null() {
        try {
            var Wsh = new ActiveXObject("WScript.Shell");
            HKEY_Key = "header";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
            HKEY_Key = "footer";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
            HKEY_Key = "margin_bottom";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
            HKEY_Key = "margin_left";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
            HKEY_Key = "margin_right";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
            HKEY_Key = "margin_top";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "0");
        }
        catch (e) {
            //alert(e);
        }
    }
    //设置网页打印的页眉页脚为默认值
    function PageSetup_Default() {
        try {
            var Wsh = new ActiveXObject("WScript.Shell");
            HKEY_Key = "header";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
            HKEY_Key = "footer";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "");
        }
        catch (e) { }
    }
    
    //不缩小内容
    function PageSetup_Shrink_To_Fit(){
    	 try {
            var Wsh = new ActiveXObject("WScript.Shell");
            HKEY_Key = "Shrink_To_Fit";
            Wsh.RegWrite(HKEY_Root + HKEY_Path + HKEY_Key, "no");
        }
        catch (e) { }
    }
    
    PageSetup_Null();
    PageSetup_Default();
    PageSetup_Shrink_To_Fit();
</script>