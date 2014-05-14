package Newbee.p2p.upd.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.TimerTask;

public class P2PServerThread extends TimerTask {
	MainServer mainSrv;
	
	public  P2PServerThread (MainServer mainSrv){
		this.mainSrv = mainSrv;
		
	}
	
	@Override
	public void run() {
		
		if (!(mainSrv==null))
			try {
				mainSrv.StartP2PServiveChanege();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		if (!(mainSrv==null)) mainSrv.refreshConnectionClientInfo ();
		
		
	}
	
}
