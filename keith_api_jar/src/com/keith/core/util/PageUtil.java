package com.keith.core.util;

/**
 * 分页工具类
 * @author czyke
 * 2017年6月14日 11:08:10
 */
public class PageUtil {
   private int nowPage=1;
   private int countNum=10;
   private int startNum=0;
   public PageUtil(String page,String queryNum){
	   if(page!=null&&!page.isEmpty()){
		   nowPage=Integer.parseInt(page);
	   }
	   if(queryNum!=null&&!queryNum.isEmpty()){
		   countNum=Integer.parseInt(queryNum);
	   }
	   startNum=(nowPage-1)*countNum;
   }
   public int getStartNum(){
	   return startNum;
   }
   public int getCountNum(){
	   return countNum;
   }
}
