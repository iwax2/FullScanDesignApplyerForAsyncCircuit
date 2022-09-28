package iwalab.async;

import java.util.ArrayList;
import java.util.LinkedList;


public class Module {
	private String type = null;
	private String name = null;

	private ModuleDefinition definition = null;

	private LinkedList<Module>  input = new LinkedList<Module>();
	private LinkedList<Module> output = new LinkedList<Module>();
	private ArrayList<Module> modules;

	public Module( String module_type, String instance_name, ModuleDefinition definition ) {
		type = module_type;
		name = instance_name;
		this.definition = definition;
		modules = definition.makeInstance();

	}



}
