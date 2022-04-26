package Qtest;

import com.alibaba.fastjson.JSONObject;

import java.util.*;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-24 10:48
 **/
public class Test {
    static class ListNode {
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

    public void reorderList(ListNode head) {
        if (head.next == null) {
            return;
        }
        //找到中点
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        //slow是中点，下一个是后半部分的第一个
        ListNode mid = slow.next;
        //从中间截开
        slow.next = null;
        //拿到反转后的后半部分的头
        ListNode afterHead = reverse(mid);
        //前半部分的头
        ListNode preHead = head;
        //连接两部分
        while (preHead != null && afterHead != null) {
            ListNode preNext = preHead.next;
            ListNode afterNext = afterHead.next;
            afterHead.next = preNext;
            preHead.next = afterHead;
            preHead = preNext;
            afterHead = afterNext;
        }
    }

    //反转链表
    public ListNode reverse(ListNode node) {
        ListNode pre = null;
        ListNode cur = node;
        while (cur != null) {
            ListNode next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Test test = new Test();
        ListNode head = new ListNode(1);
        int cnt = 1;
        ListNode next = head;
        while(cnt < 7){
            next.next = new ListNode(++cnt);
            next = next.next;
        }
        test.print(head);
        System.out.println();
        test.reorderList(head);
        test.print(head);
    }

    public void print(ListNode head){
        ListNode node = head;
        while(node != null){
            System.out.print(node.val + " ");
            node = node.next;
        }
    }

}
