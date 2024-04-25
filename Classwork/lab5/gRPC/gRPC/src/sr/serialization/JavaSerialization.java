package sr.serialization;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

enum PhoneTypeJ { MOBILE,  HOME,  WORK};

class PhoneNumberJ implements java.io.Serializable {
	private static final long serialVersionUID = 2463673954867473008L;
	public String number;
	public PhoneTypeJ type;

	public PhoneNumberJ(String number, PhoneTypeJ type) 
    { 
        this.number = number; 
        this.type = type; 
    } 
}


class PersonJ implements java.io.Serializable 
{ 
	private static final long serialVersionUID = 2363673954867473008L;
	public int a; 
	public String b; 

	public String name;
	public int id;
	public String email;
	public List<PhoneNumberJ> phones;

	public PersonJ(String name, int id, String email, List<PhoneNumberJ> phones) 
    { 
        this.name = name; 
        this.id = id; 
        this.email = email;
        this.phones = phones;
    } 

} 

public class JavaSerialization 
{ 
	public static void main(String[] args) 
	{    
		List<PhoneNumberJ> phones = new ArrayList<PhoneNumberJ>();
		phones.add(new PhoneNumberJ("+48-12-555-4321", PhoneTypeJ.HOME));
		phones.add(new PhoneNumberJ("+48-699-989-796", PhoneTypeJ.MOBILE));
		
		PersonJ object = new PersonJ("Włodzimierz Wróblewski", 123456, "wrobel@poczta.com", phones);
		
		try
		{    
			long n = 1000;
			System.out.println("Performing java serialization " + n + " times...");
			System.out.println("Timer start");
			long start = System.currentTimeMillis();

	        for(long i = 0; i< n; i++)
	        {
		        ByteArrayOutputStream bos = new ByteArrayOutputStream();
		        ObjectOutputStream oos = new ObjectOutputStream(bos);
		        oos.writeObject(object);
		        oos.flush();
		        bos.toByteArray();
	        }

			long finish = System.currentTimeMillis();
			System.out.println("... finished.");
			System.out.println("Timer stop");
			long timeElapsed = finish - start;
			System.out.println("Elapsed time: " + timeElapsed);
			System.out.println("Time per 1 ser: " + (float) (timeElapsed * 1000 / n));
			
	        
	        //serialize again (only once) and write to a file
			System.out.println("Timer start");
			start = System.currentTimeMillis();
			FileOutputStream file = new FileOutputStream("person1.ser"); 
			ObjectOutputStream out = new ObjectOutputStream(file); 
			out.writeObject(object); 
			out.close(); 
			file.close();
			finish = System.currentTimeMillis();
			timeElapsed = finish - start;
			System.out.println("... finished.");
			System.out.println("Timer stop");
			System.out.println("Elapsed time: " + timeElapsed);
		} 
		catch(IOException ex) 
		{ 
			System.out.println("IOException"); 
		} 
	}
} 