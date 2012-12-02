package com.itech.user.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.itech.common.db.hibernate.HibernateCommonBaseDAO;
import com.itech.user.model.LinkedAccountType;
import com.itech.user.model.SocialProfileConnection;
import com.itech.user.model.User;



@Repository
public class SocialProfileConnectionDAOImpl extends HibernateCommonBaseDAO<SocialProfileConnection> implements SocialProfileConnectionDao {

	@Override
	public SocialProfileConnection getSocialProfileConnectionFor(long socialProfileId1, long socialProfileId2) {
		String hql = " from " + getEntityClassName() + " where (socialProfile1.id = ? and socialProfile2.id=?) " +
				" or (socialProfile1.id = ? and socialProfile2.id=?)";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, socialProfileId1);
		query.setParameter(1, socialProfileId2);
		query.setParameter(2, socialProfileId2);
		query.setParameter(3, socialProfileId1);
		List list = query.list();
		if (!list.isEmpty()) {
			return (SocialProfileConnection) list.get(0);
		}
		return null;
	}

	@Override
	public List<SocialProfileConnection> getSocialProfileConnectionsFor(long socialProfileId) {
		String hql = " from " + getEntityClassName() + " where (socialProfile1 = ? or socialProfile2=?)";
		Query query = getSession().createQuery(hql);
		query.setParameter(0, socialProfileId);
		query.setParameter(1, socialProfileId);
		List list = query.list();
		return list;
	}

	@Override
	protected Class getEntityClass() {
		return SocialProfileConnection.class;
	}

	@Override
	public List<SocialProfileConnection> getSocialProfileConnectionFor(User user) {
		String hql = " from " + getEntityClassName() + " spc, SocialProfile sp1, SocialProfile sp2, LinkedAccount la " +
				" where (spc.socialProfile1=sp1 and spc.socialProfile2=sp2) " +
				"  and  (sp1.linkedAccount = la or sp2.linkedAccount=la) and la.user=:user";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		List list = query.list();
		return list;
	}

	@Override
	public List<SocialProfileConnection> getSocialProfileConnectionFor(
			User user, LinkedAccountType socialProfileType) {
		String hql = " from " + getEntityClassName() + " spc, SocialProfile sp1, SocialProfile sp2, LinkedAccount la " +
				" where (spc.socialProfile1=sp1 and spc.socialProfile2=sp2) " +
				"  and  (sp1.linkedAccount = la or sp2.linkedAccount=la) and la.user=:user and la.linkedAccountType=:linkedAccountType";
		Query query = getSession().createQuery(hql);
		query.setParameter("user", user);
		query.setParameter("linkedAccountType", socialProfileType);
		List list = query.list();
		return list;
	}

}
