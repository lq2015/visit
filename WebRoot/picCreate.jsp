<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.io.*" %> 

<% 
//显示本地图片文件预览 ppath为例如：d:/test.jpg
String ppath=request.getParameter("ppath"); 
String file = ppath; 

FileInputStream in = new FileInputStream(new File(file)); 
out.clearBuffer(); 
out = pageContext.pushBody();
OutputStream o = response.getOutputStream(); 
int l = 0; 
byte[] buffer = new byte[4096]; 

while((l = in.read(buffer)) != -1){ 
	o.write(buffer,0,l); 
} 
o.flush(); 
in.close(); 
o.close(); 
%> 

