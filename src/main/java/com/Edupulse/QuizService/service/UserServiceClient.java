package com.Edupulse.QuizService.service;



import com.Edupulse.QuizService.model.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "${USER_SERVICE_URL:user-service-url}")

public interface UserServiceClient {






    @GetMapping("/students/validate/{studentId}")
    UserResponse validateStudent(@PathVariable Long studentId);

    @GetMapping("/lecturers/validate/{lecturerId}")
    UserResponse validateLecturer(@PathVariable Long lecturerId);

    // NEW - for UserDetailsService

}
