package com.thread.pc_tradition_fake_noity;

/**
 * synchronized 生产者消费者问题
 * 解决虚假唤醒，不能if去判断是否等待，需要循环判断，如while(xx!=sss)
 * 使用： Objec.wait() 等待 ，Object.notifyAll()唤醒
 */
public class A {
    public static void main(String[] args) {
        B b = new B();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    b.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    b.decincr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    b.incr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    b.decincr();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();

    }
}

class B {
    int nums = 0;
    public  synchronized void incr() throws InterruptedException {
        //判断等待,让其他线程跑
        while (nums != 0) {
            this.wait();
        }
        //业务
        nums++;
        System.out.println(Thread.currentThread().getName()+"==>"+nums);
        //通知
        this.notifyAll();
    }

    public synchronized void decincr() throws InterruptedException {
        //判断等待,让其他线程跑
        while (nums == 0) {
            this.wait();
        }
        //业务
        nums--;
        System.out.println(Thread.currentThread().getName()+"==>"+nums);
        //通知
        this.notifyAll();
    }

}