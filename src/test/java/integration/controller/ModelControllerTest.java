package integration.controller;

import com.dashboard.domain.Brand;
import com.dashboard.domain.Model;
import com.dashboard.domain.Type;
import com.dashboard.repository.ModelRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import integration.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


public class ModelControllerTest extends IntegrationTest {

    @Autowired
    private ModelRepository modelRepository;

    @Test
    public void modelsControllerTest() throws Exception {
        Model model = new Model(
                1L,
                Brand.TOYOTA,
                Type.PASSENGER,
                50.0,
                1.5,
                5
        );
        modelRepository.save(model);

        String response = mockMvc
                .perform(
                        get("/api/models")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn()
                .getResponse().getContentAsString();

        List<Model> actual = objectMapper.readValue(response, new TypeReference<>() {
        });

        assertThat(actual).hasSize(1);
        assertThat(actual.get(0)).isEqualTo(model);
    }
}