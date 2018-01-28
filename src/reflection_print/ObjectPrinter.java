package reflection_print;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ObjectPrinter
{
	private static final String MULTI_LINE = "(?m)^";
	private static final String TAB = "\t";
	private static final String SPACE = " ";
	private static final String JUMP_LINE = "\n";
	private static final String ARRAY_VALUE_DELIMITER = ",";
	private static final String EMPTY_STRING = "";
	private static final String NAME_VALUE_DELIMITER = ":";
	private static final String ARRAY_OPEN = "[";
	private static final String ARRAY_CLOSE = "]";
	private TypeFormatter typeFormatter = new TypeFormatter();

	public String printObject(Object objectToPrint)
	{
		String fieldName;
		Object fieldValue;
		String formattedField;
		StringBuilder output = new StringBuilder();
		Field[] objectFields = objectToPrint.getClass().getDeclaredFields();
		for (Field objectField : objectFields)
		{
			try
			{
				objectField.setAccessible(true);
				fieldName = objectField.getName();
				fieldValue = objectField.get(objectToPrint);
				if (fieldValue != null && !typeFormatter.isTypeMappable(fieldValue))
				{
					String complexObject = printObject(fieldValue);
					String tabbedOutPut = complexObject.replaceAll(MULTI_LINE, TAB);
					appendNameValueToOutput(output, fieldName.concat(NAME_VALUE_DELIMITER).concat(JUMP_LINE), tabbedOutPut, EMPTY_STRING);
				}
				else
				{
					formattedField = typeFormatter.formatType(fieldValue);
					appendNameValueToOutput(output, fieldName, formattedField + JUMP_LINE, NAME_VALUE_DELIMITER);
				}
			}
			catch (IllegalArgumentException | IllegalAccessException exception)
			{
				exception.printStackTrace();
			}
		}
		return output.toString();
	}

	/**
	 * Will append to the field name and its corresponding value to the output
	 * in the following form "fieldName+delimiter+FieldValue"
	 * 
	 * @param output
	 *            the output to append the name and value to
	 * @param fieldName
	 *            The name of the field
	 * @param fieldValue
	 *            The value of the field
	 * @param delimiter
	 *            The delimiter between the name and its value
	 */
	private void appendNameValueToOutput(StringBuilder output, String fieldName, Object fieldValue, String delimiter)
	{
		int nameLength = ((String) fieldName).length();
		int valueLength = ((String) fieldValue).length();
		output.append(String.format("%" + nameLength + "s %" + valueLength + "s", fieldName + delimiter, fieldValue));
	}

	public TypeFormatter getTypeFormatter()
	{
		return typeFormatter;
	}

	public void setTypeFormatter(TypeFormatter typeFormatter)
	{
		this.typeFormatter = typeFormatter;
	}

	/**
	 * Interface to define a method to handle formatting for each class type
	 */
	public interface TypeMethod
	{
		public String format(Object fieldValue);
	}

	/**
	 * Interface to define a method to check if a fieldValue is an instance of
	 * any of the types in {@link typesMap}
	 * 
	 * @param fieldValue
	 *            the object to check if it is an instance of
	 */
	public interface IsInstanceOfType
	{
		public Class<?> isInstanceOf(Object fieldValue);
	}

	/**
	 * The class functions as a Formatter for object types, to format them
	 * according to their special designated MessageFormat, DecimalFormat or
	 * SimpleDateFormat. Each type will be stored in {@link typesMap} so it can
	 * be retrieved easily and {@link instanceOfTypeMapper} to figure if its a
	 * sub type, and if found will be mapped to its Formatting method as defined
	 * by {@link TypeMethod} and implemented at The {@link TypeFormatter}
	 */
	class TypeFormatter
	{

		private MessageFormat stringFormatter = new MessageFormat("\"{0}\"");
		private MessageFormat characterFormatter = new MessageFormat("''{0}''");
		private DecimalFormat integerFormatter = new DecimalFormat("#0");
		private DecimalFormat doubleFormatter = new DecimalFormat("#0.0");
		private DecimalFormat floatFormatter = new DecimalFormat("#0.0");
		private DecimalFormat longFormatter = new DecimalFormat("#0");
		private DecimalFormat byteFormatter = new DecimalFormat("#0");
		private DecimalFormat bigIntegerFormatter = new DecimalFormat("#0");
		private SimpleDateFormat dateFormatter = new SimpleDateFormat("\"yyyy-MM-dd\"");
		private HashMap<Class<?>, TypeMethod> mapTypeToMethod = new HashMap<>();

		public String formatType(Object fieldValue)
		{
			Class<?> superType;
			if (fieldValue == null || !isTypeMappable(fieldValue))
			{
				return null;
			}
			superType = instanceOfTypeMapper.get(typesMap).isInstanceOf(fieldValue);
			return mapTypeToMethod.get(superType).format(fieldValue);
		}

		/**
		 * A map to hold types(keys) and their counterparts(values). The map
		 * function is to map each type to its designated Method to be formatted
		 * appropriately by the {@link TypeFormatter}
		 */
		public final Map<Class<?>, Class<?>> typesMap = new HashMap<Class<?>, Class<?>>();
		{
			typesMap.put(Boolean.class, Boolean.class);
			typesMap.put(Byte.class, Byte.class);
			typesMap.put(Short.class, Short.class);
			typesMap.put(Character.class, Character.class);
			typesMap.put(Integer.class, Integer.class);
			typesMap.put(Long.class, Long.class);
			typesMap.put(Float.class, Float.class);
			typesMap.put(Double.class, Double.class);
			typesMap.put(String.class, String.class);
			typesMap.put(Date.class, Date.class);
			typesMap.put(BigInteger.class, BigInteger.class);
			typesMap.put(boolean[].class, Object[].class);
			typesMap.put(byte[].class, Object[].class);
			typesMap.put(short[].class, Object[].class);
			typesMap.put(char[].class, Object[].class);
			typesMap.put(int[].class, Object[].class);
			typesMap.put(long[].class, Object[].class);
			typesMap.put(float[].class, Object[].class);
			typesMap.put(double[].class, Object[].class);
			typesMap.put(String[].class, Object[].class);
			typesMap.put(Object[].class, Object[].class); // generic array
			typesMap.put(Collection.class, Collection.class);
		}

		/**
		 * The map functions as a replacement for "instance of" operation in
		 * cases such as java.util.ArrayList that needs to be mapped correctly
		 * to its designated supertype which is java.util.Collection that can't
		 * be handled by the {@link #typesMap}
		 */
		public final Map<Map<Class<?>, Class<?>>, IsInstanceOfType> instanceOfTypeMapper = new HashMap<Map<Class<?>, Class<?>>, IsInstanceOfType>();
		{
			instanceOfTypeMapper.put(typesMap, new IsInstanceOfType()
			{
				public Class<?> isInstanceOf(Object fieldValue)
				{
					for (Entry<Class<?>, Class<?>> entry : typesMap.entrySet())
					{
						Class<?> key = entry.getKey();
						if (key.isInstance(fieldValue))
						{
							return entry.getValue();
						}
					}
					return null;
				}
			});
		}

		/**
		 * @return true in case the fieldValue is contained in typesMap or if
		 *         its an instance of one of the Types in typesMap, else returns
		 *         false
		 */
		protected boolean isTypeMappable(Object fieldValue)
		{
			return instanceOfTypeMapper.get(typesMap).isInstanceOf(fieldValue) == null ? false : true;
		}

		private void appendOpenerForComplexObject(StringBuilder output, Object objectValue)
		{
			String opener = objectValue.getClass().getSimpleName().concat(NAME_VALUE_DELIMITER);
			output.append(String.format("\n%-15s", opener));
		}

		public TypeFormatter()
		{
			mapTypeToMethod.put(String.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return stringFormatter.format(new Object[] { fieldValue });
				}
			});
			mapTypeToMethod.put(Integer.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return fieldValue == null ? EMPTY_STRING : integerFormatter.format(fieldValue);
				}
			});
			mapTypeToMethod.put(Character.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return characterFormatter.format(new Object[] { fieldValue });
				}
			});
			mapTypeToMethod.put(Collection.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					int i = 0;
					StringBuilder output = new StringBuilder();
					int collectionSize = ((Collection<?>) fieldValue).size();
					output.append(ARRAY_OPEN).append(SPACE);
					for (Object collectionElement : (Collection<?>) fieldValue)
					{
						if (collectionElement != null && !isTypeMappable(collectionElement))
						{
							appendOpenerForComplexObject(output, collectionElement);
							String complexObject = printObject(collectionElement).trim();
							String tabbedOutput = complexObject.replaceAll(MULTI_LINE, TAB);
							appendNameValueToOutput(output, JUMP_LINE, tabbedOutput, EMPTY_STRING);
						}
						else
						{
							String nameValueDelimiter = getNameValueDelimiter(collectionSize, i);
							output.append(String.format("%1s", formatType(collectionElement))).append(nameValueDelimiter).append(SPACE);
						}
						i++;
					}
					return output.append(ARRAY_CLOSE).toString();
				}
			});

			mapTypeToMethod.put(Object[].class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					StringBuilder output = new StringBuilder();
					int arrayLength = Array.getLength(fieldValue);
					output.append(ARRAY_OPEN).append(SPACE);
					for (int i = 0; i < arrayLength; i++)
					{
						Object loopValue = Array.get(fieldValue, i);
						if (loopValue != null && !isTypeMappable(loopValue))
						{
							appendOpenerForComplexObject(output, loopValue);
							String complexObject = printObject(loopValue).trim();
							String tabbedOutput = complexObject.replaceAll(MULTI_LINE, TAB);
							appendNameValueToOutput(output, JUMP_LINE, tabbedOutput, EMPTY_STRING);
						}
						else
						{
							String nameValueDelimiter = getNameValueDelimiter(arrayLength, i);
							output.append(String.format("%1s", formatType(loopValue))).append(nameValueDelimiter).append(SPACE);
						}
					}
					output.append(ARRAY_CLOSE);
					return output.toString();
				}
			});
			mapTypeToMethod.put(Date.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return dateFormatter.format(((Date) fieldValue));
				}
			});
			mapTypeToMethod.put(Boolean.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return Boolean.toString((boolean) fieldValue);
				}
			});
			mapTypeToMethod.put(Float.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return fieldValue == null ? EMPTY_STRING : floatFormatter.format(fieldValue);
				}
			});
			mapTypeToMethod.put(Double.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return fieldValue == null ? EMPTY_STRING : doubleFormatter.format(fieldValue);
				}
			});
			mapTypeToMethod.put(Long.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return fieldValue == null ? EMPTY_STRING : longFormatter.format(fieldValue);
				}
			});
			mapTypeToMethod.put(Byte.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return fieldValue == null ? EMPTY_STRING : byteFormatter.format(fieldValue);
				}
			});
			mapTypeToMethod.put(BigInteger.class, new TypeMethod()
			{
				@Override
				public String format(Object fieldValue)
				{
					return fieldValue == null ? EMPTY_STRING : bigIntegerFormatter.format(fieldValue);
				}
			});
		}

		/**
		 * Returns a delimiter for the name value depending on the current loop iteration.
		 * if its the last loop the delimiter will be empty, no need for "," for the last value in an array
		 * */
		private String getNameValueDelimiter(int arrayLength, int i)
		{
			return i != arrayLength - 1 ? ARRAY_VALUE_DELIMITER : EMPTY_STRING;
		}

		public MessageFormat getStringFormatter()
		{
			return stringFormatter;
		}

		public void setStringFormatter(MessageFormat stringFormatter)
		{
			this.stringFormatter = stringFormatter;
		}

		public MessageFormat getCharacterFormatter()
		{
			return characterFormatter;
		}

		public void setCharacterFormatter(MessageFormat characterFormatter)
		{
			this.characterFormatter = characterFormatter;
		}

		public DecimalFormat getIntegerFormatter()
		{
			return integerFormatter;
		}

		public void setIntegerFormatter(DecimalFormat integerFormatter)
		{
			this.integerFormatter = integerFormatter;
		}

		public DecimalFormat getDoubleFormatter()
		{
			return doubleFormatter;
		}

		public void setDoubleFormatter(DecimalFormat doubleFormatter)
		{
			this.doubleFormatter = doubleFormatter;
		}

		public DecimalFormat getFloatFormatter()
		{
			return floatFormatter;
		}

		public void setFloatFormatter(DecimalFormat floatFormatter)
		{
			this.floatFormatter = floatFormatter;
		}

		public DecimalFormat getLongFormatter()
		{
			return longFormatter;
		}

		public void setLongFormatter(DecimalFormat longFormatter)
		{
			this.longFormatter = longFormatter;
		}

		public DecimalFormat getByteFormatter()
		{
			return byteFormatter;
		}

		public void setByteFormatter(DecimalFormat byteFormatter)
		{
			this.byteFormatter = byteFormatter;
		}

		public DecimalFormat getBigIntegerFormatter()
		{
			return bigIntegerFormatter;
		}

		public void setBigIntegerFormatter(DecimalFormat bigIntegerFormatter)
		{
			this.bigIntegerFormatter = bigIntegerFormatter;
		}

		public SimpleDateFormat getDateFormatter()
		{
			return dateFormatter;
		}

		public void setDateFormatter(SimpleDateFormat dateFormatter)
		{
			this.dateFormatter = dateFormatter;
		}
	}
}