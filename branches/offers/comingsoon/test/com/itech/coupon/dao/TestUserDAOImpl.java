package com.itech.coupon.dao;

import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;

import com.itech.common.test.CommonTestDao;
import com.itech.coupon.model.Gender;
import com.itech.coupon.model.LoginType;
import com.itech.coupon.model.User;
import com.itech.coupon.model.constants.UserModelConstants;

public class TestUserDAOImpl extends CommonTestDao<User>{
	private UserDAO userDAO;
	private final String [] ignoreCols = {"ID", "NAME", "PASSWORD",
			"LOCATION", "LANGUAGE", "EMAIL_ID"};

	@Override
	public void setUp() throws Exception {
		super.setUp();
		userDAO = new UserDAOImpl();
		((UserDAOImpl)userDAO).setConnection(con);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetUserById_userExists() throws Exception {
		executeCleanInsert("TestUserDAOImpl_AllTest_in.xml");
		User user = userDAO.getByUserId("ashish");
		assertNotNull("User is Null", user);
		assertEquals("ashish", user.getUserId());
	}

	public void testGetUserById_userNotExists() throws Exception {
		executeCleanInsert("TestUserDAOImpl_AllTest_in.xml");
		User user = userDAO.getByUserId("ankit");
		assertNull("User is Null", user);
	}

	public void testAddOrUpdate() throws Exception {
		executeCleanInsert("TestUserDAOImpl_AllTest_in.xml");
		User user = new User();
		user.setUserId("ankit");
		user.setAge(31);
		user.setGender(Gender.M);
		user.setLoginType(LoginType.INTERNAL);
		userDAO.addOrUpdate(user);
		QueryDataSet tableData = getQueryDataSet(UserModelConstants.TABLE_USER , getSelectAllQuery());

		IDataSet expectedTableData = getFlatXmlDataSet("TestUserDAOImpl_addOrUpdateUser_out.xml");

		compareDataSets(expectedTableData,tableData, UserModelConstants.TABLE_USER,ignoreCols);
	}

	private String getSelectAllQuery(){
		return "select * from " + UserModelConstants.TABLE_USER  ;
	}


}
