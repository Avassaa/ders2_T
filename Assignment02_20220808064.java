import java.util.ArrayList;
public class Assignment02_20220808064 {
    //PAY METODUNU TEKRAR YAZ
}


class Product {
    private Long Id;
    private String Name;
    private int Quantity;
    private double Price;


    public Product(Long Id, String Name, int Quantity, double Price) {
        this.Id = Id;
        this.Name = Name;

        if(Quantity>=0)
            this.Quantity = Quantity;
        else
            throw new InvalidAmountException(Quantity);

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

    public int remaining() {
        return Quantity;
    }



    public int addToInventory(int amount) {
        if (amount >= 0) {
            this.Quantity=this.Quantity + amount;
        }
        else
            throw new InvalidAmountException(amount);
        return Quantity;
    }

    public double purchase(int amount) {
        if( amount < 0 ){
            throw new InvalidAmountException(amount);
        }
        else if (amount>Quantity){
            throw new InvalidAmountException(amount,Quantity);
        }
        else{
            this.Quantity-=amount;
            return amount*getPrice();

        }
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

    public FoodProduct(Long Id,String Name,int Quantity,double Price,int Calories,boolean Dairy,boolean Peanuts,
                       boolean Eggs, boolean Gluten){
        super(Id,Name,Quantity,Price);

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

    public CleaningProduct(Long ID,String name,int quantity,double price,boolean liquid,String whereToUse){
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
    private ArrayList<Product> customerCart;
    private ArrayList<Integer> countList;
    public Customer(String Name){
        this.Name=Name;
        customerCart=new ArrayList<>();
          countList=new ArrayList<>();

    }

    public String getName() {
        return Name;
    }

    public ArrayList<Integer> getCountList() {
        return countList;
    }

    public void setName(String name) {
        this.Name = name;
    }
    public ArrayList<Product> getCustomerCart(){
        return customerCart;
    }

    public void addToCart(Product product,int count){

        try {
            customerCart.add(product);
            countList.add(count);
             product.purchase(count);

        }catch(InvalidAmountException ex){
            System.out.println("ERROR:INVALID AMOUNT EXCEPTION for item "+product.getName()+", "+count+" exceeds the amount" +
                    " or is negative.");
        }catch (ProductNotFoundException ex){
            System.out.println("ERROR:Product not found.");
        }


    }

    public String receipt(){
        int ln =customerCart.size();
        String receipt="";
        for(int i = 0; i < ln; i++){
            double total=customerCart.get(i).getPrice()*countList.get(i);
            if(countList.get(i)>=0)
            receipt+=customerCart.get(i).getName()+" - "+customerCart.get(i).getPrice()
            +"  X "+countList.get(i)+" =" +total+"\n";
        }
return receipt+"*******************************\nTotal due - "+getTotalDue();

    }
    public double getTotalDue(){
        double totalDue=0;
        int ln =customerCart.size();
        for(int i=0;i<ln;i++){
            if(countList.get(i)>=0)
            totalDue+=customerCart.get(i).getPrice()*countList.get(i);
        }
        return totalDue;
    }

    public double pay(double amount){
        if(amount>=this.getTotalDue()){
            double temp=getTotalDue();
            System.out.println("Thank you");
            customerCart.clear();
            countList.clear();
            return (amount-temp);
        }else{
            throw new InsufficientFundsException(getTotalDue(),amount);
        }

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
    public double pay(double amount,boolean usePoints) {
        double afterTotal = getTotalDue() - (this.Points / 100.0);
        double beforeTotal = getTotalDue();

        if (!usePoints) {

            if (beforeTotal > amount) {
                throw new InsufficientFundsException(beforeTotal, amount);
            } else {
                this.Points += beforeTotal;
                getCustomerCart().clear();
                getCountList().clear();
                return amount - beforeTotal;
            }
        } else {
            if (afterTotal > amount) {
                throw new InsufficientFundsException(afterTotal, amount);
            } else {
                if(afterTotal<0) {
                    getCustomerCart().clear();
                    getCountList().clear();
                    this.Points=(int)((this.Points-getTotalDue()*100.0));
                    return amount;
                }else {
                    this.Points = 0;
                    this.Points += afterTotal;
                    getCustomerCart().clear();
                    getCountList().clear();
                    return amount - afterTotal;
                }
            }
        }
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
    private ArrayList<ClubCustomer> customers;
    private ArrayList<Product> products;

    private String Name;
    private String Website;

    public Store(String name, String website) {
        this.Name = name;
        this.Website = website;
        products = new ArrayList<>();
        customers= new ArrayList<>();

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

    public void addProduct(Product product) {
        products.add(product);
    }

    public Product getProduct(Long ID) {
        int ln=products.size();
        for(int i=0; i<ln; i++){
            if(products.get(i).getId().equals(ID)){
                return products.get(i);
            }
        }
        throw new ProductNotFoundException(ID);

    }
    public Product getProduct(String name){
        int ln=products.size();
        for(int i=0; i<ln; i++){
            if(products.get(i).getName().equalsIgnoreCase(name)){
                return products.get(i);
            }
        }
        throw new ProductNotFoundException(name);
    }

    public void addCustomer(ClubCustomer customer){
        customers.add(customer);
    }
    public ClubCustomer getCustomer(String phone){
        int ln=customers.size();
        for(int i=0; i<ln;i++){
            if(customers.get(i).getPhone()==phone)
                return customers.get(i);
        }
        throw new CustomerNotFoundException(phone);
    }
    public void removeProduct(Long ID){
        int ln=products.size();
        for(int i=0;i<ln;i++){
            if(products.get(i).getId().equals(ID)){
                products.remove(i);
            }
        }
        throw new ProductNotFoundException(ID);
    }
    public void removeProduct(String name){
        int ln=products.size();
        for(int i=0;i<ln;i++){
            if(products.get(i).getName().equals(name))
                products.remove(i);

        }

        throw new ProductNotFoundException(name);
    }
    public void removeCustomer(String phone){
        int ln=customers.size();
        for(int i=0;i<ln;i++){
            if(customers.get(i).getPhone().equals(phone))
                customers.remove(i);
        }
        throw new CustomerNotFoundException(phone);
    }

}



class CustomerNotFoundException extends IllegalArgumentException{
    private String phone;

    public CustomerNotFoundException(String phone){
        this.phone = phone;
    }
    @Override
    public String toString(){
        return "CustomerNotFoundException "+phone;
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

    private Long ID;
    private String name;

    public ProductNotFoundException(Long ID){
        this.ID = ID;
    }
    public ProductNotFoundException(String name) {
        this.name = name;
    }
    public ProductNotFoundException(Long ID, String name){this.ID = ID;this.name = name;}

    @Override
    public String toString(){
        if(name==null){
            return "ProductNotFoundException " +ID;
        }
        else {
            return "ProductNotFoundException " + name;
        }
    }
}