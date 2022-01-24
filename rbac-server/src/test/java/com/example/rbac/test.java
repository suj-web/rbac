package com.example.rbac;

import com.alibaba.druid.sql.visitor.functions.Char;
import io.swagger.models.auth.In;

/**
 * @Author suj
 * @create 2022/1/13
 */
public class test {
    public static void main(String[] args) throws Exception {
        Thread t = new Thread() {

            public void run() {
                my360DW();
            }
        };

//        t.run();
        t.start();
        Thread.sleep(2000);
        System.out.print("DW");


    }

    static void my360DW() {

        System.out.print("360");

    }
}

class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }
}

class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode listNode = new ListNode();
        ListNode temp = listNode;
        int num = 0;
        while (l1 != null && l2 != null) {
            int num1 = l1.val;
            int num2 = l2.val;

            temp.next = new ListNode((num1 + num2 + num) % 10);
            temp = temp.next;

            l1 = l1.next;
            l2 = l2.next;

            if(9 < num1 + num2 + num) {
                num = 1;
                continue;
            }
            num = 0;
        }

        while (l1!=null) {
            int num1 = l1.val;
            temp.next = new ListNode((num1+num)%10);
            temp = temp.next;

            l1 = l1.next;

            if(9 < num1 + num) {
                num = 1;
                continue;
            }
            num = 0;
        }

        while (l2!=null) {
            int num2 = l2.val;
            temp.next = new ListNode((num2+num)%10);
            temp = temp.next;

            l2 = l2.next;

            if(9 < num2 + num) {
                num = 1;
                continue;
            }
            num = 0;
        }

        if(num == 1) {
            temp.next = new ListNode(1);
        }

        return listNode.next;
    }
}
