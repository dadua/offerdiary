package com.itech.user.dao;

import java.util.List;

import org.hibernate.Query;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.SocialProfile;
import com.itech.user.model.User;

public class SocialProfileDAOImpl extends HibernateCommonBaseDAO<SocialProfile>
implements SocialProfileDAO {

	@Override
	public SocialProfile getSocialProfileFor(String uniqueId,
			LinkedAccountType socialProfileType) {
		String hql = "from " + getEntityClassName() + " where uniqueId = :uniqueId and socialProfileType=:socialProfileType";
		Query query = getSession().createQuery(hql);
		query.setParameter("uniqueId", uniqueId);
		query.setParameter("socialProfileType", socialProfileType);
		return getSingleResultFrom(query);
	}

	@Override
	public List<SocialProfile> getSocialProfilesFor(User user) {
		String hql = "from " + getEntityClassName() + " sp, LinkedAccount la " +
				" where sp.linkedAccount = la and la.user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		return query.list();
	}

	@Override
	protected Class getEntityClass() {
		return SocialProfile.class;
	}


}
