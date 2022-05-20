package controllers;

public class Objet1{
    private int int1;
    private String string1;

    public Objet1(int i){
        this.int1 = 1;
        this.string1 = "test1";
        if(i==1) {
            this.int1 = 1;
            this.string1 = "test1";
        }

        else if(i==2) {
            this.int1 = 4;
            this.string1 = "caca";
        }

        if(i==3) {
            this.int1 = 2;
            this.string1 = "pipi";
        }
    }




    public int getInt1(){return int1;}
    public String getString1(){return string1;}

    public void setString1(String string1) {
        this.string1 = string1;
    }

    @Override
    public String toString() {
        return "Objet1{" +
                "int1=" + int1 +
                ", string1='" + string1 + '\'' +
                '}';
    }
}
