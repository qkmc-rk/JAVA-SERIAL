package org.cat.jni.SerialPort;

public class SerialPort {
	
	public native int[] getPorts();			//获取串口号
	
	public native void getHandle(int port);		//获得句柄
	
	public native void startPort(int _BaudRation, int _parity,int _stopBits,int _byteSize);		//开启串口
	
	public native String readPort();		//读取一次数据
	
	public native boolean sendDatatoP04();		//发送一次信号
	
	//加载动态库
	static{
		System.loadLibrary("Serial_Port");
	}
}
