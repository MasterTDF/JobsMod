package com.mastertdf.jobs.events.server;

import com.mastertdf.jobs.data.GainXPUtil;
import com.mastertdf.jobs.data.JobsInfo;
import com.mastertdf.jobs.data.PlayerData;
import com.mastertdf.jobs.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class CommonEvents {

    @SubscribeEvent
    public void onEntityCreating(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject().level.isClientSide) return;
        if (!(event.getObject() instanceof PlayerEntity)) return;
        if (!event.getObject().getCapability(PlayerData.JOBS, null).isPresent()) {
            event.addCapability(new ResourceLocation(Reference.MOD_ID, "jobs"), new PlayerData.JobsDispatcher());
        }
    }

    @SubscribeEvent
    public void onEntityCloned(PlayerEvent.Clone event) {
        if (!event.isWasDeath()) return;
        if (event.getOriginal().getCapability(PlayerData.JOBS, null).isPresent()) {
            JobsInfo old_jobs = event.getOriginal().getCapability(PlayerData.JOBS, null).resolve().get();
            JobsInfo new_jobs = PlayerData.getPlayerJobs(event.getPlayer());
            new_jobs.copy(old_jobs);
        }
    }

    @SubscribeEvent
    public void onPlayerJoined(EntityJoinWorldEvent event) {
        if (!(event.getEntity() instanceof ServerPlayerEntity)) return;
        GainXPUtil.sendDataToClient((ServerPlayerEntity) event.getEntity());
    }

}
