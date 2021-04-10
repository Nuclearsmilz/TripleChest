package timelord.triplechest.inventory;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.ARBTextureFilterAnisotropic;

public class TripleInventory implements Inventory {
	
	private final Inventory first;
	private final Inventory second;
	private final Inventory third;
	
	public TripleInventory (Inventory first, Inventory second, Inventory third) {
		if (first == null) {
			first = second;
		}
		
		if (second == null) {
			second = third;
		}
		
		if (third == null) {
			third = first;
		}
		
		this.first = first;
		this.second = second;
		this.third = third;
	}
	
	@Override
	public int size () {
		return this.first.size() + this.second.size() + this.third.size();
	}
	
	@Override
	public boolean isEmpty () {
		return this.first.isEmpty() && this.second.isEmpty() && this.third.isEmpty();
	}
	
	public boolean isPart (Inventory inventory) {
		return this.first == inventory || this.second == inventory || this.third == inventory;
	}
	
	@Override
	public ItemStack getStack (int slot) {
		return slot >= this.first.size() ? this.second.getStack(slot - this.first.size()) : this.third.getStack(slot);
	}
	
	@Override
	public ItemStack removeStack (int slot, int amount) {
		return slot >= this.first.size() ? this.second.removeStack(slot - this.first.size(), amount) : this.third.removeStack(slot, amount);
	}
	
	@Override
	public ItemStack removeStack (int slot) {
		return slot >= this.first.size() ? this.second.removeStack(slot - this.first.size()) : this.third.removeStack(slot);
	}
	
	@Override
	public void setStack (int slot, ItemStack stack) {
		if (slot >= this.first.size()) {
			this.second.setStack(slot - this.first.size(), stack);
		} else if (slot >= this.second.size()) {
			this.third.setStack(slot, stack);
		} else {
			this.first.setStack(slot, stack);
		}
	}
	
	@Override
	public void markDirty () {
		this.first.markDirty();
		this.second.markDirty();
		this.third.markDirty();
	}
	
	@Override
	public int getMaxCountPerStack () {
		return this.first.getMaxCountPerStack();
	}
	
	@Override
	public boolean canPlayerUse (PlayerEntity player) {
		return this.first.canPlayerUse(player) && this.second.canPlayerUse(player) && this.third.canPlayerUse(player);
	}
	
	@Override
	public void clear () {
		this.first.clear();
		this.second.clear();
		this.third.clear();
	}
	
	@Override
	public void onOpen (PlayerEntity player) {
		this.first.onOpen(player);
		this.second.onOpen(player);
		this.third.onOpen(player);
	}
	
	@Override
	public void onClose (PlayerEntity player) {
		this.first.onClose(player);
		this.second.onClose(player);
		this.third.onClose(player);
	}
	
	@Override
	public boolean isValid (int slot, ItemStack stack) {
		return slot >= this.first.size() ? this.second.isValid(slot - this.first.size(), stack) : this.third.isValid(slot, stack);
	}
}
