package ru.kolosov.lab2.model;

public class ItemEntity{

    private final Integer id;
    private String name;
    private int quantity;

    public  ItemEntity(Integer id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    public ItemEntity(String name, int quantity){
        this(null, name, quantity);
    }
    public Integer getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ItemEntity{id=" + id + ", name='" + name + "', quantity=" + quantity + "}";
    }

}
