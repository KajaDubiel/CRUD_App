package com.crud.tasks.controller;


import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/trello")
public class TrelloController {

    @Autowired
    private TrelloClient trelloClient;

    @RequestMapping(method = RequestMethod.GET, value = "getTrelloBoards")
    public void getTrelloBoards() {

        List<TrelloBoardDto> trelloBoards = trelloClient.getTrelloBoards();

        Optional idOpt = Optional.ofNullable(trelloBoards.stream().map(tb -> tb.getId()));
        Optional nameOpt = Optional.ofNullable(trelloBoards.stream().map(tb -> tb.getName()));

        trelloBoards.stream()
                .filter(tb -> idOpt.isPresent() && nameOpt.isPresent() && tb.getName()
                        .toUpperCase()
                        .matches("(.*)KODILLA(.*)")).forEach(tb -> System.out.println(tb.getId() + " " + tb.getName()));

//            trelloBoards.stream()
//                    .filter(b -> b.getId() != null && b.getName() != null && b.getName()
//                            .toUpperCase()
//                            .matches("(.*)STUPID(.*)"))
//                    .forEach(b -> System.out.println(b.getId() + " " + b.getName()));
    }
}
