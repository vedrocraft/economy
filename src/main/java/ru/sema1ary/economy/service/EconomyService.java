package ru.sema1ary.economy.service;

import lombok.NonNull;
import org.bukkit.entity.Player;
import ru.sema1ary.economy.model.EconomyUser;
import ru.sema1ary.vedrocraftapi.service.UserService;

public interface EconomyService extends UserService<EconomyUser> {
    void give(@NonNull Player player, int amount);

    void withdraw(@NonNull Player player, int amount);

    boolean isEnoughMoney(@NonNull Player player, int amount);

    boolean withdrawWithMoneyCheck(@NonNull Player player, int amount);
}
