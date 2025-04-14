package com.romeo.birdssighting.unit.controllers;


import com.romeo.birdssighting.controllers.BirdController;
import com.romeo.birdssighting.dto.BirdDTO;
import com.romeo.birdssighting.services.BirdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = BirdController.class)
@AutoConfigureMockMvc
public class BirdControllerTest {

    private static final String API_BIRDS_SAVE = "/api/birds/save";
    private static final String API_BIRDS = "/api/birds";
    private static final String API_BIRDS_NAME = "/api/birds/name";
    private static final String NAME = "name";
    private static final String API_BIRDS_COLOR = "/api/birds/color";
    private static final String COLOR = "color";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BirdService birdService;

    /**
     * This method is used for testing when return all birds
     */
    @Test
    public void testGetAllBirds() throws Exception {
        var birdDTO = new BirdDTO();
        birdDTO.setName("Test");
        birdDTO.setColor("Red");
        birdDTO.setHeight(1);
        birdDTO.setWeight(2);

        List<BirdDTO> birds = new ArrayList<>();
        birds.add(birdDTO);

        given(birdService.getAllBirds()).willReturn(birds);

        mockMvc.perform(MockMvcRequestBuilders.get(API_BIRDS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(birdDTO)))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(1)));

        verify(birdService, VerificationModeFactory.times(1)).getAllBirds();
        reset(birdService);
    }

    /**
     * This method is used for testing when create a bird
     */
    @Test
    public void testCreateBird() throws Exception {
        var bird = new BirdDTO();
        bird.setName("Test");
        bird.setColor("Red");
        bird.setHeight(57);
        bird.setWeight(89);
        birdService.saveBird(bird);

        given(birdService.saveBird(bird)).willReturn(bird);

        mockMvc.perform(MockMvcRequestBuilders.post(API_BIRDS_SAVE)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(bird)))
                .andExpect(status().isOk());

        verify(birdService, VerificationModeFactory.times(2)).saveBird(any());
        reset(birdService);
    }

    /**
     * This method is used for testing when update a bird
     */
    @Test
    public void testUpdateBird() throws Exception {
        var bird = new BirdDTO();
        var id  = 1L;
        bird.setName("Test");
        bird.setColor("Blue");
        bird.setHeight(2);
        bird.setWeight(3);
        birdService.updateBird(id, bird);
        given(birdService.saveBird(bird)).willReturn(bird);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/birds/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(bird)))
                        .andExpect(status().isOk());
        verify(birdService, VerificationModeFactory.times(2)).updateBird(any(), any());
         reset(birdService);
    }

    /**
     * This method is used for testing when filter a bird by name
     */
    @Test
    public void testGetBirdByName() throws Exception {
        var birdDTO = new BirdDTO();
        var name = "Test";
        birdDTO.setName(name);

        given(birdService.findBirdByName(name)).willReturn(birdDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(API_BIRDS_NAME)
                        .param(NAME, name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(birdService, VerificationModeFactory.times(1)).findBirdByName(any());
        reset(birdService);
    }

    /**
     * This method is used for testing when filter a bird by color
     */
    @Test
    public void testGetBirdByColor() throws Exception {
        var birdDTO = new BirdDTO();
        var color = "Red";
        birdDTO.setColor(color);

        given(birdService.findBirdByColor(color)).willReturn(birdDTO);

        mockMvc.perform(MockMvcRequestBuilders.get(API_BIRDS_COLOR)
                        // Include color as a query parameter
                        .param(COLOR, color)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Asserting that the response contains one sighting
        verify(birdService, VerificationModeFactory.times(1)).findBirdByColor(any());
        reset(birdService);
    }

    /**
     * This method is used for testing when delete a bird
     */
    @Test
    public void testDeleteBird() throws Exception {
        Long id = 1L;

        doNothing().when(birdService).deleteBird(id);
        // Perform the DELETE request and verify the response status
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/birds/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
