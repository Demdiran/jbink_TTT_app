package nl.sogyo.ttt_app.api;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import nl.sogyo.ttt_app.domain.Player;

class DatabaseAccessor{
	private Session hibernateSession;
	public static SessionFactory buildSessionFactory(String settings) {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
					.configure(settings).build();
			Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
			return metadata.getSessionFactoryBuilder().build();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public DatabaseAccessor(){
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);
		hibernateSession = buildSessionFactory("hibernate.cfg.xml").openSession();
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.OFF);
	}

	public DatabaseAccessor(Session hibernateSession){
		this.hibernateSession = hibernateSession;
	}

	public void closeSession(){
		if(hibernateSession.isOpen()){
			hibernateSession.close();
		}
	}

	public Player getOrCreatePlayerWithOutsideID(String outside_ID){
		Player result = null;
        try {
			hibernateSession.beginTransaction();
			OutsideIDPlayerID outsideIDPlayerID = hibernateSession.get(OutsideIDPlayerID.class, outside_ID);
			if(outsideIDPlayerID == null){
				result = new Player();
				hibernateSession.persist(result);
				outsideIDPlayerID = new OutsideIDPlayerID();
				outsideIDPlayerID.setOutside_ID(outside_ID);
				outsideIDPlayerID.setPlayer_ID(result.getID());
				hibernateSession.persist(outsideIDPlayerID);
			}
			else{
				result = hibernateSession.get(Player.class, outsideIDPlayerID.getPlayer_ID());
			}
			hibernateSession.getTransaction().commit();            
        } catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
		return result;
	}

	public <T extends IStorable> List<T> getAllFromDB(Class<T> typeToGet){
		List<T> toGet = null;
        try {
			hibernateSession.beginTransaction();
			CriteriaBuilder builder = hibernateSession.getCriteriaBuilder();
			CriteriaQuery<T> criteria = builder.createQuery(typeToGet);
			criteria.from(typeToGet);
			toGet = hibernateSession.createQuery(criteria).getResultList();
			hibernateSession.getTransaction().commit();            
        } catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
		return toGet;
	}
	
	public <T extends IStorable> IStorable getFromDB(int ID, Class<T> typeToGet){
		IStorable toGet = null;
        try {
			hibernateSession.beginTransaction();
			toGet = hibernateSession.get(typeToGet, ID);
			hibernateSession.getTransaction().commit();            
        } catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
		return toGet;
	}

	public void createInDB(IStorable toCreate){
		try {
			hibernateSession.beginTransaction();
			hibernateSession.persist(toCreate);
			hibernateSession.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
	}

	public void updateInDB(IStorable toUpdate){
		try {
			hibernateSession.beginTransaction();
			hibernateSession.merge(toUpdate);
			hibernateSession.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
	}
}