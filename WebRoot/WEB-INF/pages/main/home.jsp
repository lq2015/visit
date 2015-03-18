<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>
<iframe name="homeframe" id="homeframe" height="100%" width="100%" src="<%=basePath%>/main.do?protal" frameborder="0"
scrolling="no"></iframe>