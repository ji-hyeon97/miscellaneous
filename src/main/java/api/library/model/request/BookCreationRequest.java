package api.library.model.request;

import lombok.Data;

@Data
public class BookCreationRequest {
    private String name;
    private String isbn;
    private Long authorId;
}
