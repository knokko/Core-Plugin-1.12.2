package nl.knokko.core.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import nl.knokko.core.plugin.command.CommandItemAttribute;

public class CorePlugin extends JavaPlugin {
	
	@Override
	public void onEnable() {
		getCommand("itemattribute").setExecutor(new CommandItemAttribute());
	}
}