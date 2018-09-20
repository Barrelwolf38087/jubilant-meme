package grapher;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.io.PrintStream;

/**
 * Represents a database for holding the inputs and outputs of various {@link Function Function}s.
 * @author William Leverone
 * @version 1.0
 */
public class PointDB {
	/**
	 * Holds the daabase's tables.
	 */
	private List<LinkedHashMap<Double, Double>> tables = new ArrayList<>();
	
	/**
	 * Gets an immutable copy of the database's tables.
	 * @return an immutable copy of the database's tables.
	 */
	public List<LinkedHashMap<Double, Double>> getTables() {
		return Collections.unmodifiableList(this.tables);
	}
	
	/**
	 * The length of the String into which inputs and outputs should be inserted and padded when {@link #printTables(PrintStream, String, String) printTables} is called.
	 */
	private int padding;
	
	/**
	 * Gets the length of the String into which inputs and outputs should be inserted and padded when {@link #printTables(PrintStream, String, String) printTables} is called.
	 * @return the length of the String into which inputs and outputs should be inserted and padded when {@link #printTables(PrintStream, String, String) printTables} is called.
	 * @see #format(Double, int, Boolean, char)
	 */
	public int getPadding() {
		return this.padding;
	}
	public void setPadding(int padding) {
		this.padding = padding;
	}
	
	/**
	 * The character with which to pad inputs and outputs.
	 * @see #format(Double, int, Boolean, char)
	 */
	private char paddingChr;
	
	/**
	 * Gets the character with which to pad inputs and outputs.
	 * @return the character with which to pad inputs and outputs.
	 * @see #format(Double, int, Boolean, char)
	 */
	public char getPaddingChr() {
		return this.paddingChr;
	}
	
	/**
	 * The constructor.
	 * @param padding    assigned to {@link #padding padding}.
	 * @param paddingChr assigned to {@link #paddingChr paddingChr}.
	 */
	public PointDB(int padding, char paddingChr) {
		this.padding = padding;
		this.paddingChr = paddingChr;
	}
	
	/**
	 * Defaults {@link #paddingChr paddingChr} to ' '.
	 * @param padding assigned to {@link #padding padding}.
	 * @see #PointDB(int, char)
	 */
	public PointDB(int padding) {
		this.padding = padding;
		this.paddingChr = ' ';
	}
	
	/**
	 * Defaults {@link #padding padding} to 12.
	 * @param paddingChr assigned to {@link #paddingChr paddingChr}.
	 * @see #PointDB(int, char)
	 */
	public PointDB(char paddingChr) {
		this.padding = 12;
		this.padding = paddingChr;
	}
	
	/**
	 * Defaults {@link #padding padding} to 12 and {@link #paddingChr paddingChr} to '='.
	 * @see #PointDB(int, char)
	 */
	public PointDB() {
		this.padding = 12;
		this.paddingChr = '=';
	}
	
	/**
	 * Fits a {@code x} to the left or right of a String of a given length, using a given character as padding.
	 * @param x       the double to pad.
	 * @param length  the length of the String into which {@code x} will be padded.
	 * @param isKey   aligns {@code x} to the left if true, and to the right if false.
	 * @param padding the character to pad {@code x} with.
	 * @return        the padded String.
	 * @throws        PaddingException
	 */
	private String format(Double x, int length, Boolean isKey, char padding) {
		String s = x.toString().substring(0, length - 1);
		String paddedString = "";
		int strLen = s.length();
		int paddingLen = length - strLen;
		
		if (isKey) {
			paddedString += s;
			for (int i = 0; i < paddingLen; i++) {
				paddedString.concat(String.valueOf(padding));
			}
		} else {
			for (int i = 0; i < paddingLen; i++) {
				paddedString += padding;
			}
			paddedString += s;
		}
		
		return paddedString;
	}
	
	/**
	 * Replaces {n} in a {@code header} with {@code iter}. Used to format table headers.
	 * @param header the String in which to replace {n}.
	 * @param iter	 the int with which to replace {n} in {@code header}.
	 * @return       the formatted String.
	 */
	private String formatHeader(String header, int iter) {
		if (header.contains("{n}")) {
			return header.replaceAll("\\{n\\}", String.valueOf(iter));
		} else {
			return header;
		}
	}
	
	/**
	 * Adds a {@link Function Function} to the database and initializes its table.
	 * @param function the {@link Function Function} to add.
	 * @param min      the minimum value with which to initialize {@code function}'s table.
	 * @param max      the maximum value with which to initialize {@code function}'s table.
	 * @param stepping the double by which {@code function}'s inputs should increment.
	 */
	public void addFunction(Function function, double min, double max, double stepping) {
		LinkedHashMap<Double, Double> table = new LinkedHashMap<>();
		
		double x = min;
		while (x <= max) {
			table.put(x, function.func(x));
			x += stepping;
		}
		
		this.tables.add(table);
	}

	/**
	 * Defaults the stepping to 1.
	 * @param func the {@link Function Function} to add.
	 * @param min  the minimum value with which to initialize {@code function}'s table.
	 * @param max  the maximum value with which to initialize {@code function}'s table.
	 */
	public void addFunction(Function func, double min, double max) {
		addFunction(func, min, max, 1);
	}
	
	/**
	 * Removes the table at {@code index} from the database. If the index is out of range, this does nothing.
	 * @param index the index of the table to be removed.
	 */
	public void delFunction(int index) {
		if (index >= 0 && index + 1 <= this.tables.size()) {
			this.tables.remove(index);
		}
	}
	
	/**
	 * @param outStream the {@link java.io.PrintStream PrintStream} to which the tables are printed.
	 * @param delimiter is printed between tables.
	 * @param header    is printed above each table. "{n} is replaced by the table number."
	 * @see #formatHeader(String, int)
	 */
	public void printTables(PrintStream outStream, String delimiter, String header) {
		for (int i = 0; i < this.tables.size(); i++) {
			// Local copy (So that this.tables.get is not called every sub-iteration)
			LinkedHashMap<Double, Double> table = this.tables.get(i);
			
			Set<Double> keys = this.tables.get(i).keySet();
			
			if (header != null) {
				outStream.println(this.formatHeader(header, i + 1));
			}
			
			// Iterate through the LinkedHashMap's keys
			for (Double key:keys) {
				Double value = table.get(key);
				
				outStream.println(this.format(key, this.padding, true, this.paddingChr) + "|" + this.format(value, this.padding, false, this.paddingChr));
				
				// Create horizontal lines
				for (int j = 0; j < (this.padding * 2) + 1; j++) {
					outStream.print("-");
				}
				outStream.println();
			}
			
			// Don't print an extra delimiter
			if (i + 1 != this.tables.size()) {
				outStream.print(delimiter);
			}
		}
	}
	
	/**
	 * Defaults {@code delimiter} to an empty String and {@code header} to {@code null}.
	 * @param outStream the {@link java.io.PrintStream PrintStream} to which the tables are printed.
	 */
	public void printTables(PrintStream outStream) {
		printTables(outStream, "", null);
	}
	
	/**
	 * Defaults {@code outStream} to {@link java.lang.System#out System.out} and {@code delimiter} to an empty String.
	 * @param header is printed above each table. "{n} is replaced by the table number."
	 */
	public void printTables(String header) {
		printTables(System.out, "", header);
	}
	
	/**
	 * Defaults {@code outStream} to {@link java.lang.System#out System.out}, {@code delimiter} to an empty String, and {@code header} to {@code null}.
	 */
	public void printTables() {
		printTables(System.out, "", null);
	}
}
