package com.mastertdf.jobs.events.client;


import com.mastertdf.jobs.gui.screens.MainJobsMenu;
import com.mastertdf.jobs.network.PacketAskClientUpdate;
import com.mastertdf.jobs.util.handler.PacketHandler;
import com.mastertdf.jobs.util.keybindings.KeyBindings;
import com.mastertdf.jobs.util.keybindings.Keys.Key;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber
public class KeyBindingsEvent {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void handleKeyBindings(KeyInputEvent event) {
        Key keyPressed = getPressedKey();
        if (keyPressed == Key.OPEN_GUI) {
            if (Minecraft.getInstance().screen == null) {
                PacketHandler.INSTANCE.sendToServer(new PacketAskClientUpdate());
                Minecraft.getInstance().setScreen(new MainJobsMenu());
            }
        }

    }


    private static Key getPressedKey() {
        if (KeyBindings.open_gui.isDown())
            return Key.OPEN_GUI;

        else return Key.NONE;
    }
}
