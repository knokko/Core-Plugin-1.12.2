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
package nl.knokko.core.plugin.menu;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import nl.knokko.core.plugin.CorePlugin;

public class MenuEventHandler implements Listener {
	
	private Map<UUID,Menu> openMenus;
	
	public void openMenu(Player player, Menu menu) {
		player.openInventory(menu.getInventory());
		openMenus.put(player.getUniqueId(), menu);
	}
	
	public MenuEventHandler() {
		openMenus = new HashMap<UUID,Menu>();
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (event.getWhoClicked() instanceof Player) {
			Menu menu = openMenus.get(event.getWhoClicked().getUniqueId());
			if (menu != null && event.getRawSlot() < menu.getSize()) {
				event.setCancelled(true);
				Bukkit.getScheduler().scheduleSyncDelayedTask(CorePlugin.getInstance(), () -> {
					menu.onClick((Player) event.getWhoClicked(), event.getRawSlot());
				});
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		openMenus.remove(event.getPlayer().getUniqueId());
	}
}