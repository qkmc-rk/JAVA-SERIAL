package org.cat.test;

import static org.junit.Assert.fail;

import org.cat.jni.SerialPort.SerialPort;
import org.junit.Test;


public class TestNative {

	@Test
	public void test() {
		fail("Not yet implemented");
		
		
		
	}
	@Test
	public void m1(){
		SerialPort sp = new SerialPort();
		sp.getHandle(5);
		sp.startPort(4800, 0, 0, 8);
		boolean x= sp.sendDatatoP04();
		System.out.println(x);
	}
}
