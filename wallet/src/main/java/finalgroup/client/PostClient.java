package finalgroup.client;

import finalgroup.DTO.PostDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "https://jsonplaceholder.typicode.com/", name = "POST-CLIENT")
public interface PostClient {

    @GetMapping("/posts")
    public List<PostDTO> getPosts();
}
