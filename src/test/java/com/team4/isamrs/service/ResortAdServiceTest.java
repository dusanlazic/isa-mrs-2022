package com.team4.isamrs.service;

import com.team4.isamrs.constants.ResortAdConstants;
import com.team4.isamrs.dto.creation.ResortAdCreationDTO;
import com.team4.isamrs.dto.display.ResortAdDisplayDTO;
import com.team4.isamrs.dto.display.ResortAdSimpleDisplayDTO;
import com.team4.isamrs.model.advertisement.ResortAd;
import com.team4.isamrs.model.user.Advertiser;
import com.team4.isamrs.repository.ReservationRepository;
import com.team4.isamrs.repository.ResortAdRepository;
import com.team4.isamrs.repository.TagRepository;
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
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ResortAdServiceTest {

    @Mock
    ResortAdRepository resortAdRepositoryMock;
    @Mock
    ReservationRepository reservationRepositoryMock;
    @Mock
    TagRepository tagRepositoryMock;

    @Mock
    ResortAd resortAdMock;
    @Mock
    Advertiser advertiserMock;

    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    private ResortAdService resortAdService;


    @Test
    public void testFindAll() {
        when(resortAdRepositoryMock.findAll())
                .thenReturn(Arrays.asList(resortAdMock));

        Collection<ResortAdDisplayDTO> resorts = resortAdService.findAll();

        assertThat(resorts).hasSize(1);
        verify(resortAdRepositoryMock, times(1))
                .findAll();
        verifyNoMoreInteractions(resortAdRepositoryMock);
    }

    @Test
    public void testFindById() {
        when(resortAdRepositoryMock.findById(ResortAdConstants.DB_ID)).thenReturn(Optional.of(resortAdMock));
        ResortAdDisplayDTO resortDto = resortAdService.findById(ResortAdConstants.DB_ID);

        assertEquals(resortDto, modelMapper.map(resortAdMock, ResortAdDisplayDTO.class));

        verify(resortAdRepositoryMock, times(1)).findById(ResortAdConstants.DB_ID);
        verifyNoMoreInteractions(resortAdRepositoryMock);
    }

    @Test
    public void testFindTopSix() {
        when(resortAdRepositoryMock.findAll(PageRequest.of(0, 6)))
                .thenReturn(new PageImpl<ResortAd>(Arrays.asList(resortAdMock)));

        Collection<ResortAdSimpleDisplayDTO> resorts = resortAdService.findTopSix();

        assertThat(resorts).hasSize(1);
        verify(resortAdRepositoryMock, times(1))
                .findAll(PageRequest.of(0, 6));
        verifyNoMoreInteractions(resortAdRepositoryMock);
    }

    @Test
    public void testSearch() {
        String where = "where";
        int guests = 1;
        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();

        when(resortAdRepositoryMock.search(where, guests))
                .thenReturn(Arrays.asList(resortAdMock));

        Page<ResortAdSimpleDisplayDTO> resorts =
                resortAdService.search(where, guests, startDate,
                 endDate, PageRequest.of(0, 20),"price", true);

        assertThat(resorts.getContent()).hasSize(1);
        verify(resortAdRepositoryMock, times(1))
                .search(where, guests);
        verifyNoMoreInteractions(resortAdRepositoryMock);
    }

    @Test
    @Transactional
    public void testCreate() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(advertiserMock);

        when(resortAdRepositoryMock.save(any())).thenReturn(resortAdMock);

        ResortAd savedResortAd = resortAdService.create(new ResortAdCreationDTO(), authentication);

        assertEquals(resortAdMock, savedResortAd);
    }

    @Test(expected = NoSuchElementException.class)
    @Transactional
    public void testDelete() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(advertiserMock);

        ResortAd rad1 = new ResortAd();
        ResortAd rad2 = new ResortAd();
        Long id2 = Long.valueOf("2");
        rad1.setId(Long.valueOf("1"));
        rad2.setId(id2);

        when(resortAdRepositoryMock.findAll()).thenReturn(Arrays.asList(rad1, rad2));
        doNothing().when(resortAdRepositoryMock).delete(rad2);
        when(resortAdRepositoryMock.findById(id2)).thenReturn(Optional.empty());
        when(resortAdRepositoryMock.findResortAdByIdAndAdvertiser(id2, advertiserMock))
                .thenReturn(Optional.of(rad2));

        int dbSizeBeforeRemove = resortAdService.findAll().size();
        resortAdService.delete(id2, authentication);

        when(resortAdRepositoryMock.findAll()).thenReturn(Arrays.asList(rad1));

        Collection<ResortAdDisplayDTO> resortAdDisplayDTOs = resortAdService.findAll();
        assertThat(resortAdDisplayDTOs).hasSize(dbSizeBeforeRemove - 1);

        verify(resortAdRepositoryMock, times(1)).delete(rad2);
        verify(resortAdRepositoryMock, times(2)).findAll();

        ResortAdDisplayDTO resortAdDisplayDTO = resortAdService.findById(id2);
    }
}
