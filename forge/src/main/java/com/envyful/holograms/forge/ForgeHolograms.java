package com.envyful.holograms.forge;


import com.envyful.api.forge.command.ForgeCommandFactory;
import com.envyful.holograms.api.hologram.Hologram;
import com.envyful.holograms.api.manager.HologramFactory;
import com.envyful.holograms.forge.command.HologramsCommand;
import com.envyful.holograms.forge.hologram.HologramManager;
import com.envyful.holograms.forge.hologram.manager.ForgeHologramManager;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

@Mod(
        modid = "holograms",
        name = "Holograms Forge",
        version = "0.0.1",
        acceptableRemoteVersions = "*"
)
public class ForgeHolograms {

    private static ForgeHolograms instance;

    private ForgeCommandFactory commandFactory = new ForgeCommandFactory();

    @Mod.EventHandler
    public void onServerStarting(FMLPreInitializationEvent event) {
        HologramFactory.setHologramManager(new ForgeHologramManager());
        instance = this;
    }

    @Mod.EventHandler
    public void onServerStarting(FMLServerStartingEvent event) {
        this.commandFactory.registerInjector(Hologram.class, (sender, args) -> {
            Hologram byId = HologramManager.getById(args[0]);

            if (byId == null) {
                sender.sendMessage(new TextComponentString("§4Cannot find a hologram with that id"));
                return null;
            }

            return byId;
        });

        this.commandFactory.registerCommand(event.getServer(), new HologramsCommand());
    }

    public static ForgeHolograms getInstance() {
        return instance;
    }
}
