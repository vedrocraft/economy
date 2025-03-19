package ru.sema1ary.economy.dao.impl;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.field.SqlType;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import lombok.NonNull;
import ru.sema1ary.economy.dao.EconomyUserDao;
import ru.sema1ary.economy.model.EconomyUser;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;

public class EconomyUserDaoImpl extends BaseDaoImpl<EconomyUser, Long> implements EconomyUserDao {
    public EconomyUserDaoImpl(ConnectionSource connectionSource, Class<EconomyUser> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    @Override
    public EconomyUser save(@NonNull EconomyUser user) throws SQLException {
        createOrUpdate(user);
        return user;
    }

    @Override
    public void saveAll(@NonNull List<EconomyUser> users) throws SQLException {
        callBatchTasks((Callable<Void>) () -> {
            for (EconomyUser user : users) {
                createOrUpdate(user);
            }
            return null;
        });
    }

    @Override
    public Optional<EconomyUser> findById(Long id) throws SQLException {
        EconomyUser result = queryForId(id);
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<EconomyUser> findByUsername(@NonNull String username) throws SQLException {
        QueryBuilder<EconomyUser, Long> queryBuilder = queryBuilder();
        Where<EconomyUser, Long> where = queryBuilder.where();
        String columnName = "username";

        SelectArg selectArg = new SelectArg(SqlType.STRING, username.toLowerCase());
        where.raw("LOWER(" + columnName + ")" + " = LOWER(?)", selectArg);
        return Optional.ofNullable(queryBuilder.queryForFirst());
    }

    @Override
    public List<EconomyUser> findAll() throws SQLException {
        return queryForAll();
    }
}
