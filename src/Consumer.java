import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class Consumer {

	public static void main(String[] args) throws Exception {
		String interfaceName = SayHelloService.class.getName();
		Method method = SayHelloService.class.getMethod("sayHello", java.lang.String.class);
		Object[] argments = {"hello"};
		
		Socket socket = new Socket("127.0.0.1", 1234);
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		outputStream.writeUTF(interfaceName);
		outputStream.writeUTF(method.getName());
		outputStream.writeObject(method.getParameterTypes());
		outputStream.writeObject(argments);
		System.out.println("發送信息到服務端，發送的信息為："+argments[0]);
		
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
		Object object = inputStream.readObject();
		System.out.println("服務返回的結果為" + object);
	}

}
