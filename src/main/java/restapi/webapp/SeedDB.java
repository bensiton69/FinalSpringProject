package restapi.webapp;

import org.apache.catalina.Manager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Configuration
// class declares one or one @Bean method
// Spring container to generates bean definitions and handles requests beans (at runtime)
class SeedDB {
    @Bean
    // CommandLineRunner beans once the application context is loaded.
    CommandLineRunner initDatabase(PostRepo postRepo, ProfileRepo profileRepo) {
        // runner gets a copy of the new DB and creates the following
        // products and saves them

        return args -> {
            Profile profile = profileRepo.save(new Profile("shosh"));
            Post post1 = postRepo.save(new Post("title1", "this is the first post", profile));
            Post post2 = postRepo.save(new Post("title2", "this is the second post",profile));

            profile.setPosts(Arrays.asList(post1,post2));
            profileRepo.save(profile);
        };
    }
}