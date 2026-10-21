package kh.edu.istad.platform.customer.persistence.mapper;


import kh.edu.istad.platform.customer.domain.entity.Customer;
import kh.edu.istad.platform.customer.domain.valueobject.Email;
import kh.edu.istad.platform.customer.domain.valueobject.PhoneNumber;
import kh.edu.istad.platform.customer.persistence.document.CustomerDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.Set;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CustomerPersistenceMapper {

    @Mapping(source = "id.id", target = "id")
    @Mapping(source = "email.value", target = "email")
    @Mapping(source = "phoneNumber.value", target = "phoneNumber")
    @Mapping(source = "segmentIds", target = "segmentIds")
    CustomerDocument toDocument(Customer customer);


    @Mapping(source = "id", target = "id.id")
    @Mapping(source = "email", target = "email.value")
    @Mapping(source = "phoneNumber", target = "phoneNumber.value")
    @Mapping(source = "segmentIds", target = "segmentIds")
    Customer toDomain(CustomerDocument document);

    default String map(Email email) {
        return email == null ? null : email.value();
    }


    default Email map(String email) {
        return email == null ? null : new Email(email);
    }


    default String mapPhoneNumber(PhoneNumber phoneNumber) {
        return phoneNumber == null ? null : phoneNumber.value();
    }


    default PhoneNumber mapPhoneNumber(String phoneNumber) {
        return phoneNumber == null ? null : new PhoneNumber(phoneNumber);
    }

    default Set<String> mapSegmentIds(Set<UUID> ids) {
        return ids == null ? null : ids.stream().map(UUID::toString).collect(java.util.stream.Collectors.toSet());
    }

    default Set<UUID> mapSegmentIdsToUuid(Set<String> ids) {
        return ids == null ? null : ids.stream().map(UUID::fromString).collect(java.util.stream.Collectors.toSet());
    }
}
