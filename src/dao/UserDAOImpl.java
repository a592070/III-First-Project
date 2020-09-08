package dao;

import connections.DBConnectionPool;
import dao.InitTable.CreateAccount;
import dao.delete.UserDelete;
import dao.insert.UserInsert;
import dao.query.UserQuery;
import dao.update.UserUpdate;
import pojo.UserDO;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl implements DataBaseDAO<pojo.UserDO> {
    private UserDO user;

    public UserDAOImpl(UserDO user) {
        this.user = user;
    }

    public UserDAOImpl() {
    }

    @Override
    public List<pojo.UserDO> getLists() throws IOException, SQLException {
        return new UserQuery().listUsers();
    }

    @Override
    public pojo.UserDO getElement(String id) throws IOException, SQLException {
        return new UserQuery().query(id);
    }

    @Override
    public boolean isExist() throws IOException, SQLException {
        return isExist(user);
    }
    public boolean isExist(UserDO user) throws IOException, SQLException {
        return new UserQuery().isUserExist(user);
    }

    @Override
    public boolean setElement(pojo.UserDO ele) throws IOException, SQLException {
        return new UserUpdate(ele).update();
    }

    @Override
    public boolean addElement(pojo.UserDO ele) throws IOException, SQLException {
        return new UserInsert().insert(ele);
    }

    @Override
    public UserDO remove(pojo.UserDO ele) throws IOException, SQLException {
        return new UserDelete(ele).delete();
    }

    @Override
    public boolean isEmpty() throws IOException, SQLException {
        return new CreateAccount().isExist();
    }
}
