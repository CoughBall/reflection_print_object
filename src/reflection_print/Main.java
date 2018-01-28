package reflection_print;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main
{

	public static void main(String[] args)
	{
		
		ObjectPrinter formatter = new ObjectPrinter();
		Tester tester = new Tester();
		//change types formats
		/*
		formatter.getTypeFormatter().getStringFormatter().applyPattern("{0}");
		formatter.getTypeFormatter().getBigIntegerFormatter().applyPattern("#0.0");
		formatter.getTypeFormatter().getDateFormatter().applyPattern("\"yyyy-MM-dd hh:mm:ss\"");
		.
		.
		.
		*/	
		System.out.print(formatter.printObject(tester));
	}

	public static class Tester
	{
		BigInteger bigIntegerTest = new BigInteger("100000000000000000000");
		byte byteTest = 127;
		long longTest = 23423l;
		float floatTest = 23423.2234f;
		double doubleTest = 55.3332;
		int primInt = 999;
		String stringTest = "stringTestValue";
		Integer integerTest = new Integer(6666);
		String[] stringArray = { "a", "b", "asdsadsad", null, "" };
		Object[] objectArray = { "a", "b", "asdsadsad", null, '4', 33, new Date(0), new TestD() };
		char[] charArray = { 'a', 'b', 'c', ' ' };
		String nullStringTest = null;
		int[] intArray = { 10, 20, 30, 40, 50, 60, 71, -803, 9000000, 91 };
		TesterB innerB = new TesterB();
		Boolean b = true;
		Date dateobj = new Date(0);
	}

	public static class TesterB
	{
		char characterTest = 'C';
		String emptyStringTest = "";
		List<Integer> arrayListTest = Arrays.asList(1, 2);
		Boolean[] booleanArray = { true, false, null, true };
		boolean[] booleanArray2 = { true, false, true };
		ArrayList<Integer> arrayListTest2 = new ArrayList<>();
		ArrayList<Object> arrayListTest3 = new ArrayList<>();
		Date theDate;
		TestC testC = new TestC();
		TestC testC2 = new TestC();

		public TesterB()
		{
			arrayListTest2.add(3);
			arrayListTest2.add(4);
			TestD firstDInArray = new TestD();
			testDCollection.add(firstDInArray);
			testDCollection.add(new TestD());
			arrayListTest3.add(5);
			arrayListTest3.add(5.5);
			arrayListTest3.add("455");
			arrayListTest3.add(new Date(0));
			arrayListTest3.add(null);
			arrayListTest3.add(new TestD());
			Calendar myCal = Calendar.getInstance();
			myCal.set(Calendar.YEAR, 1995);
			myCal.set(Calendar.MONTH, 0);
			myCal.set(Calendar.DAY_OF_MONTH, 3);
			theDate = myCal.getTime();
		}

		Boolean nullBoolean = null;
		boolean defaultBoolean;
		ArrayList<TestD> testDCollection = new ArrayList<>(2);

	}

	public static class TestC
	{
		String nullStringTest = null;
		int[] intArray = { 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 };
		Boolean booleanTest = true;
		TestD innerD2 = new TestD();
		TestD[] testDArray = { new TestD(), new TestD() };
	}

	public static class TestD
	{
		String name = "testDName";
		String last = null;
		String testAnotherString = "asdsadsadasdsad";
		int[] data = { 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 };
		TestE e = new TestE();
	}

	public static class TestE
	{
		String name = "testDName";
		String last = null;
		String testAnotherString = "asdsadsadasdsad";
		int[] data = { 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 };
	}
}
