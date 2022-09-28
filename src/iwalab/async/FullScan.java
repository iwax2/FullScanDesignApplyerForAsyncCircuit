package iwalab.async;

public class FullScan {
	static String VERSION = "0.1";
	static String DATE = "Sep 19, 2011";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String input_file  = null;
		String scan_list   = null;
		String scan_lib    = null;
		String output_file = null;
		try {
			for( int i=0; i<args.length; i++ ) {
				if( args[i].equals("-i") ) {
					input_file  = args[++i];
				} else if( args[i].equals("-s") ) {
					scan_list   = args[++i];
				} else if( args[i].equals("-l") ) {
					scan_lib    = args[++i];
				} else if( args[i].equals("-o") ) {
					output_file = args[++i];
				} else {
					usage();
				}
			}
		} catch( ArrayIndexOutOfBoundsException e ) {
			usage();
		}
		if( input_file == null || scan_list == null ) {
			usage();
		}
		Verilog verilog = new Verilog( input_file, "noc4x4_async_vc0_4" );

	}

	public static void usage() {
		System.out.println("insert_dft for asynchronous circuits version "+VERSION+" Last update "+DATE);
		System.out.println("usage: insert_dft <-i ORIGINAL_CIRCUIT> <-s SCAN_ELEMENT_LIST> [-l SCAN_LIBRARY] [-o TESTABLE_CIRCUIT]");
		System.exit(0);
	}
}
