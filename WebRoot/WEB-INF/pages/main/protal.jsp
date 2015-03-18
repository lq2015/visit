<%@ page pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path;

			
	
%>

<script type="text/javascript"
	src="<%=basePath%>/plug-in/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/plug-in/easyui1.3.2/jquery.easyui.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/plug-in/easyui1.3.2/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/plug-in/easyui1.3.2/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/plug-in/easyui1.3.2/jquery-easyui-portal/portal.css">
<script type="text/javascript"
	src="<%=basePath%>/plug-in/easyui1.3.2/jquery-easyui-portal/jquery.portal.js"></script>
<style type="text/css">
.title {
	font-size: 16px;
	font-weight: bold;
	padding: 20px 10px;
	background: #eee;
	overflow: hidden;
	border-bottom: 1px solid #ccc;
}

.t-list {
	padding: 5px;
}

body {
	margin: 0;
	padding: 0;
}
</style>

<script>
	$(function() {
		$('#pp').portal({
			border : false,
			fit : false
		});
		//add();
	});
	function add() {
		for ( var i = 0; i < 3; i++) {
			var p = $('<div/>').appendTo('body');
			p.panel({
				title : 'Title' + i,
				content : '<div style="padding:5px;">Content' + (i + 1)
						+ '</div>',
				height : 100,
				closable : true,
				collapsible : true,
				tools:[
						{
							iconCls:'icon-reload',
							handler:function(){alert('reload')}
						}]
			});
			
			$('#pp').portal('add', {
				panel : p,
				columnIndex : i
			});
		}
		$('#pp').portal('resize');
	}
	
	function remove() {
		$('#pp').portal('remove', $('#pgrid'));
		$('#pp').portal('resize');
	}

	function openUrl() {
		window.parent.add('学员报名',
				'student.do?main&menuId=402886a048beffc50148bf04d2b70001');
	}
</script>

<body>
	<div class="easyui-layout" style="width: 100%;" id="cc" fit="true">
		<div region="center" border="false">
			<div id="pp" style="position: relative">
				<div style="width: 20%;">
					<div title="通知通告" style="height: 200px; padding: 5px;"
						data-options="iconCls:'icon-ok',tools:[
				{
					iconCls:'icon-reload',
					handler:function(){alert('reload')}
				}]">
						<div class="t-list">
						</div>
						<div class="t-list">
							
						</div>
					</div>
					<div title="消息提醒" collapsible="true" closable="true"
						style="height: 200px; padding: 5px;">
					</div>
				</div>
				<div style="width: 20%;">
					<div id="pgrid" title="待办事项" closable="true" style="height: 200px;">

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
