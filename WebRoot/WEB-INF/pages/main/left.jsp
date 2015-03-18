<%@ page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script type="text/javascript">
	$(function() {
		$('#myaccrdion').accordion();
	});
	
	function add(title,url){
		 if($("#maintabs").tabs("exists",title)){
	       $("#maintabs").tabs("select",title);    
	    }else{
	       $("#maintabs").tabs("add",{
	           title:title,
	           href: url,
	           closable:true
	       });
	    }
	}
	
	$(function () {
		$('#myaccrdion div ul li div').click(function () {
			$("#myaccrdion div ul li div").each(function(){
				$(this).removeClass();
			 });
			 $(this).addClass("selected");
		});
	});
	
</script>
<div style="width:100%; height:100%; overflow: hidden;"
	id="myaccrdion" border="false" fit="true" class="easyui-accordion">
	<c:forEach var="m1" items="${menuList}">
		<c:if test="${m1.menuLevel == 1}">
			<div title="${m1.menuName}" icon="${m1.icoIndex}">
				<ul >
				<c:forEach var="m2" items="${menuList}">
					<c:if test="${m1.id == m2.parentMenu}">
						<li>
							<div onclick="add('${m2.menuName}','${m2.linkPage}')" >
								<span class='leftMenuItem ${m2.icoIndex}'></span> <span>${m2.menuName}</span>
							</div>
						</li>
					</c:if>
				</c:forEach>
				</ul>
			</div>
		</c:if>
	</c:forEach>
</div>