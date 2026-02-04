import java.sql.*;
import java.util.ArrayList;

public class DBManager {

    private final String URL = "jdbc:postgresql://localhost:5432/storedb"; //connecting pgAdmin
    private final String USER = "postgres";
    private final String PASSWORD = "anime";

    private Connection connect() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // this is for fetching products and returning them as arrays
    public ArrayList<Product> getProducts() {
        ArrayList<Product> list = new ArrayList<>();

        try {
            Connection con = connect();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM products");

//          iterating through each database row then creates a product object for each row
            while (rs.next()) {
                list.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
        return list;
    }

    // admin: adding products
    public void addProduct(String name, double price, int qty) {
        try {
//            what sir showed us
            Connection con = connect();
            PreparedStatement ps =
                    con.prepareStatement(
                            "INSERT INTO products(name,price,quantity) VALUES(?,?,?)"
                    );
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.executeUpdate();//executes the sql command and updates the database
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
    }

    // admin: deleting products
    public void deleteProduct(int id) {
        try {
            Connection con = connect();
            PreparedStatement ps =
                    con.prepareStatement("DELETE FROM products WHERE id=?");
//            supplies the id
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
    }

    // user: buying products
    public boolean buyProduct(int id) {
        try {
            Connection con = connect();

            PreparedStatement check =
                    con.prepareStatement(
                            "SELECT quantity FROM products WHERE id=?"
                    );
            check.setInt(1, id);
            ResultSet rs = check.executeQuery();

            if (rs.next() && rs.getInt("quantity") > 0) {
                PreparedStatement update =
                        con.prepareStatement(
                                "UPDATE products SET quantity = quantity - 1 WHERE id=?"
                        );
                update.setInt(1, id);
                update.executeUpdate();
                return true;
            }
        } catch (Exception e) {
            System.out.println("ERROR: "+e);
        }
        return false;
    }
}
