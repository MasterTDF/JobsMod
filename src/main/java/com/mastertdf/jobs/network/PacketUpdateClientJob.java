package com.mastertdf.jobs.network;

import com.mastertdf.jobs.data.ClientInfos;
import com.mastertdf.jobs.data.JobsInfo;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class PacketUpdateClientJob {

    private long[] xps = new long[]{0, 0, 0, 0};

    public PacketUpdateClientJob() {
    }

    public PacketUpdateClientJob(long[] xp_values) {
        this.xps = xp_values;
    }


    public static PacketUpdateClientJob fromBytes(PacketBuffer buf) {
        PacketUpdateClientJob packet = new PacketUpdateClientJob();
        for (int i = 0; i < 4; i++)
            packet.xps[i] = buf.readLong();
        return packet;
    }


    public static void toBytes(PacketUpdateClientJob packet, PacketBuffer buf) {
        for (int i = 0; i < 4; i++)
            buf.writeLong(packet.xps[i]);
    }

    public static void handle(PacketUpdateClientJob message, Supplier<Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ClientInfos.job = new JobsInfo().fromTotalXPs(message.xps);
        }

        ctx.get().setPacketHandled(true);
    }
}
