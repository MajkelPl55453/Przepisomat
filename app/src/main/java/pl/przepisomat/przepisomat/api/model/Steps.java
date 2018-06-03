package pl.przepisomat.przepisomat.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Steps {
    private Long id;
    private String opis;

    public String toString(){
        return opis;
    }
}
