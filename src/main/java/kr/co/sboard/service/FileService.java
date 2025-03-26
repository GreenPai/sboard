package kr.co.sboard.service;

import kr.co.sboard.dto.ArticleDTO;
import kr.co.sboard.dto.FileDTO;
import kr.co.sboard.entity.File;
import kr.co.sboard.repository.FileRepository;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUpload;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;

    public void save(FileDTO fileDTO) {

        File file = modelMapper.map(fileDTO, File.class);
        fileRepository.save(file);
    }


    // application.yml에 설정
    @Value("${spring.servlet.multipart.location}")
    private String uploadDir;

    private MultipartFile file1;
    private MultipartFile file2;
    
    public List<FileDTO> uploadFile(ArticleDTO articleDTO){

        // 파일 업로드 디렉토리 객체 생성
        java.io.File fileUploadDir = new java.io.File(uploadDir);

        if(!fileUploadDir.exists()){
            // 파일 업로드 디렉토리가 존재하지 않으면 생성
            fileUploadDir.mkdirs();
        }

        // 파일 업로드 디렉토리 시스템 경로 구하기
        String fileUploadPath = fileUploadDir.getAbsolutePath();
        log.info("fileUploadPath: {}", fileUploadPath);

        // 업로드 파일 정보 리스트 생성
        List<FileDTO> list = new ArrayList<>();

        
        // 파일 정보 객체 가져오기
        List<MultipartFile> multipartFiles = articleDTO.getMultipartFile();

        for(MultipartFile multipartFile : multipartFiles){
            if(!multipartFile.isEmpty()){

                String oName = multipartFile.getOriginalFilename();
                String ext = oName.substring(oName.lastIndexOf("."));
                String sName = UUID.randomUUID().toString() + ext;

                // 파일 저장
                try {
                    multipartFile.transferTo(new java.io.File(fileUploadPath, sName));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // 반환용 리스트 객체
                FileDTO fileDTO = FileDTO.builder()
                        .oName(oName)
                        .sName(sName)
                        .build();

                list.add(fileDTO);

            }
        }

        return list;

    }

    public void downloadFile(){

    }
}
