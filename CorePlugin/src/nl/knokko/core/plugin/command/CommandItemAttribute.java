package nl.knokko.core.plugin.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import nl.knokko.core.plugin.item.attributes.ItemAttributes;

public class CommandItemAttribute implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (player.isOp()) {
				ItemStack item = player.getInventory().getItemInMainHand();
				if (item != null) {
					if (args.length == 0)
						player.sendMessage("Use /ia set/get/clear/reset/list ...");
					else {
						if (args[0].equals("set")) {
							if (args.length < 3) {
								player.sendMessage("Use /ia set <attributeName> <value> [slot]");
							} else {
								try {
									double value = Double.parseDouble(args[2]);
									String slot = "mainhand";
									if (args.length >= 4)
										slot = args[3];
									player.getInventory().setItemInMainHand(ItemAttributes.setAttribute(item, args[1], value, slot));
								} catch (NumberFormatException nfe) {
									player.sendMessage("'" + args[2] + "' should be a number");
								}
							}
						} else if(args[0].equals("get")) {
							if (args.length < 2) {
								player.sendMessage("Use /ia get <attributeName>");
							} else {
								double value = ItemAttributes.getAttribute(item, args[1]);
								if (value == value)
									player.sendMessage("The value is " + value);
								else
									player.sendMessage("The value is not set");
							}
						} else if(args[0].equals("clear")) {
							player.getInventory().setItemInMainHand(ItemAttributes.clearAttributes(item));
						} else if(args[0].equals("list")) {
							String[] attributes = ItemAttributes.listAttributes(item);
							if (attributes != null) {
								player.sendMessage("Attributes: ");
								for (String attribute : attributes)
									player.sendMessage(attribute);
							} else
								player.sendMessage("This item doesn't have the attribute modifiers tag");
						} else if(args[0].equals("reset")){
							player.getInventory().setItemInMainHand(ItemAttributes.resetAttributes(item));
						} else
							player.sendMessage("Use /ia set/get/clear/reset/list ...");
					}
				} else
					player.sendMessage(ChatColor.RED + "You need to hold the item to modify in your main hand.");
			} else
				player.sendMessage(ChatColor.DARK_RED + "Only operators can use this command.");
		} else
			sender.sendMessage("Only players can use this command.");
		return false;
	}
}