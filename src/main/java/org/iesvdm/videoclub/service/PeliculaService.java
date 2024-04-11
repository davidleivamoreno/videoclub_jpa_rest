package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.exception.PeliculaNotFoundException;
import org.iesvdm.videoclub.repository.PeliculaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PeliculaService {

    private final PeliculaRepository peliculaRepository;

    public PeliculaService(PeliculaRepository peliculaRepository) {
        this.peliculaRepository = peliculaRepository;
    }

    public List<Pelicula> all() {
        return this.peliculaRepository.findAll();
    }
    public Map<String,Object> all(int pagina, int tamanio){
        Pageable paginado =PageRequest.of(pagina,tamanio,Sort.by("idPelicula").ascending());
        Page<Pelicula> pageAll =this.peliculaRepository.findAll(paginado);
        Map<String,Object> response =new HashMap<>();
        response.put("peliculas",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());
        return response;
    }


    public Pelicula save(Pelicula pelicula) {
        return this.peliculaRepository.save(pelicula);
    }

    public Pelicula one(Long id) {
        return this.peliculaRepository.findById(id)
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }

    public Pelicula replace(Long id, Pelicula pelicula) {

        return this.peliculaRepository.findById(id).map( p -> (id.equals(pelicula.getIdPelicula())  ?
                                                            this.peliculaRepository.save(pelicula) : null))
                .orElseThrow(() -> new PeliculaNotFoundException(id));

    }
    public List<Pelicula> all(String[]orden){
        List<Pelicula> listOrd=new ArrayList<>();
        String parametro =orden[0];
        String valorden=orden[1];
        String campo = orden[0].split(",")[0];
        String sentido = orden[0].split(",")[1];
        Sort.Direction direccion = sentido.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direccion, campo);

        return this.peliculaRepository.findAll(sort);

    }

    public void delete(Long id) {
        this.peliculaRepository.findById(id).map(p -> {this.peliculaRepository.delete(p);
                                                        return p;})
                .orElseThrow(() -> new PeliculaNotFoundException(id));
    }



    public List<Pelicula> allOrderedBy(String campoOrden, String orden) {
        List<Pelicula> categorias = all();


        Comparator<Pelicula> comparator = (p1, p2) -> {
            switch (campoOrden) {
                case "titulo":
                    return p1.getTitulo().compareTo(p2.getTitulo());
                case "idPelicula":
                    return Long.compare(p1.getIdPelicula(),p2.getIdPelicula());
                case "descripcion":
                    return p1.getTitulo().compareTo(p2.getTitulo());
                default:
                    return 0;
            }
        };

        // Si el tipo de orden es descendente, invertimos el Comparator
        if (orden.equals("desc")) {
            comparator = comparator.reversed();
        }

        // Ordenamos la lista de categorías utilizando el Comparator
        Collections.sort(categorias, comparator);

        return categorias;
    }

    public List<Pelicula> allOrderedByVarios(String[] camposOrden, String[] ordenes) {
        List<Pelicula> peliculas = all();

        Comparator<Pelicula> comparator = (p1, p2) -> {
            for (int i = 0; i < camposOrden.length; i++) {
                String campoOrden = camposOrden[i];
                String orden = ordenes[i];
                int comparacion;

                switch (campoOrden) {
                    case "titulo":
                        comparacion = p1.getTitulo().compareTo(p2.getTitulo());
                        break;
                    case "idPelicula":
                        comparacion = Long.compare(p1.getIdPelicula(), p2.getIdPelicula());
                        break;
                    case "descripcion":
                        comparacion = p1.getDescripcion().compareTo(p2.getDescripcion());
                        break;
                    default:
                        comparacion = 0;
                        break;
                }

                if (comparacion != 0) {
                    return orden.equals("asc") ? comparacion : -comparacion;
                }
            }
            return 0;
        };


        Collections.sort(peliculas, comparator);

        return peliculas;
    }
}
