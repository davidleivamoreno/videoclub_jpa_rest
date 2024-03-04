package org.iesvdm.videoclub.service;

import org.iesvdm.videoclub.domain.Categoria;
import org.iesvdm.videoclub.exception.CategoriaNotFound;
import org.iesvdm.videoclub.repository.CategoriaCustomRepository;
import org.iesvdm.videoclub.repository.CategoriaCustomRepositoryJPQLImpl;
import org.iesvdm.videoclub.repository.CategoriaRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
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
