package test;
//Finding a root or an exponent of a BigDecimal using Newton's Method

import java.math.*;

public class NewtonsMethod{
	
	//---------------------------------------------------------------VARIANTS OF BDexp----------------------------------------------------------------------------------------------------
	
	public static BigDecimal BDexp(BigDecimal base, BigDecimal expD) {
		BigDecimal num = base;
		BigInteger expI = new BigInteger((expD.toBigInteger()).toString()); //Converts expD to integer (Drops the decimal) then changes it to a string so expI can have the integer value
		
		for(BigInteger i = new BigInteger("1"); i.compareTo(expI) == -1; i = i.add(BigInteger.ONE)) {
			num = num.multiply(base);
			System.out.println(num); //<-for debuging purposes
		}
		
		//preparing numbers for the root portion of the calculation
		BigInteger rootB = //got from StackOverflow   this takes the decimal part of expD
                expD.remainder(BigDecimal.ONE).movePointRight(expD.scale()).abs().toBigInteger();
		long scale = 10^expD.scale(); 
		long root = rootB.intValue();
		BigDecimal rootBD = new BigDecimal(rootB.toString());
		

		//Math derived from x^(a/b) = xroot(x,b) * x^a
		if(root!=0) {
			num = (BDexp(num, root)).multiply(BDroot(num, scale, num.divide(rootBD,MathContext.DECIMAL128)));
		}
		
		return num;
	}
	
	
	public static  BigDecimal BDexp(BigDecimal base, long exp) {//is equivilant to bd.pow(long) (long instead of int)
		
		BigDecimal num = base;
		
		for(long i = 1; i < exp; i++) {
			num = num.multiply(base);
		}
		
		return num;
	}
	
	
	public static  BigDecimal BDexp(BigDecimal base, int exp) {//is equivilant to bd.pow(int)
	
		BigDecimal num = base;
	
		for(int i = 1; i < exp; i++) {
			num = num.multiply(base);
		}
	
		return num;
	}
	
	
	//--------------------------------------------------------------------VARIANTS OF BDroot-------------------------------------------------------------------------------------------
	
	
	
	public static BigDecimal BDroot(BigDecimal base, int root, BigDecimal guess){

		BigDecimal rootBD = new BigDecimal(String.valueOf(root));
		BigDecimal num = BigDecimal.ONE;
		int count = 0;
		//MathContext round = new MathContext(20,RoundingMode.HALF_DOWN);
		
		
		num = guess.subtract((BDexp(guess,root).subtract(base, MathContext.DECIMAL128)).divide(rootBD.multiply(guess.pow(root-1), MathContext.DECIMAL128), MathContext.DECIMAL128), MathContext.DECIMAL128);
		//divide has DECIMAL64 (precision 16) and subtract has DECIMAL32 (precision 7)
		// (above) num = guess - (guess^root - start)/(root * guess^(root-1))	
																																						       
		do {
			
			num = num.subtract((num.pow(root).subtract(base, MathContext.DECIMAL128)).divide(rootBD.multiply(guess.pow(root-1), MathContext.DECIMAL128), MathContext.DECIMAL128), MathContext.DECIMAL128);

			count++;
			
		}while(count < 100);
		
		return num;
	}
	
	
	public static BigDecimal BDroot(BigDecimal base, long root, BigDecimal guess){

		BigDecimal rootBD = new BigDecimal(String.valueOf(root));
		BigDecimal num = BigDecimal.ONE;
		int count = 0;
		//MathContext round = new MathContext(20,RoundingMode.HALF_DOWN);
		
		
		num = guess.subtract((BDexp(guess,root).subtract(base, MathContext.DECIMAL128)).divide(rootBD.multiply(BDexp(guess,(root-1)), MathContext.DECIMAL128), MathContext.DECIMAL128), MathContext.DECIMAL128);
		//divide has DECIMAL64 (precision 16) and subtract has DECIMAL32 (precision 7)
		// (above) num = guess - (guess^root - start)/(root * guess^(root-1))	
																																						       
		do {
			
			num = num.subtract((BDexp(guess,root).subtract(base, MathContext.DECIMAL128)).divide(rootBD.multiply(BDexp(guess,(root-1)), MathContext.DECIMAL128), MathContext.DECIMAL128), MathContext.DECIMAL128);

			count++;
			
		}while(count < 100);
		
		return num;
	}

}
