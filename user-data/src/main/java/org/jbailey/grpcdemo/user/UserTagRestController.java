package org.jbailey.grpcdemo.user;

import org.jbailey.grpcdemo.user.client.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class UserTagRestController {

    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> getTags() {
        List<Tag> tags = new ArrayList<>() {{
            add(getFakeTag());
            add(getFakeTag());
            add(getFakeTag());
            add(getFakeTag());
        }};

        return ResponseEntity.ok(tags);
    }

    private Tag getFakeTag() {
        return new Tag(UUID.randomUUID(), "RESTY TAG!");
    }
}
