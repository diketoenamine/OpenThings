package zane1117.openthings.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import zane1117.openthings.tile.TileMUS;

import javax.annotation.Nullable;

// It stands for "Multidimensional Ultraprecision Sonar"
public class BlockMUS extends Block {
    public BlockMUS() {
        super(Material.IRON);
        this.setRegistryName("sonar");
        this.setHardness(2f);
        this.setResistance(5f);
        this.setTranslationKey("openthings.sonar");
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileMUS();
    }
}
