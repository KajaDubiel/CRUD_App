package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloValidatorTestSuite {

    @Autowired
    TrelloValidator trelloValidator;

    @Test
    public void testValidateTrelloBoards() {
        //Given
        TrelloList trelloList1 = new TrelloList("id1", "name1", true);
        TrelloList trelloList2 = new TrelloList("id2", "name2", true);
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloList1);
        trelloLists.add(trelloList2);
        TrelloBoard trelloBoard1 = new TrelloBoard("id", "normal name", trelloLists);
        TrelloBoard trelloBoard2 = new TrelloBoard("id", "test", trelloLists);
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);
        //When
        List<TrelloBoard> resultList = trelloValidator.validateTrelloBoards(trelloBoards);
        //Then
        Assert.assertEquals(1, resultList.size());
    }

    @Test
    public void testValidateCardOk(){
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "desc", "pos", "2");
        //When
        String message = trelloValidator.validateCard(trelloCard);
        //Then
        Assert.assertEquals("Seems that app is used in proper way", message);
    }

    @Test
    public void testValidateCardTest(){
        //Given
        TrelloCard trelloCard = new TrelloCard("test", "test", "pos", "2");
        //When
        String message = trelloValidator.validateCard(trelloCard);
        //Then
        Assert.assertEquals("Someone is testing this app!", message);
    }
}
