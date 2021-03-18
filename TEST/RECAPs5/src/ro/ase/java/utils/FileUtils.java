package ro.ase.java.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro.ase.java.models.Coffe;

public class FileUtils {

	public FileUtils() {
		
	}
	
	public void saveOrderToTextFile(List<Coffe> orderDetails,String fileName) throws IOException {
		File file=new File(fileName);
		FileWriter writer=new FileWriter(file);
		for(Coffe c:orderDetails) {
			writer.write(c.getBeverageName()+","+c.getCoffeType()+","+c.getCoffePrice()+"\n");
		}
		writer.close();
		System.out.println("Order details were saved to file:"+file.getAbsolutePath());
	}
	
	public Map<String,Integer> getOrderFromTextFile(String fileName) throws IOException{
		Map<String,Integer> orderDetails=new HashMap<String,Integer>();
		File file=new File(fileName);
		BufferedReader reader=new BufferedReader(new FileReader(file));
		String line="";
		while((line=reader.readLine()) !=null) {
			String[] values=line.split(",");
			String beverageName=values[0];
			if(orderDetails.containsKey(beverageName)) {
				orderDetails.put(beverageName, orderDetails.get(beverageName)+1);
				
			}else {
				orderDetails.put(beverageName, 1);
			}
		}
		reader.close();
		return orderDetails;
	}
	
	public void writeOrderDetailsToBinaryFile(List<Coffe>OrderDetails,String fileName) throws IOException {
		File file=new File(fileName);
		FileOutputStream fos=new FileOutputStream(file);
		DataOutputStream oos=new DataOutputStream(fos);
		oos.writeInt(OrderDetails.size());
		for(Coffe c:OrderDetails) {
			oos.writeUTF(c.getBeverageName());
			oos.writeUTF(c.getCoffeType());
			oos.writeDouble(c.getCoffePrice());
		}
		oos.close();
		fos.close();
		System.out.println("Order details were saved in binary format to:"+file.getAbsolutePath());	
	}
	
	public void readOrderDetailsFromBinaryFile(String fileName) throws IOException {
		File file=new File(fileName);
		FileInputStream fis=new FileInputStream(file);
		DataInputStream dis=new DataInputStream(fis);
		int NrObjects=dis.readInt();
		for(int i=0;i<NrObjects;i++) {
			System.out.println(dis.readUTF());
			System.out.println(dis.readUTF());
			System.out.println(dis.readDouble());	
		}
		dis.close();
		fis.close();
	}
	
	public void SerializeOrderDetails(List<Coffe>OrderDetails,String fileName) throws IOException {
		File file=new File(fileName);
		FileOutputStream fos=new FileOutputStream(file);
		ObjectOutputStream oos=new ObjectOutputStream(fos);
		for(Coffe c :OrderDetails) {
			oos.writeObject(c);
		}
		oos.close();
		fos.close();
	}
	
	public void DeserializeOrderDetails(String fileName) throws IOException, ClassNotFoundException{
		File file=new File(fileName);
		FileInputStream fis=new FileInputStream(file);
		ObjectInputStream ois=new ObjectInputStream(fis);
		while(true) {
			try {
				Coffe c=(Coffe) ois.readObject();
				System.out.println(c.getBeverageName()+","+c.getCoffeType()+","+c.getCoffePrice()+"\n");
			}catch(EOFException ex) {
				break;
			}
		}
		ois.close();
		fis.close();
	}
}
