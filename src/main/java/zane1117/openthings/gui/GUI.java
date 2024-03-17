package zane1117.openthings.gui;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import zane1117.openthings.tile.TECaseT4;

public enum GUI {
    CASE_T4(GuiChat.class, Container.class, TECaseT4.class);

    public final Class<? extends Gui> gui;
    public final Class<? extends Container> container;
    public final Class<? extends TileEntity> teClass;
    GUI(Class<? extends Gui> gui, Class<? extends Container> container, Class<? extends TileEntity> teClass) {
        this.gui = gui;
        this.container = container;
        this.teClass = teClass;
    }
}
