package com.team4.isamrs.service;

import com.team4.isamrs.dto.display.ReviewAdminDisplayDTO;
import com.team4.isamrs.model.advertisement.Advertisement;
import com.team4.isamrs.model.enumeration.ApprovalStatus;
import com.team4.isamrs.model.review.Review;
import com.team4.isamrs.model.user.Customer;
import com.team4.isamrs.repository.ReservationRepository;
import com.team4.isamrs.repository.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
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
}
