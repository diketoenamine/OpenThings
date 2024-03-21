package zane1117.openthings.tile;

import li.cil.oc.api.machine.Machine;
import li.cil.oc.api.machine.MachineHost;
import li.cil.oc.api.network.Node;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import zane1117.openthings.impl.T4CaseItemHandler;

import javax.annotation.Nullable;

public class TileCaseT4 extends TileEntityEnvironment implements MachineHost, ICapabilityProvider {
    private Machine machine;
    private T4CaseItemHandler inventory = new T4CaseItemHandler();

    public TileCaseT4() {
        super();

    }

    @Override
    public Machine machine() {
        return this.machine;
    }



    @Override
    public Iterable<ItemStack> internalComponents() {
        return null;
    }

    @Override
    public int componentSlot(String s) {
        return 0;
    }

    @Override
    public void onMachineConnect(Node node) {

    }

    @Override
    public void onMachineDisconnect(Node node) {

    }

    @Override
    public World world() {
        return this.getWorld();
    }

    @Override
    public double xPosition() {
        return this.getPos().getX();
    }

    @Override
    public double yPosition() {
        return this.getPos().getY();
    }

    @Override
    public double zPosition() {
        return this.getPos().getZ();
    }

    @Override
    public void markChanged() {

    }

    @Override
    public Node node() {
        return machine != null ? machine().node() : null;
    }

    @Override
    public void onConnect(Node node) {
        super.onConnect(node);
        if (node == node()) {

        }
    }

    @Override
    public void onDisconnect(Node node) {
        super.onDisconnect(node);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return (T) inventory;
        }
        return super.getCapability(capability,facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability,facing);
    }
}
