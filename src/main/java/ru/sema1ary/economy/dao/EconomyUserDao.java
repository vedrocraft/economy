package ru.sema1ary.economy.dao;

import ru.sema1ary.economy.model.EconomyUser;
import ru.sema1ary.vedrocraftapi.ormlite.dao.UserDao;

public interface EconomyUserDao extends UserDao<EconomyUser, Long> {
}
