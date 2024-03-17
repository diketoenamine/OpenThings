package zane1117.openthings.tile;

import li.cil.oc.api.Network;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.Connector;
import li.cil.oc.api.network.Visibility;
import li.cil.oc.api.prefab.TileEntityEnvironment;
import net.minecraft.command.server.CommandListPlayers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import zane1117.openthings.OpenThings;

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
        if (node != null && isTracking) {
            // Consume some energy per tick to keep the sonar running!
            hasEnergy = ((Connector) node).tryChangeBuffer(-energyPerTick);
        }
    }

    @Callback(doc = "function(playerName:string):boolean -- Begins tracking the player with the specified name. Returns true if the player is being tracked, and false if the call failed.")
    public Object[] startTracking(Context context, Arguments args) {
        if (this.isTracking) {return new Object[]{false};}
        World world = this.getWorld();
        MinecraftServer server = world.getMinecraftServer();
        if (server == null) {
            OpenThings.LOGGER.error("Server is null!"); return new Object[]{false};}
        PlayerList players = server.getPlayerList();
        EntityPlayerMP player = players.getPlayerByUsername(args.checkString(0));
        if (player != null) {
            this.isTracking = true;
            this.currentlyTrackedPlr = player;
            player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "You feel a strange prescence, almost as if a far-away machine is watching you..."));
            return new Object[]{true};
        } else {
            OpenThings.LOGGER.error("Player is null!");
            return new Object[]{false};
        }
    }

    @Callback(doc = "function():nil -- Stops tracking the currently tracked player, if any.")
    public Object[] endTracking(Context context, Arguments args) {
        this.isTracking = false;
        currentlyTrackedPlr.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Your unusual feeling has subsided. It was probably just paranoia..."));
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

    @Callback(doc = "function():tuple -- Gets and returns the dimension ID that the currently tracked player is in.")
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
}
