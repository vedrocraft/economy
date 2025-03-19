package ru.sema1ary.economy.service.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import ru.sema1ary.economy.dao.EconomyUserDao;
import ru.sema1ary.economy.model.EconomyUser;
import ru.sema1ary.economy.service.EconomyService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class EconomyServiceImpl implements EconomyService {
    private final EconomyUserDao userDao;

    @Override
    public EconomyUser save(@NonNull EconomyUser economyUser) {
        try {
            return userDao.save(economyUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(@NonNull List<EconomyUser> list) {
        try {
            userDao.saveAll(list);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<EconomyUser> findById(Long aLong) {
        try {
            return userDao.findById(aLong);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<EconomyUser> findByUsername(@NonNull String s) {
        try {
            return userDao.findByUsername(s);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<EconomyUser> findAll() {
        try {
            return userDao.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EconomyUser getUser(@NonNull String s) {
        return findByUsername(s).orElseGet(() -> save(EconomyUser.builder()
                .username(s)
                .balance(0)
                .build()));
    }

    @Override
    public void give(@NonNull Player player, int amount) {
        EconomyUser user = getUser(player.getName());
        user.setBalance(user.getBalance() + amount);
        save(user);
    }

    @Override
    public void withdraw(@NonNull Player player, int amount) {
        EconomyUser user = getUser(player.getName());
        user.setBalance(user.getBalance() - amount);
        save(user);
    }

    @Override
    public boolean isEnoughMoney(@NonNull Player player, int amount) {
        return getUser(player.getName()).getBalance() >= amount;
    }

    @Override
    public boolean withdrawWithMoneyCheck(@NonNull Player player, int amount) {
        if(!isEnoughMoney(player, amount)) {
            return false;
        }

        withdraw(player, amount);
        return true;
    }
}
