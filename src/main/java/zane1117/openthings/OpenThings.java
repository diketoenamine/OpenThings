package zane1117.openthings;

import li.cil.oc.api.Machine;
import li.cil.oc.common.block.Case;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import zane1117.openthings.architecture.WD6502Architecture;
import zane1117.openthings.block.BlockCaseT4;
import zane1117.openthings.item.TooltippedItem;
import zane1117.openthings.tile.TECaseT4;

import javax.tools.Tool;

@Mod(modid = OpenThings.MODID, name = OpenThings.NAME, version = OpenThings.VERSION, dependencies = OpenThings.DEPENDENCIES)
@Mod.EventBusSubscriber
public class OpenThings {
    public static final String MODID = "openthings";
    public static final String NAME = "OpenThings";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "required-after:opencomputers@1.8.3";

    @Mod.Instance
    public static final OpenThings INSTANCE = null;
    public static Logger LOGGER = LogManager.getLogger(MODID);
    public static CreativeTabs OpenThingsTab = new CreativeTabs("openthings") {
        @Override
        public ItemStack createIcon() {
            return ItemStack.EMPTY;
        }
    };

    public OpenThings() {} // This doesn't need to be here...

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        LOGGER.info("Registering items");
        ForgeRegistries.ITEMS.register(new TooltippedItem("emerald_chip", "A small piece of a once lustrous emerald. Your villagers will never want to see it again."));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Item> event) {
        LOGGER.info("Registering blocks");
        ForgeRegistries.BLOCKS.register(new BlockCaseT4());
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        GameRegistry.registerTileEntity(TECaseT4.class, new ResourceLocation("openthings", "case_t4"));
        Machine.add(WD6502Architecture.class);
    }

    @EventHandler
    public void init(FMLInitializationEvent e) {

    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {

    }
    
    @GameRegistry.ObjectHolder(MODID)
    public static class ObjectHolder {
        public static final Item emerald_chip = null;
    }
}
