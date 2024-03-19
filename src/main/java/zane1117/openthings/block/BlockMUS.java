package zane1117.openthings.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import zane1117.openthings.tile.TileMUS;

import javax.annotation.Nullable;

// It stands for "Multidimensional Ultraprecision Sonar"
public class BlockMUS extends Block {
    public static final PropertyBool TRACKING = PropertyBool.create("tracking"); // I had no idea PropertyBool existed until now.
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

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TRACKING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TRACKING) ? 1 : 0;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TRACKING, (meta == 1));
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(TRACKING) ? 15 : 0;
    }
}
