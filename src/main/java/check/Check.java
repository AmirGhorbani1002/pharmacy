package check;

import java.util.Objects;
import java.util.Scanner;

public class Check {

    private final Scanner scanner = new Scanner(System.in);

    public int checkCorrectInt(String input){

        while(true){
            try{
                 Integer.parseInt(input);
                 if(Integer.parseInt(input) >= 0)
                     return Integer.parseInt(input);
                 else{
                     System.out.println("The number cannot be negative");
                     System.out.print("Enter again: ");
                     input = scanner.next();
                 }
            } catch (NumberFormatException e){
                System.out.println("just Enter integer");
                System.out.print("Enter again: ");
                input = scanner.next();
            }
        }
    }

    public int checkIntegerRange(int input, int range){
        while(true){
            try{
                if(input > 0 && input < range)
                    return input;
                else {
                    return -1;
                }
            } catch (NumberFormatException ignored){
            }
        }
    }

    public String checkYesOrNo(String input){
        while(true){
            if(Objects.equals(input, "yes") || Objects.equals(input, "no"))
                return input;
            System.out.println("Just enter yes or no");
            input = scanner.next();
        }
    }

    public float checkPrice(String input){
        while (true){
            try{
                Float.parseFloat(input);
                if(Float.parseFloat(input) > 0)
                    return Float.parseFloat(input);
                else{
                    System.out.println("The price cannot be negative");
                    System.out.print("Enter again: ");
                    input = scanner.next();
                }
            } catch (NumberFormatException e){
                System.out.println("Just enter float");
                input = scanner.next();
            }
        }
    }

    public static void pressEnter(){
        System.out.println("Press Enter to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        }
    }

}
