package org.acme;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/autores")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Autor Resource", description = "CRUD operations for autores")
public class AutorResource {

    @GET 
    @Operation(summary = "Get all autores", description = "Returns a list of all autores")
    public List<Autor> getAllAutores() {
        return Autor.listAll();
    }
    
    @POST
    @Transactional
    @Operation(summary = "Add a new autores", description = "Creates a new autores and returns it")
    public Autor addAutores(Autor autor) {
        autor.id = null;
        autor.persist();
        return autor;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing autor", description = "Updates the details of an existing autor")
    public Autor updateAutor(@PathParam("id") Long id, Autor autor) {
        Autor entity = Autor.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.nome = autor.nome;
        entity.dataNascimento = autor.dataNascimento;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a autor", description = "Deletes a autor by its ID")
    public void deleteAutor(@PathParam("id") Long id) {
        Autor entity = Autor.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }

}