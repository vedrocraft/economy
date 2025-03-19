package ru.sema1ary.economy.placeholder;

import lombok.RequiredArgsConstructor;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import ru.sema1ary.economy.service.EconomyService;

@RequiredArgsConstructor
public class BalancePlaceholder extends PlaceholderExpansion {
    private final EconomyService economyService;

    @Override
    public @NotNull String getIdentifier() {
        return "balance";
    }

    @Override
    public @NotNull String getAuthor() {
        return "sema1ary";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String username = player.getName();

        if(username == null || username.isEmpty()) {
            return null;
        }

        return String.valueOf(economyService.getUser(username).getBalance());
    }
}
