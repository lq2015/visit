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

.panelContent {
 	 width:100%
}
.panelContent td{
	font-family:Tahoma,Verdana,Microsoft YaHei;
	padding: 0px 15px;
	border:1px;
	line-height:28px;
	font-size: 12px;
	border-bottom: 1px dashed #ccc;
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
		for (var i = 0; i < 3; i++) {
			var p = $('<div/>').appendTo('body');
			p.panel({
				title : 'Title' + i,
				content : '<div style="padding:5px;">Content' + (i + 1)
						+ '</div>',
				height : 100,
				closable : true,
				collapsible : true,
				tools : [ {
					iconCls : 'icon-reload',
					handler : function() {
						alert('reload')
					}
				} ]
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

	function openUrl(modelCn,url) {
		window.parent.add(modelCn,url);
	}
	
	function refreshPanel(item){
		if(item=='panel1'){
			$("#panel1").html("正在查询....");
			ajaxData('jobDispatch.do?list&qJdStatus=0',function(rows){
				var _data=[];
				for (var i = 0; i < rows.length; i++) {
					var _row={};
					_row.url= 'jobDispatch.do?main&menuId=402886e54c26caab014c2706cf16000a&id=' +rows[i].id;
					_row.msg='【'+rows[i].unit.uiName+'】'+rows[i].jdJobContent;
					_row.date=rows[i].jdOperateTime;
					_data[i]=_row;
				}
				var _html = buildHtml('派工管理',_data);
				$("#panel1").html(_html);
			});
		}
		
		if(item=='panel2'){
			$("#panel2").html("正在查询....");
			ajaxData('jobDispatch.do?list&qJdStatus=1',function(rows){
				var _data=[];
				for (var i = 0; i < rows.length; i++) {
					var _row={};
					_row.url= 'jobDispatch.do?main&menuId=402886e54c26caab014c2706cf16000a&id=' +rows[i].id;
					_row.msg='【'+rows[i].unit.uiName+'】'+rows[i].jdJobContent;
					_row.date=rows[i].jdOperateTime;
					_data[i]=_row;
				}
				var _html = buildHtml('派工管理',_data);
				$("#panel2").html(_html);
			});
		}
		
		if(item=='panel3'){
			$("#panel3").html("正在查询....");
			ajaxData('jobDispatch.do?list&qJdStatus=2',function(rows){
				var _data=[];
				for (var i = 0; i < rows.length; i++) {
					var _row={};
					_row.url= 'jobDispatch.do?main&menuId=402886e54c26caab014c2706cf16000a&id=' +rows[i].id;
					_row.msg='【'+rows[i].unit.uiName+'】'+rows[i].jdJobContent;
					_row.date=rows[i].jdOperateTime;
					_data[i]=_row;
				}
				var _html = buildHtml('派工管理',_data);
				$("#panel3").html(_html);
			});
		}
	}
	
	function buildHtml(modelCn,data){
		var _html=[];
		_html.push('<table class="panelContent" >');
		var _nums = data.length>5?5:data.length;
		
		for (var i = 0; i < _nums; i++) {
			_html.push(' <tr >');
			_html.push('    <td><a href="#" onClick="openUrl(\''+modelCn+'\',\''+data[i].url+'\')">'+data[i].msg+'</a></td>');
			_html.push('    <td align="right">'+data[i].date.substr(0,10)+'</td>');
			_html.push(' </tr >');
		}
		
		_html.push('</table>');
		return _html.join("");
	}
	
    function ajaxData(url,callback){
		$.ajax({url:url, type:"POST", dataType : 'json', success:function (data) {
			 callback(data.rows);
		}});
	}
	
	refreshPanel('panel1');
	refreshPanel('panel2');
	refreshPanel('panel3');
</script>

<body>
	<div class="easyui-layout" style="width: 100%;" id="cc" fit="true">
		<div region="center" border="false">
			<div id="pp" style="position: relative">
				<div style="width: 20%;" >
					<div title="待派工" style="height:auto; padding: 5px;"  collapsible="true" id="panel1"
						data-options="iconCls:'icon-catalog',tools:[
						{
							iconCls:'icon-reload',
							handler:function(){refreshPanel('panel1')}
						}]">
						
					</div>
					<div title="待签到" collapsible="true" id="panel2"
						style="height:auto; padding: 5px;"  	data-options="iconCls:'icon-dictionary',tools:[
						{
							iconCls:'icon-reload',
							handler:function(){refreshPanel('panel2')}
						}]">
					</div>
				</div>
				<div style="width: 20%;">
					<div  title="待签离" closable="true" style="height: auto;padding: 5px;" id="panel3" 
					data-options="iconCls:'icon-theory',tools:[
						{
							iconCls:'icon-reload',
							handler:function(){refreshPanel('panel3')}
						}]">

					</div>
				</div>
			</div>
		</div>
	</div>
</body>
