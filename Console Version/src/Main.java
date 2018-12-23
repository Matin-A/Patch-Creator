import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        do {
            Scanner scanner = new Scanner(System.in);
            boolean badInput;

            String oldPath;
            do {
                System.out.println("Enter Old Version Path: ");
                oldPath = scanner.nextLine();
                if(!new File(oldPath).exists()){
                    badInput=true;
                    System.out.println("Path Not Exists.");
                }else{
                    badInput=false;
                }
            }while (badInput);

            String newPath;
            do {
                System.out.println("Enter New Version Path: ");
                newPath = scanner.nextLine();
                if(!new File(oldPath).exists()){
                    badInput=true;
                    System.out.println("Path Not Exists.");
                }else{
                    badInput=false;
                }
            }while (badInput);

            String saveTo;
            do {
                System.out.println("Enter Save To Path: ");
                saveTo = scanner.nextLine();
                if(!new File(oldPath).exists()){
                    badInput=true;
                    System.out.println("Path Not Exists.");
                }else{
                    badInput=false;
                }
            }while (badInput);

            boolean ignoreHashcode = false;
            do {
                System.out.println("Ignore Hashcode? (t or f)");
                try{
                    ignoreHashcode = trueOrFalse(scanner.nextLine());
                    badInput=false;
                }catch (Exception e){
                    badInput=true;
                }
            }while (badInput);



            PatchCreator patchCreator = new PatchCreator(new File(oldPath), new File(newPath), new File(saveTo), !ignoreHashcode);

            do {
                System.out.println("Start? (y or n)");
                String input = scanner.nextLine();
                if (input.matches("n")){
                    return;
                }else badInput = !input.matches("y");
            }while (badInput);

            patchCreator.createPatch();
            System.out.println("Patch Created.");

            do {
                System.out.println("Exit? (y or n)");
                String input = scanner.nextLine();
                if (input.matches("y")){
                    return;
                }else badInput = !input.matches("n");
            }while (badInput);
        }while (true);
    }

    private static boolean trueOrFalse(String input) throws Exception {
        if(input.matches("t")){
            return true;
        }else if(input.matches("f")){
            return false;
        }else{
            System.out.println("Wrong Input.");
            throw new Exception();
        }
    }
}
