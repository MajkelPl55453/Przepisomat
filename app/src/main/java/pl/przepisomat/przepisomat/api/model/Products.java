package pl.przepisomat.przepisomat.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Products {
    private Long id;
    private String nazwa;
    private String symbol;
    private String wartosc;
}
