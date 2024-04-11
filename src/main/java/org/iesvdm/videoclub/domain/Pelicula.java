package org.iesvdm.videoclub.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="pelicula")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_pelicula")
    private long idPelicula;
    private String titulo;
    private String descripcion;

    @Column(name = "duracion_alquiler")
    private int duracionAlquiler;

    @Column(name = "rental_rate")
    private BigDecimal rentalRate;
    private int duracion;

    @Column(name = "replacement_cost")
    private BigDecimal replacementCost;
    private String clasificacion;

    @Column(name = "caracteristicas_especiales")
    private String caracteristicasEspeciales;

    @ManyToMany
    @JoinTable(
            name = "pelicula_categoria",
            joinColumns = @JoinColumn(name = "iwd_pelicula", referencedColumnName = "id_pelicula"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria"))
    Set<Categoria> categorias = new HashSet<>();
    public Pelicula(long idPelicula,String titulo,String descripcion){
            this.titulo=titulo;
            this.idPelicula=idPelicula;
            this.descripcion=descripcion;
    }


}
