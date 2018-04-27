package com.example.demo.tryjava8;

/**
 * @author wanghao
 * @Description
 * @date 2018-04-27 8:39
 */
public class Entity {
    private String a;

    private String b;

    protected String c;

    String d;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // 这边getClass的方法可能有问题
        // 1. 不支持子类和父类的比较
        // 2. 使用匿名内部类的时候，也是永远返回false
        if (o == null || getClass() != o.getClass()) return false;

        Entity entity = (Entity) o;

        return a != null ? a.equals(entity.a) : entity.a == null;

    }

    @Override
    public int hashCode() {
        return a != null ? a.hashCode() : 0;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    protected void setC(String c) {
        this.b = c;
    }

    void setD(String d) {
        this.b = d;
    }
}
