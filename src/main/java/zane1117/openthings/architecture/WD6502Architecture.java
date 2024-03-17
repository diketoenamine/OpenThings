package zane1117.openthings.architecture;

import li.cil.oc.api.machine.Machine;
import li.cil.oc.api.machine.Architecture;
import li.cil.oc.api.machine.ExecutionResult;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import zane1117.openthings.OpenThings;

@Architecture.Name("WD65C02S (WIP, DO NOT USE)")
public class WD6502Architecture implements Architecture {

    private final Machine machine;

    public WD6502Architecture(Machine machine) {
        this.machine = machine;
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    @Override
    public boolean recomputeMemory(Iterable<ItemStack> iterable) {
        return true;
    }

    @Override
    public boolean initialize() {
        return true;
    }

    @Override
    public void close() {

    }

    @Override
    public void runSynchronized() {
        OpenThings.LOGGER.info("run synced");
    }

    @Override
    public ExecutionResult runThreaded(boolean b) {
        OpenThings.LOGGER.info("run threaded");
        return new ExecutionResult.Sleep(0);
    }

    @Override
    public void onSignal() {

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void load(NBTTagCompound nbtTagCompound) {

    }

    @Override
    public void save(NBTTagCompound nbtTagCompound) {

    }
}
