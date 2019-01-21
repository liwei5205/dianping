package com.kirck.test;

import com.alibaba.fastjson.JSONObject;
import com.kirck.pojo.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

public class TreadTest {
    @Test
    public void test() {
        int size = 100;
        List<User> users1 = new ArrayList<User>(size);
        User user;
        for (int i = 0; i < size; i++) {
            user = new User();
            user.setId(i);
            user.setAge(i + 10);
            user.setName("user" + i);
            users1.add(user);
        }
        List<User> users2 = new ArrayList<User>(size);
        for (int i = 100; i > 0; i--) {
            user = new User();
            user.setId(i);
            user.setAge(i + 10);
            user.setName("resu" + i);
            users2.add(user);
        }

        CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<User>();

        Vector<Thread> threads = new Vector<Thread>();
        Thread thread1 = new Thread(new ListRunnable(users,users1),"AAA");
        Thread thread2 = new Thread(new ListRunnable(users,users2),"BBB");

          threads.add(thread1);
          threads.add(thread2);
          thread1.start();
          thread2.start();

     for (Thread iThread : threads) {
        try {
            // 等待所有线程执行完毕
            iThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    System.out.println("主线执行。");
    }

    private class ListRunnable implements Runnable {

        CopyOnWriteArrayList<User> users;

        List<User> userList;

        public ListRunnable(CopyOnWriteArrayList<User> users,List<User> userList) {
            this.users = users;
            this.userList = userList;
        }


        @Override
        public void run() {
            for (User user : userList) {
                synchronized (users) {
                    try {
                        users.wait(1000L);
                    }catch (InterruptedException e){

                    }
                    users.add(user);
                    System.out.println(Thread.currentThread().getName() + "--"
                            + user);
                }
            }
        }
    }

}




