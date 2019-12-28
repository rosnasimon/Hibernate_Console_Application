import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class Application {

	public static void main(String[] args) throws IOException 
	{
		Scanner sc=new Scanner(System.in);
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		int ch;
		String resp;
		Student s=new Student();
		Trainer t=new Trainer();
		
		SessionFactory sf=new Configuration().configure("hiber_console.cfg.xml").buildSessionFactory();
		Session session=sf.openSession();
		Transaction tx=session.beginTransaction();
		do
		{
			System.out.println("1. Insert Details");
			System.out.println("2. Retrieve Data");
			System.out.println("3. Delete Record");
			System.out.println("4. Exit");
			System.out.println("Enter the Choice : ");
			ch=sc.nextInt();
			switch(ch)
			{
				case 1:
					System.out.println("Add Student / Trainer [ s/t ] : ");
					resp=br.readLine();
					if(resp.equalsIgnoreCase("s"))
					{
						System.out.println("Enter the Student Details....");
						System.out.print("Roll Number : ");
						String id=br.readLine();
						s.setsId(id);
						System.out.print("Student Name : ");
						String name=br.readLine();
						s.setName(name);
						System.out.print("Batch Number : ");
						String batch=br.readLine();
						s.setBatch(batch);
						System.out.print("Email Id : ");
						String email=br.readLine();
						s.setEmail(email);
						System.out.print("Phone Number : ");
						String ph=br.readLine();
						s.setPhone(ph);
						session.beginTransaction();
						session.save(s);
						tx.commit();
					}
					else if(resp.equalsIgnoreCase("t"))
					{
						System.out.println("\nEnter the Trianer Details.....");
						System.out.print("Trianer ID : ");
						t.settId(br.readLine());
						System.out.print("Trainer Name : ");
						t.setName(br.readLine());
						System.out.print("Department : ");
						t.setDept(br.readLine());
						System.out.print("Phone Number : ");
						t.setPhone(br.readLine());
						session.beginTransaction();				
						session.save(t);
						tx.commit();
					}
					break;
				case 2:
					session.beginTransaction();
					Query qs=session.createQuery("from Student");
					Query qt=session.createQuery("from Trainer");
					
					List<Student> rs=qs.list();
					List<Trainer> rt=qt.list();
					System.out.println("STUDENT RECORD\n***************************");
					for(Student Object:rs)
					{
						System.out.println("Roll Number 	: "+Object.getsId());
						System.out.println("Student Name 	: "+Object.getName());
						System.out.println("Batch Number    : "+Object.getBatch());
						System.out.println("Email ID  	: "+Object.getEmail());
						System.out.println("Phone Number    : "+Object.getPhone());
						System.out.println();
					}					
					System.out.println("TRAINER RECORD\n***************************");
					for(Trainer Object:rt)
					{
						System.out.println("Trainer ID 	: "+Object.gettId());
						System.out.println("Trainer Name 	: "+Object.getName());
						System.out.println("Department   	: "+Object.getDept());
						System.out.println("Phone Number    : "+Object.getPhone());
						System.out.println();
					}
					tx.commit();
					break;
				case 3:
					session.beginTransaction();
					System.out.print("Student / Trainer Record (s/t) ? ");
					resp=br.readLine();
					Query q;
					if(resp.equalsIgnoreCase("s"))
					{
						System.out.println("Enter the Student ID : ");
						String ids=br.readLine();
						q=session.createQuery("delete from Student where sId=:sId");
						q.setParameter("sId", ids);
						q.executeUpdate();
						tx.commit();
					}
					else if(resp.equalsIgnoreCase("t"))
					{
						System.out.println("Enter the Trainer ID : ");
						String ids=br.readLine();
						q=session.createQuery("delete from Trainer where Id=:Id");
						q.setParameter("Id", ids);
						q.executeUpdate();
						tx.commit();
					}
					break;
				case 4:
					System.out.println("Thank You....");
					System.exit(0);
				default:
					System.out.println("Wrong Choice");
			}
		}while(true);
	}
}