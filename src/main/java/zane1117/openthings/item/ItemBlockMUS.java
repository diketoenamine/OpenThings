package zane1117.openthings.item;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import zane1117.openthings.OpenThings;

import javax.annotation.Nullable;
import java.util.List;

// Do I really need this?
public class ItemBlockMUS extends ItemBlock {
    public ItemBlockMUS() {
        super(OpenThings.ObjectHolder.blockSonar);
        this.setRegistryName(OpenThings.ObjectHolder.blockSonar.getRegistryName());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.DARK_PURPLE + "The name's 'sonar'.");
    }
}
