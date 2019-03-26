/*******************************************************************************
 * Copyright (c) 2019 IBM Corporation and others.
 *  This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package nl.knokko.core.plugin.command;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandKnokkoCore implements CommandExecutor {
	
	private CommandItemAttribute itemAttribute = new CommandItemAttribute();
	private CommandItemName itemName = new CommandItemName();
	private CommandTest test = new CommandTest();
	private CommandItemTag tag = new CommandItemTag();
	
	private void sendUseage(CommandSender sender) {
		sender.sendMessage(ChatColor.YELLOW + "You should use /knokkocore itemattribute/itemname/test");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender.isOp()) {
			if (args.length > 0) {
				String[] subArgs = Arrays.copyOfRange(args, 1, args.length);
				if (args[0].equals("itemattribute"))
					itemAttribute.onCommand(sender, command, args[0], subArgs);
				else if (args[0].equals("itemname"))
					itemName.onCommand(sender, command, args[0], subArgs);
				else if (args[0].equals("test"))
					test.onCommand(sender, command, args[0], subArgs);
				else if (args[0].equals("tag"))
					tag.onCommand(sender, command, label, subArgs);
				else
					sendUseage(sender);
			} else {
				sendUseage(sender);
			}
		} else {
			sender.sendMessage(ChatColor.DARK_RED + "You do not have access to this command.");
		}
		return false;
	}
}