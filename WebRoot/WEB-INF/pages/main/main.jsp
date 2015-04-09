<%@ page pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/header.jsp"%>
<style>
.shortcut {
	margin-left: 5px;
	margin-top: 8px;
	height: 62px;
}

.shortcut li {
	float: left;
	list-style: none;
	margin-right: 10px;
	cursor: pointer;
}
-->
</style>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<script type="text/javascript">
		$(function(){
		    $("#maintabs").bind('contextmenu',function(e){
		        e.preventDefault();
		        $('#rcmenu').menu('show', {
		            left: e.pageX,
		            top: e.pageY
		        });
		    });
		    //关闭当前标签页
		    $("#closecur").bind("click",function(){
		        var tab = $('#maintabs').tabs('getSelected');
		        var index = $('#maintabs').tabs('getTabIndex',tab);
		        if(index==0) return ;
		        $('#maintabs').tabs('close',index);
		    });
		    //关闭所有标签页
		    $("#closeall").bind("click",function(){
		        var tablist = $('#maintabs').tabs('tabs');
		        for(var i=tablist.length-1;i>=0;i--){
		        	if(i==0) return ;
		            $('#maintabs').tabs('close',i);
		        }
		    });
		    //关闭非当前标签页（先关闭右侧，再关闭左侧）
		    $("#closeother").bind("click",function(){
		        var tablist = $('#maintabs').tabs('tabs');
		        var tab = $('#maintabs').tabs('getSelected');
		        var index = $('#maintabs').tabs('getTabIndex',tab);
		        for(var i=tablist.length-1;i>index;i--){
		        	if(i==0) return ;
		            $('#maintabs').tabs('close',i);
		        }
		        var num = index-1;
		        for(var i=1;i<=num;i++){
		        	$('#maintabs').tabs('close',1);
		        }
		    });
		    //关闭当前标签页右侧标签页
		    $("#closeright").bind("click",function(){
		        var tablist = $('#maintabs').tabs('tabs');
		        var tab = $('#maintabs').tabs('getSelected');
		        var index = $('#maintabs').tabs('getTabIndex',tab);
		        for(var i=tablist.length-1;i>index;i--){
		        	if(i==0) return ;
		            $('#maintabs').tabs('close',i);
		        }
		    });
		    //关闭当前标签页左侧标签页
		    $("#closeleft").bind("click",function(){
		        var tab = $('#maintabs').tabs('getSelected');
		        var index = $('#maintabs').tabs('getTabIndex',tab);
		        var num = index-1;
		        for(var i=1;i<=num;i++){
		        	$('#maintabs').tabs('close',1);
		        }
		    });
		         
		});
	
		function changeUserPwd(id) {
			$.miaxisTools.openPopupWin({
				url : 'user.do?changeUserPwd&id=' + id,
				width : 500,
				height : 150,
				title : '修改密码',
				okBtnId : 'btn_sub'
			});
		}

		function downDrive() {
			$.miaxisTools.openPopupWin({
				url : 'miaxisOcx.do?downDrive',
				width : 850,
				height : 500,
				title : '驱动下载'
			});
		}

		function showUser(name) {
			$.miaxisTools.alert('hello,' + name);
		}
	</script>
	<!-- 顶部-->
	<div region="north" border="false" title=""
		style="background: url('images/top2.jpg') repeat-x; height: 60px; padding: 1px; overflow: hidden;">
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="background-image: images/top.jpg">
			<tr>
				<td align="left" style="vertical-align: text-bottom"><img
					src="images/top.jpg" /></td>
				<td align="right" nowrap>
					<table border="0" cellpadding="0" cellspacing="0">
						<tr style="height: 15px;" align="right">
							<td style="" colspan="2">
								<div>
									<div style="float: left; line-height: 25px; margin-left: 40px;">
										<span style="color: #FFFFFF">当前用户:</span> <span
											style="color: #FFFFFF">${userSession.realName }</span>&nbsp;&nbsp;
										<span style="color: #FFFFFF">登陆名:</span> <span
											style="color: #FFFFFF">${userSession.userName }</span>
									</div>
									<div style="float: left; margin-left: 5px;">
										<div style="right: 0px; bottom: 0px;">
											<a href="javascript:void(0);" class="easyui-menubutton"
												menu="#layout_north_kzmbMenu" iconCls="icon-comturn"
												style="color: #FFFFFF">控制面板</a> <a
												href="javascript:void(0);" class="easyui-menubutton"
												menu="#layout_north_zxMenu" iconCls="icon-exit"
												style="color: #FFFFFF">注销</a>
										</div>
										<div id="layout_north_kzmbMenu"
											style="width: 60px; display: none;">
											<div onClick="showUser('${userSession.realName}')">
												个人信息</div>
											<div class="menu-sep"></div>
											<div onClick="changeUserPwd('${userSession.id}')">修改密码
											</div>
										</div>
										<div id="layout_north_zxMenu"
											style="width: 100px; display: none;"
											onclick="javascript:window.location.href='main.do?logout';">
											<div>退出系统</div>
										</div>
									</div>
								</div>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<!-- 左侧-->
	<div region="west" split="true" href="main.do?left" title="导航菜单"
		style="width: 150px; padding: 1px;"></div>
	<!-- 中间-->
	<div id="mainPanle" region="center" style="overflow: hidden;">
		<div id="maintabs" class="easyui-tabs" fit="true" border="false">
			<div class="easyui-tab" title="首页" href="main.do?home"
				style="padding: 2px; overflow: hidden;"></div>
		</div>
	</div>
	<!-- 底部 -->
	<div region="south" border="false" class='top_menu_bar'>
		<div align="center" style="color: #0000000;">
			<span><b>技术维护：杭州浙泰科技有限公司</b> V1.0.0 Build:150410-01</span> <span
				style=" float:right; margin-right:30px;"><img
				src="images/unstick.gif" style="height:13px; " /> <a href="#"
				onClick="downDrive()">驱动下载</a></span>
		</div>
	</div>
	
	<!-- TAB右键菜单 -->
	<div id="rcmenu" class="easyui-menu" style="">
		<div data-options="iconCls:'icon-cancel'" id="closecur">关闭</div>
		<div id="closeall">关闭全部</div>
		<div id="closeother">关闭其他</div>
		<div class="menu-sep"></div>
		<div id="closeright">关闭右侧标签页</div>
		<div id="closeleft">关闭左侧标签页</div>
	</div>
</body>
</html>