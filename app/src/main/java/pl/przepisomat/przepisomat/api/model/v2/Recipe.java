package pl.przepisomat.przepisomat.api.model.v2;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable {
    public Long id;
    public String name;
    public String image;
    public String time;
    public String difficulty;
    public String portions;
//    private Category category;
//    private List<Products> products;
//    private List<Steps> steps;
}
