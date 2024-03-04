package org.iesvdm.videoclub.repository;

import org.iesvdm.videoclub.domain.Categoria;

import java.util.List;
import java.util.Optional;

public interface CategoriaCustomRepository {
    List<Categoria> queryCustomCategoria(Optional<String> buscarOptional, Optional<String> ordenarOptional);
}
