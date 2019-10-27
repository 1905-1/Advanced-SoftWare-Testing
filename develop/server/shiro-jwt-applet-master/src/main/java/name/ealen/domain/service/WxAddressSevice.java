package name.ealen.domain.service;

import name.ealen.domain.entity.Address;
import name.ealen.domain.repository.AddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
public class WxAddressSevice {

    @Resource
    private AddressRepository addressRepository;

    public Address add(Address address){
        return  addressRepository.save(address);
    }

    public void addAll(List<Address> addresses){
        addressRepository.saveAll(addresses);
    }

    public Address getById(String id){
        return addressRepository.findByAddressId(id);
    }

    public List<Address> getAll(){
        return addressRepository.findAll();
    }
}
