package nl.sogyo.ttt_app.api;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import nl.sogyo.ttt_app.domain.*;

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

	public void dispose(){
		if(hibernateSession.isOpen()){
			hibernateSession.close();
		}
	}
	
	public Player getPlayer(int ID){
		Player player = null;
        try {
			hibernateSession.beginTransaction();
			player = hibernateSession.get(Player.class, ID);
			hibernateSession.getTransaction().commit();            
        } catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
		return player;
	}

	public void persistPlayer(Player player){
		try {
			hibernateSession.beginTransaction();
			hibernateSession.persist(player);
			hibernateSession.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
	}

	public void removePlayer(int ID){
		try {
			hibernateSession.beginTransaction();
			hibernateSession.delete(hibernateSession.get(Player.class, ID));
			hibernateSession.getTransaction().commit();
		} catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
	}

	public Tournament getTournament(int ID){
		Tournament tournament = null;
        try {
			hibernateSession.beginTransaction();
			tournament = hibernateSession.get(Tournament.class, ID);
			hibernateSession.getTransaction().commit();            
        } catch (Exception sqlException) {
			if (null != hibernateSession.getTransaction()) {
				System.out.println("\n.......Transaction Is Being Rolled Back.......");
				hibernateSession.getTransaction().rollback();
			}
			sqlException.printStackTrace();
		}
		return tournament;
	}

	public void mergeTournament(Tournament tournament){
		try {
			hibernateSession.beginTransaction();
			hibernateSession.merge(tournament);
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