package com.mastertdf.jobs.network;

import com.mastertdf.jobs.data.ClientInfos;
import com.mastertdf.jobs.util.Constants.Job;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class PacketAddXP {

    private int job;
    private long xpAdded;

    public PacketAddXP() {
    }

    public PacketAddXP(Job j, long xp) {
        this.job = j.index;
        this.xpAdded = xp;
    }

    public static PacketAddXP fromBytes(PacketBuffer buf) {
        return new PacketAddXP(Job.byIndex(buf.readInt()), buf.readLong());
    }


    public static void toBytes(PacketAddXP packet, PacketBuffer buf) {
        buf.writeInt(packet.job);
        buf.writeLong(packet.xpAdded);
    }


    public static void handle(PacketAddXP message, Supplier<Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ClientInfos.showAddGui(Job.byIndex(message.job), message.xpAdded);
        }
        ctx.get().setPacketHandled(true);
    }
}
