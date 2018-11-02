package nl.knokko.core.plugin.item.attributes;

import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagDouble;
import net.minecraft.server.v1_12_R1.NBTTagInt;
import net.minecraft.server.v1_12_R1.NBTTagList;
import net.minecraft.server.v1_12_R1.NBTTagLong;
import net.minecraft.server.v1_12_R1.NBTTagString;

public class ItemAttributes {
	
	private static long uniqueCounter;
	
	public static ItemStack setAttribute(ItemStack original, String attribute, double value, String slot) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(original);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagList modifiers = compound.getList("AttributeModifiers", 10);
		if (modifiers == null)
			modifiers = new NBTTagList();
		setAttribute(modifiers, attribute, value, slot);
		compound.set("AttributeModifiers", modifiers);
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public static double getAttribute(ItemStack stack, String attribute) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		if (nmsStack.hasTag()) {
			NBTTagCompound compound = nmsStack.getTag();
			NBTTagList modifiers = compound.getList("AttributeModifiers", 10);
			if (modifiers != null) {
				for (int index = 0; index < modifiers.size(); index++) {
					NBTTagCompound attributeTag = modifiers.get(index);
					if (attributeTag.getString("AttributeName").equals(attribute))
						return attributeTag.getDouble("Amount");
				}
				return Double.NaN;
			} else
				return Double.NaN;
		} else
			return Double.NaN;
	}
	
	public static ItemStack clearAttributes(ItemStack original) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(original);
		NBTTagCompound compound = nmsStack.hasTag() ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		setAttribute(modifiers, "dummy", 0, "dummyslot");
		compound.set("AttributeModifiers", modifiers);
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public static ItemStack resetAttributes(ItemStack original) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(original);
		if (nmsStack.hasTag()) {
			NBTTagCompound compound = nmsStack.getTag();
			NBTTagList modifiers = compound.getList("AttributeModifiers", 10);
			if (modifiers != null) {
				compound.remove("AttributeModifiers");
				nmsStack.setTag(compound);
				return CraftItemStack.asBukkitCopy(nmsStack);
			} else
				return original;
		} else
			return original;
	}
	
	public static String[] listAttributes(ItemStack stack) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
		if (nmsStack.hasTag()) {
			NBTTagCompound compound = nmsStack.getTag();
			NBTTagList modifiers = compound.getList("AttributeModifiers", 10);
			if (modifiers != null) {
				String[] attributes = new String[modifiers.size()];
				for (int index = 0; index < modifiers.size(); index++) {
					NBTTagCompound attributeTag = modifiers.get(index);
					attributes[index] = attributeTag.getString("AttributeName") + ": " + attributeTag.getDouble("Amount");
				}
				return attributes;
			} else
				return null;
		} else
			return null;
	}
	
	private static void setAttribute(NBTTagList modifiers, String name, double value, String slot){
		NBTTagCompound damage = new NBTTagCompound();
		damage.set("AttributeName", new NBTTagString(name));
		damage.set("Name", new NBTTagString(name));
		damage.set("Amount", new NBTTagDouble(value));
		damage.set("Operation", new NBTTagInt(0));
		damage.set("UUIDLeast", new NBTTagLong(System.currentTimeMillis()));
		damage.set("UUIDMost", new NBTTagLong(uniqueCounter++));
		damage.set("Slot", new NBTTagString(slot));
		modifiers.add(damage);
	}
}