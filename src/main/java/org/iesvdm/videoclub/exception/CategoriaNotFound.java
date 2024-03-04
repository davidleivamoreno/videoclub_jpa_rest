package org.iesvdm.videoclub.exception;

public class CategoriaNotFound extends RuntimeException{
    public CategoriaNotFound(Long id) {
        super("Not found Categoria with id: " + id);
    }

}
