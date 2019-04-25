package util._6net.HandServer.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

/**
 * 目标：封装响应信息
 * 1.内容可以动态添加
 * 2.关注状态吗，拼装好响应的协议信息
 * @author Cardiac
 *
 */
public class Response {
	//响应的数据流
	private BufferedWriter bw;
	//正文
	private StringBuilder content;
	//协议头（状态行与请求头 回车）信息
	private StringBuilder headInfo;
	private int len; //正文的字节数
	//内部
	private final String BLANK = " ";
	private final String CRLF = "\r\n";	//回车换行
	private Response() {
		content =new StringBuilder();
		headInfo=new StringBuilder();
		len =0;
	}
	public Response(Socket client) {
		this();
		try {
			bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
			headInfo = null;
		}
	}
	public Response(OutputStream os) {
		this();
		bw=new BufferedWriter(new OutputStreamWriter(os));
	}
	
	//动态添加内容-->用户看到的信息
	public Response print(String info) {
		content.append(info);
		len+=info.getBytes().length;
		return this;
	}
	public Response println(String info) {
		content.append(info).append(CRLF);
		len+=(info+CRLF).getBytes().length;
		return this;
	}
	//推送响应信息
	public void pushToBrower(int code) throws IOException {
		if(null ==headInfo) {
			code = 505;
		}
		createHeadInfo(code);
		bw.append(headInfo);
		bw.append(content);
		bw.flush();
	}
	/**
	 * 目标：构建头信息-->http协议所需的必要信息
	 *  1.状态行：HTTP/1.0 200 OK
		2.请求头：
		Date：Mon,31Dec209904:25:57GMT
		Server:hang Server/0.0.1;charset=GBK
		Content-type:text/html
		Content-length:39725423
		3.请求正文（注意与请求头之间有个空行）
		
		******
	 * @param code
	 */
	private void createHeadInfo(int code){
		//1、响应行: HTTP/1.1 200 OK
		headInfo.append("HTTP/1.1").append(BLANK);
		headInfo.append(code).append(BLANK);
		switch(code) {
			case 200:
				headInfo.append("OK").append(CRLF);
				break;
			case 404:
				headInfo.append("NOT FOUND").append(CRLF);
				break;	
			case 505:
				headInfo.append("SERVER ERROR").append(CRLF);
				break;	
		}
		//2、响应头(最后一行存在空行):
		headInfo.append("Date:").append(new Date()).append(CRLF);
		headInfo.append("Server:").append("hang Server/0.0.1;charset=GBK").append(CRLF);
		headInfo.append("Content-type:text/html").append(CRLF);
		headInfo.append("Content-length:").append(len).append(CRLF);
		headInfo.append(CRLF);	
	}
	
}
