package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class DbServiceTestSuite {

    @Autowired
    DbService dbService;

    @Autowired
    TaskRepository taskRepository;

    @Test
    public void testGetAllTasks() {
        //Given
        Task testTask = new Task(12l, "title", "content");
        taskRepository.save(testTask);

        // When
        List<Task> resultList = dbService.getAllTasks();

        //Then
        Assert.assertNotEquals(0, resultList.size());
        //CleanUp
        taskRepository.delete(testTask);
    }

    @Test
    public void testGetEmptyTask() {
        //Given
        Task testTask1 = new Task(12l, "title1", "content1");
        taskRepository.save(testTask1);
        Task testTask2 = new Task(13l, "title2", "content2");
        taskRepository.save(testTask2);

        //When
        Optional<Task> resultTask = dbService.getTask(0l);

        //Then
        Assert.assertFalse(resultTask.isPresent());

        //CleanUp
        taskRepository.delete(testTask1);
        taskRepository.delete(testTask2);
    }

    @Test
    public void testGetTask() {
        //Given
        Task testTask1 = new Task("title1", "content1");
        taskRepository.save(testTask1);
        Task testTask2 = new Task("title2", "content2");
        taskRepository.save(testTask2);

        //When
        Optional<Task> result = dbService.getTask(testTask1.getId());

        //Then
        Assert.assertTrue(result.isPresent());

        //CleanUp
        taskRepository.delete(testTask1);
        taskRepository.delete(testTask2);
    }

    @Test
    public void testDelete(){
        //Given
        Task testTask1 = new Task("title1", "content1");
        taskRepository.save(testTask1);

        //When
        dbService.delete(testTask1.getId());

        //Then
        Assert.assertFalse(taskRepository.findById(testTask1.getId()).isPresent());
    }

    @Test
    public void testSaveTask(){
        //Given
        Task testTask1 = new Task("title1", "content1");

        //When
        dbService.saveTask(testTask1);
        long taskId = testTask1.getId();
        Optional<Task> resultTask = taskRepository.findById(taskId);

        //Then
        Assert.assertTrue(resultTask.isPresent());

        //CleanUp
        taskRepository.delete(taskId);
    }

}
