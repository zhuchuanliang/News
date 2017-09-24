package com.util;
//新闻上方的导航栏
public class NavUtil {
	
	public static String genNewsListNacigation(String typeName,String typeId){
		StringBuffer navCode=new StringBuffer();
		navCode.append("当前位置:&nbsp;&nbsp");
		navCode.append("<a href='goIndex'>主页</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='news?action=list&typeId="+typeId+"'>"+typeName+"</a>");
		return navCode.toString();
	}
	
	public static String genNewsNavigation(String typeName,String typeId,String newsName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("当前位置:&nbsp;&nbsp");
		navCode.append("<a href='goIndex'>主页</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='news?action=show&typeId="+typeId+"'>"+typeName+"</a>&nbsp;&nbsp;&nbsp;"+newsName);
		return navCode.toString();
	}
	
	public static String genNewsManageNavigation(String modName,String actionName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("当前位置:&nbsp;&nbsp;");
		navCode.append("主页&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(modName+"&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(actionName+"&nbsp;&nbsp;");
		return navCode.toString();
	}

}
