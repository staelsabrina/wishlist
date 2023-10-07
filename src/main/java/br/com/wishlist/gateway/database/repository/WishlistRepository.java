package br.com.wishlist.gateway.database.repository;

import br.com.wishlist.gateway.database.model.WishlistDatabase;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WishlistRepository extends MongoRepository<WishlistDatabase, String> {
    Optional<WishlistDatabase> findByCustomerId(String customerId);
}
