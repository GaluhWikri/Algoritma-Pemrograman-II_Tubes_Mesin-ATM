package Tubes;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class MainNasabah {
	    Scanner sc = new Scanner(System.in);
	    
	   public void SaveToFile(){ // menulis ke file
	        Nasabah R= new Nasabah();
	        int Saldoku=0;
	        String No="", Pin="";
	        
	        System.out.println("========== SaveToFile ======");
	        ObjectOutputStream out = null;
	        try {  
	         // 1. buka file untuk ditulis
	          out=new ObjectOutputStream(new FileOutputStream
	              ("C:\\Nasabah/NasabahKasihBunda.txt"+ ""));// nama direktori+file

	          BufferedReader brInput= new BufferedReader
	                    (new InputStreamReader(System.in)); 
	          for (int i=0;i<5;i++){
	            try {
	                System.out.print("No Rekening : ");
	                No=brInput.readLine();
	            } catch (IOException e){
	                e.printStackTrace();  
	            } 
	            try {
	            	  System.out.print("PIN (jangan lupa): ");
		              Pin=brInput.readLine();
	              } catch (IOException e){
	                e.printStackTrace();  
	              } 
	           try {
	                 System.out.print("Saldo : ");
	                 Saldoku=sc.nextInt();
	                 R=new Nasabah(No,Pin,Saldoku);    
	                 out.writeObject(R);// tulis record ke file
	           } catch (IOException e){
	                 e.printStackTrace();  
	           }
	           System.out.println(""); // br
	          }                       
	          out.close();
	          } catch (IOException e) {
	                e.printStackTrace();
	        }
	 }

	public void ViewFile(){
	    Nasabah R = new Nasabah();
	    int total=0;
	    System.out.println("========== ViewFile ======");     
	    ObjectInputStream in = null;
	    try {
	     // 1. buka file untuk dibaca	
	       in=new ObjectInputStream(new FileInputStream
	    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
	       Object curR = in.readObject();					
	       try {	
	       // 2. baca dan proses setiap record yang dibaca                 
	          while (true) {
	             R = (Nasabah) curR; //inputstream -> objek customer
	             System.out.println("No Rekening: "+R.getNorek());
	             System.out.println("Saldo : "+R.getSaldo());
	             total++;		
	             curR = in.readObject(); // baca lagi...
	          }          	
	       } catch (EOFException e) {}
	             System.out.println("Total record: "+ total);				
	       } catch (ClassNotFoundException e) {
	             System.out.println("Class tidak ditemukan.");
	       } catch (IOException e) {
	             e.printStackTrace();
	      }           
	    }
	
	public void ViewTemp(){
	    Nasabah R = new Nasabah();
	    int total=0;
	    System.out.println("========== ViewTemp ======");     
	    ObjectInputStream in = null;
	    try {
	     // 1. buka file untuk dibaca	
	       in=new ObjectInputStream(new FileInputStream
	    		   ("C:\\Nasabah/Temp.txt"));
	       Object curR = in.readObject();					
	       try {	
	       // 2. baca dan proses setiap record yang dibaca                 
	          while (true) {
	             R = (Nasabah) curR; //inputstream -> objek customer
	             System.out.println("No Rekening: "+R.getNorek());
	             System.out.println("Saldo : "+R.getSaldo());
	             total++;		
	             curR = in.readObject(); // baca lagi...
	          }          	
	       } catch (EOFException e) {}
	             System.out.println("Total record: "+ total);				
	       } catch (ClassNotFoundException e) {
	             System.out.println("Class tidak ditemukan.");
	       } catch (IOException e) {
	             e.printStackTrace();
	      }           
	    }

//	MAIN PROGRAM
    public static void main(String[] args) {
        Nasabah  R= new Nasabah();
        MainNasabah B= new MainNasabah();
        Functions F= new Functions();
    	Scanner sc= new Scanner(System.in);
              
   // B.SaveToFile(); // tulis ke file
    	//  B.ViewFile();
//        B.ViewTemp();
    	int i=1;
    	do {
    		String norek=F.Login();
            if(!norek.isEmpty()) {
            	int x;
            	 System.out.println("Selamat Datang");
            	do {
                	 System.out.println("Silahkan Pilih layanan berikut:");
                	 System.out.println("1: Cek Saldo");
                	 System.out.println("2: Stor Saldo");
                	 System.out.println("3: Tarik Saldo");
                	 System.out.println("4: Transfer Saldo");
                	 System.out.println("5: Topup Pulsa");
                	 System.out.println("6: Bayar Listrik");
                	 System.out.println("7: Bayar PDAM");
                	 System.out.println("0: Logout");
                 	 System.out.print("Printah: ");x=sc.nextInt();
                 	 
                 	 switch(x) {
                 	 case 0:
                 		 break;
                 	 case 1:
                 		F.CekSaldo(norek);
                 		break;
                 	 case 2:
                 		 F.StorSaldo(norek);
                 		 break;
                 	 case 3:
                 		F.TarikSaldo(norek);
                 		break;
                 	case 4:
                 		String norekTujuan = F.TransferSaldo(norek);
                 		float transfer = F.KurangSaldo(norek, norekTujuan);
                 		F.TambahSaldo(norekTujuan, transfer);
                 		break;
                 	case 5:
                 		F.TopupPulsa(norek);
                 		break;
                	case 6:
                 		F.BayarListrik(norek);
                 		break;
                	case 7:
                 		F.BayarPdam(norek);
                 		break;
                 	default:
                 		System.out.println("");
                 		System.out.println("Mohon masukan printah yang sudah dibuat!");
                 	 }
     
            	}while(x!=0);
            	System.out.println("");
            }else {
           	 System.out.print("Coba lagi? (1.iya/0.tidak): ");i=sc.nextInt();
           	 System.out.println("");
            }
    	}while(i!=0);
    	System.out.println("");
        System.out.println("Terimakasih telah menggunakan layanan kita.");
    }  



}

