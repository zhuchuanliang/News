package com.util;
//�����Ϸ��ĵ�����
public class NavUtil {
	
	public static String genNewsListNacigation(String typeName,String typeId){
		StringBuffer navCode=new StringBuffer();
		navCode.append("��ǰλ��:&nbsp;&nbsp");
		navCode.append("<a href='goIndex'>��ҳ</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='news?action=list&typeId="+typeId+"'>"+typeName+"</a>");
		return navCode.toString();
	}
	
	public static String genNewsNavigation(String typeName,String typeId,String newsName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("��ǰλ��:&nbsp;&nbsp");
		navCode.append("<a href='goIndex'>��ҳ</a>&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append("<a href='news?action=show&typeId="+typeId+"'>"+typeName+"</a>&nbsp;&nbsp;&nbsp;"+newsName);
		return navCode.toString();
	}
	
	public static String genNewsManageNavigation(String modName,String actionName){
		StringBuffer navCode=new StringBuffer();
		navCode.append("��ǰλ��:&nbsp;&nbsp;");
		navCode.append("��ҳ&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(modName+"&nbsp;&nbsp;>&nbsp;&nbsp;");
		navCode.append(actionName+"&nbsp;&nbsp;");
		return navCode.toString();
	}

}
