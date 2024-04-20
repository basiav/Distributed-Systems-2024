package sr.serialization;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import sr.proto.AddressBookProtos.Person;

public class ProtoSerialization {

	public static void main(String[] args)
	{
		try {
			new ProtoSerialization().testProto();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void testProto() throws IOException
	{
		Person p1 =
				  Person.newBuilder()
				    .setId(123456)
				    .setName("Włodzimierz Wróblewski")
				    .setEmail("wrobel@poczta.com")
				    .addPhones(
						      Person.PhoneNumber.newBuilder()
						        .setNumber("+48-12-555-4321")
						        .setType(Person.PhoneType.HOME))
				    .addPhones(
						      Person.PhoneNumber.newBuilder()
						        .setNumber("+48-699-989-796")
						        .setType(Person.PhoneType.MOBILE))
				  	.addSalaries(
								Person.Salaries.newBuilder()
								.setSalaries(1.111))
				  	.addSalaries(
							  Person.Salaries.newBuilder()
							  .setSalaries(2222.22))
				  	.addSalaries(
				  				Person.Salaries.newBuilder()
							  	.setSalaries(3333.3333))
				    .build();
		
		byte[] p1ser = null;

		long n = 1000;
        System.out.println("Performing proto serialization " + n + " times...");
		System.out.println("Timer start");
		long start = System.currentTimeMillis();
        for(long i = 0; i < n; i++)
		{
			p1ser = p1.toByteArray();
		}
        System.out.println("... finished.");
		long finish = System.currentTimeMillis();
		System.out.println("Timer stop");
		long timeElapsed = finish - start;
		System.out.println("Elapsed time: " + timeElapsed);
		System.out.println("Time per 1 ser: " + (float) (timeElapsed * 1000 / n));

		//print data as hex values
		for (byte b : p1ser) { System.out.print(String.format("%02X", b)); }

		//serialize again (only once) and write to a file
		System.out.println("Timer start");
		start = System.currentTimeMillis();
		FileOutputStream file = new FileOutputStream("person2.ser");
		file.write(p1.toByteArray()); 
		file.close();
		finish = System.currentTimeMillis();
		System.out.println("Timer stop");
		timeElapsed = finish - start;
		System.out.println("Elapsed time: " + timeElapsed);
	}	
}
