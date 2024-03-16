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

public class Functions {
	    Scanner sc = new Scanner(System.in);
	    
	    public String Login(){
			Nasabah R = new Nasabah();
			String norek, pin;
			Boolean ketemu=false;

			System.out.println("========== Login ATM Kasih Bunda ======");     
			ObjectInputStream in = null;
			 // TRY DI DALAM TRY TETAPI IN OUT NYA SATU SAJA
			try {
				// 1. buka file untuk dibaca	
				in=new ObjectInputStream(new FileInputStream
	    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
	       Object curR = in.readObject();	
	       try {	
	    	   Scanner sc = new Scanner(System.in);
	       // 2. baca dan proses setiap record yang dibaca  
	    	   System.out.print("Norek :  "); norek=sc.next();
	    	   System.out.print("PIN :  "); pin=sc.next();
		
	    	   while (ketemu==false) {
	        	   R = (Nasabah) curR; //inputstream -> objek customer
	        	   // cek apakah norek ada?
	        	  if(R.getNorek().contentEquals(norek)){
	        		  ketemu=true;
	        	  }
	        	   // kalo ada masuk ke sini
	        	  if(ketemu==true) {
	  	        		int coba=1;
	  	        	  do {
	  	              	  if(R.getPin().contentEquals(pin)) {
	  	              		  return norek;
	  		        	  }else {
	  		        		  System.out.println("Password yang anda masukan salah!");
	  		        		  if(coba==1) {
	  		        			  System.out.println("Percobaan hanya sampai 3x, kalo tidak rekening anda ditahan");		        			  
	  		        		  }
	  		        		  if(coba<=3) {
	  		        			  System.out.println("Percobaan ke-"+coba);
	  		        			  System.out.print("PIN :  "); pin=sc.next();
	  		        			  coba++;
	  		        		  }
	  		        	  }
	  	        	  }while(coba<=3);
	  	        	 System.out.println("=== Mohon maaf norek anda saya tahan ===");
	  	          }
	        	  
	        	  curR = in.readObject(); // baca terus kedata selanjutnya
	         }    
	          
	      
	       } catch (EOFException e) {}
	             System.out.println("");	
	             System.out.println("Nasabah tidak ditemukan");
	       } catch (ClassNotFoundException e) {
	             System.out.println("Class tidak ditemukan.");
	       } catch (IOException e) {
	             e.printStackTrace();
	      }   
		return "";	
	    }
	
	    // Cek Saldo
		public void CekSaldo(String norek){
		    Nasabah R = new Nasabah();
		    Boolean ketemu=false;
		    System.out.println("====== Cek Saldo ======");     
		    ObjectInputStream in = null;
		    try {
		     // 1. buka file untuk dibaca	
		       in=new ObjectInputStream(new FileInputStream
		    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
		       Object curR = in.readObject();					
		       try {	
		       // 2. baca dan proses setiap record yang dibaca                 
		    	   while (ketemu==false) {
			        	   R = (Nasabah) curR; //inputstream -> objek customer
			        	  if(R.getNorek().contentEquals(norek)){
			        		  ketemu=true;
			        	  }
			        	  
			        	  if(ketemu==true) {
			        		  System.out.println("Saldo Anda : Rp."+ R.getSaldo());
			        	  }
			        	  
			        	  curR = in.readObject(); // baca lagi...
			         } 
		       } catch (EOFException e) {}
		             System.out.println("");				
		       } catch (ClassNotFoundException e) {
		             System.out.println("Class tidak ditemukan.");
		       } catch (IOException e) {
		             e.printStackTrace();
		      }           
		    }
		
		// Stor Saldo
		public void StorSaldo(String norek){
		    Nasabah R = new Nasabah();
		    float saldoBaru, stor;
		    System.out.println("====== Stor Saldo ======");     
		    ObjectInputStream in = null; // read
		    ObjectOutputStream out = null; // write
		    // TRY DI DALAM TRY APABILA IN OUT NYA MASING MASING
		    try {
		       try {
		    	// ISI FILE UTAMA DI BACKUP KE TEMP
		    	// 1. buka file untuk dibaca	
			       in=new ObjectInputStream(new FileInputStream
			    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
			    		   Object curR = in.readObject();
			       // 2. buka file untuk ditulis	
			    	out=new ObjectOutputStream(new FileOutputStream
			    		              ("C:\\Nasabah/Temp.txt"+ ""));// nama direktori+file
		       // 2. baca dan proses setiap record yang dibaca    
		     	   while (true) {
		        	   R = (Nasabah) curR; //inputstream -> objek customer
		        	   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());    
		               out.writeObject(R); 
		        	   curR = in.readObject(); // baca lagi...
		           }   
		       } catch (EOFException e) {}
		       
				try {
					// FILE TEMP DI UBAH LALU DI PINDAHKAN KEMBALI KE FILE UTAMA
				 	// 1. buka file untuk dibaca	
				    in=new ObjectInputStream(new FileInputStream
				    		   ("C:\\Nasabah/Temp.txt"));
				    		   Object curR = in.readObject();
				    // 2. buka file untuk ditulis	
				    out=new ObjectOutputStream(new FileOutputStream
				    		   ("C:\\Nasabah/NasabahKasihBunda.txt"+ ""));// nama direktori+file
			       BufferedReader brInput= new BufferedReader
		                    (new InputStreamReader(System.in)); 
			       
			       System.out.print("1.Lanjut/0.Batal:  ");int x=sc.nextInt();
			       if(x!=0) { 
			    	   do {
				    	   System.out.print("Stor Saldo (min 10k) :  ");stor=sc.nextFloat();
	        		   }while(stor<10000);
					  // 2. baca dan proses setiap record yang dibaca    
			     		   while (true) {
				        	   R = (Nasabah) curR; //inputstream -> objek customer
				        	   
				        	   if(!R.getNorek().contentEquals(norek)) {
				        		   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo()); 
				        	   }else {
				        		   saldoBaru=R.getSaldo()+stor;
				        		   R=new Nasabah(R.getNorek(),R.getPin(),saldoBaru); 
				        		   System.out.println("Saldo berhasil distor");
				        		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
				        	   }
				               out.writeObject(R); 
				        	   curR = in.readObject(); // baca lagi...
			     		   }   
			       }else {
			    	   // 2. baca dan proses setiap record yang dibaca    
		     		   while (true) {
			        	   R = (Nasabah) curR; //inputstream -> objek customer
			        	   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo()); 
			               out.writeObject(R); 
			        	   curR = in.readObject(); // baca lagi...
		     		   }  
			       }
				}catch (EOFException e) {}
				 System.out.println("");	
		       } catch (ClassNotFoundException e) {
		             System.out.println("Class tidak ditemukan.");
		       } catch (IOException e) {
		             e.printStackTrace();
		      }  
		    }	
		
