package com.example.spring.posts;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostsController {

    @Autowired
    PostsService postsService;

    private final String uploadPath = "C:/uploads/board";

    // 게시글 등록 (화면, GET)
    @GetMapping("/create")
    public String createGet() {
        return "posts/create";
    }

    // 게시글 등록 (처리, POST)
    @PostMapping("/create")
    public String createPost(PostsVo postsVo, RedirectAttributes redirectAttributes) {
        try {
            // 파일 업로드 처리
            MultipartFile uploadFile = postsVo.getUploadFile();
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String originalFileName = uploadFile.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 업로드 디렉토리가 없으면 생성
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 파일 저장
                File destFile = new File(uploadPath + File.separator + fileName);
                uploadFile.transferTo(destFile);

                // 파일 정보 설정
                postsVo.setFileName(fileName);
                postsVo.setOriginalFileName(originalFileName);
            }

            // 게시글 저장
            boolean created = postsService.create(postsVo);

            if (created) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 등록되었습니다.");
                return "redirect:/posts/";
            }

            redirectAttributes.addFlashAttribute("errorMessage", "게시글 등록에 실패했습니다.");
            return "redirect:/posts/create";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/posts/create";
        }
    }

    // 게시글 목록 (화면, GET)
    @GetMapping("/")
    public String listGet(
        @RequestParam(value = "page", defaultValue = "1") int page, 
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchKeyword,
        Model model
    ) {
        int pageSize = 10; // 페이지당 게시글 수
        Map<String, Object> result = postsService.list(page, pageSize, searchType, searchKeyword);
        model.addAttribute("postsVoList", result.get("postsVoList"));
        model.addAttribute("pagination", result.get("pagination"));
        model.addAttribute("searchType", result.get("searchType"));
        model.addAttribute("searchKeyword", result.get("searchKeyword"));
        return "posts/list";
    }

    // 게시글 보기 (화면, GET)
    @GetMapping("/{id}")
    public String readGet(@PathVariable("id") int id, Model model) {
        model.addAttribute("postsVo", postsService.read(id));
        return "posts/read";
    }

    // 게시글 수정 (화면, GET)
    @GetMapping("/{id}/update")
    public String updateGet(@PathVariable("id") int id, Model model) {
        model.addAttribute("postsVo", postsService.read(id));
        return "posts/update";
    }

    // 게시글 수정 (처리, POST)
    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable("id") int id, PostsVo postsVo, RedirectAttributes redirectAttributes) {
        postsVo.setId(id);

        try {
            // 기존 게시글 정보 조회
            PostsVo existingPostsVo = postsService.read(postsVo.getId());
            if (existingPostsVo == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "게시글을 찾을 수 없습니다.");
                return "redirect:/posts/";
            }

            // 파일 처리
            MultipartFile uploadFile = postsVo.getUploadFile();
            String existingFileName = existingPostsVo.getFileName();

            // 기존 파일 삭제 처리
            if (postsVo.isDeleteFile() || (uploadFile != null && !uploadFile.isEmpty())) {
                if (existingFileName != null) {
                    File fileToDelete = new File(uploadPath + File.separator + existingFileName);
                    if (fileToDelete.exists()) {
                        fileToDelete.delete();
                    }
                    // 파일 정보 초기화
                    postsVo.setFileName(null);
                    postsVo.setOriginalFileName(null);
                }
            } else {
                // 파일을 삭제하지 않고 유지하는 경우
                postsVo.setFileName(existingFileName);
                postsVo.setOriginalFileName(existingPostsVo.getOriginalFileName());
            }

            // 새 파일 업로드 처리
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String originalFileName = uploadFile.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 업로드 디렉토리가 없으면 생성
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 파일 저장
                File destFile = new File(uploadPath + File.separator + fileName);
                uploadFile.transferTo(destFile);

                // 파일 정보 설정
                postsVo.setFileName(fileName);
                postsVo.setOriginalFileName(originalFileName);
            }

            // 게시글 수정
            boolean updated = postsService.update(postsVo);
            if (updated) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
                return "redirect:/posts/" + id;
            }

            redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다.");
            return "redirect:/posts/" + id + "/update";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/posts/" + id + "/update";
        }
    }

    // 게시글 삭제 (처리, POST)
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") int id, PostsVo postsVo, RedirectAttributes redirectAttributes) {
        postsVo.setId(id);
        boolean deleted = postsService.delete(postsVo);

        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 삭제되었습니다.");
            return "redirect:/posts/";
        }
        
        redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다.");
        return("redirect:/posts/" + id);
    }
}