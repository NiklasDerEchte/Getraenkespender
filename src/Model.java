import bin.DAO;

public class Model {
    public Model () {
        DAO.init(new DAO("localhost", "pi", "nikeneo", "3306", "getraenkespender"));
    }
}
