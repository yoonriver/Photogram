package com.cos.photogramstart.service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    @Transactional(readOnly = true) // 영속성 컨텍스트에서 변경감지를 해서 더티체킹, flush(반영) X
    public Page<Image> 이미지스토리(int principalId, Pageable pageable) {
        Page<Image> images = imageRepository.mStory(principalId, pageable);

        // 2(cos) 로그인
        // images에 좋아요 상태 담기
        images.forEach((image)-> {
            image.getLikes().forEach((like) -> {
                // 해당 이미지에 좋아요 한 사람들을 현재 로그인 한 사람이 좋아요 한것인지 비교
                if(like.getUser().getId() == principalId) {
                    image.setLikeState(true);
                }
            });
        });

        return images;
    }


    @Value("${file.path}") // application.yml에서 설정한 파일 경로를 저장한 변수 이름
    private String uploadFolder;

    @Transactional
    public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails) {
        UUID uuid = UUID.randomUUID(); // UUID : 네트워크 상에서 고유성이 보장되는 id를 만들기위한 표준 규약

        // 'uuid + _ + 실제파일이름'으로 조합 하면 이름이 겹칠 확률이 희박해진다
        String imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename(); // 실제 파일 이름 저장
        System.out.println("이미지 파일 이름 " + imageFileName);

        Path imageFilePath = Paths.get(uploadFolder + imageFileName);


        // 통신, I/O -> 예외가 발생할 수 있다.
        try {
            // Files.write(파일이 저장 될 경로, 업로드 될 파일, 옵션(생략 가능)
            Files.write(imageFilePath, imageUploadDto.getFile().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // image 테이블에 저장
        Image image = imageUploadDto.toEntity(imageFileName, principalDetails.getUser()); // imageFileName = uuid + "_" + imageUploadDto.getFile().getOriginalFilename();
        imageRepository.save(image);

        //System.out.println(imageEntity);
    }
}
