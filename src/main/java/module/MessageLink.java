//多线程实现不间断通信
package module;

import java.util.Scanner;
import instance.*;

public class MessageLink {

    Client client;
    Scanner sc;
    public MessageLink(Client client, Scanner sc) {
        this.client = client;
        this.sc = sc;
    }

    public void run() {
        Listen listenThread = new Listen();
        listenThread.start();
        send();
    }

    private void send() {
        try {
            System.out.println("你现在可以发消息了：");
            client.initSend();
            while (true) {
                String message = sc.next();
                client.sendMessage(message);
                if (message.equals("exit")) {
                    break;
                }
            }
            client.endSend();
        } catch (Exception e) {
            System.out.println("Send Error!");
            e.printStackTrace();
        }
    }

    private class Listen extends Thread {

        @Override
        public void run() {
            try {
                client.initReceive();
                while (true) {
                    String message = client.receiveMessage();
                    if (message == null) {
                        break;
                    }
                    if (message.equals("exit")) {
                        System.out.println("远端结束了连接。");
                        break;
                    }
                    System.out.println("来自" + client.getIP() + "的消息：" + message);
                }
                client.endReceive();
            }
         catch (Exception e) {
            System.out.println("Listen Error!");
            e.printStackTrace();
        }
    }
}

}
