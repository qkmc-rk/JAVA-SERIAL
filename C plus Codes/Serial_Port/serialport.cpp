
//#include "stdafx.h"
#include "serialport.h"

#define PORTLISTBUFFERSIZE 8		//定义最多8个串口
#define MAX_LENGTH 256
#define LPDATABUFFER 25
#define BUFFERSIZE 20

char serialContent[20] = "";

//SerialPort serialHandle;	//类

	//获取串口名
	LONG SerialPort::getSerialPortNames() {
		LPCTSTR lpSubKey =L"HARDWARE\\DEVICEMAP\\SERIALCOMM\\";
		HKEY hKey;
		CString portNameListTemp[PORTLISTBUFFERSIZE];
		long result = RegOpenKeyEx(HKEY_LOCAL_MACHINE, lpSubKey, 0, KEY_READ, &hKey);
		if (result == ERROR_SUCCESS) {
			long status;
			wchar_t serialPortValueName[MAX_LENGTH];
			DWORD serialPortValueNameSize;
			DWORD lpcbData;
			DWORD dwIndex = 0;
			unsigned char lpData[LPDATABUFFER];
			serialPortValueNameSize = sizeof(serialPortValueName);
			lpcbData = sizeof(lpData);
			do {
				status = RegEnumValue(hKey, dwIndex, serialPortValueName, &serialPortValueNameSize, NULL, NULL, lpData, &lpcbData);
				if (status == ERROR_SUCCESS) {
					for (int i = 1; i < lpcbData / 2; i++)
					{
						lpData[i] = lpData[i * 2];
					}
					portNameListTemp[dwIndex] = CString(lpData);
					dwIndex++;
				}

				serialPortValueNameSize = sizeof(serialPortValueName);
				lpcbData = sizeof(lpData);

			} while (!status);

			RegCloseKey(hKey);
			portCount = dwIndex;
			if (!portCount)
			{
				errorInfo = L"NO SERIAL PORT AVALIBLE";
				return 2;
			}
			this->serialPortNameList = nullptr;
			this->serialPortNameList = new CString[portCount];
			for (int temp = 0; temp<portCount; temp++)
			{
				this->serialPortNameList[temp] = portNameListTemp[temp];
			}
			return ERROR_SUCCESS;
		}
		else {
			errorInfo = L"READ SERIAL PORT FAILED";
			//TO DO FormatMessage();
		}
		return 1;
	}

	//获取串口句柄
	BOOL SerialPort::getSerialPortResource(CString serialPortNumber) {
		CloseHandle(hCom);
		this->hCom = CreateFile(serialPortNumber,//COM口
			GENERIC_READ | GENERIC_WRITE, //允许读和写
			0, //独占方式
			NULL,
			OPEN_EXISTING, //打开而不是创建
			0, //同步方式FILE_FLAG_OVERLAPPED
			NULL);
		if (this->hCom == INVALID_HANDLE_VALUE)
		{
			return FALSE;
		}
		return TRUE;
	}

	//连接串口,建立通信
	void SerialPort::StartSerialPort(CString serialPortName, int baudRate, int parity, int stopBits, int byteSize) {
			 if (this->getSerialPortResource(serialPortName)) {

				SetupComm(hCom, 1024, 1024); //输入缓冲区和输出缓冲区的大小都是1024 字节

				COMMTIMEOUTS TimeOuts;     //串口超时

				TimeOuts.ReadIntervalTimeout = 20;

				TimeOuts.ReadTotalTimeoutMultiplier = 0;
				TimeOuts.ReadTotalTimeoutConstant = 0;

				//设定写超时
				TimeOuts.WriteTotalTimeoutMultiplier = 0;
				TimeOuts.WriteTotalTimeoutConstant = 0;

				SetCommTimeouts(hCom, &TimeOuts); //设置超时
				GetCommState(hCom, &dcb);
				dcb.BaudRate = baudRate; //波特率为115200
				dcb.ByteSize = byteSize; //每个字节有8位
				dcb.Parity = parity; //无奇偶校验位
				dcb.StopBits = stopBits; //一个停止位
				SetCommState(hCom, &dcb);	
				errorInfo = L"open hcom succeed";
			}
			 else {
				 errorInfo = L"open hcom failed";
			 }
	}

	char* SerialPort::ReadSerialPort() {
		CString data;
		DWORD wCount;//读取的字节数
		COMSTAT ComStat;
		DWORD dwErrorFlags;
		ClearCommError(hCom, &dwErrorFlags, &ComStat);
		PurgeComm(hCom, PURGE_TXCLEAR | PURGE_RXCLEAR); //清空缓冲区
		if (ReadFile(hCom, &serialContent, sizeof(serialContent), &wCount, NULL))
		{
		//tempurature = (int)serialContent[9];
		//wet= (int)serialContent[8];
		//data.Format(_T("当前  温度：%d℃  湿度：%d%%"), tempurature, wet);
		//cout << "fuck you1---"<<serialContent << endl;
		return serialContent;
	}
			else
			{
				char* error = "read serial port failed!";
				return error;
			}
	}
	//向串口写入数据
	bool SerialPort::WriteSerialPort(char*& data) {
		DWORD wCount; //实际读取的字节数
		DWORD wToCount = 256; //要读取的字节数
		COMSTAT ComStat;
		DWORD dwErrorFlags;
		ClearCommError(hCom, &dwErrorFlags, &ComStat);
		PurgeComm(hCom, PURGE_TXCLEAR | PURGE_RXCLEAR); //清空缓冲区
		if (WriteFile(hCom, &data, strlen(data), &wCount, NULL))
			return true;
		return false;
	}

	SerialPort::SerialPort() {
		hCom = NULL;
		portCount = 0;
		serialPortNameList = NULL;
		this->blockFlags = true;
		endflags = false;
		tempurature = 0;
		wet = 0;
	}
	SerialPort::~SerialPort() {
		if (serialPortNameList) {
			delete[] serialPortNameList;
		}
		CloseHandle(hCom);
	}