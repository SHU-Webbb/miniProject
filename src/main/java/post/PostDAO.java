package post;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import common.JDBConnect;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.Part;
import webtoonplatform.dto.loginDTO;

public class PostDAO extends JDBConnect {

    // DB연결 작업 처리
    public PostDAO(ServletContext application) {
        super(application); // JDBconnect(application) 생성자 호출
    }

    // 게시글 작성
    public int createPost(String title, String content, String titlenum, int usernum, Part imageSumPart, Part imageUrlPart) {
        int result = 0;
        try {
            // 쿼리 작성
            String query = "INSERT INTO posts (post_id, usernum, title, content, image_url, image_sum, titlenum) VALUES (seq_posts_num.NEXTVAL, ?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(query);

            pstmt.setInt(1, usernum);
            pstmt.setString(2, title);
            pstmt.setString(3, content);

            // 파일 업로드 메서드 호출하여 파일 업로드 후 파일명 설정
            String imageUrlFileName = (imageUrlPart != null) ? uploadFile(imageUrlPart) : "";
            pstmt.setString(4, imageUrlFileName);

            // 파일 업로드 메서드 호출하여 파일 업로드 후 파일명 설정
            String imageSumFileName = (imageSumPart != null) ? uploadFile(imageSumPart) : "";
            pstmt.setString(5, imageSumFileName);

            pstmt.setString(6,titlenum);

            // 쿼리 실행
            result = pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    // 파일 업로드를 처리하는 메서드 추가
    private String uploadFile(Part filePart) {
        // 업로드 경로 설정 (실제 업로드 경로로 수정해야 합니다.)
        String uploadPath = "C:\\Users\\user\\Desktop\\JspWorkspace\\image";

        // 파일명 생성
        String fileName = getFileName(filePart);

        // 파일 업로드
        try (InputStream input = filePart.getInputStream();
                OutputStream output = new FileOutputStream(new File(uploadPath + File.separator + fileName))) {
            byte[] buffer = new byte[20971520];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileName;
    }
 
    // 파일 이름 가져오기 메서드 추가
    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] tokens = contentDisposition.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }

  // 게시글 리스트 가져오기
  public List<PostDTO> getAllPosts() {
    List<PostDTO> posts = new ArrayList<>();

    // 쿼리문 작성
    String query = "SELECT * FROM posts ORDER BY created_at DESC";
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query); // 쿼리실행
      while (rs.next()) {
        PostDTO post = new PostDTO();
        post.setPostId(rs.getInt("post_id"));
        post.setUserNum(rs.getInt("usernum"));
        post.setTitle(rs.getString("title"));
        post.setId(rs.getString("id"));
        post.setContent(rs.getString("content"));
        post.setImageUrl(rs.getString("image_url"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        post.setUpdatedAt(rs.getTimestamp("updated_at"));

        posts.add(post);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return posts;
  }

  // 새로운 게시물의 ID 가져오기
  public int getLastPostId() {
    int lastInsertedId = -1;
    try {
      String query = "SELECT MAX(post_id) AS last_id FROM posts";
      pstmt = conn.prepareStatement(query);
      rs = pstmt.executeQuery();
      if (rs.next()) {
        lastInsertedId = rs.getInt("last_id");
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return lastInsertedId;
  }

  // 메인 페이지에서 사용할 웹툰 정보 가져오기 (title, content, image_url만 포함)
  public List<PostDTO> getMainImage() {
    List<PostDTO> posts = new ArrayList<>();

    // 쿼리문 작성 (title, content, image_sum만 가져오도록 수정)
    String query = "SELECT post_id, usernum, title, content, image_sum, created_at, updated_at, titlenum FROM posts ORDER BY created_at DESC";
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query); // 쿼리실행
      while (rs.next()) {
        PostDTO post = new PostDTO();
        post.setPostId(rs.getInt("post_id"));
        post.setUserNum(rs.getInt("usernum"));
        post.setTitle(rs.getString("title"));
        post.setContent(rs.getString("content"));
        post.setImageSum(rs.getString("image_sum"));
        post.setCreatedAt(rs.getTimestamp("created_at"));
        post.setUpdatedAt(rs.getTimestamp("updated_at"));
        post.setTitleNum(rs.getString("titlenum"));

        posts.add(post);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return posts;
  }

  // 메인 페이지에서 사용할 웹툰 정보 가져오기 (usernum, titleNum만 포함)
  public List<PostDTO> getList() {
    List<PostDTO> posts = new ArrayList<>();

    // 쿼리문 작성 (usernum, titleNum만 가져오도록 수정)
    String query = "SELECT usernum, titlenum, post_id FROM posts ORDER BY created_at DESC";
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query); // 쿼리실행
      while (rs.next()) {
        PostDTO post = new PostDTO();
        post.setUserNum(rs.getInt("usernum"));
        post.setTitleNum(rs.getString("titlenum"));
        post.setPostId(rs.getInt("post_id"));
        posts.add(post);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return posts;
  }

  // 게시글 상세보기
  public PostDTO getPost(int postId) {
    PostDTO post = new PostDTO();
    loginDTO userId = new loginDTO();
    String query = "SELECT p.*, u.id as user_id FROM posts p JOIN users u ON p.usernum = u.usernum WHERE p.post_id =?";

    try {
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, postId);
      rs = pstmt.executeQuery();

      if (rs.next()) {
        userId.setId(rs.getString("user_id")); // users 테이블의 id 가져옴.

        post = new PostDTO();
        post.setPostId(rs.getInt("post_id"));
        post.setUserNum(rs.getInt("usernum"));
        post.setId(userId.getId());
        post.setTitle(rs.getString("title"));
        post.setTitleNum(rs.getString("titlenum"));
        post.setContent(rs.getString("content"));
        post.setImageUrl(rs.getString("image_url"));
        post.setImageSum(rs.getString("image_sum"));
        post.setCreatedAt(rs.getDate("created_at"));
        post.setUpdatedAt(rs.getDate("updated_at"));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return post;
  }

//게시글 수정하기
public int updatePost(int postId, String title, String content, String titlenum, Part imageSumPart, Part imageUrlPart) {
   try {
       // 수정할 게시글 정보 가져오기
       PostDTO post = getPost(postId);
       if (post == null) {
           // 게시글이 존재하지 않으면 -1 반환
           return -1;
       }

       String imageUrlFileName = post.getImageUrl(); // 기존 이미지 파일명 가져오기
       String imageSumFileName = post.getImageSum(); // 기존 썸네일 파일명 가져오기

       // 파일 업로드 처리
       if (imageUrlPart != null) {
           imageUrlFileName = uploadFile(imageUrlPart); // 새로운 이미지 파일명 생성
       }
       if (imageSumPart != null) {
           imageSumFileName = uploadFile(imageSumPart); // 새로운 썸네일 파일명 생성
       }

       // 쿼리 작성
       String query = "UPDATE posts SET title=?, titlenum=?, content=?,  image_url=?, image_sum=?,  updated_at=SYSTIMESTAMP WHERE post_id=?";
       pstmt = conn.prepareStatement(query);
       pstmt.setString(1, title);
       pstmt.setString(2, titlenum);
       pstmt.setString(3, content);
       pstmt.setString(4, imageUrlFileName); // 새로운 이미지 파일명 설정
       pstmt.setString(5, imageSumFileName); // 새로운 썸네일 파일명 설정
       pstmt.setInt(6, postId);

       // 쿼리 실행
       int result = pstmt.executeUpdate();
       return result;
   } catch (Exception e) {
       e.printStackTrace();
       return -1; // 에러 발생 시 -1 반환
   }
}

 
  //게시글 삭제하기
  public int deletePost(int postId) {
    try {
      // 쿼리 작성
      String query = "DELETE FROM posts WHERE post_id=?";
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, postId);

      // 쿼리 실행
      int result = pstmt.executeUpdate();
      return result;
    } catch (Exception e) {
      e.printStackTrace();
      return -1; // 에러 발생 시 -1 반환
    }
  }

  // 웹툰 보는 DAO
  public List<PostDTO> getPostViewData() {
    List<PostDTO> posts = new ArrayList<>();

    // 쿼리문 작성 (image_url, title, usernum, titlenum, created_at 가져오도록 수정)
    String query = "SELECT image_url, title, usernum, titlenum, created_at FROM posts ORDER BY created_at DESC";
    try {
      stmt = conn.createStatement();
      rs = stmt.executeQuery(query); // 쿼리실행
      while (rs.next()) {
        PostDTO post = new PostDTO();
        post.setImageUrl(rs.getString("image_url"));
        post.setTitle(rs.getString("title"));
        post.setUserNum(rs.getInt("usernum"));
        post.setTitleNum(rs.getString("titlenum"));
        post.setCreatedAt(rs.getTimestamp("created_at"));

        posts.add(post);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return posts;
  }

  // 웹툰 보는 DAO
  public List<PostDTO> getPostViewData(int postId) {
    List<PostDTO> posts = new ArrayList<>();

    // 쿼리문 작성 (image_url, title, usernum, titlenum, created_at 가져오도록 수정)
    String query = "SELECT image_url, title, usernum, titlenum, created_at FROM posts WHERE post_id = ? ORDER BY created_at DESC";
    try {
      pstmt = conn.prepareStatement(query);
      pstmt.setInt(1, postId);
      rs = pstmt.executeQuery(); // 쿼리실행
      while (rs.next()) {
        PostDTO post = new PostDTO();
        post.setImageUrl(rs.getString("image_url"));
        post.setTitle(rs.getString("title"));
        post.setUserNum(rs.getInt("usernum"));
        post.setTitleNum(rs.getString("titlenum"));
        post.setCreatedAt(rs.getTimestamp("created_at"));

        posts.add(post);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return posts;
  }
  
  public List<PostDTO> getListByUserNum(int userNum) {
       List<PostDTO> posts = new ArrayList<>();

       // 쿼리문 작성 (usernum에 해당하는 게시글만 가져오도록 수정)
       String query = "SELECT * FROM posts WHERE usernum = ? ORDER BY created_at DESC";
       try {
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1, userNum);
         rs = pstmt.executeQuery(); // 쿼리실행
         while (rs.next()) {
           PostDTO post = new PostDTO();
           post.setPostId(rs.getInt("post_id"));
           post.setUserNum(rs.getInt("usernum"));
           post.setTitle(rs.getString("title"));;
           post.setContent(rs.getString("content"));
           post.setImageUrl(rs.getString("image_url"));
           post.setCreatedAt(rs.getDate("created_at"));
           post.setUpdatedAt(rs.getDate("updated_at"));
           post.setImageSum(rs.getString("image_sum"));
           post.setTitleNum(rs.getString("titlenum"));

           posts.add(post);
         }
       } catch (SQLException e) {
         e.printStackTrace();
       }
       return posts;
     }
  
}