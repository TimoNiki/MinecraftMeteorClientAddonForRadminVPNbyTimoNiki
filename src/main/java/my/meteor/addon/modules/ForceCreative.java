package my.meteor.addon.modules;

import meteordevelopment.meteorclient.events.world.TickEvent;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.GameMode;

public class ForceCreative extends Module {
    public ForceCreative() {
        super(my.meteor.addon.Addon.CATEGORY, "force-creative", "Выдает ГМ 1 всем игрокам, кроме вас и хоста.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (mc.world == null || mc.player == null) return;

        for (PlayerEntity player : mc.world.getPlayers()) {
            String name = player.getEntityName();
            if (!name.equals(mc.player.getEntityName()) && !player.isCreativeLevelTwoOp()) { 
                if (mc.getNetworkHandler().getPlayerListEntry(player.getUuid()) != null && 
                    mc.getNetworkHandler().getPlayerListEntry(player.getUuid()).getGameMode() != GameMode.CREATIVE) {
                    mc.player.networkHandler.sendCommand("gamemode creative " + name);
                }
            }
        }
    }
}
