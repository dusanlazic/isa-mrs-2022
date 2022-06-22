package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ComplaintCreationDTO;
import com.team4.isamrs.dto.display.ComplaintDisplayDTO;
import com.team4.isamrs.dto.updation.ComplaintResponseDTO;
import com.team4.isamrs.exception.ComplaintAlreadyResolvedException;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.complaint.Complaint;
import com.team4.isamrs.model.enumeration.ResponseStatus;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.AdvertisementRepository;
import com.team4.isamrs.repository.ComplaintRepository;
import com.team4.isamrs.repository.ReservationRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ComplaintServiceTest {

    @Mock
    private ComplaintRepository complaintRepositoryMock;
    @Mock
    private ReservationRepository reservationRepositoryMock;
    @Mock
    private AdvertisementRepository advertisementRepositoryMock;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private Complaint complaintMock;
    @Mock
    private Customer customerMock;
    @Mock
    private Advertisement advertisementMock;


    @InjectMocks
    private ComplaintService complaintService;

    @Spy
    private ModelMapper modelMapper;

    @Test
    public void testFindAllPending() {
        when(complaintRepositoryMock.findByResponseStatusEquals(ResponseStatus.PENDING)).
                thenReturn(Arrays.asList(complaintMock));

        Collection<ComplaintDisplayDTO> complaints = complaintService.findAllPending();

        assertThat(complaints).hasSize(1);
        verify(complaintRepositoryMock, times(1))
                .findByResponseStatusEquals(ResponseStatus.PENDING);
        verifyNoMoreInteractions(complaintRepositoryMock);
    }

    @Test
    @Transactional
    public void testCreate() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(customerMock);

        when(complaintRepositoryMock.save(any())).thenReturn(complaintMock);
        when(advertisementRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(advertisementMock));
        when(reservationRepositoryMock
                .existsByAdvertisementEqualsAndCustomerEqualsAndCancelledIsFalseAndEndDateTimeBefore(
                        any(), any(), any())).thenReturn(true);

        Complaint savedComplaint = complaintService.create(Long.valueOf("1"), new ComplaintCreationDTO(), authentication);

        assertEquals(complaintMock, savedComplaint);
    }

    @Test(expected = ComplaintAlreadyResolvedException.class)
    @Transactional
    public void respondToComplaintAlreadyResolved() {
        when(complaintMock.getResponseStatus()).thenReturn(ResponseStatus.RESOLVED);
        when(complaintRepositoryMock.lockGetById(Mockito.anyLong())).thenReturn(Optional.of(complaintMock));

        complaintService.respondToComplaint(Long.valueOf("1"), new ComplaintResponseDTO());

        when(complaintRepositoryMock.save(any())).thenReturn(complaintMock);
        doNothing().when(emailServiceMock).sendComplaintResponseToBothParties(any(), any(), any(), any(), any(), any());
    }
}
