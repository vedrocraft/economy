package ru.sema1ary.economy.command;

import dev.rollczi.litecommands.annotations.argument.Arg;
import dev.rollczi.litecommands.annotations.async.Async;
import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.sema1ary.economy.service.EconomyService;
import ru.sema1ary.vedrocraftapi.player.PlayerUtil;
import ru.sema1ary.vedrocraftapi.service.ConfigService;

@RequiredArgsConstructor
@Command(name = "economy", aliases = {"eco", "money", "cash"})
public class EconomyCommand {
    private final ConfigService configService;
    private final EconomyService economyService;

    @Async
    @Execute(name = "reload")
    @Permission("economy.reload")
    void reload(@Context CommandSender sender) {
        PlayerUtil.sendMessage(sender, (String) configService.get("reload-message"));
        configService.reload();
    }

    @Async
    @Execute(name = "give", aliases = {"add"})
    @Permission("economy.give")
    void give(@Context CommandSender sender, @Arg("игрок") Player target, @Arg("количество") int amount) {
        economyService.give(target, amount);
        PlayerUtil.sendMessage(sender,
                ((String) configService.get("give-message")).replace("{player}", target.getName())
                        .replace("{amount}", String.valueOf(amount))
        );
    }

    @Async
    @Execute(name = "withdraw", aliases = {"take", "remove"})
    @Permission("economy.withdraw")
    void withdraw(@Context CommandSender sender, @Arg("игрок") Player target, @Arg("количество") int amount) {
        if(!economyService.withdrawWithMoneyCheck(target, amount)) {
            PlayerUtil.sendMessage(sender, (String) configService.get("withdraw-error-message"));
            return;
        }

        PlayerUtil.sendMessage(sender, (String) configService.get("withdraw-successful-message"));
    }
}
