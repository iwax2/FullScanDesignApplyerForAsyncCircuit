package iwalab.async;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModuleDefinition {
	private ArrayList<String> file = null;
	private String name = "";
	private String port[] = null;
	private ArrayList<String> inout_port = new ArrayList<String>();

	public ModuleDefinition( ArrayList<String> module ) {
		Pattern p_begin_module = Pattern.compile("^\\s*module\\s+(\\w+)\\s*\\((.*)\\)\\s*;\\s*(//.*)?$");
		Pattern p_inout_port   = Pattern.compile("^\\s*(in|out)put\\s+(\\[\\d+:\\d+\\])?\\s*.*;\\s*(//.*)?$");
		this.file = module;
		// analyze
		for( int i=0; i<file.size(); i++ ) {
			Matcher m_begin_module = p_begin_module.matcher(file.get(i));
			if( m_begin_module.matches() ) {
				name = m_begin_module.group(1);
				port = m_begin_module.group(2).split("\\s*,\\s*");
			}
			if( p_inout_port.matcher(file.get(i)).matches() ) {
				inout_port.add(file.get(i));
			}
		}
	}

	// Moduleをインスタンス化
	public ArrayList<Module> makeInstance() {
		Pattern p_module_instance = Pattern.compile("^\\s*(\\w+)\\s+(\\w+)\\s*\\((.*)\\)\\s*;\\s*(//.*)?$");
		ArrayList<Module> modules = new ArrayList<Module>();

		for( int i=0; i<file.size(); i++ ) {
			Matcher m_module_instance = p_module_instance.matcher(file.get(i));
			if( m_module_instance.matches() ) {
				String module_type = m_module_instance.group(1);
				String instance_name = m_module_instance.group(2);
//				String connection = m_module_instance.group(3);
				modules.add( new Module( module_type, instance_name, this ) );
			}
		}
		return(modules);
	}


	public String getName() {
		return name;
	}
	public String[] getPort() {
		return port;
	}
}
