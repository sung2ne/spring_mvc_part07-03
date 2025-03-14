package com.example.spring.posts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.spring.libs.Pagination;

@Service
public class PostsService {
    
    @Autowired
    PostsDao postsDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 비밀번호 검증
    private boolean verifyPassword(int id, String password) {
        PostsVo posts = postsDao.read(id);
        return posts != null && passwordEncoder.matches(password, posts.getPassword());
    }

    // 게시글 등록
    public boolean create(PostsVo postsVo) {
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(postsVo.getPassword());
        postsVo.setPassword(encodedPassword);

        int result = postsDao.create(postsVo);
        return result > 0;
    }

    // 게시글 목록
    public Map<String, Object> list(int page, int pageSize, String searchType, String searchKeyword) {
        // 전체 게시글 수 조회
        int totalCount = postsDao.getTotalCount(searchType, searchKeyword);

        // 페이지네이션 정보 생성
        Pagination pagination = new Pagination(page, pageSize, totalCount);

        // 페이징된 게시글 목록 조회
        List<PostsVo> postsVoList = postsDao.list(pagination.getOffset(), pageSize, searchType, searchKeyword);

        // 결과 맵 생성
        Map<String, Object> result = new HashMap<>();
        result.put("postsVoList", postsVoList);
        result.put("pagination", pagination);
        result.put("searchType", searchType);
        result.put("searchKeyword", searchKeyword);

        return result;
    }

    // 게시글 보기
    public PostsVo read(int id) {
        return postsDao.read(id);
    }

    // 게시글 수정
    public boolean update(PostsVo postsVo) {
        // 비밀번호 검증
        if (!verifyPassword(postsVo.getId(), postsVo.getPassword())) {
            return false;
        }

        int result = postsDao.update(postsVo);
        return result > 0;
    }

    // 게시글 삭제
    public boolean delete(PostsVo postsVo) {
        // 비밀번호 검증
        if (!verifyPassword(postsVo.getId(), postsVo.getPassword())) {
            return false;
        }

        int result = postsDao.delete(postsVo.getId());
        return result > 0;
    }
}
