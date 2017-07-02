package com.foresee.test.util.jodd.madvoc;

import jodd.db.oom.meta.DbColumn;
import jodd.db.oom.meta.DbTable;

/**
 * User model object.
 */
@DbTable
public class User extends DbEntity {
	
	@DbColumn
	private String username;
	
	@DbColumn
	private String password;
	
	@DbColumn("roleName")
	private String roleName;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
}