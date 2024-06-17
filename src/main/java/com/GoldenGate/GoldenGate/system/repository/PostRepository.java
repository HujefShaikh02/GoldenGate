package com.GoldenGate.GoldenGate.system.repository;

import com.GoldenGate.GoldenGate.system.model.Comment;
import com.GoldenGate.GoldenGate.system.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {


   // @Query("SELECT p FROM Post p JOIN FETCH p.images pi WHERE p.postId > ?2 AND p.user.userId <> ?1 ORDER BY p.postId ASC")
    //Page<Post> findPostsForHomeFeed(Long userId, Long lastPostId, Pageable pageable);



    Page<Post> findAll(Pageable pageable);

    List<Post> findByUser_UserId(Long userId);

    Page<Post> findByUser_UserId(Long userId, Pageable pageable);
//
//    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId")
//    List<Comment> findCommentsByPostId(@Param("postId") Long postId);

   // List<Post> findByIdGreaterThan(Long lastFetchedPostId, int count);

    //List<Post> findFirstN(int count);
}

