package com.xuanniu.bean;

import xuanniuSgq.Xuanniu_db;

import com.db.trade.P2p_contract;
import com.xuanniu.util.CalcNum;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 下午2:02:49 类说明
 */

public class P2pContractBean {
	P2p_contract p2pc = new P2p_contract();
	CalcNum cn = new CalcNum();
	Xuanniu_db xdb = new Xuanniu_db();

	public void getRelateInfo(String financing_contract_id ) {
		String Financing_contract_id = p2pc.getFinancing_contract_id(financing_contract_id);
		String P2p_contract_sn = p2pc.getP2p_contract_sn(financing_contract_id);
		String Financing_account_id = p2pc.getFinancing_account_id(financing_contract_id);
		String Prod_desc = p2pc.getProd_desc(financing_contract_id);
		String Raise_status = p2pc.getRaise_status(financing_contract_id);
		

		System.out.print("Financing_contract_id: "+Financing_contract_id+"\t");
		System.out.print("P2p_contract_sn: "+P2p_contract_sn+"\t");
		System.out.print("Financing_account_id: "+Financing_account_id+"\t");
		System.out.print("Prod_desc: "+Prod_desc+"\t");
		System.out.print("Raise_status: "+Raise_status+"\t");
	}

	public static void main(String[] args) {
		P2pContractBean pcb = new P2pContractBean();
		pcb.getRelateInfo("504");
	}
}
