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
public class RealWorld {
    private static Semaphore writer=new Semaphore(1);     
    private static Semaphore reader=new Semaphore(1);
    private static int AvailableTicket=5;
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
            while(AvailableTicket!=0){
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
                System.out.println("Passenger " +Thread.currentThread().getId()+" checked the available tickets.");
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
            while(AvailableTicket!=0){
                try {
                writer.acquire();
            } catch (InterruptedException e) {
                     e.printStackTrace();
            }
            AvailableTicket--;
            System.out.println("Passenger " + Thread.currentThread().getId()+ " booked a ticket.");
            writer.release();
            System.out.println("currently tickets Available: "+AvailableTicket);
            try{
                    Thread.sleep(2000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
            System.out.println("no available tickets.");
        }
    }

}

