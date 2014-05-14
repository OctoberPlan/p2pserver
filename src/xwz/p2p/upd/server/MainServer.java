package xwz.p2p.upd.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import xwz.p2p.upd.util.ConnectionClientInfo;
import xwz.p2p.upd.util.MyProtocol;
import xwz.p2p.upd.util.StringUtil;

public class MainServer {
	private static List<ConnectionClientInfo> allClients = new ArrayList<ConnectionClientInfo>();
	private static DatagramSocket ds = null; 
	private static P2PSrvFrame p2pFrame = new P2PSrvFrame();
	private static MainServer mainServer = new MainServer();
	private static Timer submitTimer = new Timer();
	
	public static void StartP2PServiveChanege() throws IOException {
		byte[] buf = new byte[1024];
		DatagramPacket p = new DatagramPacket(buf, 1024);

		ds.receive(p);

		String content = new String(p.getData(), 0, p.getLength());
		String ip = p.getAddress().getHostAddress();
		int port = p.getPort();

		if (!content.startsWith(MyProtocol.HEART)) {
			System.out.println(ip + ":" + port + " >>>> " + content);
		}

		if (content.startsWith(MyProtocol.LOGIN)) {
			dealLogin(ds, p, content);
		} else if (content.startsWith(MyProtocol.HEART)) {
			dealHeart(ds, p, content);
		} else if (content.startsWith(MyProtocol.WANT_TO_CONNECT)) {
			notifyPunchHole(ds, p, content);
		} else if (content.startsWith(MyProtocol.SUCCESS_HOLE_TO)) {
			notifyPunchHoleSuccess(ds, p, content);
		} else {
			dealOther(ds, p, content);
		}
	}

	private static void dealLogin(DatagramSocket ds, DatagramPacket p,String content) {
		ConnectionClientInfo c = new ConnectionClientInfo();

		String[] clientLogin = StringUtil.splitString(content,MyProtocol.SPLITOR);
		System.out.println("clientLogin " + clientLogin.length);
		c.setNickname(clientLogin[1]);
		c.setIp(p.getAddress().getHostAddress());		
		c.setPort(p.getPort());
		allClients.add(c);

		String listStr = MyProtocol.LIST_ONLINE + MyProtocol.SPLITOR + serialList();
		System.out.println(listStr);

		for (ConnectionClientInfo cif : allClients) {
			try {
				DatagramPacket p2 = new DatagramPacket(listStr.getBytes(), listStr.getBytes().length, InetAddress.getByName(cif.getIp()), cif.getPort());
				ds.send(p2);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private static String serialList() {
		String str = "";

		for (ConnectionClientInfo cif : allClients) {
			String nickname = cif.getNickname();
			String ip = cif.getIp();
			int port = cif.getPort();
			String one = ip + "," + port + "," + nickname + MyProtocol.SPLITOR;

			str += one;
		}

		return str;
	}

	private static void dealHeart(DatagramSocket ds, DatagramPacket p,String content) {
		
		
	}

	private static void notifyPunchHole(DatagramSocket ds, DatagramPacket p,String content) throws IOException {
		String[] clientInfo = StringUtil.splitString(content,MyProtocol.SPLITOR);

		String ip = clientInfo[1];
		int port = Integer.parseInt(clientInfo[2]);
		String nickname = clientInfo[3];

		System.out.println(ip + port + nickname);

		String punchToIp = p.getAddress().getHostAddress();
		int punchToPort = p.getPort();

		String send = MyProtocol.PUNCH_HOLE_TO + MyProtocol.SPLITOR + punchToIp + MyProtocol.SPLITOR + punchToPort + MyProtocol.SPLITOR + nickname;
		System.out.println(send);

		DatagramPacket p2 = new DatagramPacket(send.getBytes(),send.getBytes().length, InetAddress.getByName(ip), port);

		ds.send(p2);

	}

	private static void notifyPunchHoleSuccess(DatagramSocket ds,DatagramPacket p, String content) throws IOException {

		String[] clientInfo = StringUtil.splitString(content,MyProtocol.SPLITOR);

		String ip = clientInfo[1];
		int port = Integer.parseInt(clientInfo[2]);
		String nickname = clientInfo[3];

		String send = MyProtocol.CAN_P2P_TO + MyProtocol.SPLITOR + p.getAddress().getHostAddress() + MyProtocol.SPLITOR + p.getPort() + MyProtocol.SPLITOR + nickname;

		DatagramPacket p2 = new DatagramPacket(send.getBytes(),send.getBytes().length, InetAddress.getByName(ip), port);

		ds.send(p2);

	}

	private static void dealOther(DatagramSocket ds, DatagramPacket p,String content) {
	}

	public static void main(String[] args) throws IOException {
		if (args.length>0){
			startP2PServer(Integer.parseInt(args[0]));
		}else {
			p2pFrame.setSize(600,400);
			p2pFrame.setVisible(true);
		}
		
	}
	
	
	public static void startP2PServer(int port) throws SocketException{
		ds = new DatagramSocket(port);
		
		submitTimer.schedule(new P2PServerThread(mainServer), 1000, 1000);
	}
	public static void stopP2PServer(){
		submitTimer.cancel();
		if (!(ds==null)) ds.close();
		allClients.clear();
	}
	
 
	public static List<ConnectionClientInfo> getConnectionClientInfo(){
		return allClients; 
	}
	public static void refreshConnectionClientInfo(){
		p2pFrame.getConnectionClientInfo ();
		
	}
	
}
