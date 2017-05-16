import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Provider {

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(1234);
		Map services = new HashMap();
		services.put(SayHelloService.class, new SayHelloServiceImpl());
		while(true){
			System.out.println("服務提供者啟動，等待客戶端調用…………");
			Socket socket = serverSocket.accept();
	
			//收到消息后进行解码
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			String interfaceName = objectInputStream.readUTF();
			System.out.println("interfaceName："+interfaceName);
			String methodName = objectInputStream.readUTF();
			System.out.println("methodName："+methodName);
			Class[] paramterTypes = (Class[])objectInputStream.readObject();
			Object[] argments = (Object[])objectInputStream.readObject();
			System.out.println("客戶端調用服務端接口"+interfaceName+"的"+ methodName+"方法");
	
			//根据解码结果调用本地的服务
			Class serviceClass = Class.forName(interfaceName);
			Object serivce = services.get(serviceClass);
			Method method = serviceClass.getMethod(methodName, paramterTypes);
			Object result = method.invoke(serivce, argments);
	
			//服务提供者发送result给服务调用者
			ObjectOutputStream stream = new ObjectOutputStream(socket.getOutputStream());
			stream.writeObject(result);
			System.out.println("服務端返回結果為"+result);
		}
	}

}
