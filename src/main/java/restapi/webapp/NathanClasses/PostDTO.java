package restapi.webapp.NathanClasses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;
import restapi.webapp.NathanClasses.Post;

@Value
@JsonPropertyOrder({"id", "title", "greeting"})
public class PostDTO {
        @JsonIgnore
        private final Post post;

        public Long getId() {
            return this.post.getId();
        }

        public String getTitle() {
            return this.post.getTitle();
        }

        public String getGreeting(){
            return "This is a DTO greeting";
        }
    }

