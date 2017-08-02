package com.admin.exe.shop.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Release {
	public static void release(Socket socket) {
		release(null, socket, null, null);
	}
	public static void release (Socket socket,BufferedWriter bw){
		release(null, socket, bw, null);
	}
	public static void release(Socket socket, BufferedReader br) {
		release(null, socket, null, br);
	}

	public static void release(ServerSocket server) {
		release(server, null, null, null);
	}

	public static void release(ServerSocket server, Socket socket, BufferedWriter bw, BufferedReader br) {
		if (server != null) {
			try {
				server.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if (bw != null) {
			try {
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
