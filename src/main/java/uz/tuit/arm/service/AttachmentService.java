package uz.tuit.arm.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import uz.tuit.arm.entity.Attachment;
import uz.tuit.arm.payload.ApiResponseModel;
import uz.tuit.arm.repository.AttachmentContentRepository;
import uz.tuit.arm.repository.AttachmentRepository;
import uz.tuit.arm.repository.AttachmentTypeRepository;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class AttachmentService {

    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentTypeRepository attachmentTypeRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    public Attachment uploadFile1(MultipartHttpServletRequest request) {
        Attachment attachment = new Attachment();
        Iterator<String> iterator = request.getFileNames();
        System.out.println(iterator);
        MultipartFile file1;
        while (iterator.hasNext()) {
            file1 = request.getFile(iterator.next());

            try {
                File file =
                        new File(
                                "C:\\Users\\khusan_madjitov\\OneDrive\\Рабочий стол\\Projects\\arm\\src\\main\\resources" +  file1.getOriginalFilename());
                file.mkdirs();
                file1.transferTo(file);
                attachment.setName(file1.getOriginalFilename());
                attachment.setContentType(file1.getContentType());
                attachment.setSize(file1.getSize());
                attachment.setPath(file.getPath());

                Attachment save = attachmentRepository.save(attachment);
                return save;

            } catch (Exception e) {
               return null;
            }

        }

        return attachment;
    }


    public Attachment uploadFile2(MultipartFile request) {
        Attachment attachment = new Attachment();
            try {
                File file =
                        new File(
                                "/home/transport/uploads/images" +  request.getOriginalFilename());
                file.mkdirs();
                request.transferTo(file);
                attachment.setName(request.getOriginalFilename());
                attachment.setContentType(request.getContentType());
                attachment.setSize(request.getSize());
                attachment.setPath(file.getPath());
                Attachment save = attachmentRepository.save(attachment);
                return save;
            } catch (Exception e) {
                return null;
            }
    }

//    public ApiResponseModel uploadFile(MultipartHttpServletRequest request) {
//        ApiResponseModel apiResponseModel = new ApiResponseModel();
//        Status status = new Status();
//        try{
//            Iterator<String> iterator =request.getFileNames();
//            MultipartFile file;
//            while (iterator.hasNext()){
//                file = request.getFile(iterator.next());
//                AttachmentType attachmentType = attachmentTypeRepository.findAttachmentTypeByAttachmentTypeEnum(AttachmentTypeEnum.valueOf(request.getParameter("type")));
//                AttachmentContent attachmentContent = attachmentContentRepository.save(new AttachmentContent(file.getBytes()));
//                Attachment attachment = attachmentRepository.save(new Attachment(
//                        file.getName().concat(new Timestamp(System.currentTimeMillis()).toString()),
//                        file.getContentType(),
//                        file.getSize(),
//                        attachmentContent,
//                        attachmentType));
//                status.setCode(200);
//                status.setMessage(attachment.getName());
//                apiResponseModel.setData(attachment.getId());
//            }
//            apiResponseModel.setStatus(status);
//        } catch (Exception e) {
//            status.setCode(500);
//            status.setMessage("error");
//        }
//        apiResponseModel.setStatus(status);
//        return apiResponseModel;
//    }




//    public ApiResponseModel addFile(MultipartHttpServletRequest request) {
//        ApiResponseModel apiResponseModel = new ApiResponseModel();
//        Status status = new Status();
//        try{
//            Iterator<String> iterator =request.getFileNames();
//            MultipartFile file;
//            List<Attachment> attachmentList = new ArrayList<>();
//
//            while (iterator.hasNext()){
//
//                file = request.getFile(iterator.next());
//
//                AttachmentType attachmentType = attachmentTypeRepository.findAttachmentTypeByAttachmentTypeEnum(AttachmentTypeEnum.valueOf(request.getParameter("type")));
//
//                BufferedImage image = ImageIO.read(file.getInputStream());
//
//                BufferedImage image1 = resizeImage(image, 512, 512);
//
//                byte[] bytes = toByteArray(image1, "jpg");
//
//                AttachmentContent attachmentContent = attachmentContentRepository.save(new AttachmentContent(bytes));
//                AttachmentContent attachmentContent1 = attachmentContentRepository.save(new AttachmentContent(file.getBytes()));
//
//                Attachment attachment = attachmentRepository.save(new Attachment(
//                        file.getName().concat(new Timestamp(System.currentTimeMillis()).toString()),
//                        file.getContentType(),
//                        file.getSize(),
//                        attachmentContent,
//                        attachmentType));
//
//                Attachment attachment1 = attachmentRepository.save(new Attachment(
//                        file.getName().concat(new Timestamp(System.currentTimeMillis()).toString()),
//                        file.getContentType(),
//                        file.getSize(),
//                        attachmentContent1,
//                        attachmentType));
//                attachmentList.add(attachment);
//                attachmentList.add(attachment1);
//                status.setCode(200);
//                status.setMessage("success");
//            }
//            apiResponseModel.setData(attachmentList);
//            apiResponseModel.setStatus(status);
//        } catch (Exception e) {
//            status.setCode(500);
//            status.setMessage("error");
//        }
//        apiResponseModel.setStatus(status);
//        return apiResponseModel;
//    }


//    public ApiResponseModel addFile1(MultipartHttpServletRequest request) {
//        ApiResponseModel apiResponseModel = new ApiResponseModel();
//        Status status = new Status();
//        try{
//            Iterator<String> iterator =request.getFileNames();
//            MultipartFile file;
//            List<Long> attachmentList = new ArrayList<>();
//
//            while (iterator.hasNext()){
//
//                file = request.getFile(iterator.next());
//
//                AttachmentType attachmentType = attachmentTypeRepository.findAttachmentTypeByAttachmentTypeEnum(AttachmentTypeEnum.valueOf(request.getParameter("type")));
//
//                BufferedImage image = ImageIO.read(file.getInputStream());
//
//                BufferedImage image1 = resizeImage(image, 512, 512);
//
//                byte[] bytes = toByteArray(image1, "jpg");
//
//                AttachmentContent attachmentContent = attachmentContentRepository.save(new AttachmentContent(bytes));
//                AttachmentContent attachmentContent1 = attachmentContentRepository.save(new AttachmentContent(file.getBytes()));
//
//                Attachment attachment = attachmentRepository.save(new Attachment(
//                        file.getName().concat(new Timestamp(System.currentTimeMillis()).toString()),
//                        file.getContentType(),
//                        file.getSize(),
//                        attachmentContent,
//                        attachmentType));
//
//                Attachment attachment1 = attachmentRepository.save(new Attachment(
//                        file.getName().concat(new Timestamp(System.currentTimeMillis()).toString()),
//                        file.getContentType(),
//                        file.getSize(),
//                        attachmentContent1,
//                        attachmentType));
//                attachmentList.add(attachment.getId());
//                attachmentList.add(attachment1.getId());
//                status.setCode(200);
//                status.setMessage("success");
//            }
//            apiResponseModel.setData(attachmentList);
//            apiResponseModel.setStatus(status);
//        } catch (Exception e) {
//            status.setCode(500);
//            status.setMessage("error");
//        }
//        apiResponseModel.setStatus(status);
//        return apiResponseModel;
//    }

//    public ApiResponseModel addAudio(MultipartHttpServletRequest request) {
//        ApiResponseModel apiResponseModel = new ApiResponseModel();
//        Status status = new Status();
//        try{
//            Iterator<String> iterator =request.getFileNames();
//            MultipartFile file;
//            while (iterator.hasNext()){
//
//                file = request.getFile(iterator.next());
//
//                AttachmentType attachmentType = attachmentTypeRepository.findAttachmentTypeByAttachmentTypeEnum(AttachmentTypeEnum.valueOf(request.getParameter("type")));
//
//                AttachmentContent attachmentContent = attachmentContentRepository.save(new AttachmentContent(file.getBytes()));
//
//                Attachment attachment = attachmentRepository.save(new Attachment(
//                        file.getName().concat(new Timestamp(System.currentTimeMillis()).toString()),
//                        file.getContentType(),
//                        file.getSize(),
//                        attachmentContent,
//                        attachmentType));
//                status.setCode(200);
//                status.setMessage(attachment.getName());
//                apiResponseModel.setData(attachment);
//            }
//            apiResponseModel.setStatus(status);
//        } catch (Exception e) {
//            status.setCode(500);
//            status.setMessage("error");
//        }
//        apiResponseModel.setStatus(status);
//        return apiResponseModel;
//    }

    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

//    public BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
//        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//        Thumbnails.of(originalImage)
//                .size(targetWidth, targetHeight)
//                .outputFormat("JPG")
//                .outputQuality(0.2)
//                .toOutputStream(outputStream);
//        byte[] data = outputStream.toByteArray();
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(data);
//        return ImageIO.read(inputStream);
//    }

//    public HttpEntity<?> getFoto(Long id){
//        Attachment attachment = attachmentRepository.findById(id).get();
//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(attachment.getContentType()))
//                .body(attachment.getAttachmentContent().getSize());
//    }
//
//    public ApiResponseModel delete(Long id){
//        ApiResponseModel model = new ApiResponseModel();
//        Status status = new Status();
//        try {
//            if (attachmentRepository.existsById(id)){
//                Attachment attachment = attachmentRepository.findById(id).get();
//                attachmentRepository.delete(attachment);
//                attachmentContentRepository.delete(attachment.getAttachmentContent());
//                status.setMessage("success");
//                status.setCode(200);
//            }else {
//                status.setCode(202);
//                status.setMessage("bunday id lik foto mavjud emas");
//            }
//        }catch (Exception e){
//            status.setCode(500);
//            status.setMessage("bunday id lik foto mavjud emas");
//        }
//        model.setStatus(status);
//        return model;
//    }
}
