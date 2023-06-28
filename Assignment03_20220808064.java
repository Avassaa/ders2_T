import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author BAHA TÜTÜNCÜOĞLU
 * @since 30.05.2023
 */
public class Assignment03_20220808064 {
  public static void main(String[] args){
      Store s1 = new Store("Migros", "www.migros.com.tr");
      Store s2 = new Store("BIM", "www.bim.com.tr");
      Customer c = new Customer("CSE 102");
      Customer cc = new Customer("Club CSE 102");
      s1.addCustomer(cc);
      Product p = new Product(123456L, "Computer", 1000.00);
      FoodProduct fp = new FoodProduct(456798L, "Snickers", 2, 250, true, true, true, false);
      CleaningProduct cp = new CleaningProduct(31654L, "Mop", 99, false, "Multi-room");
      System.out.println(cp);
      s1.addToInventory(p, 20);
      s2.addToInventory(p, 10);
      s2.addToInventory(fp, 100);
      s1.addToInventory(cp, 28);
      System.out.println(s1.getName() + " has " + s1.getCount() + " products");
      System.out.println(s1.getProductCount(p));
      System.out.println(s1.purchase(p, 2));
      s1.addToInventory(p, 3);
      System.out.println(s1.getProductCount(p));
      System.out.println(s2.getProductCount(p));


      //System.out.println(s1.getProductCount(fp));//results in Exception

      //   System.out.println(s2.purchase (fp, 288)); // results in Exception


      c.addToCart(s1, p, 2);
      c.addToCart(s1, fp, 1); // NOTE: This does not stop the program because the Exception is caught
      c.addToCart(s1, cp, 1);
      System.out.println("Total due - " +c.getTotalDue (s1));
      System.out.println("\n\nReceipt: \n" + c.receipt(s1));


      //System.out.println("\n\nReceipt: \n" + c.receipt(s2)); // results in Exception

      //System.out.println("After paying: "+ c.pay(s1, 2000, true)); // results in Exception
      System.out.println("After paying:" + c.pay(s1, 2100, true));

      //System.out.println("Total due "+c.getTotalDue(s1));// results in Exception -
      //System.out.println("\n\nReceipt: \n " +c.receipt(s1)); // results in Exception

      cc.addToCart(s2, fp, 2);
      cc.addToCart(s2, fp, 1);
      System.out.println(cc.receipt(s2));
      cc.addToCart(s2, fp, 10);
      System.out.println(cc.receipt(s2));
  }

}

class Product {
    private Long Id;
    private String Name;
    private double Price;


    public Product(Long Id, String Name, double Price) {
        this.Id = Id;
        this.Name = Name;



        if(!(Price<0))
            this.Price = Price;
        else
            throw new InvalidPriceException(Price);

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        this.Id =id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        if(!(Price<0))
            this.Price = price;
        else{
            throw new InvalidPriceException(price);
        }
    }



    @Override
    public String toString() {
        return "{"+getId()+"} -" + getName() + "@"+Price;
    }

    @Override
    public boolean equals(Object o) {
        double threshold = 0.001;
        return(o instanceof Product)&&Math.abs(((Product)o).Price-Price)<=threshold;

    }
}


class FoodProduct extends Product {

    private int Calories;
    private boolean Dairy;
    private boolean Eggs;
    private boolean Peanuts;
    private boolean Gluten;

    public FoodProduct(Long Id,String Name,double Price,int Calories,boolean Dairy,boolean Peanuts,
                       boolean Eggs, boolean Gluten){
        super(Id,Name,Price);

        if(!(Calories<0)) {
            this.Calories = Calories;
        }else{
            throw new InvalidAmountException(Calories);
        }


        this.Dairy=Dairy;
        this.Peanuts=Peanuts;
        this.Eggs=Eggs;
        this.Gluten=Gluten;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
        if(calories<0){
            throw new InvalidAmountException(calories);
        }
        this.Calories = calories;
    }
    public boolean containsDairy(){
        return Dairy;
    }
    public boolean containsEggs(){
        return Eggs;
    }
    public boolean containsPeanuts(){
        return Peanuts;
    }
    public boolean containsGluten(){
        return Gluten;
    }
}
class CleaningProduct extends Product {
    private boolean Liquid;
    private String WhereToUse;

    public CleaningProduct(Long ID,String name,double price,boolean liquid,String whereToUse){
        super(ID,name,price);
        this.Liquid=liquid;
        this.WhereToUse=whereToUse;
    }

