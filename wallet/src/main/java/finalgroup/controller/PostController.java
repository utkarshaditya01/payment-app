package finalgroup.controller;

import finalgroup.DTO.PostDTO;
import finalgroup.client.PostClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    @Autowired
    PostClient postClient;

    @GetMapping("/posts")
    public List<PostDTO> getAllPosts(){
        return postClient.getPosts();
    }
}
