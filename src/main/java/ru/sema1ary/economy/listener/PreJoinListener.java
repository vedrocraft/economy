package ru.sema1ary.economy.listener;

import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import ru.sema1ary.economy.model.EconomyUser;
import ru.sema1ary.economy.service.EconomyService;

@RequiredArgsConstructor
public class PreJoinListener implements Listener {
    private final EconomyService economyService;

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent event) {
        String username = event.getName();

        if(username.isEmpty()) {
            return;
        }

        if(economyService.findByUsername(username).isEmpty()) {
            economyService.save(EconomyUser.builder()
                    .username(username)
                    .balance(0)
                    .build());
        }
    }
}
