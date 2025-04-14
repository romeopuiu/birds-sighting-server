package com.romeo.birdssighting.unit.controllers;

import com.romeo.birdssighting.controllers.SightingController;
import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.services.SightingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(SightingController.class)
public class SightingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SightingService sightingService;


    /**
     * This method is used for testing when returns all sightings by bird id
     */
    @Test
    public void testGetAllSightingsByBirdId() throws Exception {
        var sightingDTO = new SightingDTO();
        sightingDTO.setLocation("Bucharest");
        var birdId = 1L;

        List<SightingDTO> sightings = new ArrayList<>();
        sightings.add(sightingDTO);

        given(sightingService.findAllSightingsByBirdId(birdId)).willReturn(sightings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/birds/{birdId}/sightings", birdId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        // Asserting that the response contains one sighting
        verify(sightingService, VerificationModeFactory.times(1)).findAllSightingsByBirdId(any());
        reset(sightingService);
    }

    /**
     * This method is used for testing when save a sighting
     */
    @Test
    public void testSaveSighting() throws Exception {
        var birdId = 1L;
        var sightingDTO = new SightingDTO();
        sightingDTO.setLocation("London");

        given(sightingService.createSighting(birdId, sightingDTO)).willReturn(sightingDTO);

        mockMvc.perform(post("/api/birds/{birdId}/sightings/save", birdId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(sightingDTO)))
                .andExpect(status().isOk());
        verify(sightingService, VerificationModeFactory.times(1)).createSighting(birdId, sightingDTO);
        reset(sightingService);
    }

    /**
     * This method is used for testing when update a sighting
     */
    @Test
    public void testUpdateSighting() throws Exception {
        var id = 1L;

        var sightingDTO = new SightingDTO();
        sightingDTO.setLocation("London");

        given(sightingService.updateSighting(id, sightingDTO)).willReturn(sightingDTO);
        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/sighting/{id}", id) // Use the correct path variable name in the URL
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(sightingDTO)))
                .andExpect(status().isOk());
        // Verify that updateSighting method is called with the correct arguments
        verify(sightingService, VerificationModeFactory.times(1)).updateSighting(id, sightingDTO);
        reset(sightingService);
    }


    /**
     * This method is used for testing when returns all sightings
     */
    @Test
    public void testGetAllSightings() throws Exception {
        var sightingDTO = new SightingDTO();
        sightingDTO.setLocation("Bucharest");

        List<SightingDTO> sightings = new ArrayList<>();
        sightings.add(sightingDTO);

        given(sightingService.findAllSightings()).willReturn(sightings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sightings")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        verify(sightingService, times(1)).findAllSightings();
        reset(sightingService);
    }

    /**
     * This method is used for testing when return a sighting by id
     */
    @Test
    public void testGetSightingId() throws Exception {
        var sightingDTO = new SightingDTO();
        sightingDTO.setLocation("Paris");
        var id = 1L;

        given(sightingService.findSighting(id)).willReturn(sightingDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sightings/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        // Asserting that the response contains one sighting
        verify(sightingService, VerificationModeFactory.times(1)).findSighting(any());
        reset(sightingService);
    }

    /**
     * This method is used for testing when return a sighting by location
     */
    @Test
    public void testGetSightingByLocation() throws Exception {
        var location = "Berlin";
        var sightingDTO = new SightingDTO();
        sightingDTO.setLocation(location);

        List<SightingDTO> sightings = new ArrayList<>();
        sightings.add(sightingDTO);

        given(sightingService.findByLocation(location)).willReturn(sightings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sightings/location")
                        // Include location as a query parameter
                        .param("location", location)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Asserting that the response contains one sighting
        verify(sightingService, VerificationModeFactory.times(1)).findByLocation(location);
        reset(sightingService);
    }

    /**
     * This method is used for testing when return a sighting by date time
     */
    @Test
    public void testGetSightingByDateTime() throws Exception {
        var dateTime = LocalDateTime.parse("2021-12-03T15:02");
        var sightingDTO = new SightingDTO();
        sightingDTO.setDateTime(dateTime);

        List<SightingDTO> sightings = new ArrayList<>();
        sightings.add(sightingDTO);

        given(sightingService.findByDateTime(dateTime)).willReturn(sightings);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/sightings/date-time")
                        // Include dateTime as a query parameter
                        .param("dateTime", String.valueOf(dateTime))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        // Asserting that the response contains one sighting
        verify(sightingService, VerificationModeFactory.times(1)).findByDateTime(any());
        reset(sightingService);
    }

    /**
     * This method is used for testing when delete a sighting by id
     */
    @Test
    public void testDeleteSighting() throws Exception {
        Long id = 1L;
        doNothing().when(sightingService).deleteSighting(id);
        // Perform the DELETE request and verify the response status
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/sightings/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
