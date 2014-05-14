/**
 * HeartThread.java Nov 25, 2009
 * 
 * Copyright 2009 xwz, Inc. All rights reserved.
 */
package xwz.p2p.upd.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.TimerTask;

/**
 * 发送心跳包线程
 * 
 * @author xwz
 * @version 1.0, Nov 25, 2009 11:33:59 PM
 */
public class P2PServerThread extends TimerTask {
	MainServer mainSrv;
	
	public  P2PServerThread (MainServer mainSrv){
		this.mainSrv = mainSrv;
		
	}
	
	@Override
	public void run() {
		//启动P2P服务线程
		
		if (!(mainSrv==null))
			try {
				mainSrv.StartP2PServiveChanege();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		//刷新用户
		if (!(mainSrv==null)) mainSrv.refreshConnectionClientInfo ();
		
		
	}
	
}
