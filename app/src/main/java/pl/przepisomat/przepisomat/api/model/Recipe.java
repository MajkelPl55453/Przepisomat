package pl.przepisomat.przepisomat.api.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe implements Serializable{
    private Long id;
    private String nazwa;
    private String czas_przygotowania;
    private String trudnosc;
    private String ilosc_porcji;
    private String zdjecie;

    public String toString(){
        return nazwa;
    }
}

