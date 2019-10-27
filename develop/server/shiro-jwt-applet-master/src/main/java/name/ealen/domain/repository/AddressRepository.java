package name.ealen.domain.repository;

import name.ealen.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    public Address findByAddressId(String AddressId);
}
