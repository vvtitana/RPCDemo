
public class SayHelloServiceImpl implements SayHelloService {

	@Override
	public String sayHello(String msg) {
		if("hello".equals(msg)) {
			return "hello client";
		} else {
			return "bye bye";
		}
	}

}
