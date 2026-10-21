package kh.edu.istad.platform.customer.persistence.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "customer_segments")
public class CustomerSegmentDocument {
    @Id private String id;
    private String name;
    private String description;
}
