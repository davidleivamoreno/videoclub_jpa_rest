package org.iesvdm.videoclub.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.iesvdm.videoclub.domain.Categoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class CategoriaCustomRepositoryJPQLImpl implements CategoriaCustomRepository {
    @Autowired
    private EntityManager em;
    @Override
    public List<Categoria> queryCustomCategoria(Optional<String> buscarOptional,Optional<String> ordenarOptional){
        StringBuilder queryBuilder =new StringBuilder("SELECT C FROM Categoria C");
        if(buscarOptional.isPresent()){
        queryBuilder.append(" ").append("WHERE C.nombre like :nombre");
        }
        if(ordenarOptional.isPresent()){
            if(ordenarOptional.isPresent() && "asc".equalsIgnoreCase(ordenarOptional.get())){
                queryBuilder.append(" ").append(" ORDER BY  C.nombre ASC");

            }else if(ordenarOptional.isPresent() && "desc".equalsIgnoreCase(ordenarOptional.get())){
                queryBuilder.append(" ").append(" ORDER BY  C.nombre DESC");

            }
        }
        Query query=em.createQuery(queryBuilder.toString());
        if(buscarOptional.isPresent()){
            query.setParameter("nombre","%"+buscarOptional.get()+"%");
        }

        return query.getResultList();
    }
}
