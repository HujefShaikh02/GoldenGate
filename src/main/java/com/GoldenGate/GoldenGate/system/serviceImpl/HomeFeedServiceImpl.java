package com.GoldenGate.GoldenGate.system.serviceImpl;

import com.GoldenGate.GoldenGate.system.DTO.HomeFeedDTO;
import com.GoldenGate.GoldenGate.system.model.Post;
import com.GoldenGate.GoldenGate.system.model.PostImage;
import com.GoldenGate.GoldenGate.system.model.Profile;
import com.GoldenGate.GoldenGate.system.repository.CommentRepository;
import com.GoldenGate.GoldenGate.system.repository.PostImageRepository;
import com.GoldenGate.GoldenGate.system.repository.PostRepository;
import com.GoldenGate.GoldenGate.system.repository.ProfileRepository;
import com.GoldenGate.GoldenGate.system.service.HomeFeedService;
import com.GoldenGate.GoldenGate.system.service.UserPostLikesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeFeedServiceImpl implements HomeFeedService {


    private final PostRepository postRepository;
    private final PostImageRepository postImageRepository;
    private final UserPostLikesService userPostLikesService;
    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;

    @Autowired
    public HomeFeedServiceImpl(PostRepository postRepository, PostImageRepository postImageRepository, UserPostLikesService userPostLikesService, CommentRepository commentRepository, ProfileRepository profileRepository) {
        this.postRepository = postRepository;
        this.postImageRepository = postImageRepository;
        this.userPostLikesService = userPostLikesService;
        this.commentRepository = commentRepository;
        this.profileRepository = profileRepository;
    }




    @Override
    public Page<HomeFeedDTO> getAllPosts(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Post> postPage = postRepository.findAll(pageable);

        return postPage.map(this::convertToHomeFeedDTO);
    }

    public HomeFeedDTO convertToHomeFeedDTO(Post post) {
        HomeFeedDTO homeFeedDTO = new HomeFeedDTO();

        Integer userId=post.getUser().getUserId();
        Profile Profile = profileRepository.findByUser_UserId(userId);

        homeFeedDTO.setProfileId(Profile.getProfileId());
        homeFeedDTO.setFullName(Profile.getFullName());
        homeFeedDTO.setAvatar(Profile.getAvatar());
        homeFeedDTO.setPostId(post.getPostId());
        homeFeedDTO.setContent(post.getContent());

        List<PostImage> postImages = postImageRepository.findByPost(post);
        List<String> imageUrls = postImages.stream()
                .map(PostImage::getImage)
                .collect(Collectors.toList());
        homeFeedDTO.setImages(imageUrls);


//        homeFeedDTO.setImages(postImageRepository.findByPost(post)
//                .stream()
//                .map(PostImage::getImage)
//                .collect(Collectors.toList()));
//

        return homeFeedDTO;
    }

}
