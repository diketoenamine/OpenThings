package zane1117.openthings.tile;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Connector;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import zane1117.openthings.OpenThings;
import zane1117.openthings.block.BlockMUS;

// Basically just repurposed example code. Do I care? No!
public class TileMUS extends TileEntityEnvironment implements ITickable {

    public static final double energyPerTick = 4; // Tracking players from large distances is expensive!

    protected boolean isTracking;
    protected EntityPlayer currentlyTrackedPlr;
    protected boolean hasEnergy;

    public TileMUS() {
        node = Network.newNode(this, Visibility.Network).
                withConnector().
                withComponent("sonar").
                create();
    }

    @Override
    public void update() {
        if (!this.getWorld().isRemote) {
            if (node != null && isTracking) {
                // Consume some energy per tick to keep the sonar running!
                hasEnergy = ((Connector) node).tryChangeBuffer(-energyPerTick);
                if (!hasEnergy) {
                    OpenThings.LOGGER.info("MUS at coordinates " + this.getPos() + " has ran out of energy, stopping tracking");
                    forceEndTracking();
                    return;
                }
                this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(BlockMUS.TRACKING, true));
            } else {
                this.getWorld().setBlockState(this.getPos(), this.getWorld().getBlockState(this.getPos()).withProperty(BlockMUS.TRACKING, false));
            }
        }
    }

    @Callback(doc = "function(playerName:string):boolean -- Begins tracking the player with the specified name. Returns true if the call succeeded, false and an error message if not.")
    public Object[] startTracking(Context context, Arguments args) {
        if (this.isTracking) {return new Object[]{false, "already tracking"};}
        FMLCommonHandler handler = FMLCommonHandler.instance();
        MinecraftServer server = handler.getMinecraftServerInstance();
        if (server == null) {
            OpenThings.LOGGER.error("Server is null!"); return new Object[]{false, "server doesn't exist"};}
        PlayerList players = server.getPlayerList();
        EntityPlayerMP player = players.getPlayerByUsername(args.checkString(0));
        if (player != null) {
            this.isTracking = true;
            this.currentlyTrackedPlr = player;
            player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "You feel a strange presence, almost as if a far-away machine is watching you..."));
            return new Object[]{true};
        } else {
            OpenThings.LOGGER.error("Player is null!");
            return new Object[]{false, "player doesn't exist"};
        }
    }

    @Callback(doc = "function():nil -- Stops tracking the currently tracked player, if any.")
    public Object[] endTracking(Context context, Arguments args) {
        this.isTracking = false;
        if (currentlyTrackedPlr != null) {
            currentlyTrackedPlr.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Your unusual feeling has subsided. It was probably just paranoia..."));
        }
        this.currentlyTrackedPlr = null;
        return new Object[]{};
    }

    @Callback(doc = "function():tuple -- Gets and returns the X, Y, and Z coordinates of the currently tracked player.")
    public Object[] getCoordinates(Context context, Arguments args) {
        if (this.isTracking) {
            BlockPos position = this.currentlyTrackedPlr.getPosition();
            context.pause(1.2);
            return new Object[]{position.getX(), position.getY(), position.getZ()};
        } else {
            return new Object[]{};
        }
    }

    @Callback(doc = "function():number -- Gets and returns the dimension ID that the currently tracked player is in.")
    public Object[] getDimension(Context context, Arguments args) {
        if (this.isTracking) {
            context.pause(1.2);
            return new Object[]{this.currentlyTrackedPlr.dimension};
        } else {
            return new Object[]{};
        }
    }

    @Callback(doc = "function():number -- Gets and returns the health of the currently tracked player.")
    public Object[] getHealth(Context context, Arguments args) {
        if (this.isTracking) {
            context.pause(1.2);
            return new Object[]{this.currentlyTrackedPlr.getHealth()};
        } else {
            return new Object[]{};
        }
    }

    @Callback(doc = "function():boolean -- Returns true if the sonar is currently tracking a player.")
    public Object[] isTracking(Context context, Arguments args) {
        return new Object[]{this.isTracking};
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
        return oldState.getBlock() != newState.getBlock();
    }

    protected void forceEndTracking() {
        this.isTracking = false;
        if (currentlyTrackedPlr != null) {
            currentlyTrackedPlr.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Your unusual feeling has subsided. It was probably just paranoia..."));
        }
        this.currentlyTrackedPlr = null;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound newNBT = super.writeToNBT(nbt);
        newNBT.setBoolean("tracking", this.isTracking);
        if (this.isTracking) {
            newNBT.setUniqueId("trackedPlrID", this.currentlyTrackedPlr.getUniqueID());
        }
        return newNBT;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.isTracking = nbt.getBoolean("tracking");

        if (this.isTracking) {
            // Get the entity player
            FMLCommonHandler handler = FMLCommonHandler.instance();

            if (handler.getEffectiveSide() == Side.SERVER) {
                MinecraftServer server = handler.getMinecraftServerInstance();
                PlayerList players = server.getPlayerList();

                this.currentlyTrackedPlr = players.getPlayerByUUID(nbt.getUniqueId("trackedPlrID"));

                if (currentlyTrackedPlr == null) {
                    OpenThings.LOGGER.warn("A sonar tracking a nonexistent player was loaded! Tracking will be force-ended.");
                    this.forceEndTracking();
                }
            }
        } else {
            this.currentlyTrackedPlr = null;
        }
    }
}
