package ro.ase.java;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import ro.ase.java.models.Coffe;
import ro.ase.java.utils.FileUtils;

public class Application {

	public static void main(String[] args) throws ClassNotFoundException{
		Coffe c1=new Coffe("Lavazza","Italy",10);
		Coffe c2=new Coffe("Lavazza","Italy",10);
		Coffe c3=new Coffe("Lavazza","Italy",10);
		Coffe c4=new Coffe("Lavazza","Italy",10);
		Coffe c5=new Coffe("Kenya","Kenya",14);
		Coffe c6=new Coffe("Kenya","Kenya",14);
		Coffe c7=new Coffe("Kenya","Kenya",14);
		Coffe c8=new Coffe("Columbia","Columbia",13);
		
		List<Coffe>order=new ArrayList<Coffe>();
		order.add(c1);
		order.add(c2);
		order.add(c3);
		order.add(c4);
		order.add(c5);
		order.add(c6);
		order.add(c7);
		order.add(c8);
		FileUtils utils=new FileUtils();
		try {
			utils.saveOrderToTextFile(order,"OrderDetails.txt");
			Map<String,Integer>details=utils.getOrderFromTextFile("OrderDetails.txt");
			for(Entry<String,Integer>entry:details.entrySet()) {
				System.out.println(entry.getKey()+":"+entry.getValue());
			}
			utils.writeOrderDetailsToBinaryFile(order, "Details.dat");
			utils.readOrderDetailsFromBinaryFile("Details.dat");
			utils.SerializeOrderDetails(order,"serialize.dat");
			utils.DeserializeOrderDetails("serialize.dat");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
