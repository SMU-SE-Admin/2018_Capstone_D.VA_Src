package com.dva.app_project.internet;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SendRecvString implements Runnable {
    private String ip;
    private int port;
    private String msg;
    private String result;
    Handler han = null;


    public SendRecvString(String ip, int port, String msg) {
        this.ip = ip;
        this.port = port;
        this.msg = msg + ":::";
    }

    public SendRecvString(String ip, int port, String msg, Handler han){
        this.ip = ip;
        this.port = port;
        this.msg = msg+":::";
        this.han = han;
    }

    public void run() {
        if(han == null) {
            try {
                Socket socket = new Socket(this.ip, this.port);
                System.out.println("socket create");

                OutputStream os = socket.getOutputStream();
                os.write(msg.getBytes());
                os.flush();
                System.out.println("send msg");

                InputStream is = socket.getInputStream();
                byte[] msg_buf = new byte[1024];
                is.read(msg_buf);
                System.out.println("recv msg");

                result = new String(msg_buf).split(":::")[0];
                System.out.println(result);

                os.close();
                socket.close();
                System.out.println("thread end");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                Socket socket = new Socket(this.ip, this.port);
                System.out.println("socket create");

                OutputStream os = socket.getOutputStream();
                os.write(msg.getBytes());
                os.flush();
                System.out.println("send msg");

                InputStream is = socket.getInputStream();
                byte[] msg_buf = new byte[1024];
                is.read(msg_buf);
                System.out.println("recv msg");

                result = new String(msg_buf).split(":::")[0];
                System.out.println(result);

                Bundle bundle = new Bundle();
                bundle.putString("data", result);
                Message hmsg = han.obtainMessage();
                hmsg.setData(bundle);
                han.sendMessage(hmsg);

                os.close();
                socket.close();
                System.out.println("thread end");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getResult() {
        return result;
    }
}
