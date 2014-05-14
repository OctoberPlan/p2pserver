/**
 * MyProtocol.java Nov 25, 2009
 * 
 * Copyright 2009 xwz, Inc. All rights reserved.
 */
package Newbee.p2p.upd.util;

/**
 * ָ��һЩЭ��(��ʵӦ��ʹ��byte��,Ϊ�˼���ʹ��String)
 * 
 * @author xwz
 * @version 1.0, Nov 25, 2009 10:02:53 PM
 */
public class MyProtocol {
	/*
	 * ˵��: (C)��ʾ��Client���ʹ����� (S)��ʾ��Server���ʹ����� <C>��ʾ��Clientֱ�ӵ�ͨ�� 
	 * ()��ʾC/S֮���ͨ��
	 * <>��ʾC֮���ͨ�� []
	 * 
	 * xxx:�����ӵĶ�,��ɴ���yyy�Ķ� yyy:���P2P���ӵĶ�
	 */

	// (C) �ͻ��˵�һ�ε�½ʱ,���ʹ�����
	// LOGIN|nickname
	public static final String LOGIN = "LOGIN";

	// (S) ���߿ͻ����б�
	// LIST_ONLINE|ip1,port1,name1|ip2,port2,name2
	public static final String LIST_ONLINE = "LIST_ONLINE";

	// (C) ����
	// HEART|nickname
	public static final String HEART = "HEART";

	// (C) ��Ҫ����xxx,�����xxx��һ�����ҵĶ�
	// WANT_TO_CONNECT|ip|port|nickname
	public static final String WANT_TO_CONNECT = "WANT_TO_CONNECT";

	// (S) ���һ����yyy�Ķ�
	// PUNCH_HOLE_TO|ip|port|nickname
	public static final String PUNCH_HOLE_TO = "PUNCH_HOLE_TO";

	// SUCCESS_HOLE_TO|ip|port|nickname
	public static final String SUCCESS_HOLE_TO = "SUCCESS_HOLE_TO";

	// CAN_P2P_TO|ip|port|nickname
	public static final String CAN_P2P_TO = "CAN_P2P_TO";

	// HELLO_MYP2P_FRIEND|nickname
	public static final String HELLO_P2P_FRIEND = "HELLO_MYP2P_FRIEND";

	// P2P_MESSAGE|msg's content
	public static final String P2P_MESSAGE = "P2P_MESSAGE";

	public static final String SPLITOR = "|";
}
