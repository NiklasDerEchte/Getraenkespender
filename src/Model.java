import bin.DB;

public class Model {
    public Model () {
        DB.init(new DB("localhost", "pi", "nikeneo", "3306", "getraenkespender"));
    }
}
