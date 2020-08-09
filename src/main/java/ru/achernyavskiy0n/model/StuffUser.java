package ru.achernyavskiy0n.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * 09.08.2020
 *
 * @author a.chernyavskiy0n
 */
@Data
@Document(collation = "stuffUsersForSpringSecurityKeyValuePairs")
public class StuffUser {

    @Id
    private @Getter Integer id;
    private @Getter String username;
    private @Getter String password;

    @Getter List<Role> roles;

}
