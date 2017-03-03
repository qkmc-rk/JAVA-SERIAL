# include <iostream>

# include "org_cat_jni_SerialPort_SerialPort.h"

# include "serialport.h"

using namespace std;

//函数声明
jstring stoJstring(JNIEnv* env, const char* pat);
int portNums;		//记录串口号码,多个函数会调用

/*
*变量:		serialport
*类型:		全局变量
*作用:		调用内部方法
*/
SerialPort sp;


/**************************************************************************************************/

/*
* Class:     org_cat_jni_SerialPort_SerialPort
* Method:    getPorts
* Signature: ()[I
*/
JNIEXPORT jintArray JNICALL Java_org_cat_jni_SerialPort_SerialPort_getPorts
(JNIEnv * env, jobject obj)
{
	sp.getSerialPortNames();		//首先获取串口号

	int i = 0;

	int index;    //用于找串口号数的索引变量
	int size = sp.portCount;	//串口个数

	CString portName;		//记录串口名
	CString s = "M";		//划分地址

	jintArray n = env->NewIntArray(size);	//记录串口号数的数组

	jint *h = env->GetIntArrayElements(n, NULL);	//指针

	CString *portNames = sp.serialPortNameList;		//指针

	while (size--)
	{
		portName = sp.serialPortNameList[i];
		index = portName.Find(s);
		h[i] = _ttoi(portName.Right(index - 1));
		i++;
	}
	env->ReleaseIntArrayElements(n, h, 0);	//释放资源
	return n;
}

/*
* Class:     org_cat_jni_SerialPort_SerialPort
* Method:    getHandle
* Signature: (I)V
*/
JNIEXPORT void JNICALL Java_org_cat_jni_SerialPort_SerialPort_getHandle
(JNIEnv * env, jobject obj, jint _jint)
{
	//cout << "no do" << endl;
	//CString spNum;
	int num = _jint + 48;
	char spNum[5] = { 'C','O','M',(char)num };
	portNums = _jint;		//保存数据,以后使用
	BOOL isSuccess = sp.getSerialPortResource(spNum);

	if (isSuccess)	cout << "成功获取串口资源" << endl;
	else
	{
		cout << "获取串口资源失败" << endl;
	}
}

/*
* Class:     org_cat_jni_SerialPort_SerialPort
* Method:    startPort
* Signature: (IIII)V
*/
JNIEXPORT void JNICALL Java_org_cat_jni_SerialPort_SerialPort_startPort
(JNIEnv *, jobject, jint bdrx, jint _parity, jint _stopBits, jint _byteSize)
{
	int baudRatio = bdrx;
	int parit = _parity;
	int stops = _stopBits;
	int bytes = _byteSize;
	cout << "port identify:" << bdrx << "," << parit<<"," << stops<<"," << bytes << endl;
	//cout << "no do" << endl;
	char spNum[5] = { 'C','O','M',(char)(portNums + 48) };
	//cout << "串口号"<<spNum << endl;
	sp.StartSerialPort(spNum, bdrx, _parity, _stopBits, _byteSize);

	wcout << sp.errorInfo.GetString() << endl;
}

/*
* Class:     org_cat_jni_SerialPort_SerialPort
* Method:    readPort
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_org_cat_jni_SerialPort_SerialPort_readPort
(JNIEnv * env, jobject job)
{
	char *info;
	info = sp.ReadSerialPort();
	//wcout << info.GetString() << endl;
	//size_t len = wcslen(info) + 1;
	//size_t converted = 0;
	////char *host_;
	//host_ = (char*)malloc(len * sizeof(char));
	//wcstombs_s(&converted, host_, len, info, _TRUNCATE);
	//cout << "no do" << endl;
	//int m = portNums+48;
	//char ms;
	//ms = (char)m;
	//char z[5] = {'C','O','M',ms};
	//char *x = z;
	//cout << host_[1] << endl;

	//cout << "fuc you 2" << info << endl;

	jstring y = stoJstring(env, info);
	//cout << y [0]<< endl;
	return y;
}

/*
* Class:     org_cat_jni_SerialPort_SerialPort
* Method:    sendDatatoP04
* Signature: ()Z
*/
JNIEXPORT jboolean JNICALL Java_org_cat_jni_SerialPort_SerialPort_sendDatatoP04
(JNIEnv * env, jobject obj)
{
	cout << "fucking  you" << endl;
	jboolean r = 1;
	char *data = "1";
	if (sp.WriteSerialPort(data)) 
	{
		cout << "fucking succeed" << endl;
		return r;
	}
	cout << "fucking faild" << endl;
	return !r;
	
}

//百度得来
jstring stoJstring(JNIEnv* env, const char* pat)
{
	jclass strClass = env->FindClass("Ljava/lang/String;");
	jmethodID ctorID = env->GetMethodID(strClass, "<init>", "([BLjava/lang/String;)V");
	jbyteArray bytes = env->NewByteArray(strlen(pat));
	env->SetByteArrayRegion(bytes, 0, strlen(pat), (jbyte*)pat);
	jstring encoding = env->NewStringUTF("utf-8");
	return (jstring)env->NewObject(strClass, ctorID, bytes, encoding);
}