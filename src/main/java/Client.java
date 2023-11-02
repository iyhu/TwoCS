import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        // 创建Socket并连接到服务器
        Socket socket = new Socket("localhost", 8081);
        System.out.println("已连接到服务器：" + socket.getInetAddress().getHostAddress());

        // 发送请求并接收响应
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        String request = "add_friends jone 1265";
        out.println(request);
        String response = in.readLine();
        System.out.println("响应：" + response);

        // 关闭连接
        socket.close();
        System.out.println("已断开与服务器的连接");
    }
}
