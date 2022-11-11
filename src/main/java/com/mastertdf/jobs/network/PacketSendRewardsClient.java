package com.mastertdf.jobs.network;


import com.mastertdf.jobs.data.ClientInfos;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class PacketSendRewardsClient {

    private final List<ItemStack> stacks = new ArrayList<>();

    public PacketSendRewardsClient() {
    }

    public PacketSendRewardsClient(List<ItemStack> rewards) {
        for (ItemStack s : rewards)
            stacks.add(s.copy());
    }


    public static PacketSendRewardsClient fromBytes(PacketBuffer buf) {
        PacketSendRewardsClient packet = new PacketSendRewardsClient();
        int size = buf.readInt();
        for (int i = 0; i < size; i++) {
            Item item = Item.byId(buf.readInt());
            int count = buf.readInt();
            packet.stacks.add(new ItemStack(item, count));
        }
        return packet;
    }


    public static void toBytes(PacketSendRewardsClient packet, PacketBuffer buf) {
        buf.writeInt(packet.stacks.size());
        for (ItemStack s : packet.stacks) {
            buf.writeInt(Item.getId(s.getItem()));
            buf.writeInt(s.getCount());
        }

    }

    public static void handle(PacketSendRewardsClient m, Supplier<Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ClientInfos.CURRENT_REWARDS.clear();
            for (ItemStack s : m.stacks)
                ClientInfos.CURRENT_REWARDS.add(s.copy());
        }
        ctx.get().setPacketHandled(true);
    }
}
