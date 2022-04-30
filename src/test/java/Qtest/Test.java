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
    class MyCalendar {

        private TreeMap<Integer, Integer> map;

        public MyCalendar() {
            this.map = new TreeMap<>();
        }

        public boolean book(int start, int end) {


            Map.Entry<Integer, Integer> entry1 = map.floorEntry(start);
            Map.Entry<Integer, Integer> entry2 = map.ceilingEntry(start);

            if (entry1 != null && entry1.getValue() > start) {
                return false;
            }

            if (entry2 != null && entry2.getKey() < end) {
                return false;
            }

            map.put(start, end);
            return true;
        }
    }


    public static void main(String[] args) {
        Test test = new Test();
        TreeMap<Integer, Integer> map = new TreeMap<>();
        map.put(1,4);
        map.put(3,4);
        map.put(5,4);
        map.put(7,4);


        Map.Entry<Integer, Integer> entry = map.ceilingEntry(8);
        if(entry == null){
            System.out.println("找不到合适的！");
        }else {
            System.out.println(entry.getKey());
        }

    }

}