    public String getWhereToUse() {
        return WhereToUse;
    }
    public boolean isLiquid(){
        return this.Liquid;
    }
    public void setWhereToUse(String size) {
        this.WhereToUse = size;
    }
}
class Customer{
    private String Name;
    private HashMap<Store,HashMap<Product,Integer>> customerCart;
    public Customer(String Name){
        this.Name=Name;
        customerCart=new HashMap<>();

    }

    public String getName() {
        return Name;
    }


    public void setName(String name) {
        this.Name = name;
    }
    public HashMap<Store,HashMap<Product,Integer>> getCustomerCart(){
        return customerCart;
    }

    public void addToCart(Store store,Product product,int count){ //TRY CATCH?
        int ln=store.products.size();



        try {
            if (!store.products.containsKey(product)) {
                throw new ProductNotFoundException(product);
    }




            if (!customerCart.containsKey(store)) {
                HashMap<Product, Integer> map = new HashMap<>();
                map.put(product, count);
                customerCart.putIfAbsent(store, map);
            } else {
                if (customerCart.get(store).containsKey(product))
                    customerCart.get(store).put(product, customerCart.get(store).get(product) + count);
                    else {
                        customerCart.get(store).put(product, count);
                    }
                    store.purchase(product, count);
        }
    }catch(ProductNotFoundException ex){
    System.out.println(ex);
        }catch(InvalidAmountException ex){
            System.out.println(ex);
        }

   }









    public String receipt(Store store) {
        double totalDue=getTotalDue(store);
        if (!customerCart.containsKey(store))
            throw new StoreNotFoundException("ERROR:StoreNotFoundException"); //ekrana bir şey basmayacak
        else {
            StringBuilder sb = new StringBuilder();

            HashMap<Product, Integer> store_cart = customerCart.get(store);
            sb.append("STORE:").append(store.getName()).append("\n");
            for (Product p : store_cart.keySet()) {
                int item_count = store_cart.get(p);
                double item_price = p.getPrice();
                double total = item_count * item_price;
                sb.append(p.getId()).append("-").append(p.getName()).append("@")
                        .append(p.getPrice()).append(" X ").append(item_count).append( "---->").append(total).append("\n");
            }
            sb.append("--*--*--*--*--*--*--*--*--*--*--*\n");
            sb.append("TOTAL DUE:").append(totalDue);

            return sb.toString();
        }

    }





    public double getTotalDue(Store store){
        double totalDue=0;
        if(!customerCart.containsKey(store))
            throw new StoreNotFoundException("ERROR:StoreNotFoundException");
        else {
            HashMap<Product,Integer> store_cart=customerCart.get(store);

            for(Product p:store_cart.keySet()){
                totalDue+=p.getPrice()*store_cart.get(p);
            }


            return totalDue;
        }
    }
    public int getPoints(Store store){
        if(!store.customers.containsKey(this))//PROTECTED????
            throw new StoreNotFoundException("ERROR:StoreNotFoundException");

        return store.getCustomerPoints(this);
    }

    public double pay(Store store,double amount,boolean usePoints){ //purchase'li kısım addTOCart'da olmalı
        if(!customerCart.containsKey(store))
            throw new StoreNotFoundException("ERROR:StoreNotFoundException");

        double total_due=getTotalDue(store);
        double points_money;
        if(!store.customers.containsKey(this)){
            points_money=0;
        }else {
            points_money = store.getCustomerPoints(this) / 100.0;
        }
            boolean canPay = amount - total_due >= 0;
            boolean canPay_points = amount + points_money - total_due >= 0;


            if (usePoints) {
                double total_after_usePoints = total_due - points_money;
                if (!canPay_points) {
                    throw new InsufficientFundsException(total_after_usePoints, amount);
                }
                if (total_after_usePoints <= 0) {
                    points_money = Math.abs(total_after_usePoints);
                    store.customers.put(this,(int) points_money * 100);
                    total_after_usePoints = 0;
                    HashMap<Product,Integer> store_cart=customerCart.get(store);

                    customerCart.remove(store);

                    return amount;
                }else{
                    points_money=0;
                    store.customers.put(this,(int)total_after_usePoints);
                    HashMap<Product,Integer> store_cart=customerCart.get(store);

                    customerCart.remove(store);
                    return amount-total_after_usePoints;

                }

            }
            else{
                if(!canPay)
                    throw new InsufficientFundsException(total_due,amount);
                    else{
                    HashMap<Product,Integer> store_cart=customerCart.get(store);

                    customerCart.remove(store);
                    store.customers.put(this,store.customers.get(this)+(int)(total_due));
                    return amount-total_due;
                }
            }


    }
    @Override
    public String toString(){
        return getName();
    }
}




