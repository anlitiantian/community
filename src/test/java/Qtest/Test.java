package Qtest;

import java.util.*;

/**
 * @program: community
 * @description:
 * @author: Mr.Liu
 * @create: 2022-04-24 10:48
 **/
public class Test {
    List<String> res = new ArrayList<>();

    public List<String> restoreIpAddresses(String s) {
        if(s.length() < 4 || s.length() > 12) return res;
        backtrack(s, 0, new ArrayList<String>());
        return res;
    }

    public void backtrack(String s, int index, List<String> tmp){
        //ip位数够，但后面还有剩余
        if(tmp.size() == 4 && index != s.length()){
            return;
        }
        //数字用完，但 ip 位数不够
        if(tmp.size() < 4 && index == s.length()){
            return;
        }
        if(tmp.size() == 4 && index == s.length()){
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < tmp.size(); i++){
                sb.append(tmp.get(i));
                if(i != tmp.size() - 1){
                    sb.append(".");
                }
            }
            res.add(sb.toString());
            return;
        }
        if(s.charAt(index) == '0'){
            tmp.add("0");
            backtrack(s, index + 1, tmp);
            tmp.remove(tmp.size() - 1);
            return;
        }
        //offset，偏移量，可以是1、2、3
        for(int offset = 1; offset < 4 && index + offset <= s.length(); offset++){
            String numStr = s.substring(index, index + offset);
            int num = Integer.parseInt(numStr);
            if(num <= 255){
                tmp.add(numStr);
                backtrack(s, index + offset, tmp);
                tmp.remove(tmp.size() - 1);
            }
        }
    }

    public int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<>();
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < asteroids.length; i++){
            int num = asteroids[i];
            //正数先加进栈
            if(num > 0){
                stack.addLast(num);
            }else{
                if(stack.isEmpty()){
                    list.add(num);
                }else{
                    while (!stack.isEmpty()){
                        int peek = stack.peekLast();
                        if(num + peek > 0){
                            //负数爆炸
                            break;
                        }else if(num + peek == 0){
                            //两者都爆炸
                            stack.pollLast();
                            break;
                        }else{
                            //正数爆炸
                            stack.pollLast();
                            if(stack.isEmpty()){
                                list.add(num);
                            }
                            continue;
                        }
                    }
                }
            }
        }
        list.addAll(stack);
        int[] res = list.stream().mapToInt(Integer::intValue).toArray();
        return res;
    }

    public static void main(String[] args) {
        Test test = new Test();

//        int[] a = {5, 10, -5};
//        int[] b = new int[10];
////        System.out.println(test.asteroidCollision(a));
//
//
//        String ip = "010010";
//        List<String> list = test.restoreIpAddresses(ip);
//        for(String s : list){
//            System.out.println(s);
//        }

        int[] arr = {1,3,5,7};
        System.out.println(Arrays.binarySearch(arr,8));

    }
}
