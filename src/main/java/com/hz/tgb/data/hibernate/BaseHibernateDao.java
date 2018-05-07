package com.hz.tgb.data.hibernate;

import com.hz.tgb.tool.PageBean;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * BaseHibernateDao 定义DAO的通用操作的实现
 * 
 * @author hezhao
 * @date 2015-05-16
 * 
 */
@SuppressWarnings("unchecked")
public abstract class BaseHibernateDao<T> {

	private Class<T> clazz;

	/**
	 * 反射 通过构造方法指定DAO的具体实现类
	 */
	public BaseHibernateDao() {
		ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		clazz = (Class<T>) type.getActualTypeArguments()[0];
		// System.out.println("DAO的真实实现类是：" + this.clazz.getName());
	}

	/**
	 * 查询返回集合
	 * 
	 * @param hql
	 * @param params
	 * @return List
	 */
	public List<T> queryList(String hql, Object... params) {
		return prepareQuery(hql, params).list();
	}

	/**
	 * 查询返回集合
	 * 
	 * @param hql
	 * @param params
	 * @return List
	 */
	public List<T> queryList(String hql, Map<Object, Object> params) {
		return prepareQuery(hql, params).list();
	}

	/**
	 * 查询返回集合
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的Map集合
	 * 
	 * @return List
	 */
	public List<T> queryListByProperties(String hql, Map<String, Object> params) {
		return HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params).list();
	}

	/**
	 * 查询返回集合
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的对象
	 * 
	 * @return List
	 */
	public List<T> queryListByProperties(String hql, Object params) {
		return HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params).list();
	}

	/**
	 * 查询返回单个对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public T queryFirst(String hql, Object... params) {
		return (T) prepareQuery(hql, params).setMaxResults(1).uniqueResult();
	}

	/**
	 * 查询返回单个对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public T queryFirst(String hql, Map<Object, Object> params) {
		return (T) prepareQuery(hql, params).setMaxResults(1).uniqueResult();
	}

	/**
	 * 查询返回单个对象
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的Map集合
	 * 
	 * @return
	 */
	public T queryFirstByProperties(String hql, Map<String, Object> params) {
		return (T) HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params).setMaxResults(1).uniqueResult();
	}

	/**
	 * 查询返回单个对象
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的对象
	 * 
	 * @return
	 */
	public T queryFirstByProperties(String hql, Object params) {
		return (T) HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params).setMaxResults(1).uniqueResult();
	}

	/**
	 * 根据主键查询一条数据，立即查询， 若加载的数据不存在，返回NULL
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public T get(Serializable id) {
		return (T) HibernateSessionFactory.getSession().get(this.clazz, id);
	}

	/**
	 * 根据主键查询一条数据，延时加载， 若加载的数据不存在，返回NULL
	 * 
	 * @param id
	 * 
	 * @return
	 */
	public T load(Serializable id) {
		try {
			return (T) HibernateSessionFactory.getSession()
					.load(this.clazz, id);
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取数据的数量
	 * 
	 * @param hql
	 * @param params
	 * @return Integer
	 */
	public Integer getCount(String hql, Object... params) {
		long count = (Long) prepareQuery(hql, params).setMaxResults(1)
				.uniqueResult();
		return (int) count;
	}

	/**
	 * 获取数据的数量
	 * 
	 * @param hql
	 * @param params
	 * @return Integer
	 */
	public Integer getCount(String hql, Map<Object, Object> params) {
		long count = (Long) prepareQuery(hql, params).setMaxResults(1)
				.uniqueResult();
		return (int) count;
	}

	/**
	 * 获取数据的数量
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的Map集合
	 * @return Integer
	 */
	public Integer getCountByProperties(String hql, Map<String, Object> params) {
		Query query = HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params);
		long count = (Long) query.setMaxResults(1).uniqueResult();
		return (int) count;
	}

	/**
	 * 获取数据的数量
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的对象
	 * @return Integer
	 */
	public Integer getCountByProperties(String hql, Object params) {
		Query query = HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params);
		long count = (Long) query.setMaxResults(1).uniqueResult();
		return (int) count;
	}

	/**
	 * 投影查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List Projection(String hql, Object... params) {
		return prepareQuery(hql, params).list();
	}

	/**
	 * 投影查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	public List Projection(String hql, Map<Object, Object> params) {
		return prepareQuery(hql, params).list();
	}

	/**
	 * 投影查询
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的Map集合
	 * @return
	 */
	public List ProjectionByProperties(String hql, Map<String, Object> params) {
		return HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params).list();
	}

	/**
	 * 投影查询
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的对象
	 * @return
	 */
	public List ProjectionByProperties(String hql, Object params) {
		return HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params).list();
	}

	/**
	 * 获得唯一结果
	 * 
	 * @param hql
	 * @param params
	 * @return Object
	 */
	public Object Single(String hql, Object... params) {
		return prepareQuery(hql, params).setMaxResults(1).uniqueResult();
	}

	/**
	 * 获得唯一结果
	 * 
	 * @param hql
	 * @param params
	 * @return Object
	 */
	public Object Single(String hql, Map<Object, Object> params) {
		return prepareQuery(hql, params).setMaxResults(1).uniqueResult();
	}

	/**
	 * 获得唯一结果
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的Map集合
	 * @return Object
	 */
	public Object SingleByProperties(String hql, Map<String, Object> params) {
		return HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params).setMaxResults(1).uniqueResult();
	}

	/**
	 * 获得唯一结果
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的对象
	 * @return Object
	 */
	public Object SingleByProperties(String hql, Object params) {
		return HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params).setMaxResults(1).uniqueResult();
	}

	/**
	 * 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param params
	 *            如果hql有多个参数需要传入，params就是传入的参数数组
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	public List<T> queryForPage(String hql, int pageIndex, int pageSize,
			Object... params) {
		return prepareQuery(hql, params)
				.setFirstResult((pageIndex - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	/**
	 * 使用hql 语句进行分页查询操作
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param params
	 *            如果hql有多个参数需要传入，params就是传入的Map集合
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	public List<T> queryForPage(String hql, int pageIndex, int pageSize,
			Map<Object, Object> params) {
		return prepareQuery(hql, params)
				.setFirstResult((pageIndex - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	/**
	 * 使用hql 语句进行分页查询操作 参数绑定为条件集合（Map）
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param params
	 *            封装条件的Map集合
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	public List<T> queryForPageByProperties(String hql, int pageIndex,
			int pageSize, Map<String, Object> params) {
		return HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params)
				.setFirstResult((pageIndex - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	/**
	 * 使用hql 语句进行分页查询操作 参数绑定为条件对象
	 * 
	 * @param hql
	 *            需要查询的hql语句
	 * @param params
	 *            封装条件的对象
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页需要显示的记录数
	 * @return 当前页的所有记录
	 */
	public List<T> queryForPageByProperties(String hql, int pageIndex,
			int pageSize, Object params) {
		return HibernateSessionFactory.getSession().createQuery(hql)
				.setProperties(params)
				.setFirstResult((pageIndex - 1) * pageSize)
				.setMaxResults(pageSize).list();
	}

	/**
	 * 获取分页对象
	 * 
	 * @param hql
	 * @param params
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页显示的数量
	 * @return PageBean
	 */
	public PageBean<T> findForPage(String hql, int pageIndex, int pageSize,
								   Object... params) {
		int totalCount = queryList(hql, params).size(); // 总记录数
		List<T> list = queryForPage(hql, pageIndex, pageSize, params);
		// 把分页信息保存到pageBean中
		return new PageBean<T>(list, pageSize, pageIndex, totalCount);
	}

	/**
	 * 获取分页对象
	 * 
	 * @param hql
	 * @param params
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页显示的数量
	 * @return PageBean
	 */
	public PageBean<T> findForPage(String hql, int pageIndex, int pageSize,
			Map<Object, Object> params) {
		int totalCount = queryList(hql, params).size(); // 总记录数
		List<T> list = queryForPage(hql, pageIndex, pageSize, params);
		// 把分页信息保存到pageBean中
		return new PageBean<T>(list, pageSize, pageIndex, totalCount);
	}

	/**
	 * 获取分页对象
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的Map集合
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页显示的数量
	 * @return PageBean
	 */
	public PageBean<T> findForPageByProperties(String hql, int pageIndex,
			int pageSize, Map<String, Object> params) {
		int totalCount = queryListByProperties(hql, params).size(); // 总记录数
		List<T> list = queryForPageByProperties(hql, pageIndex, pageSize,
				params);
		// 把分页信息保存到pageBean中
		return new PageBean<T>(list, pageSize, pageIndex, totalCount);
	}

	/**
	 * 获取分页对象 参数绑定为条件对象
	 * 
	 * @param hql
	 * @param params
	 *            封装条件的对象
	 * @param pageIndex
	 *            当前页码
	 * @param pageSize
	 *            每页显示的数量
	 * @return PageBean
	 */
	public PageBean<T> findForPageByProperties(String hql, int pageIndex,
			int pageSize, Object params) {
		int totalCount = queryListByProperties(hql, params).size(); // 总记录数
		List<T> list = queryForPageByProperties(hql, pageIndex, pageSize,
				params);
		// 把分页信息保存到pageBean中
		return new PageBean<T>(list, pageSize, pageIndex, totalCount);
	}

	/**
	 * 新增
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean save(T entity) {
		Transaction tx = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			session.save(entity);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return false;
		}
	}

	/**
	 * 修改
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean update(T entity) {
		Transaction tx = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			session.update(entity);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return false;
		}
	}

	/**
	 * 增加或修改
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean saveOrUpdate(T entity) {
		Transaction tx = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			session.saveOrUpdate(entity);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return false;
		}
	}

	/**
	 * 把一个游离态对象的属性复制到一个持久化对象中，执行更新或插入操作并返回持久化的对像，若传入的是瞬时态对象则保存并返回其副本
	 * 
	 * @param entity
	 * @return
	 */
	public T merge(T entity) {
		Transaction tx = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			T result = (T) session.merge(entity);
			tx.commit();
			return result;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return null;
		}
	}

	/**
	 * 删除
	 * 
	 * @param entity
	 * @return boolean
	 */
	public boolean delete(T entity) {
		Transaction tx = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			session.delete(entity);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return false;
		}
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return boolean
	 */
	public boolean delete(Serializable id) {
		Transaction tx = null;
		try {
			Session session = HibernateSessionFactory.getSession();
			tx = session.beginTransaction();
			T entity = get(id);
			session.delete(entity);
			tx.commit();
			return true;
		} catch (HibernateException e) {
			e.printStackTrace();
			if (tx != null) {
				tx.rollback();
			}
			return false;
		}
	}

	/**
	 * 准备query对象 内部调用
	 * 
	 * @param hql
	 * @param params
	 * @return Query
	 */
	private Query prepareQuery(String hql, Object... params) {
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}

	/**
	 * 准备query对象 内部调用
	 * 
	 * @param hql
	 * @param params
	 * @return QueryS
	 */
	private Query prepareQuery(String hql, Map<Object, Object> params) {
		Session session = HibernateSessionFactory.getSession();
		Query query = session.createQuery(hql);
		if (params != null && params.size() > 0) {
			for (Object key : params.keySet()) {
				if (key instanceof Integer) {
					query.setParameter((Integer) key, params.get(key));
				} else if (key instanceof String) {
					query.setParameter((String) key, params.get(key));
				}
			}
		}
		return query;
	}
}
