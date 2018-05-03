package pl.przepisomat.przepisomat.api;

import java.util.List;

/**
 * Created by Majkel on 2018-05-03.
 */

public class Category {
    public int id;
    public String name;
    public int root;
    public String symbol;
    public List<Category> childs;
}
