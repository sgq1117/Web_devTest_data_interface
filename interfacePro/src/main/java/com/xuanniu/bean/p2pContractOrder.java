package com.xuanniu.bean;

import com.db.trade.P2p_contract_order;

/**
 * @author sungq
 * @version 创建时间：2015年5月16日 下午3:01:04 类说明
 */

public class p2pContractOrder {
	P2p_contract_order pco = new P2p_contract_order();
	
	public void getRelateInfo(String p2p_contract_id){
		String p2p_order_id = pco.getP2p_order_id(p2p_contract_id);
		String act_p2p_contract_id = pco.getP2p_contract_id(p2p_contract_id);
		String p2p_order_sn = pco.getP2p_order_sn(p2p_contract_id);
		
		System.out.print("p2p_order_id: "+p2p_order_id+"\t");
		System.out.print("act_p2p_contract_id: "+act_p2p_contract_id+"\t");
		System.out.print("p2p_order_sn: "+p2p_order_sn);
	}
	
	public static void main(String[] args){
		p2pContractOrder pco = new p2pContractOrder();
		pco.getRelateInfo("394");
		
	}
}
