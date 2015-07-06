package org.whatsoftwarecando.legespiel;

public class Util {

	public static double nanosToMilliseconds(long nanos){
		return Math.round(nanos / 100000.0) / 10.0;
	}
	
	public static void main(String[] argv){
		System.out.println(nanosToMilliseconds(1500000));
	}
}
