package restapi.webapp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Value;

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

