package com.mastertdf.jobs;


import com.mastertdf.jobs.util.handler.PacketHandler;
import com.mastertdf.jobs.util.handler.RegistryHandler;
import com.mastertdf.jobs.util.keybindings.KeyBindings;
import com.mastertdf.jobs.util.save.LoadUtil;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("jobs")
public class ModJobs {

    private static final Logger LOGGER = LogManager.getLogger();

    public ModJobs() {
        RegistryHandler.registerListeners();
        PacketHandler.registerPackets();
        info("Packets Registered", false);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public static void info(String message, boolean isError) {
        String msg = isError ? TextFormatting.RED + "[Jobs] " : TextFormatting.BLUE + "[Jobs] ";
        msg += message;
        System.out.println(msg);
    }

    public void setup(final FMLCommonSetupEvent event) {

    }

    public void clientSetup(final FMLClientSetupEvent event) {
        KeyBindings.register();
        info("Keybindings Registered", false);
    }

    @SubscribeEvent
    public void serverStarting(FMLServerStartingEvent event) {
        LoadUtil.loadData(event.getServer());
        info("Data Loaded", false);

    }
}
