package zane1117.openthings.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import zane1117.openthings.OpenThings;
import zane1117.openthings.gui.GUI;
import zane1117.openthings.tile.TileCaseT4;

import javax.annotation.Nullable;

public class BlockCaseT4 extends Block {
    public BlockCaseT4() {
        super(Material.IRON);
        this.setRegistryName("case_t4");
        this.setHardness(2f);
        this.setResistance(5f);
        this.setTranslationKey("openthings.case_t4");
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileCaseT4();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            playerIn.openGui(OpenThings.INSTANCE, GUI.CASE_T4.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        }
        return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    }
}
