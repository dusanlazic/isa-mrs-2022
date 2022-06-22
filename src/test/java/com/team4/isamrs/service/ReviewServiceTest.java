package com.team4.isamrs.service;

import com.team4.isamrs.dto.creation.ReviewCreationDTO;
import com.team4.isamrs.dto.display.ReviewAdminDisplayDTO;
import com.team4.isamrs.dto.updation.ReviewResponseDTO;
import com.team4.isamrs.exception.ReviewAlreadyResolvedException;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.AdvertisementRepository;
import com.team4.isamrs.repository.ReservationRepository;
import com.team4.isamrs.repository.ReviewRepository;
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
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepositoryMock;
    @Mock
    private ReservationRepository reservationRepositoryMock;
    @Mock
    private AdvertisementRepository advertisementRepositoryMock;

    @Mock
    private EmailService emailServiceMock;

    @Mock
    private Review reviewMock;
    @Mock
    private Advertisement advertisementMock;
    @Mock
    private Customer customerMock;

    @Spy
    ModelMapper modelMapper;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void testFindAllPending() {
        when(reviewRepositoryMock.findByApprovalStatusEquals(ApprovalStatus.PENDING)).
                thenReturn(Arrays.asList(reviewMock));

        Collection<ReviewAdminDisplayDTO> reviews = reviewService.findAllPending();

        assertThat(reviews).hasSize(1);
        verify(reviewRepositoryMock, times(1))
                .findByApprovalStatusEquals(ApprovalStatus.PENDING);
        verifyNoMoreInteractions(reviewRepositoryMock);
    }

    @Test
    @Transactional
    public void testCreate() {

        Authentication authentication = mock(Authentication.class);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(customerMock);

        when(reviewRepositoryMock.save(any())).thenReturn(reviewMock);
        when(advertisementRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(advertisementMock));
        when(reservationRepositoryMock
                .existsByAdvertisementEqualsAndCustomerEqualsAndCancelledIsFalseAndEndDateTimeBefore(
                        any(), any(), any())).thenReturn(true);

        Review savedReview = reviewService.create(Long.valueOf("1"), new ReviewCreationDTO(), authentication);

        assertEquals(reviewMock, savedReview);
    }

    @Test(expected = ReviewAlreadyResolvedException.class)
    @Transactional
    public void respondToReviewAlreadyResolved() {
        when(reviewMock.getApprovalStatus()).thenReturn(ApprovalStatus.APPROVED);
        when(reviewRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(reviewMock));

        reviewService.respondToReview(Long.valueOf("1"), new ReviewResponseDTO());

        when(reviewRepositoryMock.save(any())).thenReturn(reviewMock);
        doNothing().when(emailServiceMock).sendNewReviewEmail(any(), any(), any(), any(), any());
    }
}
