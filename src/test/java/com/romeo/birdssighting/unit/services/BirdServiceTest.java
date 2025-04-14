package com.romeo.birdssighting.unit.services;

import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.domain.Sighting;
import com.romeo.birdssighting.dto.BirdDTO;
import com.romeo.birdssighting.dto.SightingDTO;
import com.romeo.birdssighting.exception.ResourceNotFoundException;
import com.romeo.birdssighting.mapper.BirdMapper;
import com.romeo.birdssighting.mapper.SightingMapper;
import com.romeo.birdssighting.repositories.BirdRepository;
import com.romeo.birdssighting.repositories.SightingRepository;
import com.romeo.birdssighting.services.BirdService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class BirdServiceTest {

    @Mock
    private BirdRepository birdRepository;
    @Mock
    private SightingRepository sightingRepository;

    @Mock
    private  SightingMapper sightingMapper;

    @Mock
    private BirdMapper birdMapper;

    @InjectMocks
    private BirdService birdService;

    /**
     * This method is used for testing get all birds
     */
    @Test
    public void testGetAllBirds() {
        var bird = new Bird();
        bird.setId(1L);
        bird.setName("Test 1");
        bird.setColor("Red");
        bird.setHeight(1);
        bird.setWeight(2);

        List<Bird> birds = Collections.singletonList(bird);
        List<Sighting> sightings = new ArrayList<>();
        List<SightingDTO> sightingDTOS = new ArrayList<>();
        SightingDTO sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        sightingDTOS.add(sightingDTO);
        var sighting = new Sighting();
        sighting.setLocation("London");
        sighting.setId(1L);
        sightings.add(sighting);

        var birdDto = new BirdDTO();
        birdDto.setId(1L);
        birdDto.setName("Test");
        birdDto.setSightings(sightingDTOS);

        when(birdRepository.findAll()).thenReturn(birds);
        when(sightingRepository.findByBird(bird)).thenReturn(sightings);
        when(birdMapper.convertToDTO(bird)).thenReturn(birdDto);
        when(sightingMapper.convertToDTO(sightings)).thenReturn(sightingDTOS);

        // Call the service method
        List<BirdDTO> actualBirdDTOs = birdService.getAllBirds();

        // Verify that the repository method and the mapper methods were called as expected
        verify(birdRepository, times(1)).findAll();
        // Verify that the findByBird method was called with the correct bird object
        verify(sightingRepository, times(1)).findByBird(bird);
        verify(birdMapper, times(1)).convertToDTO(bird);
        verify(sightingMapper, times(1)).convertToDTO(sightings);

        // Verify that the method returns the expected list of BirdDTO objects
        assertEquals(1, actualBirdDTOs.size());
        assertEquals(birdDto, actualBirdDTOs.get(0));
    }

    /**
     * This method is used to testing when a bird is saved
     */
    @Test
    public void testSaveBird() {
        // Mock data
        var bird = new Bird();
        bird.setId(1L);
        bird.setName("Test");
        bird.setColor("Red");
        bird.setWeight(15);
        bird.setHeight(12);

        List<Sighting> sightingList = new ArrayList<>();
        List<SightingDTO> sightingDTOList = new ArrayList<>();

        var sightingDTO = new SightingDTO();
        sightingDTO.setId(1L);
        sightingDTO.setLocation("London");
        sightingDTO.setDateTime(LocalDateTime.now());
        // Set the bird property to a valid BirdDTO object
        var birdInSighting = new BirdDTO();
        birdInSighting.setId(1L);
        birdInSighting.setColor("Red");
        birdInSighting.setHeight(12);
        birdInSighting.setWeight(15);
        birdInSighting.setName("Test");
        sightingDTO.setBird(birdInSighting);

        var birdDTO = new BirdDTO();
        birdDTO.setId(1L);
        birdDTO.setName("Test");
        birdDTO.setColor("Red");
        birdDTO.setWeight(15);
        birdDTO.setHeight(12);
        sightingDTO.setBird(birdDTO);

        sightingDTOList.add(sightingDTO);
        birdInSighting.setSightings(sightingDTOList);

        birdDTO.setSightings(sightingDTOList);
        Sighting sighting = new Sighting();
        sighting.setId(1L);
        sighting.setLocation("London");
        sighting.setDateTime(LocalDateTime.now());
        sighting.setBird(bird);
        sightingList.add(sighting);
        bird.setSightings(sightingList);

        when(birdMapper.convertCollectionToEntity(birdDTO)).thenReturn(bird);
        when(sightingMapper.convertCollectionToEntity(sightingDTOList)).thenReturn(sightingList);
        when(sightingRepository.saveAll(sightingList)).thenReturn(Collections.singletonList(new Sighting()));
        when(birdRepository.save(bird)).thenReturn(bird);
        when(birdMapper.convertToDTO(bird)).thenReturn(birdDTO);

        // Call the method
        BirdDTO result = birdService.saveBird(birdDTO);

        // Verify method calls
        verify(birdMapper).convertCollectionToEntity(birdDTO);
        verify(sightingMapper).convertCollectionToEntity(sightingDTOList);
        verify(sightingRepository).saveAll(anyList());
        verify(birdRepository).save(bird);
        verify(birdMapper).convertToDTO(bird);

        // Assertions
        assertNotNull(result);
        assertEquals(birdDTO.getName(), result.getName());
    }

    /**
     * This method is used to testing when update a bird when existing
     */
    @Test
    public void testUpdateBird_WhenExisting() {
        Long id = 1L;

        BirdDTO birdDTO = new BirdDTO();
        birdDTO.setName("Test 2");
        birdDTO.setColor("Blue");
        birdDTO.setHeight(10);
        birdDTO.setWeight(20);
        birdDTO.setId(id);

        Sighting sighting = new Sighting();
        sighting.setId(id);
        sighting.setLocation("London");
        sighting.setDateTime(LocalDateTime.now());
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(sighting);

        Bird existingBird = new Bird();
        existingBird.setId(id);
        existingBird.setName("Test 1");
        existingBird.setColor("Green");
        existingBird.setHeight(5);
        existingBird.setWeight(15);
        existingBird.setSightings(sightings);

        // Mocks
        when(birdRepository.findById(id)).thenReturn(Optional.of(existingBird));
        when(birdRepository.save(any(Bird.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(birdMapper.convertToDTO(any(Bird.class))).thenAnswer(invocation -> {
            Bird bird = invocation.getArgument(0);
            BirdDTO dto = new BirdDTO();
            dto.setName(bird.getName());
            dto.setColor(bird.getColor());
            dto.setHeight(bird.getHeight());
            dto.setWeight(bird.getWeight());
            return dto;
        });

        BirdDTO updatedBirdDTO = birdService.updateBird(id, birdDTO);

        verify(birdRepository).findById(id);
        verify(birdRepository).save(any(Bird.class));
        assertEquals(birdDTO.getName(), updatedBirdDTO.getName());
        assertEquals(birdDTO.getColor(), updatedBirdDTO.getColor());
        assertEquals(birdDTO.getHeight(), updatedBirdDTO.getHeight());
        assertEquals(birdDTO.getWeight(), updatedBirdDTO.getWeight());
    }

    /**
     * This method is used to testing update a bird when do not exist
     */
    @Test
    public void testUpdateBird_WhenNonExisting_ShouldThrowResourceNotFoundException() {
        var id = 1L;
        var birdDTO = new BirdDTO();
        birdDTO.setName("Test");
        birdDTO.setColor("Green");
        birdDTO.setHeight(10);
        birdDTO.setWeight(20);
        // Mock behavior
        when(birdRepository.findById(id)).thenReturn(java.util.Optional.empty());
        // Invoke method and verify exception
        assertThrows(ResourceNotFoundException.class, () -> birdService.updateBird(id, birdDTO));
        // Verify behavior
        verify(birdRepository, times(1)).findById(id);
        verify(birdRepository, never()).save(any());
    }

    /**
     * This method is used to testing a bird filtered by color when exist
     */
    @Test
    public void testFindBirdByColor_WhenBirdExists() {
        var color = "Red";
        var bird = new Bird();
        bird.setId(1L);
        bird.setColor(color);
        // Mock behavior to simulate the bird existing in the repository
        when(birdRepository.findByColor(color)).thenReturn(bird);
        // Mock behavior of birdMapper
        var expectedBirdDTO = new BirdDTO();
        expectedBirdDTO.setId(bird.getId());
        expectedBirdDTO.setColor(bird.getColor());
        when(birdMapper.convertToDTO(bird)).thenReturn(expectedBirdDTO);
        // Call the method under test
        var result = birdService.findBirdByColor(color);
        // Verify that findByColor(color) is called once with the specified color
        verify(birdRepository, times(1)).findByColor(color);
        // Verify that the result is not null
        assertNotNull(result);
        // Verify that the returned BirdDTO matches the expected one
        assertEquals(expectedBirdDTO.getId(), result.getId());
        assertEquals(expectedBirdDTO.getColor(), result.getColor());
    }

    /**
     * Test that if return bird by color when bird does not exist
     */
    @Test
    public void testFindBirdByColor_WhenBirdDoesNotExist() {
        var color = "Blue";
        // Mock behavior to simulate the bird not existing in the repository
        when(birdRepository.findByColor(color)).thenReturn(null);
        // Call the method under test and verify that it throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> birdService.findBirdByColor(color));
        // Verify that findByColor(color) is called once with the specified color
        verify(birdRepository, times(1)).findByColor(color);
    }

    /**
     * Test that if return bird by name when bird exist
     */
    @Test
    public void testFindBirdByName_WhenBirdExists() {
        var name = "Test";
        var bird = new Bird();
        bird.setId(1L);
        bird.setName(name);
        // Mock behavior to simulate the bird existing in the repository
        when(birdRepository.findByName(name)).thenReturn(bird);
        // Mock behavior of birdMapper
        BirdDTO expectedBirdDTO = new BirdDTO();
        expectedBirdDTO.setId(bird.getId());
        expectedBirdDTO.setName(bird.getName());
        when(birdMapper.convertToDTO(bird)).thenReturn(expectedBirdDTO);

        var result = birdService.findBirdByName(name);
        // Verify that findByName(name) is called once with the specified name
        verify(birdRepository, times(1)).findByName(name);
        // Verify that the result is not null
        assertNotNull(result);
        // Verify that the returned BirdDTO matches the expected one
        assertEquals(expectedBirdDTO.getId(), result.getId());
        assertEquals(expectedBirdDTO.getName(), result.getName());
    }

    /**
     * Test that if return bird by name when bird does not exist
     */
    @Test
    public void testFindBirdByName_WhenBirdDoesNotExist() {
        var name = "Test";
        // Simulate the bird not existing in the repository
        when(birdRepository.findByName(name)).thenReturn(null);
        // Verify that it throws ResourceNotFoundException
        assertThrows(ResourceNotFoundException.class, () -> birdService.findBirdByName(name));
        // Verify that findByName(name) is called once with the specified name
        verify(birdRepository, times(1)).findByName(name);
    }

    /**
     * Test that to delete a bird if exist
     */
    @Test
    public void testDeleteBirdTest() {
        var id = 1L;
        when(birdRepository.existsById(id)).thenReturn(true);
        doNothing().when(birdRepository).deleteById(id);

        birdService.deleteBird(id);

        verify(birdRepository, times(1)).existsById(id);
        verify(birdRepository, times(1)).deleteById(id);
    }

    /**
     * Test that to delete a bird if do not exist
     */
    @Test
    public void testDeleteNonExistentBirdTest() {
        var nonExistentId = 1L;

        when(birdRepository.existsById(nonExistentId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> birdService.deleteBird(nonExistentId));
        verify(birdRepository, times(1)).existsById(nonExistentId);
    }
}
