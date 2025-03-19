package ru.sema1ary.economy;

import lombok.SneakyThrows;
import org.bukkit.plugin.java.JavaPlugin;
import ru.sema1ary.economy.command.EconomyCommand;
import ru.sema1ary.economy.listener.PreJoinListener;
import ru.sema1ary.economy.model.EconomyUser;
import ru.sema1ary.economy.placeholder.BalancePlaceholder;
import ru.sema1ary.economy.service.EconomyService;
import ru.sema1ary.economy.service.impl.EconomyServiceImpl;
import ru.sema1ary.vedrocraftapi.BaseCommons;
import ru.sema1ary.vedrocraftapi.command.LiteCommandBuilder;
import ru.sema1ary.vedrocraftapi.ormlite.ConnectionSourceUtil;
import ru.sema1ary.vedrocraftapi.ormlite.DatabaseUtil;
import ru.sema1ary.vedrocraftapi.service.ConfigService;
import ru.sema1ary.vedrocraftapi.service.ServiceManager;
import ru.sema1ary.vedrocraftapi.service.impl.ConfigServiceImpl;

import javax.swing.text.BadLocationException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Economy extends JavaPlugin implements BaseCommons {

    @Override
    public void onEnable() {
        ServiceManager.registerService(ConfigService.class, new ConfigServiceImpl(this));

        DatabaseUtil.initConnectionSource(
                this,
                getService(ConfigService.class),
                EconomyUser.class);

        ServiceManager.registerService(EconomyService.class, new EconomyServiceImpl(
                getDao(EconomyUser.class)
        ));

        getServer().getPluginManager().registerEvents(new PreJoinListener(
                getService(EconomyService.class)
        ), this);

        LiteCommandBuilder.builder()
                .commands(new EconomyCommand(
                        getService(ConfigService.class),
                        getService(EconomyService.class)
                ))
                .build();

        if(getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new BalancePlaceholder(
                    getService(EconomyService.class)
            ).register();
        }
    }

    @Override
    public void onDisable() {
        ConnectionSourceUtil.closeConnection(true);
    }
}
