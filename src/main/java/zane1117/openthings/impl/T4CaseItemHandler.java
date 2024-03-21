package zane1117.openthings.impl;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;

public class T4CaseItemHandler implements IItemHandler {

    private ItemStack T4Card; // Slot 0
    private ItemStack[] T3Cards; // Slots 1-2
    private ItemStack T4CPU; // Slot 3
    private ItemStack[] T4Memory; // Slots 4-5
    private ItemStack T4HDD; // Slot 6
    private ItemStack T3HDD; // Slot 7
    private ItemStack Floppy; // Slot 8
    private ItemStack EEPROM; // Slot 9

    public T4CaseItemHandler() {
        this.T4Card = ItemStack.EMPTY;
        this.T3Cards = new ItemStack[2];
        this.T4CPU = ItemStack.EMPTY;
        this.T4Memory = new ItemStack[2];
        this.T4HDD = ItemStack.EMPTY;
        this.T3HDD = ItemStack.EMPTY;
        this.Floppy = ItemStack.EMPTY;
        this.EEPROM = ItemStack.EMPTY;
    }

    @Override
    public int getSlots() {
        return 10;
    }

    @Nonnull
    @Override
    public ItemStack getStackInSlot(int slot) {
        switch (slot) {
            case 0:
                return this.T4Card;
            case 1:
            case 2:
                return this.T3Cards[slot - 1];
            case 3:
                return this.T4CPU;
            case 4:
            case 5:
                return this.T4Memory[slot-4];
            case 6:
                return this.T4HDD;
            case 7:
                return this.T3HDD;
            case 8:
                return this.EEPROM;
            case 9:
                return this.Floppy;
            default:
                return ItemStack.EMPTY; // Probably a bad idea.
        }
    }

    public void forceStackIntoSlot(int slot, ItemStack stack) {
        switch (slot) {
            case 0:
                this.T4Card = stack;
            case 1:
            case 2:
                this.T3Cards[slot - 1] = stack;
            case 3:
                this.T4CPU = stack;
            case 4:
            case 5:
                this.T4Memory[slot-4] = stack;
            case 6:
                this.T4HDD = stack;
            case 7:
                this.T3HDD = stack;
            case 8:
                this.EEPROM = stack;
            case 9:
                this.Floppy = stack;
        }
    }

    @Nonnull
    @Override
    public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
        return null;
    }

    @Nonnull
    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        return null;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }


}