class Store {

    protected HashMap<Product,Integer> products;

    protected HashMap<Customer,Integer> customers;
    private String Name;
    private String Website;


    public Store(String name, String website) {
        this.Name = name;
        this.Website = website;
        products = new HashMap<>();
        customers= new HashMap<>();
    }

    public int getCount(){
        int total=0;
        for(Product p : products.keySet()){
            total+=1;
        }
        return total;
    }
    public int remaining(Product product) {
        int count=products.get(product);
        if(count==0){
            throw new ProductNotFoundException(product);
        }
        return count;

    }
    public int getCustomerPoints(Customer customer){
        if(!customers.containsKey(customer))
            throw new CustomerNotFoundException(customer);
        else{
            return customers.get(customer);
        }
    }



    public void addToInventory(Product product,int amount) {
        if(amount<0){
            throw new InvalidAmountException(amount);
        }else {
            if (products.containsKey(product)) {
                products.put(product, products.get(product) + amount);
            } else {
                products.put(product, amount);
            }
        }
    }

    public double purchase(Product product ,int amount) {
        if(!products.containsKey(product))
            throw new ProductNotFoundException(product);
        else {
            double item_price=product.getPrice();
            int current_stock=products.get(product);
            if (amount < 0) {
                throw new InvalidAmountException(amount);
            } else if (amount > current_stock) {
                throw new InvalidAmountException(amount, current_stock);
            } else {
                products.put(product, products.get(product) - amount);
                return (item_price * amount);
            }
        }
    }
    public int getProductCount(Product product) {
        if(!products.containsKey(product))
            throw new ProductNotFoundException(product);
        int count=0;
        for (Product p:products.keySet()){
                if(p.equals(product)){
                    count=products.get(p);
                }
        }
       return count;


    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        this.Website = website;
    }

    public int getInventorySize() {
        return products.size();
    }




    public void addCustomer(Customer customer){
        customers.put(customer,0);//set their points to 0
    }



    public void removeProduct(Product product){
        boolean isInInventory=products.containsKey(product);
        if(!isInInventory){
            throw new ProductNotFoundException(product);
        }
        products.remove(product);
    }



    public Customer findKey(HashMap<Customer,String> map,String value){
        Customer customer=null;
        for(Customer c:map.keySet()){
            if(value.equals(map.get(c)))
                customer = c;
        }
        return customer;
    }
    public String toString(){
        return this.getName();
    }

}



class CustomerNotFoundException extends IllegalArgumentException{

    private Customer customer;


    public CustomerNotFoundException(Customer customer){
        this.customer = customer;
    }

    @Override
    public String toString(){
        return "CustomerNotFoundException "+this.customer.getName();
    }
}



class InsufficientFundsException extends RuntimeException{
    private double total;
    private double payment;



    public InsufficientFundsException(double total, double payment){
        this.total = total;
        this.payment =payment;
    }
    @Override
    public String toString(){
        return "InsufficientFundsException: "+total+" due, but only "+payment+" given.";
    }
}



class InvalidAmountException extends RuntimeException{
    private int amount;
    private int quantity;
    private boolean qCheck;
    public InvalidAmountException(int amount){
        this.amount = amount;
        qCheck=false;
    }
    public InvalidAmountException(int amount,int quantity){
        this.amount = amount;
        this.quantity = quantity;
        qCheck=true;

    }

    public String toString(){
        // BURAYA BAK
        if(!qCheck){
            return "Invalid amount exception "+amount;
        }
        else {
            return "Invalid amount exception "+amount+ " was requested but only "+quantity+" remaining.";
        }
    }
}




class InvalidPriceException extends RuntimeException{
    private double price;

    public InvalidPriceException(double price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "InvalidPriceException "+price;
    }
}

class ProductNotFoundException extends IllegalArgumentException{


    private Product product;


    public ProductNotFoundException(Product product){
        this.product = product;
    }

    @Override
    public String toString(){
        return "ProductNotFoundException:ID-"+product.getId()+" Name- "+product.getName();
    }
}

class StoreNotFoundException extends IllegalArgumentException{
    private String name;

    public StoreNotFoundException(String name){
        this.name=name;
    }
    public String toString(){
        return "StoreNotFoundException "+name;
    }

}

