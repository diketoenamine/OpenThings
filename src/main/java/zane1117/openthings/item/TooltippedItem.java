package zane1117.openthings.item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import zane1117.openthings.OpenThings;

import javax.annotation.Nullable;
import java.util.List;

public class TooltippedItem extends Item {

    private String tooltip;

    public TooltippedItem(String registryName, String tooltip) {
        this.setRegistryName(registryName);
        this.setTranslationKey("openthings." + registryName);
        this.tooltip = tooltip;
        this.setCreativeTab(OpenThings.OpenThingsTab);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (Keyboard.isCreated() && Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode())) {
            tooltip.add(TextFormatting.GRAY + this.getTooltip());
        } else {
            tooltip.add(TextFormatting.GRAY + "Hold [" + TextFormatting.WHITE + Minecraft.getMinecraft().gameSettings.keyBindSneak.getDisplayName() + TextFormatting.GRAY + "] for a detailed tooltip.");
        }
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getTooltip() {
        return tooltip;
    }
}
