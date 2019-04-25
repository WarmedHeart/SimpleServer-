package util._6net.HandServer.server;
/**
 * 服务器小脚本接口，进一步的封装，开发人员只需写给用户响应的信息。
 * Servlet将业务代码解耦到对应的业务类中(具体的Servlet())
 * @author Cardiac
 *
 */
public interface Servlet {
	void service(Request request,Response response);
}
