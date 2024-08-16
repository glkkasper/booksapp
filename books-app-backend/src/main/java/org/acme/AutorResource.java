package org.acme;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.hibernate.exception.ConstraintViolationException;

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
        if (entity == null) {
            throw new NotFoundException();
        }
        entity.nome = autor.nome;
        entity.dataNascimento = autor.dataNascimento;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteAutor(@PathParam("id") Long id) {
        Autor autor = Autor.findById(id);
        if (autor == null) {
            throw new WebApplicationException("Autor com id de " + id + " não existe.", 404);
        }

        // Verifica se há livros associados a este autor
        long livrosAssociados = Book.count("autor", autor);
        if (livrosAssociados > 0) {
            // Cria um objeto JSON para a mensagem de erro
            String jsonErrorMessage = "{\"error\": \"Não é possível excluir o autor, pois existem " + livrosAssociados
                    + " livro(s) associado(s) a ele.\"}";
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(jsonErrorMessage)
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }

        autor.delete();
        return Response.noContent().build();
    }

}