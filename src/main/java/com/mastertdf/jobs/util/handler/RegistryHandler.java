package com.mastertdf.jobs.util.handler;

import com.mastertdf.jobs.ModJobs;
import com.mastertdf.jobs.commands.CommandAdd;
import com.mastertdf.jobs.commands.CommandInfo;
import com.mastertdf.jobs.commands.CommandSet;
import com.mastertdf.jobs.commands.JobArgument;
import com.mastertdf.jobs.events.client.GuiEvents;
import com.mastertdf.jobs.events.server.BreakBlockEvents;
import com.mastertdf.jobs.events.server.CommonEvents;
import com.mastertdf.jobs.events.server.CraftItemEvents;
import com.mastertdf.jobs.events.server.KillEntityEvent;
import net.minecraft.command.arguments.ArgumentTypes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class RegistryHandler {

    public static void registerListeners() {
        MinecraftForge.EVENT_BUS.register(new GuiEvents());
        MinecraftForge.EVENT_BUS.register(new RegistryHandler());
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        MinecraftForge.EVENT_BUS.register(new BreakBlockEvents());
        MinecraftForge.EVENT_BUS.register(new KillEntityEvent());
        MinecraftForge.EVENT_BUS.register(new CraftItemEvents());
    }

    @SubscribeEvent
    public void onCommandsRegistered(RegisterCommandsEvent event) {
        if (!ArgumentTypes.isTypeRegistered(JobArgument.job()))
            ArgumentTypes.register("job", JobArgument.class, new JobArgument.Serializer());
        ModJobs.info("Command Arguments Registered", false);

        CommandInfo.register(event.getDispatcher());
        CommandSet.register(event.getDispatcher());
        CommandAdd.register(event.getDispatcher());
        ModJobs.info("Commands Registered", false);
    }

}
