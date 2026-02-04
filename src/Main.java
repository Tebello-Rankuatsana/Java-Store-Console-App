import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        DBManager db = new DBManager();

        System.out.println("1. Admin");
        System.out.println("2. User");
        System.out.print("Choose: ");
        int role = sc.nextInt();
        sc.nextLine();

        System.out.print("Password: ");
        String pass = sc.nextLine();

        // admin credentials checker
        if (role == 1 && pass.equals("iamroot123")) {

            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("Price: ");
                double price = sc.nextDouble();
                System.out.print("Quantity: ");
                int qty = sc.nextInt();

                db.addProduct(name, price, qty);
                System.out.println("Product added.");

            } else if (choice == 2) {
                System.out.print("Product ID: ");
                int id = sc.nextInt();
                db.deleteProduct(id);
                System.out.println("Product deleted.");
            }

        }

        // user credentials checker
        else if (role == 2 && pass.equals("torvalds")) {

            ArrayList<Product> products = db.getProducts();

            for (Product p : products) {
                System.out.println(p);
            }

            System.out.print("Enter product id: ");
            int id = sc.nextInt();

            if (db.buyProduct(id)) {
                System.out.println("successful");
            } else {
                System.out.println("ran out");
            }

        } else {
            System.out.println("Mxm!!");
        }

        sc.close();
    }
}
