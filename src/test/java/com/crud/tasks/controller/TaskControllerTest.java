package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.dto.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private TaskMapper taskMapper;

    @Test
    public void testGetTask() throws Exception {
        //Given
        Task task = new Task(10l, "Title", "Content");
        Optional<Task> taskOpt = Optional.of(task);
        TaskDto taskDto = new TaskDto(10l, "Title", "Content");
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(service.getTask(anyLong())).thenReturn(taskOpt);

        //When&Then
        mockMvc.perform(get("/v1/task/getTask?taskId=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(10)))//dlaczego nie 10l ?
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.content", is("Content")));
    }

    @Test
    public void testGetTasks() throws Exception {
        //Given
        Task task1 = new Task(10l, "Title1", "Content1");
        Task task2 = new Task(11l, "Title2", "Content2");
        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);

        TaskDto taskDto1 = new TaskDto(10l, "Title1", "Content1");
        TaskDto taskDto2 = new TaskDto(11l, "Title2", "Content2");
        List<TaskDto> taskDtos = new ArrayList<>();
        taskDtos.add(taskDto1);
        taskDtos.add(taskDto2);

        when(taskMapper.mapToTaskDtoList(anyListOf(Task.class))).thenReturn(taskDtos);
        when(service.getAllTasks()).thenReturn(tasks);

        //When&Then
        mockMvc.perform(get("/v1/task/getTasks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(10)))
                .andExpect(jsonPath("$[1].title", is("Title2")))
                .andExpect(jsonPath("$[0].content", is("Content1")));
    }

    @Test
    public void testDeleteTask() throws Exception {
        //Given
        doNothing().when(service).delete(anyLong());

        //When&Then
        mockMvc.perform(delete("/v1/task/deleteTask?taskId=10").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void testUpdateTask() throws Exception {
        //Given
        Task task = new Task(10l, "Title", "Content");
        TaskDto taskDto = new TaskDto(10l, "Title", "Content");
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(service.saveTask(any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When&Then
        mockMvc.perform(put("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(10)))
                .andExpect(jsonPath("$.title", is("Title")))
                .andExpect(jsonPath("$.content", is("Content")));
    }

    @Test
    public void testCreateTask() throws Exception {

        //Given
        Task task = new Task(10l, "Title", "Content");
        TaskDto taskDto = new TaskDto(10l, "Title", "Content");
        when(taskMapper.mapToTaskDto(any(Task.class))).thenReturn(taskDto);
        when(service.saveTask(any(Task.class))).thenReturn(task);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);

        //When&Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }
}