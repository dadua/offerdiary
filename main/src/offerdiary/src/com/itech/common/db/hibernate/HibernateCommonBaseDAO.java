package com.itech.common.db.hibernate;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Query;
import org.hibernate.Session;

import com.itech.common.CommonUtilities;
import com.itech.common.db.CommonBaseDAO;
import com.itech.common.db.SearchCriteria;
import com.itech.common.exeption.CommonException;
import com.itech.common.exeption.ReturnCodes;
import com.itech.common.security.SecurityManager;
import com.itech.user.model.User;

public abstract class HibernateCommonBaseDAO <T> implements CommonBaseDAO<T> {

	public static final String TRUE_CLAUSE = " 1=1 ";
	private HibernateSessionFactory hibernateSessionFactory;
	private SecurityManager securityManager;


	@Override
	public boolean addOrUpdate(T object) {
		getSession().saveOrUpdate(object);
		return true;
	}

	@Override
	public boolean addOrUpdate(List<T> objects) {
		for (Object object : objects) {
			getSession().saveOrUpdate(object);
		}
		return true;
	}

	@Override
	public boolean delete(Long uniqueId) {
		String hql = "delete from "+getEntityClassName()+" where id = :id";
		Query query = getSession().createQuery(hql);
		query.setParameter("id", uniqueId);
		query.executeUpdate();
		return true;
	}

	@Override
	public boolean delete(Object object) {
		getSession().delete(object);
		return true;
	}

	@Override
	public boolean delete(List<T> objects) {
		for (Object object : objects) {
			getSession().delete(object);
		}
		return true;
	}

	@Override
	public List<T> getAll() {
		String hql = "from " + getEntityClassName();
		Query query = getSession().createQuery(hql);
		return query.list();
	}


	/**
	 * e.g. For a fullyQualifiedcolumnNameToSearchFor = "tv.name" of a Table
	 * and a spaceSeparatedSearchQuery = "token1 token2 token3"
	 * the method returns the where clause=
	 * tv.name like :token1 and tv.name like :token2 and tv.name like :token3
	 * and the nameValue pair map wrapped in a TokenizedWhereClauseResult object
	 * @param fullyQualifiedcolumnNameToSearchFor
	 * @param searchQuery
	 */
	protected String getTokenizedSearchWhereClause(String fullyQualifiedcolumnNameToSearchFor, String searchQuery, Map<String, Object> paramMap) {

		if (CommonUtilities.isNullOrEmpty(searchQuery)) {
			return TRUE_CLAUSE;
		}

		String[] searchTokens = searchQuery.split(" ");
		String columnNamelikeStr = " " + fullyQualifiedcolumnNameToSearchFor + " like ";
		StringBuilder whereClause = new StringBuilder();

		for (int i = 0; i < searchTokens.length; i++) {
			whereClause.append(columnNamelikeStr);
			String tokenName = "token" + i;
			whereClause.append(":" + tokenName);
			if (i != searchTokens.length -1) {
				whereClause.append(" and ");
			}
			paramMap.put(tokenName, "%" + searchTokens[i] + "%");
		}
		return whereClause.toString();
	}

	protected void setParamsToQuery(Query query,  Map<String, Object> paramMap) {
		for (Entry<String, Object> param : paramMap.entrySet()) {
			if (param.getValue() != null && param.getValue() instanceof Collection) {
				query.setParameterList(param.getKey(), (Collection) param.getValue());
			} else {
				query.setParameter(param.getKey(), param.getValue());
			}
		}
	}


	protected List getPaginatedResultFor(Query query, int pageNumber, int numberOfRecordsPerPage) {
		int startIndex = (pageNumber -1 ) * numberOfRecordsPerPage;
		query.setFirstResult(startIndex);
		query.setMaxResults(numberOfRecordsPerPage);
		return query.list();
	}

	protected List getPaginatedResultFor(Query query, SearchCriteria searchCriteria) {
		return getPaginatedResultFor(query, searchCriteria.getPageNumber(), searchCriteria.getResultsPerPage());
	}

	protected Object getSingleResult(Query query) {
		return getSingleResult(query, false);
	}

	protected Object getSingleResult(Query query, boolean failIfResultsMoreThanOne) {
		List result = query.list();
		if (result.size() == 0) {
			return null;
		}

		if ((result.size() > 1) && failIfResultsMoreThanOne) {
			throw new CommonException(ReturnCodes.UNEXPECTED_RESULT_COUNT_FROM_DB);
		}

		return result.get(0);
	}

	protected abstract Class getEntityClass();

	protected String getEntityClassName(){
		return getEntityClass().getName();
	}

	@Override
	public T getById(Long id) {
		return (T) getSession().get(getEntityClass(), id);
	}

	protected T getSingleResultFrom(Query query) {
		List list = query.list();
		if (!list.isEmpty()) {
			return (T) list.get(0);
		}
		return null;
	}
	protected Session getSession() {
		return getHibernateSessionFactory().getCurrentSession();
	}

	public void setHibernateSessionFactory(HibernateSessionFactory hibernateSessionFactory) {
		this.hibernateSessionFactory = hibernateSessionFactory;
	}

	public HibernateSessionFactory getHibernateSessionFactory() {
		return hibernateSessionFactory;
	}

	public User getLoggedInUser() {
		return getSecurityManager().getLoggedInUser();
	}

	public void setSecurityManager(SecurityManager securityManager) {
		this.securityManager = securityManager;
	}

	public SecurityManager getSecurityManager() {
		return securityManager;
	}

}
