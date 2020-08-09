package ru.achernyavskiy0n.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import ru.achernyavskiy0n.model.StuffUser;

/**
 * 09.08.2020
 *
 * @author a.chernyavskiy0n
 */
public interface StuffUserRepository extends MongoRepository<StuffUser, String> {
    /**
     * this method extract all details
     * of stuff user according to his name
     *
     * @param username - name of StuffUser
     * @return instance of StuffUser.
     */
    @Query("{ 'name' : {$regex: ?0, $options: 'i' }}")
    StuffUser findByStuffUserName(final String username);

}
