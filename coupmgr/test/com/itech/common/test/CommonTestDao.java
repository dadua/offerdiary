package com.itech.common.test;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Map;
import java.util.Map.Entry;

import org.dbunit.Assertion;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.ext.mssql.InsertIdentityOperation;
import org.dbunit.ext.mysql.MySqlConnection;
import org.xml.sax.InputSource;

import com.itech.common.db.CommonBaseDAO;
import com.itech.common.db.DBConnectionManager;
import com.itech.common.server.ServerConfig;
import com.itech.common.test.db.CommonTestDatabase;
import com.itech.common.test.db.DbTestConnectionProxy;
import com.itech.common.test.db.DbunitConnectionProxy;

public abstract class CommonTestDao<T> extends CommonTestBase {
	protected static CommonTestDatabase tDb = CommonTestDatabase.getInstance();

	protected static final String ERROR_MSG_EXPECTED_DB_ERROR = "unknown DB error was expected";

	public CommonTestDao() {

	}
	private Connection _con ;
	protected Connection con;
	protected DatabaseConnection dbunitConnection;

	@Override
	protected void setUp() throws Exception {
		_con = tDb.getConnection();
		con = (Connection)DbTestConnectionProxy.createProxy(_con);

		dbunitConnection = new MySqlConnection((Connection)DbunitConnectionProxy.createProxy(_con), ServerConfig.dbName);
	}

	public void initDAO(CommonBaseDAO<T> dao) {
		//dao.setConnection(con);
	}


	@Override
	protected void tearDown() throws Exception {
		dbunitConnection = null;
		con = null;
		DBConnectionManager.rollback(_con);
		CommonTestDatabase.closeConnection(_con);
	}

	protected FlatXmlDataSet getFlatXmlDataSet(String xmlFileName) throws Exception{
		String path = getTestFileName(xmlFileName);
		FlatXmlDataSet dataset = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(path)));

		return dataset;
	}


	protected QueryDataSet getQueryDataSet(String tableName) throws Exception{
		return getQueryDataSet(tableName, "select * from "+tableName);
	}

	protected QueryDataSet getQueryDataSet(String tableName, String query) throws Exception{
		QueryDataSet dataSet = new QueryDataSet(dbunitConnection);
		dataSet.addTable(tableName,query);
		return dataSet;
	}

	protected CachedDataSet getCachedDataSet(String tableName, String query) throws Exception{
		return new CachedDataSet(getQueryDataSet(tableName, query));
	}

	protected CachedDataSet getCachedDataSet(String tableName) throws Exception {
		return new CachedDataSet(getQueryDataSet(tableName));
	}

	protected void executeCleanInsert(String xmlFileName) throws Exception {
		FlatXmlDataSet dataset = getFlatXmlDataSet(xmlFileName);
		InsertIdentityOperation.CLEAN_INSERT.execute(dbunitConnection, dataset);
	}

	protected void executeInsert(String xmlFileName) throws Exception {
		FlatXmlDataSet dataset = getFlatXmlDataSet(xmlFileName);
		InsertIdentityOperation.INSERT.execute(dbunitConnection, dataset);
	}

	protected void executeDelete(String xmlFileName) throws Exception {
		FlatXmlDataSet dataset = getFlatXmlDataSet(xmlFileName);
		InsertIdentityOperation.DELETE.execute(dbunitConnection, dataset);
	}

	protected void executeRefresh (String xmlFileName) throws Exception {
		FlatXmlDataSet dataset = getFlatXmlDataSet(xmlFileName);
		InsertIdentityOperation.REFRESH.execute(dbunitConnection, dataset);
	}

	/*
	 * In the following set of overloaded methods,
	 * the arg replacementMap provides a way to provide the set of values
	 * to be replaced before executing the XML data on DB:
	 * For a map entry, the Key comprises old value and the Value comprises new value
	 */

	protected void executeCleanInsert(String xmlFileName, Map<Object, Object> replacementMap) throws Exception {
		FlatXmlDataSet dataset = getFlatXmlDataSet(xmlFileName);
		ReplacementDataSet replacedDataSet = new ReplacementDataSet(dataset);
		for (Entry<Object, Object> entry: replacementMap.entrySet()) {
			replacedDataSet.addReplacementObject(entry.getKey(), entry.getValue());
		}

		InsertIdentityOperation.CLEAN_INSERT.execute(dbunitConnection, replacedDataSet);
	}

	protected void executeInsert(String xmlFileName, Map<Object, Object> replacementMap) throws Exception {
		FlatXmlDataSet dataset = getFlatXmlDataSet(xmlFileName);
		ReplacementDataSet replacedDataSet = new ReplacementDataSet(dataset);
		for (Entry<Object, Object> entry: replacementMap.entrySet()) {
			replacedDataSet.addReplacementObject(entry.getKey(), entry.getValue());
		}

		InsertIdentityOperation.INSERT.execute(dbunitConnection, replacedDataSet);
	}

	protected void executeDelete(String xmlFileName, Map<Object, Object> replacementMap) throws Exception {
		FlatXmlDataSet dataset = getFlatXmlDataSet(xmlFileName);
		ReplacementDataSet replacedDataSet = new ReplacementDataSet(dataset);
		for (Entry<Object, Object> entry: replacementMap.entrySet()) {
			replacedDataSet.addReplacementObject(entry.getKey(), entry.getValue());
		}

		InsertIdentityOperation.DELETE.execute(dbunitConnection, replacedDataSet);
	}

	protected void executeRefresh(String xmlFileName, Map<Object, Object> replacementMap) throws Exception {
		FlatXmlDataSet dataset = getFlatXmlDataSet(xmlFileName);
		ReplacementDataSet replacedDataSet = new ReplacementDataSet(dataset);
		for (Entry<Object, Object> entry: replacementMap.entrySet()) {
			replacedDataSet.addReplacementObject(entry.getKey(), entry.getValue());
		}

		InsertIdentityOperation.REFRESH.execute(dbunitConnection, replacedDataSet);
	}

	protected void compareDataSets(IDataSet expected, IDataSet actual, String tableName, String[] ignoreCols) throws Exception{
		Assertion.assertEqualsIgnoreCols(expected, actual, tableName, ignoreCols);
	}

	protected void compareDataSets(IDataSet expected, IDataSet actual) throws Exception{
		Assertion.assertEquals(expected, actual);
	}

	public String getDateForDbColumn(Calendar cal){
		return cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + " "+
		cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND) + "." + cal.get(Calendar.MILLISECOND);
	}

}
