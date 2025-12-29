package com.Edupulse.QuizService.service;

import com.Edupulse.QuizService.model.dto.ClassResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;


@FeignClient(name = "class-service", url = "${CLASS_SERVICE_URL:class-service-url}")
public interface ClassServiceClient {


    @GetMapping("/classes/{classId}")
    ClassResponse getClassById(@PathVariable("classId") Long classId);

    @GetMapping("/lecturer/{lecturerId}")
    List<ClassResponse> getClassesByLecturer(
            @PathVariable Long lecturerId,
            @RequestHeader("X-User-Id") Long userId
    );
}
