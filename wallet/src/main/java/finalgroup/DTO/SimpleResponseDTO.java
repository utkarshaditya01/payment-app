package finalgroup.DTO;

import java.io.Serializable;

public class SimpleResponseDTO implements Serializable {
    String response;
    Integer id;

    public SimpleResponseDTO() {
    }

    public SimpleResponseDTO(String response, Integer id) {
        this.response = response;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
