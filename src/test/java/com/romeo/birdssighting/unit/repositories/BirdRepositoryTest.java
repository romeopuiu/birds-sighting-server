package com.romeo.birdssighting.unit.repositories;


import com.romeo.birdssighting.domain.Bird;
import com.romeo.birdssighting.repositories.BirdRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BirdRepositoryTest {

    @Mock
    private BirdRepository birdRepository;

    /**
     * This method is used for testing to find a bird by name
     */
    @Test
    public void testFindByName() {
        var name = "Test";
        var bird = new Bird();
        bird.setName(name);

        when(birdRepository.findByName(name)).thenReturn(bird);

        var result = birdRepository.findByName(name);

        assertEquals(name, result.getName());
        verify(birdRepository).findByName(name);
    }

    /**
     * This method is used for testing to find a bird by color
     */
    @Test
    public void testFindByColor() {
        var color = "Red";
        var bird = new Bird();
        bird.setColor(color);

        when(birdRepository.findByColor(color)).thenReturn(bird);

        var result = birdRepository.findByColor(color);
        assertEquals(color, result.getColor());
        verify(birdRepository).findByColor(color);
    }
}
