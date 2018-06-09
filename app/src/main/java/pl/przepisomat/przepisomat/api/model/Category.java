package pl.przepisomat.przepisomat.api.model;

import java.util.List;

/**
 * Created by Majkel on 2018-05-03.
 */

public class Category {
    public Long id;
    public String name;
    public Long root;
    public String symbol;
    public List<Category> childs;
}
