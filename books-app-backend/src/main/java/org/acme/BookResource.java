package org.acme;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Book Resource", description = "CRUD operations for books")
public class BookResource {

    @GET
    @Operation(summary = "Get all books", description = "Returns a list of all books")
    public List<Book> getAllBooks() {
        return Book.listAll();
    }

    @POST
    @Transactional
    @Operation(summary = "Add a new book", description = "Creates a new book and returns it")
    public Book addBook(Book book) {
        book.id = null;
        book.persist();
        return book;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Update an existing book", description = "Updates the details of an existing book")
    public Book updateBook(@PathParam("id") Long id, Book book) {
        Book entity = Book.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.titulo = book.titulo;
        entity.autor = book.autor;
        entity.anoPublicacao = book.anoPublicacao;
        return entity;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @Operation(summary = "Delete a book", description = "Deletes a book by its ID")
    public void deleteBook(@PathParam("id") Long id) {
        Book entity = Book.findById(id);
        if(entity == null) {
            throw new NotFoundException();
        }
        entity.delete();
    }
}