package com.adventofcode.flashk.common;

public final class MathUtil {

	private MathUtil() {}
	
	/**
	 * Sums a number N times from i = number to i = 1. 
	 * <p>
	 * Example:
	 * </p>
	 * <pre>
	 * summation(0) = 0
	 * summation(1) = 1
	 * summation(2) = 2+1
	 * summation(3) = 3+2+1
	 * summation(n) = n+(n-1)+(n-2)+...+1
	 * </pre>
	 * @param value the number to apply summation
	 * @return the number summation. It will be 0 if input parameter is zero.
	 */
	public static long summation(long value) {
		
		if(value == 0) {
			return 0;
		}
		
		if(value == 1) {
			return 1;
		}
		
		return value + summation(value-1);
	}
}
