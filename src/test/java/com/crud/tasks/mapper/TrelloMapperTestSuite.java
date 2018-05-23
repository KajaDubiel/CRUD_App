package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.dto.TrelloBoardDto;
import com.crud.tasks.domain.dto.TrelloCardDto;
import com.crud.tasks.domain.dto.TrelloListDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TrelloMapperTestSuite {


    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        List<TrelloListDto> trelloListDtos1 = new ArrayList<>();
        List<TrelloListDto> trelloListDtos2 = new ArrayList<>();

        TrelloListDto listDto1 = new TrelloListDto("1", "listDto1", true);
        TrelloListDto listDto2 = new TrelloListDto("2", "listDto2", false);
        TrelloListDto listDto3 = new TrelloListDto("3", "listDto3", false);

        trelloListDtos1.add(listDto1);
        trelloListDtos1.add(listDto2);

        trelloListDtos2.add(listDto3);

        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("1", "to do", trelloListDtos1);
        TrelloBoardDto trelloBoardDto2 = new TrelloBoardDto("2", "in progress", trelloListDtos2);

        trelloBoardDtos.add(trelloBoardDto1);
        trelloBoardDtos.add(trelloBoardDto2);

        //When
        List<TrelloBoard> resultBoards = trelloMapper.mapToBoards(trelloBoardDtos);

        //Then
        TrelloBoard trelloBoard = resultBoards.get(0);
        Assert.assertTrue(trelloBoard instanceof TrelloBoard);
        Assert.assertTrue(trelloBoard.getList().get(0) instanceof TrelloList);

    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        List<TrelloList> trelloList1 = new ArrayList<>();
        List<TrelloList> trelloList2 = new ArrayList<>();

        TrelloList list1 = new TrelloList("1", "list1", true);
        TrelloList list2 = new TrelloList("2", "list2", false);
        TrelloList list3 = new TrelloList("3", "list3", false);

        trelloList1.add(list1);
        trelloList1.add(list2);

        trelloList2.add(list3);

        TrelloBoard trelloBoard1 = new TrelloBoard("1", "to do", trelloList1);
        TrelloBoard trelloBoard2 = new TrelloBoard("2", "in progress", trelloList2);

        trelloBoards.add(trelloBoard1);
        trelloBoards.add(trelloBoard2);

        //When
        List<TrelloBoardDto> resultBoards = trelloMapper.mapToBoardsDto(trelloBoards);

        //Then
        TrelloBoardDto resultBoard = resultBoards.get(0);
        Assert.assertTrue(resultBoard instanceof TrelloBoardDto);
        Assert.assertTrue(resultBoard.getLists().get(0) instanceof TrelloListDto);
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        TrelloListDto list1 = new TrelloListDto("1", "list1", true);
        TrelloListDto list2 = new TrelloListDto("2", "list2", false);
        TrelloListDto list3 = new TrelloListDto("3", "list3", false);
        trelloListDtos.add(list1);
        trelloListDtos.add(list2);
        trelloListDtos.add(list3);

        //When
        List<TrelloList> returnedList = trelloMapper.mapToList(trelloListDtos);

        //Then
        Assert.assertTrue(returnedList.get(0) instanceof TrelloList);
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloLists = new ArrayList<>();
        TrelloList list1 = new TrelloList("1", "list1", true);
        TrelloList list2 = new TrelloList("2", "list2", false);
        TrelloList list3 = new TrelloList("3", "list3", false);
        trelloLists.add(list1);
        trelloLists.add(list2);
        trelloLists.add(list3);

        //When
        List<TrelloListDto> returnedList = trelloMapper.mapToListDto(trelloLists);

        //Then
        Assert.assertTrue(returnedList.get(0) instanceof TrelloListDto);
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("name", "description", "pos", "list id");

        //When
        TrelloCard resultCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        Assert.assertTrue(resultCard instanceof TrelloCard);
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "description", "pos", "list id");
        //When
        TrelloCardDto resultCard = trelloMapper.mapToCardDto(trelloCard);

        //Then
        Assert.assertTrue(resultCard instanceof TrelloCardDto);
    }
}
