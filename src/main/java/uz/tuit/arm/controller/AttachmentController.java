package uz.tuit.arm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.tuit.arm.entity.Attachment;
import uz.tuit.arm.payload.ApiResponseModel;
import uz.tuit.arm.payload.Status;
import uz.tuit.arm.repository.AttachmentRepository;
import uz.tuit.arm.service.AttachmentService;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/api/attach")
public class AttachmentController {

    @Autowired
    AttachmentService attachmentService;
    @Autowired
    AttachmentRepository attachmentRepository;

    @PostMapping("/upload")
    public HttpEntity<?> addFile1(MultipartHttpServletRequest request){
        Attachment attachment = attachmentService.uploadFile1(request);
        return ResponseEntity.ok(attachment.getId());
    }

    @GetMapping("/get/{id}")
    public HttpEntity<?> getFile(@PathVariable Long id) throws IOException {
        Optional<Attachment> attachment = attachmentRepository.findById(id);
        if (attachment.isPresent()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(attachment.get().getContentType()))
                    .body(Files.readAllBytes(Paths.get(attachment.get().getPath())));
        }else {
            return ResponseEntity.ok(new Status("attachment not found",101));
        }
    }

//    @GetMapping("/get/resize/{id}")
//    public HttpEntity<?> getResizeFile(@PathVariable Long id){
//        Optional<Attachment> byId = attachmentRepository.findById(id);
//        try {
//            if (byId.isPresent()) {
//                File file = new File(byId.get().getPath());
//                BufferedImage read = ImageIO.read(file);
//                BufferedImage bufferedImage = new BufferedImage(read.getWidth()/2, read.getHeight()/2, read.getType());
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                ImageIO.write(bufferedImage, byId.get().getContentType(), baos);
//                return ResponseEntity.ok()
//                        .contentType(MediaType.parseMediaType(byId.get().getContentType()))
//                        .body(baos.toByteArray());
//            } else {
//                return ResponseEntity.ok(new Status(101, "attachment not found"));
//            }
//        }catch (Exception e){
//            return ResponseEntity.ok(new Status(101, "attachment not found"));
//        }
//    }

}
