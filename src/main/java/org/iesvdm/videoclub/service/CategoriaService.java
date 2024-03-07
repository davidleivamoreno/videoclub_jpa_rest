package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.exception.CategoriaNotFound;
import org.iesvdm.videoclub.repository.CategoriaCustomRepository;
import org.iesvdm.videoclub.repository.CategoriaCustomRepositoryJPQLImpl;
import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaCustomRepository categoriaCustomRepository;
    public CategoriaService(CategoriaRepository categoriaRepository, CategoriaCustomRepository categoriaCustomRepository) {
        this.categoriaRepository = categoriaRepository;
        this.categoriaCustomRepository = categoriaCustomRepository;
    }

    public List<Categoria> all() {
        return this.categoriaRepository.findAll();
    }


    public Categoria save(Categoria categoria) {
        return this.categoriaRepository.save(categoria);
    }

    public Categoria one(Long id) {
        return this.categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFound(id));
    }


    public Categoria replace(Long id, Categoria categoria) {

        return this.categoriaRepository.findById(id).map( p -> (id.equals(categoria.getId())  ?
                        this.categoriaRepository.save(categoria) : null))
                .orElseThrow(() -> new CategoriaNotFound(id));

    }
    public Map<String,Object> all(int pagina, int tamanio){
        Pageable paginado =PageRequest.of(pagina,tamanio,Sort.by("id").ascending());
        Page<Categoria> pageAll =this.categoriaRepository.findAll(paginado);
        Map<String,Object> response =new HashMap<>();
        response.put("categorias",pageAll.getContent());
        response.put("currentPage",pageAll.getNumber());
        response.put("totalItems",pageAll.getTotalElements());
        response.put("totalPages",pageAll.getTotalPages());
        return response;
    }

    public void delete(Long id) {
        this.categoriaRepository.findById(id).map(p -> {this.categoriaRepository.delete(p);
                    return p;})
                .orElseThrow(() -> new CategoriaNotFound(id));
    }

    public List<Categoria> allByQueryFilterStream(Optional<String> buscarOptional, Optional<String> ordenarOptional) {
       var list =  categoriaCustomRepository.queryCustomCategoria(buscarOptional,ordenarOptional);

       list.forEach(categoria -> categoria.setConteo(categoria.getPeliculas().size()));

       return list;
    }

}
