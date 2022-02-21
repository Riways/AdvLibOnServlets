package command;

import java.util.HashMap;
import java.util.Map;

public class CommandContainer {

	private static Map<String, Command> commands = new HashMap<String, Command>();
	
	static {
		commands.put("welcome", new WelcomeCommand());
		commands.put("login", new LoginCommand());
		commands.put("registration", new RegistrationCommand());
		commands.put("logout", new LogoutCommand());
	}
	
	public static Command getCommand(String commandName) {
	
		if(commandName == null || !commands.containsKey(commandName)) {
			return commands.get("welcome");
		}
		return commands.get(commandName);
		
	}
	
}
