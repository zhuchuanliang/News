<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<script type="text/javascript">

function checkChange(){
	if(document.getElementById("isImage").checked){
		$("#hdtp").show();
	}else{
		$("#hdtp").hide();
	}
}

function checkForm(){
	var title=document.getElementById("title").value;
	var author=document.getElementById("author").value;
	var typeId=document.getElementById("typeId").value;
	var content=CKEDITOR.instances.content.getData();
	if(title==null||title==""){
		document.getElementById("error").innerHTML="新闻标题不能为空！";
		return false;
	}
	if(author==null||author==""){
		document.getElementById("error").innerHTML="作者不能为空！";
		return false;
	}
	if(typeId==null||typeId==""){
		document.getElementById("error").innerHTML="请选择新闻类别！";
		return false;
	}
	if(content==null||content==""){
		document.getElementById("error").innerHTML="新闻内容不能为空！";
		return false;
	}
	return true;
}
</script>
</head>
<body>
<div class="data_liat backMain">
	<div class="dataHeader navi">
		${navCode}
	</div>
	<div class="data_content">
		<form action="news?action=save" method="post" enctype="multipart/form-data" onsubmit="return checkForm()">
			<table cellpadding="5" width="100%">
				<tr>
					<td width="80px">
						<label>新闻标题:</label>
					</td>
					<td>
						<input type="text" id="title" name="title" class="input-xxlarge"/>
					</td>
				</tr>
				<tr>
					<td>
						<label>新闻作者：</label>
					</td>
					<td>
						<input type="text" id="author" name="author" />
					</td>
				</tr>
				<tr>
					<td>
						<label>新闻类别:</label>
					</td>
					<td>
						<select id="typeId" name="typeId">
							<option value="">请选择新闻类别</option>
						 	<c:forEach  var="newsType" items="${newsTypeList}">
						 		<option value="${newsType.newsTypeId}">${newsType.typeName}</option>
						 	</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>新闻属性:</label>
					</td>
					<td>
						<label class="checkbox inline">
							<input type="checkbox" id="isHead" name="isHead" value="1">头条
						</label>
						<label class="checkbox inline">
						    <input type="checkbox" id="isImage" name="isImage" onclick="checkChange()" value="1">幻灯
						</label>
						<label class="checkbox inline">
							<input type="checkbox" id="isHot" name="isHot" value="1">热点
						</label>
					</td>
				</tr>
				<tr id="hdtp" style="display:none">
					<td>
						<label>幻灯图片：</label>
					</td>
					<td>
						<input type="file" id="picFile" name="picFile" />
					</td>
				</tr>
				<tr>
					<td valign="top">
						<label>新闻内容:</label>
					</td>
					<td>
						<textarea class="ckeditor" id="content" name="content"></textarea>
					</td>
				</tr>
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<input type="submit" class="btn btn-primary" value="保存新闻"/>&nbsp;&nbsp;
						<input type="button" class="btn btn-primary" value="返回" onclick="javascript:history.back()"/>
					</td>
				</tr>
			</table>
			
		</form>
	
	</div>
</div>
</body>
</html>