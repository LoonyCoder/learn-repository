package com.lagou.service;

import com.lagou.pojo.Comment;
import com.lagou.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    //@Cacheable：将该方法查询结果comment存放在springboot默认缓存中
    //cacheNames：起一个缓存命名空间，对应缓存唯一标识
    //unless:满足指定条件就不进行缓存（支持SpEL表达式） 如果结果为null就不进行缓存
    @Cacheable(cacheNames = "comment",unless = "#result==null")
    public Comment findCommentById(Integer id){
        Optional<Comment> optional = commentRepository.findById(id);
        if(optional.isPresent()){
            Comment comment = optional.get();
            return comment;
        }
        return null;
    }


    //更新方法
    @CachePut(cacheNames = "comment",key = "#result.id")
    public Comment updateComment(Comment comment){
        commentRepository.updateAuthorById(comment.getAuthor(),comment.getId());
        return comment;
    }


    //删除方法
    @CacheEvict(cacheNames = "comment")
    public void deleteComment(Integer id){
        commentRepository.deleteById(id);
    }



}
