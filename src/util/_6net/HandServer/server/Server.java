package util._6net.HandServer.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import util._6net.HandServer.user.LoginServlet;

/**
 * 1.创建ServerSocket
 * 2.建立连接获取Socket
 * 3.通过输入流获取请求协议Get/Post
 * @author Cardiac
 *
 */
public class Server {
	private ServerSocket serverSocket;
	//只要服务器启动就一直while执行的标志
	private boolean isRunning;
	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
	//启动服务
	public void start() {
		try {
			serverSocket = new ServerSocket(7777);
			isRunning = true;
			receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//接收连接并处理
	public void receive() {
		while(isRunning) {
			try {
				Socket client = serverSocket.accept();
				System.out.println("一个客户建立了连接。。。。");
				//多线程
				new Thread(new Dispatcher(client)).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	//停止服务
	public void stop() {
		try {
			this.serverSocket.close();
			isRunning = false;
			System.out.println("服务器已停止。。。。");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
