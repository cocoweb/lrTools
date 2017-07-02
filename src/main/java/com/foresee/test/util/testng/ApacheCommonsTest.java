package com.foresee.test.util.testng;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.collections4.iterators.FilterIterator;
import org.apache.commons.collections4.iterators.UniqueFilterIterator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ApacheCommonsTest {
	public class UserGuava {
		private String name;
		private int age;

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + age;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			UserGuava other = (UserGuava) obj;
			if (age != other.age)
				return false;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

		public UserGuava() {

		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}
	}

	List<UserGuava> list = new ArrayList<UserGuava>();

	@BeforeClass
	public void setUp() throws Exception {

		UserGuava user = new UserGuava();
		user.setAge(1);
		user.setName("Apple");
		list.add(user);
		user = new UserGuava();
		user.setAge(2);
		user.setName("Facebook");
		list.add(user);
		user = new UserGuava();
		user.setAge(3);
		user.setName("Twitter");
		list.add(user);
		user = new UserGuava();
		user.setAge(4);
		user.setName("Youtube");
		list.add(user);
		user = new UserGuava();
		user.setAge(5);
		user.setName("Telerik");
		list.add(user);
		user = new UserGuava();
		user.setAge(6);
		user.setName("Google");
		list.add(user);
		user = new UserGuava();
		user.setAge(6);
		user.setName("Google");
		list.add(user);
	}

	@Test
	public void f() {
		Predicate<UserGuava> predicate = new Predicate<UserGuava>() {
			@Override
			public boolean evaluate(UserGuava arg0) {
				UserGuava u = (UserGuava) arg0;
				return u.getAge() > 5;
			}
		};
		Iterator<UserGuava> iterator = new FilterIterator<UserGuava>(list.iterator(), predicate);
		
		
		Iterator<UserGuava> iterator0 = new FilterIterator<UserGuava>(list.iterator(),new Predicate<UserGuava>(){

			@Override
			public boolean evaluate(UserGuava arg0) {
				
				return  arg0.getAge()>4;
			}
			
		});
		while (iterator0.hasNext()) {
			UserGuava u = iterator0.next();
			System.out.println(u.getName() + "," + u.getAge());
		}
		System.out.println("-------------------你懂的------------------------");
		Iterator<?> iterator1 = new UniqueFilterIterator<Object>(list.iterator());
		while (iterator1.hasNext()) {
			UserGuava u = (UserGuava) iterator1.next();
			System.out.println(u.getName() + "," + u.getAge());
		}
	}
}