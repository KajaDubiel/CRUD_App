package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.dto.CreatedTrelloCardDto;
import com.crud.tasks.domain.dto.TrelloBoardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.domain.dto.TrelloListDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import sun.java2d.pipe.AAShapePipe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {

    @InjectMocks
    TrelloService trelloService;

    @Mock
    TrelloClient trelloClient;

    @Mock
    SimpleEmailService emailService;

    @Mock
    AdminConfig adminConfig;

    @Test
    public void testShouldFetchEmptyBoard() {
        //Given
        when(trelloClient.getTrelloBoards()).thenReturn(new ArrayList<>());
        //When
        List<TrelloBoardDto> resultBoardDto = trelloService.fetchTrelloBoards();
        //Then
        Assert.assertTrue(resultBoardDto.isEmpty());
    }

    @Test
    public void shouldFetchBoard() {
        //Given
        List<TrelloListDto> lists = new ArrayList<>();
        TrelloListDto trelloListDto = new TrelloListDto("id", "name", true);
        lists.add(trelloListDto);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("id", "name", lists);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> resultBoardDto = trelloService.fetchTrelloBoards();

        //Then
        Assert.assertFalse(resultBoardDto.isEmpty());
    }

    @Test
    public void testShouldCreateTrelloCardAndSendMail() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "pos", "1");
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(new CreatedTrelloCardDto());

        //When
        CreatedTrelloCardDto trelloCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        Mockito.verify(emailService, times(1)).send(anyObject());
        Assert.assertTrue(trelloCard instanceof CreatedTrelloCardDto);
    }

    @Test
    public void testShouldNotCreateTrelloCardAndNotSendMail() {
        //Given
        TrelloCardDto trelloCardDto = null;
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(null);

        //When
        CreatedTrelloCardDto trelloCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        Mockito.verify(emailService, times(0)).send(anyObject());
        Assert.assertNull(trelloCard);
    }
}
