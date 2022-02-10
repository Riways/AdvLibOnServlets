package command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

	private static Map<String, Command> commands = new HashMap<String, Command>();
	
	static {
		commands.put("welcome", new WelcomeCommand());
	}
	
	public static Command getCommand(String commandName) {
	
		if(commandName == null || !commands.containsKey(commandName)) {
			return null;
		}
		return commands.get(commandName);
		
		
	}
	
}
