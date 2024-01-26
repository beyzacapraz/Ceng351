package ceng.ceng351.cengfactorydb;
import java.sql.*;
import java.lang.*;
import java.util.*;
public class CENGFACTORYDB implements ICENGFACTORYDB{
    /**
     * Place your initialization code inside if required.
     *
     * <p>
     * This function will be called before all other operations. If your implementation
     * need initialization , necessary operations should be done inside this function. For
     * example, you can set your connection to the database server inside this function.
     */
    private static String user = "e123456"; // TODO: Your userName
    private static String password = ""; //  TODO: Your password
    private static String host = ""; // host name
    private static String database = ""; // TODO: Your database name
    private static int port = 8080; // port
    private static Connection connection = null;
    public void initialize() {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://"+this.host+":"+this.port+"/"+this.database+"?useSSL=false",this.user,this.password);

        }
        catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }


    /**
     * Should create the necessary tables when called.
     *
     * @return the number of tables that are created successfully.
     */
    public int createTables() {
        int count = 0;
        try(Statement stmt = connection.createStatement();) {
            String factory = "CREATE TABLE IF NOT EXISTS Factory " +
                    "(factoryId INTEGER not NULL, " +
                    " factoryName Text, " +// neye göre?
                    " factoryType Text, " +
                    " country Text, " +
                    " PRIMARY KEY (factoryId));";
            stmt.executeUpdate(factory);
            count++;
            String employee = "CREATE TABLE IF NOT EXISTS Employee" +
                    "(employeeId INTEGER not NULL, " +
                    " employeeName Text, " +// text?
                    " department Text, " +
                    " salary INTEGER, " +
                    " PRIMARY KEY (employeeId));";
            stmt.executeUpdate(employee);
            count++;
            String works_in = "CREATE TABLE IF NOT EXISTS Works_In" +
                    "(factoryId INTEGER not NULL, " +
                    " employeeId INTEGER not NULL, " +
                    " startDate Date, " + // date??
                    " PRIMARY KEY (factoryId,employeeId)," +
                    " FOREIGN KEY (factoryId) REFERENCES Factory (factoryId), " +
                    " FOREIGN KEY (employeeId) REFERENCES Employee (employeeId) ON DELETE CASCADE); ";
            stmt.executeUpdate(works_in);
            count++;
            String product = "CREATE TABLE IF NOT EXISTS Product " +
                    "( productId INTEGER not NULL, " +
                    " productName Text, " +
                    " productType Text, " +
                    " PRIMARY KEY (productId));";
            stmt.executeUpdate(product);
            count++;
            String produce = "CREATE TABLE IF NOT EXISTS Produce " +
                    "(factoryId INTEGER not NULL, " +
                    "productId INTEGER not NULL, " +
                    " amount INTEGER, " + // date??
                    " productionCost INTEGER, " +
                    " PRIMARY KEY (factoryId, productId)," +
                    " FOREIGN KEY (factoryId) REFERENCES Factory (factoryId)," +
                    " FOREIGN KEY (productId) REFERENCES Product (productId)); ";
            stmt.executeUpdate(produce);
            count++;
            String shipment = "CREATE TABLE IF NOT EXISTS Shipment " +
                    "(factoryId INTEGER not NULL, " +
                    " productId INTEGER not NULL, " +
                    " amount INTEGER, " + // date??
                    " pricePerUnit INTEGER, " +
                    " PRIMARY KEY (factoryId, productId)," +
                    " FOREIGN KEY (factoryId) REFERENCES Factory (factoryId)," +
                    " FOREIGN KEY (productId) REFERENCES Product (productId));";
            stmt.executeUpdate(shipment);
            count++;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Should drop the tables if exists when called.
     *
     * @return the number of tables are dropped successfully.
     */
    public int dropTables() {
        int count = 0;
        try(Statement stmt = connection.createStatement();
        ) {
            String dropFactory = "DROP TABLE IF EXISTS Shipment";
            stmt.executeUpdate(dropFactory);
            count++;
            String dropEmployee = "DROP TABLE IF EXISTS Produce";
            stmt.executeUpdate(dropEmployee);
            count++;
            String dropWorks = "DROP TABLE IF EXISTS Product";
            stmt.executeUpdate(dropWorks);
            count++;
            String dropProduct = "DROP TABLE IF EXISTS Works_In";
            stmt.executeUpdate(dropProduct);
            count++;
            String dropProduce = "DROP TABLE IF EXISTS Employee";
            stmt.executeUpdate(dropProduce);
            count++;
            String dropShip = "DROP TABLE IF EXISTS Factory";
            stmt.executeUpdate(dropShip);
            count++;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Should insert an array of Factory into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertFactory(Factory[] factories) {
        int count = 0;
        String instance = "INSERT INTO Factory (factoryId, factoryName, factoryType, country) VALUES (?, ?, ?, ?)";
        // preparedStatement pre-compiles SQL statements that might contain parameters.
        try (PreparedStatement preparedStatement = connection.prepareStatement(instance)) {
            for (Factory factory : factories) {
                preparedStatement.setInt(1, factory.getFactoryId());
                preparedStatement.setString(2, factory.getFactoryName());
                preparedStatement.setString(3, factory.getFactoryType());
                preparedStatement.setString(4, factory.getCountry());

                int result = preparedStatement.executeUpdate(); // returns the number of affected rows
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Should insert an array of Employee into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertEmployee(Employee[] employees) {
        int count = 0;
        String instance = "INSERT INTO Employee (employeeId, employeeName, department, salary) VALUES (?, ?, ?, ?)";
        // preparedStatement pre-compiles SQL statements that might contain parameters.
        try (PreparedStatement preparedStatement = connection.prepareStatement(instance)) {
            for (Employee employee : employees) {
                preparedStatement.setInt(1, employee.getEmployeeId());
                preparedStatement.setString(2, employee.getEmployeeName());
                preparedStatement.setString(3, employee.getDepartment());
                preparedStatement.setInt(4, employee.getSalary());
                int result = preparedStatement.executeUpdate(); // returns the number of affected rows
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Should insert an array of WorksIn into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertWorksIn(WorksIn[] worksIns) {
        int count = 0;
        String instance = "INSERT INTO Works_In (factoryId, employeeId, startDate) VALUES (?, ?, ?)";
        // preparedStatement pre-compiles SQL statements that might contain parameters.
        try (PreparedStatement preparedStatement = connection.prepareStatement(instance)) {
            for (WorksIn work : worksIns) {
                preparedStatement.setInt(1, work.getFactoryId());
                preparedStatement.setInt(2, work.getEmployeeId());
                preparedStatement.setString(3, work.getStartDate());
                int result = preparedStatement.executeUpdate(); // returns the number of affected rows
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Should insert an array of Product into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduct(Product[] products) {
        int count = 0;
        String instance = "INSERT INTO Product (productId, productName, productType) VALUES (?, ?, ?)";
        // preparedStatement pre-compiles SQL statements that might contain parameters.
        try (PreparedStatement preparedStatement = connection.prepareStatement(instance)) {
            for (Product product : products) {
                preparedStatement.setInt(1, product.getProductId());
                preparedStatement.setString(2, product.getProductName());
                preparedStatement.setString(3, product.getProductType());
                int result = preparedStatement.executeUpdate(); // returns the number of affected rows
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


    /**
     * Should insert an array of Produce into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertProduce(Produce[] produces) {
        int count = 0;
        String instance = "INSERT INTO Produce (factoryId, productId, amount, productionCost) VALUES (?, ?, ?, ?)";
        // preparedStatement pre-compiles SQL statements that might contain parameters.
        try (PreparedStatement preparedStatement = connection.prepareStatement(instance)) {
            for (Produce produce : produces) {
                preparedStatement.setInt(1, produce.getFactoryId());
                preparedStatement.setInt(2, produce.getProductId());
                preparedStatement.setInt(3, produce.getAmount());
                preparedStatement.setInt(4, produce.getProductionCost());
                int result = preparedStatement.executeUpdate(); // returns the number of affected rows
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }


    /**
     * Should insert an array of Shipment into the database.
     *
     * @return Number of rows inserted successfully.
     */
    public int insertShipment(Shipment[] shipments) {
        int count = 0;
        String instance = "INSERT INTO Shipment (factoryId, productId, amount, pricePerUnit) VALUES (?, ?, ?, ?)";
        // preparedStatement pre-compiles SQL statements that might contain parameters.
        try (PreparedStatement preparedStatement = connection.prepareStatement(instance)) {
            for (Shipment ships : shipments) {
                preparedStatement.setInt(1, ships.getFactoryId());
                preparedStatement.setInt(2, ships.getProductId());
                preparedStatement.setInt(3, ships.getAmount());
                preparedStatement.setInt(4, ships.getPricePerUnit());
                int result = preparedStatement.executeUpdate(); // returns the number of affected rows
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }

    /**
     * Should return all factories that are located in a particular country.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesForGivenCountry(String country) {
        List<Factory> factoriesList = new ArrayList<>();

        String instance = "SELECT factoryId, factoryName, factoryType, country " +
                "FROM Factory WHERE country = ? " +
                "ORDER BY factoryId ASC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            preparedStatement.setString(1, country);//binds the value of the country variable to the first placeholder(?)
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { //Loop through the data in the resultset
                int factoryId = resultSet.getInt("factoryId");
                String factoryName = resultSet.getString("factoryName");
                String factoryType = resultSet.getString("factoryType");
                String countryName = resultSet.getString("country");
                Factory factory = new Factory(factoryId, factoryName, factoryType, countryName);
                factoriesList.add(factory);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return factoriesList.toArray(new Factory[0]);
    }



    /**
     * Should return all factories without any working employees.
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesWithoutAnyEmployee() {
        List<Factory> factoriesList = new ArrayList<>();

        String instance = "SELECT F.factoryId, F.factoryName, F.factoryType, F.country " +
                "FROM Factory F " +
                "WHERE F.factoryId NOT IN (SELECT W.factoryId " +
                "FROM Works_In W )" + // nested ?
                "ORDER BY factoryId ASC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { //Loop through the data in the resultset
                int factoryId = resultSet.getInt("factoryId");
                String factoryName = resultSet.getString("factoryName");
                String factoryType = resultSet.getString("factoryType");
                String countryName = resultSet.getString("country");
                Factory factory = new Factory(factoryId, factoryName, factoryType, countryName);
                factoriesList.add(factory);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return factoriesList.toArray(new Factory[0]);
    }

    /**
     * Should return all factories that produce all products for a particular productType
     *
     * @return Factory[]
     */
    public Factory[] getFactoriesProducingAllForGivenType(String productType) {
        List<Factory> factoriesList = new ArrayList<>();

        String instance = "SELECT F.factoryId, F.factoryName, F.factoryType, F.country " +
                " FROM Factory F" +
                " WHERE NOT EXISTS (SELECT T.productId " +
                "FROM Product T "+
                "WHERE T.productType = ? " +
                "EXCEPT (SELECT P.productId "+
                "FROM Produce P "+
                "WHERE P.factoryId = F.factoryId)) " +
                "ORDER BY factoryId ASC ";
        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            preparedStatement.setString(1, productType);//binds the value of the country variable to the first placeholder(?)
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { //Loop through the data in the resultset
                int factoryId = resultSet.getInt("factoryId");
                String factoryName = resultSet.getString("factoryName");
                String factoryType = resultSet.getString("factoryType");
                String countryName = resultSet.getString("country");
                Factory factory = new Factory(factoryId, factoryName, factoryType, countryName);
                factoriesList.add(factory);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return factoriesList.toArray(new Factory[0]);
    }


    /**
     * Should return the products that are produced in a particular factory but
     * don’t have any shipment from that factory.
     *
     * @return Product[]
     */
    public Product[] getProductsProducedNotShipped() {
        List<Product> ProductList = new ArrayList<>();

        String instance = "SELECT DISTINCT T.productId, T.productName, T.productType " +
                "FROM Produce P, Product T " +
                "WHERE P.productId = T.productId AND T.productId NOT IN (SELECT S.productId " +
                "FROM Shipment S " +
                "WHERE S.factoryId = P.factoryId AND S.productId = P.productId) " +
                "ORDER BY productId ASC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) { //Loop through the data in the resultset
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                String productType = resultSet.getString("productType");
                Product product = new Product(productId, productName, productType);
                ProductList.add(product);

            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return ProductList.toArray(new Product[0]);
    }


    /**
     * For a given factoryId and department, should return the average salary of
     *     the employees working in that factory and that specific department.
     *
     * @return double
     */
    public double getAverageSalaryForFactoryDepartment(int factoryId, String department) {
        String instance = "SELECT AVG(E.salary) AS avg " +
                "FROM Employee E, Works_In W " +
                "WHERE E.department = ? AND E.employeeId = W.employeeId AND W.factoryId = ? ";
        double avg = 0.0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            preparedStatement.setString(1, department);//binds the value of the country variable to the first placeholder(?)
            preparedStatement.setInt(2, factoryId);//binds the value of the country variable to the first placeholder(?)

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) { //Loop through the data in the resultset
                avg = resultSet.getDouble("avg");
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return avg;
    }


    /**
     * Should return the most profitable products for each factory
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProducts() {
        List<QueryResult.MostValueableProduct> mostProfitable = new ArrayList<>();
        String instance = "WITH Profit AS (" +
                "SELECT P.factoryId, T.productId, T.productName, T.productType, " +
                "(S.amount * S.pricePerUnit - P.amount * P.productionCost) AS profit " +
                "FROM Produce P, Product T, Shipment S " +
                "WHERE (T.productId = P.productId AND P.productId = S.productId AND P.factoryId = S.factoryId) " +
                "UNION " +
                "SELECT P.factoryId, T.productId, T.productName, T.productType, " +
                "(-P.amount * P.productionCost) AS profit " +
                "FROM Produce P, Product T, Shipment S " +
                "WHERE (T.productId = P.productId AND P.productId NOT IN " +
                "(SELECT S.productId FROM Shipment S WHERE S.productId = P.productId AND S.factoryId = P.factoryId))), " +
                "MaxProfit AS (SELECT factoryId, MAX(profit) AS max_profit FROM Profit GROUP BY factoryId) " +
                "SELECT P.factoryId, P.productId, P.productName, P.productType, P.profit " +
                "FROM Profit P, MaxProfit M " +
                "WHERE P.factoryId = M.factoryId AND P.profit = M.max_profit " +
                "ORDER BY P.profit DESC, P.factoryId ASC;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) { //Loop through the data in the resultset
                int factoryId = resultSet.getInt("factoryId");
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                String productType = resultSet.getString("productType");
                double profit = resultSet.getDouble("profit");
                QueryResult.MostValueableProduct product = new QueryResult.MostValueableProduct(factoryId, productId, productName, productType, profit);
                mostProfitable.add(product);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return mostProfitable.toArray(new QueryResult.MostValueableProduct[0]);
    }

    /**
     * For each product, return the factories that gather the highest profit
     * for that product
     *
     * @return QueryResult.MostValueableProduct[]
     */
    public QueryResult.MostValueableProduct[] getMostValueableProductsOnFactory() {
        List<QueryResult.MostValueableProduct> mostProfitableForProduct = new ArrayList<>();
        String instance  = "WITH Profit AS (" +
                "SELECT P.factoryId, T.productId, T.productName, T.productType, " +
                "(S.amount * S.pricePerUnit - P.amount * P.productionCost) AS profit " +
                "FROM Produce P, Product T, Shipment S " +
                "WHERE (T.productId = P.productId AND P.productId = S.productId AND P.factoryId = S.factoryId) " +
                "UNION " +
                "SELECT P.factoryId, T.productId, T.productName, T.productType, " +
                "(-P.amount * P.productionCost) AS profit " +
                "FROM Produce P, Product T, Shipment S " +
                "WHERE (T.productId = P.productId AND P.productId NOT IN " +
                "(SELECT S.productId FROM Shipment S WHERE S.productId = P.productId AND S.factoryId = P.factoryId))), " +
                "MaxProfit AS (SELECT productId, MAX(profit) AS max_profit FROM Profit GROUP BY productId) " +
                "SELECT P.factoryId, P.productId, P.productName, P.productType, P.profit " +
                "FROM Profit P, MaxProfit M " +
                "WHERE P.productId = M.productId AND P.profit = M.max_profit " +
                "ORDER BY P.profit DESC, P.productId ASC;";

        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) { //Loop through the data in the resultset
                int factoryId = resultSet.getInt("factoryId");
                int productId = resultSet.getInt("productId");
                String productName = resultSet.getString("productName");
                String productType = resultSet.getString("productType");
                double profit = resultSet.getDouble("profit");
                QueryResult.MostValueableProduct product = new QueryResult.MostValueableProduct(factoryId, productId, productName, productType, profit);
                mostProfitableForProduct.add(product);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return mostProfitableForProduct.toArray(new QueryResult.MostValueableProduct[0]);
    }


    /**
     * For each department, should return all employees that are paid under the
     *     average salary for that department. You consider the employees
     *     that do not work earning ”0”.
     *
     * @return QueryResult.LowSalaryEmployees[]
     */
    public QueryResult.LowSalaryEmployees[] getLowSalaryEmployeesForDepartments() {
        List<QueryResult.LowSalaryEmployees> lowSalary = new ArrayList<>();
        String instance = "SELECT E.employeeId, E.employeeName, E.department, E.salary " +
                "FROM Employee E, Works_In W " +
                "WHERE E.employeeId = W.employeeId " +
                "GROUP BY E.department, E.salary " +
                "HAVING E.salary < (SELECT AVG(COALESCE(E2.salary,0)) " +
                "                       FROM Employee E2 " +
                "                       WHERE E2.department = E.department) " +
                "UNION " +
                "SELECT E.employeeId, E.employeeName, E.department, E.salary = 0 " +
                "FROM Employee E " +
                "WHERE NOT EXISTS (SELECT * " +
                "                  FROM Works_In W " +
                "                  WHERE W.employeeId = E.employeeId) " +
                "ORDER BY employeeId ASC";
        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) { //Loop through the data in the resultset
                int employeeId = resultSet.getInt("employeeId");
                String employeeName = resultSet.getString("employeeName");
                String department = resultSet.getString("department");
                int salary = resultSet.getInt("salary");
                QueryResult.LowSalaryEmployees employee = new QueryResult.LowSalaryEmployees(employeeId, employeeName, department, salary);
                lowSalary.add(employee);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return lowSalary.toArray(new QueryResult.LowSalaryEmployees[0]);
    }


    /**
     * For the products of given productType, increase the productionCost of every unit by a given percentage.
     *
     * @return number of rows affected
     */
    public int increaseCost(String productType, double percentage) {
        String instance = "UPDATE Produce P " +
                "INNER JOIN Product T ON P.productId = T.productId " +
                "SET P.productionCost = P.productionCost + (P.productionCost * ?) " +
                "WHERE T.productType = ?";
        int result = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            preparedStatement.setDouble(1, percentage);//binds the value of the country variable to the first placeholder(?)
            preparedStatement.setString(2, productType);//binds the value of the country variable to the first placeholder(?)
            result = preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;

    }


    /**
     * Deleting all employees that have not worked since the given date.
     *
     * @return number of rows affected
     */
    public int deleteNotWorkingEmployees(String givenDate) {
        String instance = "DELETE FROM Employee  " +
                "WHERE employeeId NOT IN " +
                "(SELECT W.employeeId " +
                "FROM Works_In W " +
                "WHERE W.startDate >= ?)";
        int result = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(instance)){
            preparedStatement.setString(1, givenDate);//binds the value of the country variable to the first placeholder(?)
            result = preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     *  THE METHODS AFTER THIS LINE WILL NOT BE GRADED.
     *  YOU DON'T HAVE TO SOLVE THEM, LEAVE THEM AS IS IF YOU WANT.
     *  IF YOU HAVE ANY QUESTIONS, REACH ME VIA EMAIL.
     * *****************************************************
     * *****************************************************
     * *****************************************************
     * *****************************************************
     */

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Peers are considered tied and receive the same rank. After
     * that, there should be a gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each department, find the rank of the employees in terms of
     * salary. Everything is the same but after ties, there should be no
     * gap.
     *
     * @return QueryResult.EmployeeRank[]
     */
    public QueryResult.EmployeeRank[] calculateRank2() {
        return new QueryResult.EmployeeRank[0];
    }

    /**
     * For each factory, calculate the most profitable 4th product.
     *
     * @return QueryResult.FactoryProfit
     */
    public QueryResult.FactoryProfit calculateFourth() {
        return new QueryResult.FactoryProfit(0,0,0);
    }

    /**
     * Determine the salary variance between an employee and another
     * one who began working immediately after the first employee (by
     * startDate), for all employees.
     *
     * @return QueryResult.SalaryVariant[]
     */
    public QueryResult.SalaryVariant[] calculateVariance() {
        return new QueryResult.SalaryVariant[0];
    }

    /**
     * Create a method that is called once and whenever a Product starts
     * losing money, deletes it from Produce table
     *
     * @return void
     */
    public void deleteLosing() {

    }
}
