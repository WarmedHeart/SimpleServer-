package util._6net.HandServer.user;

import util._6net.HandServer.server.Request;
import util._6net.HandServer.server.Response;
import util._6net.HandServer.server.Servlet;
/**
 * 可以发现java处理html代码太麻烦，之后应jsp
 * @author Cardiac
 *
 */
public class LoginServlet implements Servlet{

	@Override
	public void service(Request request, Response response) {
		response.print("<html>"); 
		response.print("<head>"); 
		//告知浏览器接下来的内容是什么字符集，防止返回的中文数据出现乱码
		response.print("<meta http-equiv=\"content-type\" content=\"text/html;charset=utf-8\">" ); 
		response.print("<title>");
		response.print("第一个servlet");
		response.print("</title>");
		response.print("</head>");
		response.print("<body>");
		response.print("欢迎回来:"+request.getParameter("uname"));
		response.print("</body>");
		response.print("</html>");
		
	}

}
