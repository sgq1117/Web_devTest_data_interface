package com.xuanniu.http.zhifu;

/** 
 * @author sungq 
 * @version 创建时间：2015年6月2日 下午7:05:11 
 * 类说明 
 */

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class HttpZhifuParamTest {
	HttpZhifuPost zfp = new HttpZhifuPost();
	
	private String bankName;
	private String frpId;
	private String bankId;

	public HttpZhifuParamTest(String bankName,String frpId, String bankId) {
		this.bankName = bankName;
		this.frpId = frpId;
		this.bankId = bankId;
	}

	@Parameters
	public static Collection prepareData() {
		Object[][] object = {{"01、工商银行ICBC","001", "84"},
							{"02、招商银行CMB","002", "85"}, 
							{"03、建设银行CCB","003", "86"},
							{"04、中国银行BOC","004", "87"},
							{"05、农业银行ABC","005", "88"},
							{"06、交通银行BCN","006", "89"},
							{"07、浦发银行SPDB","007", "90"},
							{"08、广发银行CGB","008", "91"},
							{"09、中信银行CICTC","009", "92"},
							{"10、光大银行CEB","010", "93"},
							{"11、兴业银行CIB","011", "94"},
							{"12、平安银 行PAB","012", "95"},
							{"13、民生银 行CMBC","013", "96"},
							{"14、华夏银 行HXB","014", "97"},
							{"15、邮储银行PSBC","020", "98"}};  
        return Arrays.asList(object);  
	}

	@Test
	public void ZhifuParamTest() {
		System.out.println(bankName);
		zfp.zhifuPost("40", "7", "", "0.01", "", frpId, bankId);
	}

}
