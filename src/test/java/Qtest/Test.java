package Qtest;

import com.alibaba.fastjson.JSONObject;

import java.util.*;
import java.util.stream.Collectors;

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


        int[] a = {2,3,1,4};
        List<Integer> list = Arrays.stream(a).boxed().collect(Collectors.toList());
        for(int v : list){
            System.out.println(v);
        }

        Integer[] b = {1,2,3};
        List<Integer> integers = Arrays.asList(b);

    }

}
