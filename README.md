# reflection_print_object
A simple app that prints an object contents using reflection

The app prints objects using simple reflection with formatting for all the primitive datatypes with default settings that can be changed for any kind of format that user wants, it prints arrays sorrounded with square brackets [] and objects will be idented.

## Example
Example output is already ready in the Main method, when it runs it will print the following:

bigIntegerTest: 100000000000000000000
byteTest: 127
longTest: 23423
floatTest: 23423.2
doubleTest: 55.3
primInt: 999
stringTest: "stringTestValue"
integerTest: 6666
stringArray: [ "a", "b", "asdsadsad", null, "" ]
objectArray: [ "a", "b", "asdsadsad", null, '4', 33, "1970-01-01", 
TestD:         
 	name: "testDName"
	last: null
	testAnotherString: "asdsadsadasdsad"
	data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
	e:
	 	name: "testDName"
		last: null
		testAnotherString: "asdsadsadasdsad"
		data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]]
charArray: [ 'a', 'b', 'c', ' ' ]
nullStringTest: null
intArray: [ 10, 20, 30, 40, 50, 60, 71, -803, 9000000, 91 ]
innerB:
 	characterTest: 'C'
	emptyStringTest: ""
	arrayListTest: [ 1, 2 ]
	booleanArray: [ true, false, null, true ]
	booleanArray2: [ true, false, true ]
	arrayListTest2: [ 3, 4 ]
	arrayListTest3: [ 5, 5.5, "455", "1970-01-01", null, 
	TestD:         
	 	name: "testDName"
		last: null
		testAnotherString: "asdsadsadasdsad"
		data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		e:
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]]
	theDate: "1995-01-03"
	testC:
	 	nullStringTest: null
		intArray: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		booleanTest: true
		innerD2:
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
			e:
			 	name: "testDName"
				last: null
				testAnotherString: "asdsadsadasdsad"
				data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		testDArray: [ 
		TestD:         
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
			e:
			 	name: "testDName"
				last: null
				testAnotherString: "asdsadsadasdsad"
				data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		TestD:         
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
			e:
			 	name: "testDName"
				last: null
				testAnotherString: "asdsadsadasdsad"
				data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]]
	testC2:
	 	nullStringTest: null
		intArray: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		booleanTest: true
		innerD2:
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
			e:
			 	name: "testDName"
				last: null
				testAnotherString: "asdsadsadasdsad"
				data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		testDArray: [ 
		TestD:         
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
			e:
			 	name: "testDName"
				last: null
				testAnotherString: "asdsadsadasdsad"
				data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		TestD:         
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
			e:
			 	name: "testDName"
				last: null
				testAnotherString: "asdsadsadasdsad"
				data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]]
	nullBoolean: null
	defaultBoolean: false
	testDCollection: [ 
	TestD:         
	 	name: "testDName"
		last: null
		testAnotherString: "asdsadsadasdsad"
		data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		e:
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
	TestD:         
	 	name: "testDName"
		last: null
		testAnotherString: "asdsadsadasdsad"
		data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]
		e:
		 	name: "testDName"
			last: null
			testAnotherString: "asdsadsadasdsad"
			data: [ 10, 20, 30, 40, 50, 60, 71, 80, 90, 91 ]]
b: true
dateobj: "1970-01-01"
