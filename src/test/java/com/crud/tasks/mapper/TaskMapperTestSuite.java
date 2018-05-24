package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.dto.TaskDto;
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
public class TaskMapperTestSuite {
    @Autowired
    TaskMapper taskMapper;

    @Test
    public void testMapToTask(){
        //Given
        TaskDto taskDto = new TaskDto(123l, "title", "content");

        //When
        Task task = taskMapper.mapToTask(taskDto);

        //Then
        Assert.assertTrue(task instanceof Task);
    }

    @Test
    public void testMapToTaskDto(){
        //Given
        Task task = new Task(123l, "title", "content");

        //When
        TaskDto resultTask = taskMapper.mapToTaskDto(task);

        //Then
        Assert.assertTrue(resultTask instanceof TaskDto);
    }

    @Test
    public void  testMapToTaskDtoList(){
        //Given
        Task task1 = new Task(123l, "title1", "content1");
        Task task2 = new Task(124l, "title2", "content2");
        Task task3 = new Task(125l, "title3", "content3");

        List<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);

        //When
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(tasks);

        //Then
        Assert.assertEquals(3, taskDtos.size());
        taskDtos.forEach(taskDto -> {
            Assert.assertTrue(taskDto instanceof TaskDto);
        }
        );
    }
}
