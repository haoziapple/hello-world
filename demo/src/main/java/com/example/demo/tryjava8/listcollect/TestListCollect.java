package com.example.demo.tryjava8.listcollect;

import com.example.demo.tryjava8.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wanghao
 * @Description
 * @date 2018-04-27 8:41
 */
public class TestListCollect {

    public static void main(String[] args) {
        List<Entity> ebizList = new ArrayList<>();
        List<Entity> saveList = new ArrayList<>();
        Entity a = new Entity();
        a.setA("1");
        Entity bb = new Entity();
        bb.setA("2");
        Entity c = new Entity();
        c.setA("3");
        ebizList.add(a);
        ebizList.add(bb);
        ebizList.add(c);

        // 这种写法，因为在equals方法里使用了getClass() != o.getClass()，就会有问题
        // 因为本质上这里是添加了一个Entity的匿名子类的实例，class肯定不等的
        // 最好不使用Double Brace Initialization
        // Because DBI creates an anonymous class with a reference to the instance of the
        // owning object, its use can lead to memory leaks if the anonymous inner class is
        // returned and held by other objects.
        ebizList.add(new Entity(){
            {
                c = "ss";
                setA("1");
                // 声明为protected的setC方法可以调用
                setC("2");
                // default声明的方法调用不了
//                setD();
            }
        });


        List<Entity> dblist = new ArrayList<>();

        Entity sampleEntity = new Entity();
        sampleEntity.setA("2");

        dblist.add(sampleEntity);

        for(Entity e: ebizList){
            System.out.println(dblist.contains(e));
        }


        saveList = ebizList.stream()
                .filter((Entity b) -> !dblist.contains(b))
                .collect(Collectors.toList());


        System.out.println(saveList);
    }

}
