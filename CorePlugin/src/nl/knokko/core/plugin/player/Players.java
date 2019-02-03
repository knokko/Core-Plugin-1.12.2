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
package nl.knokko.core.plugin.player;

import java.util.Collection;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Players {
	
	public static Player getOnline(String name) {
		Collection<? extends Player> players = Bukkit.getOnlinePlayers();
		for (Player player : players)
			if (player.getName().equals(name))
				return player;
		return null;
	}
}