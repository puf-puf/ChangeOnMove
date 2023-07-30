package net.puf.changeonmove;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class ChangeOnMove extends JavaPlugin {

    private boolean changeOnMoveEnabled = false;
    private Material blockTypeToPlace = Material.DIRT;
    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new ChangeOnMoveListener(this), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(command.getName().equalsIgnoreCase("changeonmove")) {
            if(args.length == 1) {
                try {
                    if(blockTypeToPlace == Material.valueOf(args[0].toUpperCase())) {
                        changeOnMoveEnabled = !changeOnMoveEnabled;
                    } else {
                        blockTypeToPlace = Material.valueOf(args[0].toUpperCase());
                        sender.sendMessage(Component.text("Block type to place set to: ")
                                .append(Component.text(blockTypeToPlace.toString()))
                                .color(NamedTextColor.GREEN));
                        changeOnMoveEnabled = true;
                    }
                    String status = changeOnMoveEnabled ? "enabled" : "disabled";
                    sender.sendMessage("Block placement is now " + status + ".");
                    return true;

                } catch(IllegalArgumentException e) {

                    sender.sendMessage(Component.text("Invalid block type specified: ")
                            .append(Component.text(args[0]))
                            .color(NamedTextColor.RED));
                    return false;
                }
            } else if(args.length > 1) {
                sender.sendMessage(Component.text("Too much arguments specified")
                        .color(NamedTextColor.RED));
                return false;
            }
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 1) {
            List<String> blockList = new ArrayList<>();
            for(Material material : Material.values()) {
                if(material.isBlock()) blockList.add(material.toString().toLowerCase());
            }
            return blockList;
        }
        return null;
    }

    public boolean isChangeOnMoveEnabled() {
        return changeOnMoveEnabled;
    }

    public Material getBlockTypeToPlace() {
        return blockTypeToPlace;
    }

}
