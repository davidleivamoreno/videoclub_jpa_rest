package org.iesvdm.videoclub.controller;

import lombok.extern.slf4j.Slf4j;
import org.iesvdm.videoclub.domain.Pelicula;
import org.iesvdm.videoclub.service.PeliculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/peliculas")
public class PeliculaController {
    private final PeliculaService peliculaService;

    public PeliculaController(PeliculaService peliculaService) {
        this.peliculaService = peliculaService;
    }

    @GetMapping(value = {"","/"},params = {"!pagina","!tamanio","!orden"})
    public List<Pelicula> all() {
        log.info("Accediendo a todas las películas");
        return this.peliculaService.all();
    }
    @GetMapping(value = {"","/"})
    public ResponseEntity<Map<String,Object>> all(@RequestParam(value="pagina",defaultValue = "0") int pagina,@RequestParam(value = "tamanio",defaultValue = "3") int tamanio){
        log.info("Accediendo a todas las peliculas con paginacion");
        Map<String,Object> responseAll =this.peliculaService.all(pagina,tamanio);
        return ResponseEntity.ok(responseAll);
    }
    @GetMapping(value = {"","/"},params = {"!pagina","!tamanio"})
    public List<Pelicula> all(@RequestParam(value = "orden")String[]orden){

        return this.peliculaService.all(orden);
    }
//    @GetMapping("/all")
//    public List<Pelicula> filtrar(
//            @RequestParam(value = "ordenarPor", required = false) String campoOrden,
//            @RequestParam(value = "orden", defaultValue = "asc") String orden) {
//        if (campoOrden != null) {
//            return this.peliculaService.allOrderedBy(campoOrden, orden);
//        } else {
//            return this.peliculaService.all();
//        }
//    }
        @GetMapping("/all")
    public List<Pelicula> filtrarVarios(
            @RequestParam(value = "ordenarPor", required = false) String[] camposOrden,
            @RequestParam(value = "orden", defaultValue = "asc") String[] ordenes) {
        if (camposOrden != null && camposOrden.length > 0) {
            return this.peliculaService.allOrderedByVarios(camposOrden, ordenes);
        } else {
            return this.peliculaService.all();
        }
    }

    @PostMapping({"","/"})
    public Pelicula newPelicula(@RequestBody Pelicula pelicula) {
        return this.peliculaService.save(pelicula);
    }

    @GetMapping("/{id}")
    public Pelicula one(@PathVariable("id") Long id) {
        return this.peliculaService.one(id);
    }

    @PutMapping("/{id}")
    public Pelicula replacePelicula(@PathVariable("id") Long id, @RequestBody Pelicula pelicula) {
        return this.peliculaService.replace(id, pelicula);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deletePelicula(@PathVariable("id") Long id) {
        this.peliculaService.delete(id);
    }


}
