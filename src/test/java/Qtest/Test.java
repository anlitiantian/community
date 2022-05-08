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
    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }



    public static void main(String[] args) {
        Test test = new Test();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1,2);
        map.remove(1);
        for(Integer num : map.keySet()){

        }

        PriorityQueue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return o1.val - o2.val;
            }
        });
        queue.offer(new ListNode(1));

    }

}
