package com.foresee.test.util.jodd.madvoc;

import java.util.List;

import jodd.db.oom.DbOomQuery;
import jodd.joy.db.AppDao;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;
import static jodd.db.oom.sqlgen.DbSqlBuilder.sql;
import static jodd.db.oom.DbOomQuery.query;

@PetiteBean
public class UserService {
	@PetiteInject
	AppDao appDao;

	public List<User> findAllUsers() {
		DbOomQuery query = query(sql("select $C{user.*} from $T{User user}"));
		return query.list(User.class);
	}
}