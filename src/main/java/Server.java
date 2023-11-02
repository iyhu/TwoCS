import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private static Map<String, String> friends = new HashMap<>();

    public static void main(String[] args) throws IOException{
        // 启动服务器并监听端口
        // ...
        // 创建ServerSocket并监听端口
        ServerSocket serverSocket = new ServerSocket(8081);
        System.out.println("服务器已启动，正在监听端口 8080...");

        // 接受客户端连接并处理请求
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("客户端已连接：" + clientSocket.getInetAddress().getHostAddress());
            handleClient(clientSocket);
        }
    }

    private static void handleClient(Socket clientSocket) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String request = in.readLine();
        String response = handleRequest(request);
        out.println(response);

        clientSocket.close();
        System.out.println("客户端已断开连接：" + clientSocket.getInetAddress().getHostAddress());
    }

        // 处理客户端请求的逻辑
        /*String request = "get_friends";
        String response = handleRequest(request);
        System.out.println("处理客户端请求：" + request);
        System.out.println("响应给客户端：" + response);*/


    private static String handleRequest(String request) {
        String[] parts = request.split(" ");
        String command = parts[0];

        switch (command) {
            case "get_friends":
                return getfriends();
            case "add_friends":
                if (parts.length < 3) {
                    return "Invalid request";
                }
                String name = parts[1];
                String phone = parts[2];
                return addfriend(name, phone);
            case "remove_friends":
                if (parts.length < 2) {
                    return "Invalid request";
                }
                String removeName = parts[1];
                return removefriend(removeName);
            case "update_friends":
                if (parts.length < 3) {
                    return "Invalid request";
                }
                String updateName = parts[1];
                String updatePhone = parts[2];
                return updatefriend(updateName, updatePhone);
            default:
                return "Unknown command";
        }
    }

    private static String getfriends() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : friends.entrySet()) {
            sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
        }
        return sb.toString();
    }

    private static String addfriend(String name, String phone) {
        if (friends.containsKey(name)) {
            return "Friend already exists";
        }
        friends.put(name, phone);
        return "Friend added: " + name + ", " + phone;
    }

    private static String removefriend(String name) {
        if (!friends.containsKey(name)) {
            return "Friend not found";
        }
        friends.remove(name);
        return "Friend removed: " + name;
    }

    private static String updatefriend(String name, String phone) {
        if (!friends.containsKey(name)) {
            return "Friend not found";
        }
        friends.put(name, phone);
        return "Friend updated: " + name + ", " + phone;
    }
}
