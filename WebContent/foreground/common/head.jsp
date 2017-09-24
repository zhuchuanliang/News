<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="row-fluid">
	<div class="span12">
		<img src="${pageContext.request.contextPath}/images/logo.png"/>
	</div>
</div>
<div class="row-fluid">
	<div class="span12">
		<div class="navbar">
		  <div class="navbar-inner">
		    <a class="brand" href="goIndex">首页</a>
		    <ul class="nav">
		  
		       <c:forEach var="newsType" items="${newsTypeList}">
		       		 <li><a href="news?action=list&typeId=${newsType.newsTypeId}">${newsType.typeName}</a></li>
		       </c:forEach>
		      
		    </ul>
		  </div>
		</div>
	</div>

</div>
</body>
</html>