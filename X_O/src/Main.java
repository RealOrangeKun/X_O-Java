import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        NameMenu menu1 = new NameMenu();
        StringBuilder name= new StringBuilder();
        while (!menu1.Userinput()){
            name.append("a");
        }
        name = new StringBuilder(menu1.getUsername());
        System.out.println(name);
    }
}
