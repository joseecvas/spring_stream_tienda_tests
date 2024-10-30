package org.iesvdm.tienda.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Producto {

    @Id
    private int codigo;
    
    private String nombre;

    private double precio;

    @ManyToOne
    @JoinColumn(name = "codigo_fabricante", referencedColumnName = "codigo")
    private Fabricante fabricante;
    public static final double FACTOR_EUR_DOL=1.08;

    public Producto() {

    }

    public static Producto eurToDol(Producto p){
        p.setPrecio(FACTOR_EUR_DOL);
        return p;
    }
}    
