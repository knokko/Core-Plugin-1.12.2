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
	
	public static class Single {
		
		private final String attribute;
		private final String slot;
		private final int operation;
		
		private final double value;
		
		public Single(String attribute, String slot, int operation, double value) {
			this.attribute = attribute;
			this.slot = slot;
			this.operation = operation;
			this.value = value;
		}
	}
	
	public static class Slot {
		
		public static final String MAIN_HAND = "mainhand";
		public static final String OFF_HAND = "offhand";
		
		public static final String HELMET = "head";
		public static final String CHESTPLATE = "chest";
		public static final String LEGGINGS = "legs";
		public static final String BOOTS = "feet";
	}
	
	public static class Operation {
		
		public static final int ADD = 0;
		public static final int MULTIPLY = 1;
		public static final int CHAIN_MULTIPLY = 2;
	}
	
	public static class Attributes {
		
		public static final String ATTACK_DAMAGE = "generic.attackDamage";
		public static final String ATTACK_SPEED = "generic.attackSpeed";
		public static final String MAX_HEALTH = "generic.maxHealth";
		public static final String MOVEMENT_SPEED = "generic.movementSpeed";
		public static final String KNOCKBACK_RESISTANCE = "generic.knockbackResistance";
		public static final String LUCK = "generic.luck";
		public static final String ARMOR = "generic.armor";
		public static final String ARMOR_TOUGHNESS = "generic.armorToughness";
	}
	
	public static ItemStack setAttributes(ItemStack original, Single...attributes) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(original);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagList modifiers = compound.getList("AttributeModifiers", 10);
		if (modifiers == null)
			modifiers = new NBTTagList();
		for (Single attribute : attributes)
			setAttribute(modifiers, attribute.attribute, attribute.value, attribute.slot, attribute.operation);
		compound.set("AttributeModifiers", modifiers);
		nmsStack.setTag(compound);
		return CraftItemStack.asBukkitCopy(nmsStack);
	}
	
	public static ItemStack setAttribute(ItemStack original, String attribute, double value, String slot, int operation) {
		net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(original);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagList modifiers = compound.getList("AttributeModifiers", 10);
		if (modifiers == null)
			modifiers = new NBTTagList();
		setAttribute(modifiers, attribute, value, slot, operation);
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
		setAttribute(modifiers, "dummy", 0, "dummyslot", 0);
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
	
	private static void setAttribute(NBTTagList modifiers, String name, double value, String slot, int operation){
		NBTTagCompound damage = new NBTTagCompound();
		damage.set("AttributeName", new NBTTagString(name));
		damage.set("Name", new NBTTagString(name));
		damage.set("Amount", new NBTTagDouble(value));
		damage.set("Operation", new NBTTagInt(operation));
		damage.set("UUIDLeast", new NBTTagLong(System.currentTimeMillis()));
		damage.set("UUIDMost", new NBTTagLong(uniqueCounter++));
		damage.set("Slot", new NBTTagString(slot));
		modifiers.add(damage);
	}
}