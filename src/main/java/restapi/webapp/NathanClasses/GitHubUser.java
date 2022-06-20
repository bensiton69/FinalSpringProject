package restapi.webapp.NathanClasses;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This is Plain Old Java Object (POJO) representing a GitHub user with specific fields
 * fields: id, name, blog, email,public_repos
 * In this case, our POJO is a Data class
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
// take only the fields declared in this class and ignore all other POJO fields
public class GitHubUser {
    private Long id;
    private String name;
    private String blog;
    private String email;
    private Integer public_repos;

    @Override
    public String toString(){
        return "GitHub Information {id =" + getId() + ", name=" + getName()
                + ",blog=" + getBlog() + ", number of repos=" + getPublic_repos() +"}";
    }
}




