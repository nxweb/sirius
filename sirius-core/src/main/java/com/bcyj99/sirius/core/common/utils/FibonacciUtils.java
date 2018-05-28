package com.bcyj99.sirius.core.common.utils;

public class FibonacciUtils {
	
	public static void main(String[] args) {
		System.out.println(getFibonacci(24));
	}
	
	public static Long getFibonacci(int n){
		if(n<=0){
			return Long.valueOf(-1L);
		}
		
		Long result = Long.valueOf(0L);
		if(n==1 || n==2){
			result = Long.valueOf(1L);
		}else{
			result = getFibonacci(n-1)+getFibonacci(n-2);
		}
		return result;
	}
}
