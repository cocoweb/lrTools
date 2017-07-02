package com.foresee.test.util.jodd.madvoc;

import java.util.List;

import jodd.joy.madvoc.action.AppAction;
import jodd.jtx.meta.Transaction;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;

@MadvocAction
public class UserAction extends AppAction {
	@PetiteInject
	UserService userService;
	@Out
	List<User> users;

	/**
	 * Mapped to /index.html page. Calls a service.
	 */
	@Action
	@Transaction
	public void findAll() {
		users = userService.findAllUsers();
	}
}