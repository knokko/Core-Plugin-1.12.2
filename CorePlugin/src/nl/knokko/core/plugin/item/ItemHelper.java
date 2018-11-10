package nl.knokko.core.plugin.item;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemHelper {
	
	public static String getStackName(ItemStack stack) {
		net.minecraft.server.v1_12_R1.ItemStack nms = CraftItemStack.asNMSCopy(stack);
		return nms.getName();
	}
}