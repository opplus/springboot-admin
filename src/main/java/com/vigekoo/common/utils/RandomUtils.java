/**
 * 
 */
package com.vigekoo.common.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**  
* @Description: TODO(随机数工具类)
* @author oplus
* @date 2016年10月10日 上午8:50:13
*/
public class RandomUtils {

	public static String randomKey(String startaKey, String endKey){
		String simpleDate=new SimpleDateFormat("yyMMddHHmmss").format(new Date());
		String result=simpleDate+randomSixNum();
		if(StringUtils.isNotBlank(startaKey)){
			result=startaKey+result;
		}
		if(StringUtils.isNotBlank(endKey)){
			result=result+endKey;
		}
		return result;
	}
	
	public static Integer randomKey(int count){
		Random random=new Random();
		Integer result=random.nextInt(count);
		return result+1;
	}

	/**
	 * 随机生成六位随机数
	 * @return
	 */
	public static int randomSixNum(){
		int randomNum=(int)((Math.random()*9+1)*100000);
		return randomNum;
	}

}
