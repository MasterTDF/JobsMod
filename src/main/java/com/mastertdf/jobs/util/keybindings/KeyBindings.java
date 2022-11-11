package com.mastertdf.jobs.util.keybindings;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class KeyBindings {

    public static KeyBinding open_gui = new KeyBinding(Keys.OPEN_GUI, KeyConflictContext.IN_GAME, KeyModifier.NONE, getKey(GLFW.GLFW_KEY_J), Keys.CATEGORY);

    private static InputMappings.Input getKey(int key) {
        return InputMappings.Type.KEYSYM.getOrCreate(key);
    }

    public static void register() {
        ClientRegistry.registerKeyBinding(KeyBindings.open_gui);
    }

}
