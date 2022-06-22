package com.team4.isamrs.service;

import com.team4.isamrs.constants.BoatAdConstants;
import com.team4.isamrs.dto.creation.BoatAdCreationDTO;
import com.team4.isamrs.dto.display.BoatAdDisplayDTO;
import com.team4.isamrs.dto.display.BoatAdSimpleDisplayDTO;
import com.team4.isamrs.model.advertisement.BoatAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoatAdServiceTest {

    @Mock
    BoatAdRepository boatAdRepositoryMock;
    @Mock
    ReservationRepository reservationRepositoryMock;
    @Mock
    FishingEquipmentRepository fishingEquipmentRepositoryMock;
    @Mock
    NavigationalEquipmentRepository navigationalEquipmentRepositoryMock;
    @Mock
    TagRepository tagRepositoryMock;

    @Mock
    BoatAd boatAdMock;
    @Mock
    Advertiser advertiserMock;

    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    private BoatAdService boatAdService;


    @Test
    public void testFindAll() {
        when(boatAdRepositoryMock.findAll())
                .thenReturn(Arrays.asList(boatAdMock));

        Collection<BoatAdDisplayDTO> boats = boatAdService.findAll(BoatAdDisplayDTO.class);

        assertThat(boats).hasSize(1);
        verify(boatAdRepositoryMock, times(1))
                .findAll();
        verifyNoMoreInteractions(boatAdRepositoryMock);
    }

    @Test
    public void testFindById() {
        when(boatAdRepositoryMock.findById(BoatAdConstants.DB_ID)).thenReturn(Optional.of(boatAdMock));
        BoatAdDisplayDTO boatDto = boatAdService.findById(BoatAdConstants.DB_ID, BoatAdDisplayDTO.class);

        assertEquals(boatDto, modelMapper.map(boatAdMock, BoatAdDisplayDTO.class));

        verify(boatAdRepositoryMock, times(1)).findById(BoatAdConstants.DB_ID);
        verifyNoMoreInteractions(boatAdRepositoryMock);
    }

    @Test
    public void testFindTopSix() {
        when(boatAdRepositoryMock.findAll(PageRequest.of(0, 6)))
                .thenReturn(new PageImpl<BoatAd>(Arrays.asList(boatAdMock)));

        Collection<BoatAdSimpleDisplayDTO> boats = boatAdService.findTopSix();

        assertThat(boats).hasSize(1);
        verify(boatAdRepositoryMock, times(1))
                .findAll(PageRequest.of(0, 6));
        verifyNoMoreInteractions(boatAdRepositoryMock);
    }

    @Test
    public void testSearch() {
        String where = "where";
        int guests = 1;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();

        when(boatAdRepositoryMock.search(where, guests))
                .thenReturn(Arrays.asList(boatAdMock));

        Page<BoatAdSimpleDisplayDTO> boats =
                boatAdService.search(where, guests, startDate,
                 endDate, PageRequest.of(0, 20),"price", true);

        assertThat(boats.getContent()).hasSize(1);
        verify(boatAdRepositoryMock, times(1))
                .search(where, guests);
        verifyNoMoreInteractions(boatAdRepositoryMock);
    }

    @Test(expected = NoSuchElementException.class)
    @Transactional
    public void testDelete() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(advertiserMock);

        BoatAd rad1 = new BoatAd();
        BoatAd rad2 = new BoatAd();
        Long id2 = Long.valueOf("25");
        rad1.setId(Long.valueOf("27"));
        rad2.setId(id2);

        when(boatAdRepositoryMock.findAll()).thenReturn(Arrays.asList(rad1, rad2));
        doNothing().when(boatAdRepositoryMock).delete(rad2);
        when(boatAdRepositoryMock.findById(id2)).thenReturn(Optional.empty());
        when(boatAdRepositoryMock.findBoatAdByIdAndAdvertiser(id2, advertiserMock))
                .thenReturn(Optional.of(rad2));

        int dbSizeBeforeRemove = boatAdService.findAll(BoatAdDisplayDTO.class).size();
        boatAdService.delete(id2, authentication);

        when(boatAdRepositoryMock.findAll()).thenReturn(Arrays.asList(rad1));

        Collection<BoatAdDisplayDTO> boatAdDisplayDTOs = boatAdService.findAll(BoatAdDisplayDTO.class);
        assertThat(boatAdDisplayDTOs).hasSize(dbSizeBeforeRemove - 1);

        verify(boatAdRepositoryMock, times(1)).delete(rad2);
        verify(boatAdRepositoryMock, times(2)).findAll();

        BoatAdDisplayDTO boatAdDisplayDTO = boatAdService.findById(id2, BoatAdDisplayDTO.class);
    }
}
