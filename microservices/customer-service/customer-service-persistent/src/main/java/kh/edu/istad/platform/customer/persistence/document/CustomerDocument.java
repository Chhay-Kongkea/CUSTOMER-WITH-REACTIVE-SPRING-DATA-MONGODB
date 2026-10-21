package kh.edu.istad.platform.customer.persistence.document;


import kh.edu.istad.common.domain.valueobject.CustomerId;
import kh.edu.istad.platform.customer.domain.valueobject.CustomerStatus;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "customers")
public class CustomerDocument  implements Persistable<String> {

    @Id
    private String id;
    private String username;
    private String familyName;
    private String givenName;
    private String email;
    private String phoneNumber;
    private CustomerStatus customerStatus;
    private Set<String> segmentIds;


    @CreatedBy
    private String createdBy;


    @CreatedDate
    private Instant createdAt;


    @LastModifiedBy
    private String modifiedBy;


    @LastModifiedDate
    private String modifiedAt;

    @Override
    public boolean isNew() {
        return createdAt == null;
    }



}
