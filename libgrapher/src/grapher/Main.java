package grapher;

/**
 * The main class.
 * @author William Leverone
 * @version 1.0
 */
public class Main {
	
	public static Function sine = new Function() {
		// Input in radians
		public double func(double x) {
			return Math.sin(x);
		}
	};

	public static void main(String[] args) {
		PointDB myTable = new PointDB(12, ' ');
		
		/*
		LinearFunction myFunction = new LinearFunction(-22, 0);		
		myTable.addFunction(myFunction, 0, 5);
		
		LinearFunction myOtherFunction = new LinearFunction(3, 4);
		myTable.addFunction(myOtherFunction, 0, 5, 0.5);
		
		myTable.addFunction(new ConstFunction(6d), 4, 17);
		
		*/
		
		myTable.addFunction(sine, 0.0d, 2 * Math.PI, Math.PI / 16);
		
		myTable.printTables(System.out, "\n", "Table {n}");
	}
}