		// Tarik Saldo
		public void TarikSaldo(String norek){
		    Nasabah R = new Nasabah();
		    float saldoBaru, tarik = 0;
		    System.out.println("====== Tarik Saldo ======");     
		    ObjectInputStream in = null; // read
		    ObjectOutputStream out = null; // write
		    // TRY DI DALAM TRY APABILA IN OUT NYA MASING MASING
		    try {
		       try {
		    	 // CEK SALDO NYA CUKUP ATAU ENGGA
		    	// 1. buka file untuk dibaca	
			       in=new ObjectInputStream(new FileInputStream
			    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
		    	   Object curR = in.readObject();
		       // 2. baca dan proses setiap record yang dibaca ;
			    	 while (true) {
			        	   R = (Nasabah) curR; //inputstream -> objek customer
			        	  if(R.getNorek().contentEquals(norek)&& R.getSaldo()< 10000){
			        		  System.out.println("Mohon maaf anda miskin!");
			        			 System.out.println("");
			        			 return;
			        	  }			        	  
			        	  curR = in.readObject(); // baca lagi...
			        } 
		       } catch (EOFException e) {}
		       System.out.print("1.Lanjut/0.Batal: ");int x=sc.nextInt();
		       if(x!=0) {
		       try {	
		    	// KALO CUKUP ISI FILE UTAMA DI BACKUP KE TEMP
		    	// 1. buka file untuk dibaca	
			       in=new ObjectInputStream(new FileInputStream
			    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
			       // 2. buka file untuk ditulis	
			    	out=new ObjectOutputStream(new FileOutputStream
			    		              ("C:\\Nasabah/Temp.txt"+ ""));// nama direktori+file
				    Object curR = in.readObject();
			       // 2. baca dan proses setiap record yang dibaca  
			     	   while (true) {
			        	   R = (Nasabah) curR; //inputstream -> objek customer
			        	   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());    
			               out.writeObject(R); 
			        	   curR = in.readObject(); // baca lagi...
			           }   
			       } catch (EOFException e) {}
				try {
					// FILE TEMP DI UBAH LALU DI PINDAHKAN KEMBALI KE FILE UTAMA					
				 	// 1. buka file untuk dibaca	
				     in=new ObjectInputStream(new FileInputStream
				    		   ("C:\\Nasabah/Temp.txt"));
				    		   Object curR = in.readObject();
				    // 2. buka file untuk ditulis	
				    out=new ObjectOutputStream(new FileOutputStream
				    		   ("C:\\Nasabah/NasabahKasihBunda.txt"+ ""));// nama direktori+file
			       BufferedReader brInput= new BufferedReader
		                    (new InputStreamReader(System.in)); 
					 
					  // 2. baca dan proses setiap record yang dibaca    
			       while (true) {
		        	   R = (Nasabah) curR; //inputstream -> objek customer
		        	   if(!R.getNorek().contentEquals(norek)) {
		        		   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo()); 
		        	   }else {
		        		   Boolean status = false;
		        		   do {
		        			   if(status && tarik>R.getSaldo()) {
		        				   System.out.println("Maaf saldo anda tidak mencukupi");
					    		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
					    		   System.out.println("----------");
		        			   }
		        			   
		        			   if(status) {
		        				   System.out.print("1.Lanjut/0.Batal: "); x=sc.nextInt();
		        			   }
		        			   
					    	   if(x==1) {
				    			   System.out.print("Transfer Saldo (min 10k) :  ");tarik=sc.nextFloat();
				    		   }
					    	   
					    	   status = true;
		        		   }while(tarik<10000 && x == 1||tarik>R.getSaldo() && x == 1);
		        		   if(x==0) {
		        			   tarik = 0;
			    			   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());
			    		   }else {
			        		   saldoBaru=R.getSaldo()-tarik;
			        		   R=new Nasabah(R.getNorek(),R.getPin(),saldoBaru);
			        		   System.out.println("Saldo berhasil ditransfer");
			        		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
			    		   }
		        	   }
		               out.writeObject(R); 
		        	   curR = in.readObject(); // baca lagi...
	     		   }   
		}catch (EOFException e) {
			System.out.println("");
		}
		       }
		       // endif
		       
		  	 System.out.println("");
		       } catch (ClassNotFoundException e) {
		             System.out.println("Class tidak ditemukan.");
		       } catch (IOException e) {
		             e.printStackTrace();
		      }  
		    }	
		
		// Transfer Saldo
		 public String TransferSaldo(String norek){
				Nasabah R = new Nasabah();
				String norekTujuan;
				Boolean ketemu=false;

				System.out.println("========== Transfer Saldo ======");     
				ObjectInputStream in = null;
				 // TRY DI DALAM TRY TETAPI IN OUT NYA SATU SAJA
				try {
					// 1. buka file untuk dibaca	
					in=new ObjectInputStream(new FileInputStream
		    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
		       Object curR = in.readObject();	
		       System.out.print("1.Lanjut/0.Batal:  ");int x=sc.nextInt();
		       if(x!=0) {
		       try {	
		    	   Scanner sc = new Scanner(System.in);
		    	   System.out.print("Norek Tujuan :  "); norekTujuan=sc.next();
		    	   if(norek.contentEquals(norekTujuan)) {
		        		  System.out.println("Anda tidak boleh menginput no rekening sendiri!");
		        		  System.out.println("");
		        		  return "";
		        	 }
		       // 2. baca dan proses setiap record yang dibaca  
		    	  while (ketemu==false) {
		        	   R = (Nasabah) curR; //inputstream -> objek customer
		        	   // cek apakah norek ada?
		        	  if(R.getNorek().contentEquals(norekTujuan)){
		        		  	return norekTujuan;
		        	  }
		        	  curR = in.readObject(); // baca terus kedata selanjutnya
		    	 }
		    	  
		    	  
		    	 
		       } catch (EOFException e) {}
		       if(!ketemu) {	
		             System.out.println("Nasabah tidak ditemukan");
		       }
		       System.out.println("");
		       }
		       } catch (ClassNotFoundException e) {
		             System.out.println("Class tidak ditemukan.");
		       } catch (IOException e) {
		             e.printStackTrace();
		      }   
				return "";
		    }
		 
		 public float KurangSaldo(String norek, String norekTujuan){
			 if(norekTujuan.isEmpty()){
			 }else {
			    Nasabah R = new Nasabah();
			    float saldoBaru, transfer = 0;   
			    ObjectInputStream in = null; // read
			    ObjectOutputStream out = null; // write
			    // TRY DI DALAM TRY APABILA IN OUT NYA MASING MASING
			    try {
			       try {
			    	 // CEK SALDO NYA CUKUP ATAU ENGGA
			    	// 1. buka file untuk dibaca	
				       in=new ObjectInputStream(new FileInputStream
				    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
			    	   Object curR = in.readObject();
			       // 2. baca dan proses setiap record yang dibaca ;
				    	 while (true) {
				        	   R = (Nasabah) curR; //inputstream -> objek customer
				        	  if(R.getNorek().contentEquals(norek)&& R.getSaldo()< 10000){
				        		  System.out.println("Mohon maaf anda miskin!");
				        			 System.out.println("");
				        			 return 0;
				        	  }			        	  
				        	  curR = in.readObject(); // baca lagi...
				        } 
			       } catch (EOFException e) {}
			       try {	
			    	// KALO CUKUP ISI FILE UTAMA DI BACKUP KE TEMP
			    	// 1. buka file untuk dibaca	
				       in=new ObjectInputStream(new FileInputStream
				    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
				       // 2. buka file untuk ditulis	
				    	out=new ObjectOutputStream(new FileOutputStream
				    		              ("C:\\Nasabah/Temp.txt"+ ""));// nama direktori+file
					    Object curR = in.readObject();
				       // 2. baca dan proses setiap record yang dibaca  
				     	   while (true) {
				        	   R = (Nasabah) curR; //inputstream -> objek customer
				        	   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());    
				               out.writeObject(R); 
				        	   curR = in.readObject(); // baca lagi...
				           }   
				       } catch (EOFException e) {}
					try {
						// FILE TEMP DI UBAH LALU DI PINDAHKAN KEMBALI KE FILE UTAMA					
					 	// 1. buka file untuk dibaca	
					     in=new ObjectInputStream(new FileInputStream
					    		   ("C:\\Nasabah/Temp.txt"));
					    		   Object curR = in.readObject();
					    // 2. buka file untuk ditulis	
					    out=new ObjectOutputStream(new FileOutputStream
					    		   ("C:\\Nasabah/NasabahKasihBunda.txt"+ ""));// nama direktori+file
				       BufferedReader brInput= new BufferedReader
			                    (new InputStreamReader(System.in)); 
						 
						  // 2. baca dan proses setiap record yang dibaca    
				     		   while (true) {
					        	   R = (Nasabah) curR; //inputstream -> objek customer
					        	   if(!R.getNorek().contentEquals(norek)) {
					        		   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo()); 
					        	   }else {
					        		   Boolean status = false;
					        		   int x = 1;
					        		   do {
					        			   if(status && transfer>R.getSaldo()) {
					        				   System.out.println("Maaf saldo anda tidak mencukupi");
								    		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
								    		   System.out.println("----------");
					        			   }
					        			   
					        			   if(status) {
					        				   System.out.print("1.Lanjut/0.Batal: "); x=sc.nextInt();
					        			   }
					        			   
								    	   if(x==1) {
							    			   System.out.print("Transfer Saldo (min 10k) :  ");transfer=sc.nextFloat();
							    		   }
								    	   
								    	   status = true;
					        		   }while(transfer<10000 && x == 1||transfer>R.getSaldo() && x == 1);
					        		   if(x==0) {
					        			   transfer = 0;
						    			   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());
						    		   }else {
						        		   saldoBaru=R.getSaldo()-transfer;
						        		   R=new Nasabah(R.getNorek(),R.getPin(),saldoBaru);
						        		   System.out.println("Saldo berhasil ditransfer");
						        		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
						    		   }
					        	   }
					               out.writeObject(R); 
					        	   curR = in.readObject(); // baca lagi...
				     		   }   
					}catch (EOFException e) {
						System.out.println("");
						return transfer;
					}
			       // endif
			       } catch (ClassNotFoundException e) {
			             System.out.println("Class tidak ditemukan.");
			       } catch (IOException e) {
			             e.printStackTrace();
			      }  
			 }
			  return 0;
			}	
		 
		// Tambah Saldo
			public void TambahSaldo(String norek, float transfer){
			    Nasabah R = new Nasabah();
			    float saldoBaru;
			    Boolean baca = true;
			    ObjectInputStream in = null; // read
			    ObjectOutputStream out = null; // write
			    // TRY DI DALAM TRY APABILA IN OUT NYA MASING MASING
			    try {
			       try {
			    	// ISI FILE UTAMA DI BACKUP KE TEMP
			    	// 1. buka file untuk dibaca	
				       in=new ObjectInputStream(new FileInputStream
				    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
				    		   Object curR = in.readObject();
				       // 2. buka file untuk ditulis	
				    	out=new ObjectOutputStream(new FileOutputStream
				    		              ("C:\\Nasabah/Temp.txt"+ ""));// nama direktori+file
			       // 2. baca dan proses setiap record yang dibaca    
			     	   while (true) {
			        	   R = (Nasabah) curR; //inputstream -> objek customer
			        	   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());    
			               out.writeObject(R); 
			        	   curR = in.readObject(); // baca lagi...
			           }   
			       } catch (EOFException e) {}
			       
					try {
						// FILE TEMP DI UBAH LALU DI PINDAHKAN KEMBALI KE FILE UTAMA
					 	// 1. buka file untuk dibaca	
					    in=new ObjectInputStream(new FileInputStream
					    		   ("C:\\Nasabah/Temp.txt"));
					    		   Object curR = in.readObject();
					    // 2. buka file untuk ditulis	
					    out=new ObjectOutputStream(new FileOutputStream
					    		   ("C:\\Nasabah/NasabahKasihBunda.txt"+ ""));// nama direktori+file
				       BufferedReader brInput= new BufferedReader
			                    (new InputStreamReader(System.in)); 
						  // 2. baca dan proses setiap record yang dibaca    
				     		   while (baca) {
					        	   R = (Nasabah) curR; //inputstream -> objek customer
					        	   
					        	   if(!R.getNorek().contentEquals(norek)) {
					        		   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo()); 
					        	   }else {
					        		   saldoBaru = R.getSaldo() + transfer;
					        		   R=new Nasabah(R.getNorek(),R.getPin(),saldoBaru); 
					        	   }
					               out.writeObject(R); 
					        	   curR = in.readObject(); // baca lagi...
				     		   }
				     		  baca=false;
				   } catch (EOFException e) {}
					 System.out.println("");	
			       } catch (ClassNotFoundException e) {
			             System.out.println("Class tidak ditemukan.");
			       } catch (IOException e) {
			             e.printStackTrace();
			    }		
				}
			
			// Topup Pulsa
			 public void TopupPulsa(String norek){
				 Nasabah R = new Nasabah();
				    float saldoBaru, tarik;
				    String telepon = "";
				    System.out.println("====== Topup Pulsa ======");     
				    ObjectInputStream in = null; // read
				    ObjectOutputStream out = null; // write
				    // TRY DI DALAM TRY APABILA IN OUT NYA MASING MASING
				    try {
				       try {
				    	 // CEK SALDO NYA CUKUP ATAU ENGGA
				    	// 1. buka file untuk dibaca	
					       in=new ObjectInputStream(new FileInputStream
					    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
				    	   Object curR = in.readObject();
				       // 2. baca dan proses setiap record yang dibaca ;
					    	 while (true) {
					        	   R = (Nasabah) curR; //inputstream -> objek customer
					        	  if(R.getNorek().contentEquals(norek)&& R.getSaldo()< 10000){
					        		  System.out.println("Mohon maaf anda miskin!");
					        			 System.out.println("");
					        			 return;
					        	  }			        	  
					        	  curR = in.readObject(); // baca lagi...
					        } 
				       } catch (EOFException e) {}
				       System.out.print("1.Lanjut/0.Batal: ");int x=sc.nextInt();
				       if(x!=0) {
				       try {	
				    	// KALO CUKUP ISI FILE UTAMA DI BACKUP KE TEMP
				    	// 1. buka file untuk dibaca	
					       in=new ObjectInputStream(new FileInputStream
					    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
					       // 2. buka file untuk ditulis	
					    	out=new ObjectOutputStream(new FileOutputStream
					    		              ("C:\\Nasabah/Temp.txt"+ ""));// nama direktori+file
						    Object curR = in.readObject();
					       // 2. baca dan proses setiap record yang dibaca  
					     	   while (true) {
					        	   R = (Nasabah) curR; //inputstream -> objek customer
					        	   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());    
					               out.writeObject(R); 
					        	   curR = in.readObject(); // baca lagi...
					           }   
					       } catch (EOFException e) {}
						try {
							// FILE TEMP DI UBAH LALU DI PINDAHKAN KEMBALI KE FILE UTAMA					
						 	// 1. buka file untuk dibaca	
						     in=new ObjectInputStream(new FileInputStream
						    		   ("C:\\Nasabah/Temp.txt"));
						    		   Object curR = in.readObject();
						    // 2. buka file untuk ditulis	
						    out=new ObjectOutputStream(new FileOutputStream
						    		   ("C:\\Nasabah/NasabahKasihBunda.txt"+ ""));// nama direktori+file
					       BufferedReader brInput= new BufferedReader
				                    (new InputStreamReader(System.in)); 
							 
							  // 2. baca dan proses setiap record yang dibaca    
					     		   while (true) {
						        	   R = (Nasabah) curR; //inputstream -> objek customer
						        	   if(!R.getNorek().contentEquals(norek)) {
						        		   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo()); 
						        	   }else {
						        		   Boolean status = false;
						        		   do {
						        			   if(status) {
						        				   System.out.println("No telopon tidak ditemukan");
						        				   System.out.print("1.Lanjut/0.Batal: "); x=sc.nextInt();
						        			   }
						        			   
						        			   if(x == 1) {
						        				   System.out.print("Telopon :  ");telepon=sc.next();
						        			   }
						        			   
						        			   status = true;
						        		   }while("081931455863".contentEquals(telepon) != true && x == 1);
						        		   
						        		   if(x == 1) {
						        		   System.out.println("Pilih paket:  ");
						        		   System.out.println("1. Rp.5.000  ");
						        		   System.out.println("2. Rp.10.000  ");
						        		   System.out.println("3. Rp.15.000  ");
						        		   System.out.println("4. Rp.20.000  ");
						        		   System.out.println("5. Rp.25.000  ");
						        		   System.out.println("6. Rp.50.000  ");
						        		   System.out.println("0. Batal  ");
						        		   System.out.print("Pilih paket pulsa (Rp.1.000 biaya admin) :  ");x=sc.nextInt();
						        		   do {
						        			   switch(x) {
						        			   		case 1: tarik = 6000;
						        			   				break;
						        			   		case 2: tarik = 11000;
				        			   						break;
						        			   		case 3: tarik = 16000;
				        			   						break;
						        			   		case 4: tarik = 21000;
				        			   						break;
						        			   		case 5: tarik = 26000;
				        			   						break;
						        			   		case 6: tarik = 51000;
				        			   						break;
				        			   				default: tarik = 0;
						        			   }	
									    	   if(tarik>R.getSaldo()) {
									    		   System.out.println("Maaf saldo anda tidak mencukupi");
									    		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
									    		   System.out.println("----------");
									    		   System.out.print("1.Lanjut/0.Batal: ");x=sc.nextInt();
									    		   System.out.println("");
									    	   }
						        		   }while(tarik>R.getSaldo() && x == 1);
						        		   if(x==0) {
							    			   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());
							    		   }else {
						        		   saldoBaru=R.getSaldo()-tarik;
						        		   R=new Nasabah(R.getNorek(),R.getPin(),saldoBaru);
						        		   System.out.println("Pulsa berhasil ditopup");
						        		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
							    		   }
						        		   }
						        	   }
						               out.writeObject(R); 
						        	   curR = in.readObject(); // baca lagi...
					     		   }   
						}catch (EOFException e) {}   
				       }
				       // endif
				       
				  	 System.out.println("");
				       } catch (ClassNotFoundException e) {
				             System.out.println("Class tidak ditemukan.");
				       } catch (IOException e) {
				             e.printStackTrace();
				      }  
			 }
			 
				 // Bayar Lisrik
					 public void BayarListrik(String norek){
						 Nasabah R = new Nasabah();
						    float saldoBaru, tarik;
						    String token = "";
						    System.out.println("====== Bayar Listrik ======");     
						    ObjectInputStream in = null; // read
						    ObjectOutputStream out = null; // write
						    // TRY DI DALAM TRY APABILA IN OUT NYA MASING MASING
						    try {
						       try {
						    	 // CEK SALDO NYA CUKUP ATAU ENGGA
						    	// 1. buka file untuk dibaca	
							       in=new ObjectInputStream(new FileInputStream
							    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
						    	   Object curR = in.readObject();
						       // 2. baca dan proses setiap record yang dibaca ;
							    	 while (true) {
							        	   R = (Nasabah) curR; //inputstream -> objek customer
							        	  if(R.getNorek().contentEquals(norek)&& R.getSaldo()< 10000){
							        		  System.out.println("Mohon maaf anda miskin!");
							        			 System.out.println("");
							        			 return;
							        	  }			        	  
							        	  curR = in.readObject(); // baca lagi...
							        } 
						       } catch (EOFException e) {}
						       	System.out.print("1.Lanjut/0.Batal: ");int x=sc.nextInt();
						       if(x!=0) {
						       try {	
						    	// KALO CUKUP ISI FILE UTAMA DI BACKUP KE TEMP
						    	// 1. buka file untuk dibaca	
							       in=new ObjectInputStream(new FileInputStream
							    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
							       // 2. buka file untuk ditulis	
							    	out=new ObjectOutputStream(new FileOutputStream
							    		              ("C:\\Nasabah/Temp.txt"+ ""));// nama direktori+file
								    Object curR = in.readObject();
							       // 2. baca dan proses setiap record yang dibaca  
							     	   while (true) {
							        	   R = (Nasabah) curR; //inputstream -> objek customer
							        	   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());    
							               out.writeObject(R); 
							        	   curR = in.readObject(); // baca lagi...
							           }   
							       } catch (EOFException e) {}
								try {
									// FILE TEMP DI UBAH LALU DI PINDAHKAN KEMBALI KE FILE UTAMA					
								 	// 1. buka file untuk dibaca	
								     in=new ObjectInputStream(new FileInputStream
								    		   ("C:\\Nasabah/Temp.txt"));
								    		   Object curR = in.readObject();
								    // 2. buka file untuk ditulis	
								    out=new ObjectOutputStream(new FileOutputStream
								    		   ("C:\\Nasabah/NasabahKasihBunda.txt"+ ""));// nama direktori+file
							       BufferedReader brInput= new BufferedReader
						                    (new InputStreamReader(System.in)); 
									 
									  // 2. baca dan proses setiap record yang dibaca    
							     		   while (true) {
								        	   R = (Nasabah) curR; //inputstream -> objek customer
								        	   if(!R.getNorek().contentEquals(norek)) {
								        		   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo()); 
								        	   }else {
								        		   Boolean status = false;
								        		   do {
								        			   if(status) {
								        				   System.out.println("No token tidak ditemukan");
								        				   System.out.print("1.Lanjut/0.Batal: "); x=sc.nextInt();
								        			   }
								        			   
								        			   if(x == 1) {
								        				   System.out.print("No Token :  ");token=sc.next();
								        			   }
								        			   
								        			   status = true;
								        		   }while("3311222".contentEquals(token) != true && x == 1);
								        		   
								        		   if(x == 1) {
								        		   System.out.println("Pilih Bayar:  ");
								        		   System.out.println("1. Rp.5.000  ");
								        		   System.out.println("2. Rp.10.000  ");
								        		   System.out.println("3. Rp.15.000  ");
								        		   System.out.println("4. Rp.20.000  ");
								        		   System.out.println("5. Rp.25.000  ");
								        		   System.out.println("6. Rp.50.000  ");
								        		   System.out.println("0. Batal  ");
								        		   System.out.print("Pilih paket listrik (Rp.2.500 biaya admin) :  ");x=sc.nextInt();
								        		   do {
								        			   switch(x) {
								        			   		case 1: tarik = 7500;
								        			   				break;
								        			   		case 2: tarik = 12500;
						        			   						break;
								        			   		case 3: tarik = 17500;
						        			   						break;
								        			   		case 4: tarik = 22500;
						        			   						break;
								        			   		case 5: tarik = 27500;
						        			   						break;
								        			   		case 6: tarik = 52500;
						        			   						break;
						        			   				default: tarik = 0;
								        			   }	
											    	   if(tarik>R.getSaldo()) {
											    		   System.out.println("Maaf saldo anda tidak mencukupi");
											    		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
											    		   System.out.println("----------");
											    		   System.out.print("1.Lanjut/0.Batal: ");x=sc.nextInt();
											    		   System.out.println("");
											    	   }
								        		   }while(tarik>R.getSaldo() && x == 1);
								        		   if(x==0) {
									    			   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());
									    		   }else {
								        		   saldoBaru=R.getSaldo()-tarik;
								        		   R=new Nasabah(R.getNorek(),R.getPin(),saldoBaru);
								        		   System.out.println("Listrik berhasil dibayar");
								        		   System.out.println("Token listrik anda: 32947366493");
								        		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
									    		   }
								        		   }
								        	   }
								               out.writeObject(R); 
								        	   curR = in.readObject(); // baca lagi...
							     		   }   
								}catch (EOFException e) {}   
						       }
						       // endif
						       
						  	 System.out.println("");
						       } catch (ClassNotFoundException e) {
						             System.out.println("Class tidak ditemukan.");
						       } catch (IOException e) {
						             e.printStackTrace();
						      }  
			 }
					 
					 // Bayar PDAM
					 public void BayarPdam(String norek){
						 Nasabah R = new Nasabah();
						    float saldoBaru, tarik;
						    System.out.println("====== Bayar PDAM ======");     
						    ObjectInputStream in = null; // read
						    ObjectOutputStream out = null; // write
						    // TRY DI DALAM TRY APABILA IN OUT NYA MASING MASING
						    try {
						       try {
						    	 // CEK SALDO NYA CUKUP ATAU ENGGA
						    	// 1. buka file untuk dibaca	
							       in=new ObjectInputStream(new FileInputStream
							    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
						    	   Object curR = in.readObject();
						       // 2. baca dan proses setiap record yang dibaca ;
							    	 while (true) {
							        	   R = (Nasabah) curR; //inputstream -> objek customer
							        	  if(R.getNorek().contentEquals(norek)&& R.getSaldo()< 20000){
							        		  System.out.println("Tagihan sebesar Rp.20.000");
							        		  System.out.println("Mohon maaf saldo anda kurang cukup!");
							        			 System.out.println("");
							        			 return;
							        	  }			        	  
							        	  curR = in.readObject(); // baca lagi...
							        } 
						       } catch (EOFException e) {}
						       System.out.println("Tagihan PDAM anda sebesar Rp.20.000");
						       System.out.print("1.Bayar/0.Batal: ");int x=sc.nextInt();
						       if(x!=0) {
						       try {	
						    	// KALO CUKUP ISI FILE UTAMA DI BACKUP KE TEMP
						    	// 1. buka file untuk dibaca	
							       in=new ObjectInputStream(new FileInputStream
							    		   ("C:\\Nasabah/NasabahKasihBunda.txt"));
							       // 2. buka file untuk ditulis	
							    	out=new ObjectOutputStream(new FileOutputStream
							    		              ("C:\\Nasabah/Temp.txt"+ ""));// nama direktori+file
								    Object curR = in.readObject();
							       // 2. baca dan proses setiap record yang dibaca  
							     	   while (true) {
							        	   R = (Nasabah) curR; //inputstream -> objek customer
							        	   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo());    
							               out.writeObject(R); 
							        	   curR = in.readObject(); // baca lagi...
							           }   
							       } catch (EOFException e) {}
								try {
									// FILE TEMP DI UBAH LALU DI PINDAHKAN KEMBALI KE FILE UTAMA					
								 	// 1. buka file untuk dibaca	
								     in=new ObjectInputStream(new FileInputStream
								    		   ("C:\\Nasabah/Temp.txt"));
								    		   Object curR = in.readObject();
								    // 2. buka file untuk ditulis	
								    out=new ObjectOutputStream(new FileOutputStream
								    		   ("C:\\Nasabah/NasabahKasihBunda.txt"+ ""));// nama direktori+file
							       BufferedReader brInput= new BufferedReader
						                    (new InputStreamReader(System.in)); 
									 
									  // 2. baca dan proses setiap record yang dibaca    
							     		   while (true) {
								        	   R = (Nasabah) curR; //inputstream -> objek customer
								        	   if(!R.getNorek().contentEquals(norek)) {
								        		   R=new Nasabah(R.getNorek(),R.getPin(),R.getSaldo()); 
								        	   }else {
								        		  
								        		   saldoBaru=R.getSaldo()-20000;
								        		   R=new Nasabah(R.getNorek(),R.getPin(),saldoBaru);
								        		   System.out.println("PDAM berhasil dibayar");
								        		   System.out.println("Sisa Saldo Anda : Rp."+ R.getSaldo());
								        	   }
								               out.writeObject(R); 
								        	   curR = in.readObject(); // baca lagi...
							     		   }   
								}catch (EOFException e) {}   
						       }
						       // endif
						       
						  	 System.out.println("");
						       } catch (ClassNotFoundException e) {
						             System.out.println("Class tidak ditemukan.");
						       } catch (IOException e) {
						             e.printStackTrace();
						      }  
			 }
}
// end functions

