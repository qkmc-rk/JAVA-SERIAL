#include <Windows.h>
#include <stdio.h>
#include<iostream>
#include<string>
#include <sstream>
#include <time.h>
#include "atlstr.h"

using namespace std;

#define DWINQUEUE 1024
#define DWOUTQUEUE 1024
#define READINTERVALTIMEOUT 1000
#define READTOTALTIEMOUTMULTIPLIER  500
#define READTOTALTIMEOUTCONSTANT 500
#define WRITETOTALTIMEOUTMULTIPLIER 500
#define WRITETOTALTIMEOUTCONSTANT 1000
#define TIMER 5000
//#define TIMEOUT

class SerialPort {

public:
	LONG getSerialPortNames();	//获得所有串口
	BOOL getSerialPortResource(CString serialPortNumber);	//获得句柄
	void StartSerialPort(CString serialPortName, int baudRate, int parity, int stopBits, int byteSize);		//连接串口,建立通信
	char* ReadSerialPort();		//从串口读取信息

	bool WriteSerialPort(char*& data);	//发送数据给串口

	SerialPort();	//构造函数
	~SerialPort();		//析构函数

	HANDLE hCom;	//句柄
	CString *serialPortNameList;	//串口号列表
	DCB dcb;		//串口参数设置参数
	int portCount;		//串口数量
	CString errorInfo;	//错误信息
	BOOL serialStates;		//串口状态
	BOOL blockFlags;	
	bool endflags;	//结束标志
	CString str;	
	int tempurature	;	//温度
	int wet ;	//湿度
};