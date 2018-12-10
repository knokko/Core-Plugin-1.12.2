package nl.knokko.core.plugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import nl.knokko.core.plugin.command.*;
import nl.knokko.core.plugin.menu.MenuEventHandler;

public class CorePlugin extends JavaPlugin {
	
	private static CorePlugin instance;
	
	public static CorePlugin getInstance() {
		return instance;
	}
	
	private MenuEventHandler menuHandler;
	
	public MenuEventHandler getMenuHandler() {
		return menuHandler;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		menuHandler = new MenuEventHandler();
		Bukkit.getPluginManager().registerEvents(menuHandler, this);
		getCommand("knokkocore").setExecutor(new CommandKnokkoCore());
	}
}