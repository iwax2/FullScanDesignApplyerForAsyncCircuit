package iwalab.async;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verilog {
	private ArrayList<String> file = new ArrayList<String>();
	ArrayList<ModuleDefinition> module_definitions = new ArrayList<ModuleDefinition>();
	ArrayList<Module> modules = new ArrayList<Module>();
	String current_design = null;

	public Verilog( String filename, String current_design ) {
		this.current_design = current_design;
		// File read
		Pattern p_eol 	= Pattern.compile(".*;\\s*(//.*)?$");
		Pattern p_end_module = Pattern.compile("^\\s*endmodule\\s*(//.*)?$");
		try {
			BufferedReader br = new BufferedReader( new FileReader(filename) );
			StringBuffer sb = new StringBuffer();

			String line = null;
			while( (line=br.readLine()) != null ) {
				sb.append(line);
				if( p_eol.matcher(line).matches() || p_end_module.matcher(line).matches() ) {
					file.add(sb.toString().replaceAll("\\s{2,}", " "));
					sb = new StringBuffer();
				}
			}
			br.close();
		} catch( Exception e ) {
			e.printStackTrace();
			System.out.println("File not found, " + filename);
			System.exit(0);
		}

		// Moduleの定義を抽出
		Pattern p_begin_module = Pattern.compile("^\\s*module\\s+(\\w+)\\s*\\(.*\\)\\s*;\\s*(//.*)?$");
		boolean m_flag = false;
		ArrayList<String> module = null;

		for( int i=0; i<file.size(); i++ ) {
			if( !m_flag && p_begin_module.matcher(file.get(i)).matches() ) {
				m_flag = true;
				module = new ArrayList<String>();
			}
			if( m_flag ) {
				module.add(file.get(i));
			}
			if( m_flag && p_end_module.matcher(file.get(i)).matches() ) {
				module_definitions.add( new ModuleDefinition( module ) );
				m_flag = false;
			}
		}

		// Moduleをインスタンス化
		Pattern p_module_instance = Pattern.compile("^\\s*(\\w+)\\s+(\\w+)\\s*\\((.*)\\)\\s*;\\s*(//.*)?$");
		for( int i=0; i<file.size(); i++ ) {
			Matcher m_module_instance = p_module_instance.matcher(file.get(i));
			if( m_module_instance.matches() ) {
				String module_type = m_module_instance.group(1);
				String instance_name = m_module_instance.group(2);
//				String connection = m_module_instance.group(3);
				ModuleDefinition md = searchModuleDefinitionByName(module_type);
				if( md != null ) {
					modules.add( new Module( module_type, instance_name, md ) );
				}
			}
		}
	}

	public ModuleDefinition	searchModuleDefinitionByName( String module_name ) {
		for( int i=0; i<module_definitions.size(); i++ ) {
			if( module_definitions.get(i).getName().equals(module_name))  {
				return(module_definitions.get(i));
			}
		}
//		System.out.println("Warning: Cannot find module_definition, \""+module_name+"\"");
		return(null);
	}

}
