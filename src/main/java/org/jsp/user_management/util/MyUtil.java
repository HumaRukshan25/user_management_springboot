package org.jsp.user_management.util;

public class MyUtil {// ctrl+1
//	public static void main(String[] args) {
//		for(int i=1;i<=100;i++) {
//			System.out.println(getOtp());
//		}
//	
//	}

	public static int getOtp() {
		double otp=0;
		
		while(otp<1000) {
			double random = Math.random();
			
			otp=random*10000;
		}
			return (int)otp;
			}
}
