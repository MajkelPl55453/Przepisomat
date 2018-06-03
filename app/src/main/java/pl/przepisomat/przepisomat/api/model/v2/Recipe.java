package pl.przepisomat.przepisomat.api.model.v2;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.przepisomat.przepisomat.api.model.Product;
import pl.przepisomat.przepisomat.api.model.Steps;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable {
    private Long id;
    private String name;
    private String image;
    private String time;
    private String difficulty;
    private String portions;
    private Map<String,Product> products;
    private List<Steps> steps;
}
