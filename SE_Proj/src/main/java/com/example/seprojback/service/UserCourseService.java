package com.example.seprojback.service;

import com.example.seprojback.entity.Course;

import java.util.List;

public interface UserCourseService {
    List<Course> getUserCourses(String userId);
}
