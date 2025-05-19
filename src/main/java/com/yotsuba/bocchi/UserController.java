package com.yotsuba.bocchi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable String userId) {
        UserService.AvatarData avatarData = userService.getAvatar(userId);

        if (avatarData.data() == null) {
            return ResponseEntity.notFound().build();
        }



        HttpHeaders headers = new HttpHeaders();
        String contentType = avatarData.contentType();
        if (contentType == null || contentType.isBlank()) {
            contentType = "image/jpeg";
        }
        headers.setContentType(MediaType.parseMediaType(contentType));
        return new ResponseEntity<>(avatarData.data(), headers, HttpStatus.OK);
    }

    @PostMapping(value = "/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadAvatar(@RequestParam("userId") String userId,
                             @RequestPart("avatar") MultipartFile file) throws IOException {
        userService.saveAvatar(userId, file);
    }
}