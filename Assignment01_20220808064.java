import java.util.ArrayList;
/**
 @author Baha TÜTÜNCÜOĞLU
 @version 19.1
 @since   26.03.2023
 *
  **/

public class Assignment01_20220808064 {

}


class Product {
    private String Id;
    private String Name;
    private int Quantity;
    private double Price;

    public Product(String Id, String Name, int Quantity, double Price) {
        this.Id = Id;
        this.Name = Name;
        this.Quantity = Quantity;
        this.Price = Price;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
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

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public int remaining() {
        return Quantity;
    }



    public int addToInventory(int amount) {
        if (amount >= 0) {
           this.Quantity=Quantity + amount;
        }
        return Quantity;
    }

    public double purchase(int amount) {
        if (!((amount > Quantity) || (amount < 0))) {
           this.Quantity=Quantity-amount;
            return amount * Price;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Product " + getName() + " has " + remaining() + " remaining.";
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

    public FoodProduct(String Id,String Name,int Quantity,double Price,int Calories,boolean Dairy,boolean Peanuts,
    boolean Eggs, boolean Gluten){
       super(Id,Name,Quantity,Price);
       this.Calories=Calories;
       this.Dairy=Dairy;
       this.Peanuts=Peanuts;
       this.Eggs=Eggs;
       this.Gluten=Gluten;
    }

    public int getCalories() {
        return Calories;
    }

    public void setCalories(int calories) {
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

    public CleaningProduct(String ID,String name,int quantity,double price,boolean liquid,String whereToUse){
        super(ID,name,quantity,price);
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

    public Customer(String Name){
        this.Name=Name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }
    @Override
    public String toString(){
        return getName();
    }
}
class ClubCustomer extends Customer{
    private String Phone;
    private int Points;

    public ClubCustomer(String Name, String phone) {
        super(Name);
        Phone = phone;
        Points = 0;

    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }

    public int getPoints() {
        return Points;
    }
    public void addPoints(int points){
        if(!(points<0)){
            this.Points+=points;
        }
    }
    @Override
    public String toString(){
        return getName()+" has "+getPoints()+" points";
    }
}
class Store {
   private ArrayList<Product> products;

    private String Name;
    private String Website;

    public Store(String name, String website) {
        this.Name = name;
        this.Website = website;
        products = new ArrayList<Product>();
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

    public void addProduct(Product product, int index) {
        if ((index < 0 || index >= products.size()))
            products.add(product);
        else
            products.add(index, product);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProduct(int index) {
        if (index < 0 || index >= products.size()) {
            return null;
        }
        return products.get(index);
    }

    public int getProductIndex(Product p) {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equals(p)) {
               return i;

            }
        }
        return -1;
    }
    }