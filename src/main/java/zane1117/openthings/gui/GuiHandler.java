package zane1117.openthings.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class GuiHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        try {
            GUI enumType = GUI.values()[ID];
            Constructor<? extends Container> constructor = enumType.container.getDeclaredConstructor(InventoryPlayer.class, enumType.teClass);
            return constructor.newInstance(player.inventory, world.getTileEntity(new BlockPos(x,y,z)));
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        try {
            GUI enumType = GUI.values()[ID];
            Constructor<? extends Gui> constructor = enumType.gui.getDeclaredConstructor(InventoryPlayer.class, enumType.teClass);
            return constructor.newInstance(player.inventory, world.getTileEntity(new BlockPos(x,y,z)));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
