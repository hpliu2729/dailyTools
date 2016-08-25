package study.nio;

import java.nio.CharBuffer;

/**
 * @author XuYexin
 * @Description :
 * @date 2016/8/4
 */
public class BufferPractice {

    private static void drainBuffer(CharBuffer buffer){
        while (buffer.hasRemaining()){
            System.out.print(buffer.get());
        }
        System.out.println();
    }

    private static  boolean fillBuffer(CharBuffer buffer){
        if(index >= strings.length){
            return false;
        }
        String string = strings[index++];
        for (int i=0; i<string.length();i++){
            buffer.put(string.charAt(i));
        }
        return true;
    }

    private static int index = 0;

    private static String[] strings = {
            "我愿你是个谎",
            "从未走出南墙",
            "笑是神的伪装",
            "笑是强忍的伤",
            "就让我走向你，走向你的床",
            "就让我看见你，看见你的伤",
            "我想你就站在，站在大漠边疆",
            "我想你就站在，站在七月上"
    };

    public static void main(String[] args){
        CharBuffer buffer = CharBuffer.allocate(2000);
        while (fillBuffer(buffer)){
            buffer.flip();
            drainBuffer(buffer);
            buffer.clear();
        }
    }



}
