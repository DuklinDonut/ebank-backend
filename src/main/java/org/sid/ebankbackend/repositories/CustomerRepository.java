package org.sid.ebankbackend.repositories;

import org.sid.ebankbackend.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<org.sid.ebankbackend.entities.Customer,Long> {
}
