/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package os2project;
import java.util.concurrent.Semaphore;
/**
 *
 * @author DELL
 */
public class Main {
    private static Semaphore writer=new Semaphore(1 ,true);     
    private static Semaphore reader=new Semaphore(1 ,true);
    private static int counter=0;
    private static int readers=0;
    public static void main(String[] args){
         Thread t1=new Thread(new Reader());
         Thread t2=new Thread(new Writer());
         t1.start();
         t2.start();
         try {
            t1.join();
            t2.join();
            } catch (InterruptedException e){
                  e.printStackTrace();
              }
          }       
    static class Reader implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    reader.acquire();
                } catch (InterruptedException e) {
                     e.printStackTrace();
                }
                readers++;
                if (readers == 1){
                    try{
                        writer.acquire();
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }   
                }
                reader.release();
                System.out.println("Reader thread" +Thread.currentThread().getName()+
                        " is reading... and the value is: " +counter+".");
                try {
                    reader.acquire();
                } catch (InterruptedException e) {
                     e.printStackTrace();
                }
                readers--;
                if (readers==0){
                    writer.release();
                }
                reader.release();
                try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            } 
        }
    }
    static class Writer implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                writer.acquire();
            } catch (InterruptedException e) {
                     e.printStackTrace();
            }
            counter++;
            System.out.println("writer thread " + Thread.currentThread().getId()+ 
                    " has updated the counter value ");
            writer.release();
            try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }   
}
