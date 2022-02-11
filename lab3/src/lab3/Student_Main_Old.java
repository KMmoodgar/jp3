package lab3;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


public class Student_Main_Old {
	StandardServiceRegistry ssr = (StandardServiceRegistry) new StandardServiceRegistryBuilder().configure("hibernate.hbm.xml").build();
	Metadata md = new MetadataSources(ssr).getMetadataBuilder().build();
	SessionFactory sf = (SessionFactory) md.getSessionFactoryBuilder().build();
	Session session = (Session) ((org.hibernate.SessionFactory)sf).openSession();
	Transaction t = session.beginTransaction();
	
//	SessionFactory sc = new Configuration().configure("hibernate.hbm.xml").buildSessionFactory();
//	Session session = sc.openSession();
//	Transaction t = session.beginTransaction();

	public void insert(int id, String usn, String name, String address, int totalmarks) {
		Student s = new Student();
		s.setId(id);
		s.setUsn(usn);
		s.setName(name);
		s.setAddress(address);
		s.setTotalmarks(totalmarks);
		session.save(s);
		t.commit();
	}

	public void delete(String usn) {
		Query q = session.createQuery("delete from Student where usn = :usn");
		q.setParameter("usn", usn);
		int status = q.executeUpdate();
		if (status == 0) {
			System.out.println(usn + " Deleted successfully");
		} else {
			System.out.println(usn + " not found");
		}
	}

	public void display() {
		Query q = session.createQuery("from Student");
		List l = q.getResultList();
		Iterator it = l.iterator();
		System.out.println("List of Students:");
		while (it.hasNext()) {
			Student s = (Student) it.next();
			System.out.println(s.toString());
		}
	}

	public void search(String usn) {
		Query q = session.createQuery("from Student where usn = :usn");
		q.setParameter("usn", usn);
		List l = q.getResultList();
		if (l.isEmpty()) {
			System.out.println("Not Found");
		} else {
			Iterator it = l.iterator();
			System.out.println("Student Details:");
			while (it.hasNext()) {
				Student s = (Student) it.next();
				System.out.println(s.toString());
			}
		}
	}

	public static void main(String[] args) {
		Student_Main_Old sm = new Student_Main_Old();
		Scanner sc = new Scanner(System.in); // creating object of Scanner class


		lp: while (true) // labeling the while loop
		{
			// displaying the menu
			System.out.println("1: Insert");
			System.out.println("2: Delete");
			System.out.println("3: Search");
			System.out.println("4: Display");
			System.out.println("7: exit");
			System.out.print("Make your choice: ");
			int ch = sc.nextInt(); // reading user's choice
			switch (ch) {
			case 1: // for Right Angled Triangle
				System.out.print("Enter the Student Details to insert \n");
				System.out.print("Enter the Student id \n");
				int id = sc.nextInt();
				System.out.print("Enter the Student usn \n");
				String usn = sc.next();
				System.out.print("Enter the Student name \n");
				String name = sc.next();
				System.out.print("Enter the Student address \n");
				String add = sc.next();
				System.out.print("Enter the Student totalmarks \n");
				int tm = sc.nextInt();
				sm.insert(id, usn, name, add, tm);
				break;
			case 2:
				System.out.print("Enter student id to delete\n");
				System.out.print("Enter the Student id \n");
				usn = sc.next();
				sm.delete(usn);
				break;
			case 3:
				System.out.print("Enter student usn to search\n");
				System.out.print("Enter the Student usn \n");
				usn= sc.next();
				sm.search(usn);
				break;
			case 4:
				sm.display();
				break;
			case 7:
				// exiting from the switch-case as well as from the while loop using its label
				// lp
				break lp;
			default:
				System.out.println("Invalid choice! Please make a valid choice. \n\n");
			}
		}

	}

}
