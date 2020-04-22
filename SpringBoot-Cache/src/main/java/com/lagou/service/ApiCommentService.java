package com.lagou.service;

import com.lagou.pojo.Comment;
import com.lagou.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class ApiCommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    //先使用API方式进行缓存：先去缓存中查找，缓存中有就返回，没有就查询数据库
    @Cacheable(cacheNames = "comment",unless = "#result==null")
    public Comment findCommentById(Integer id){
        Object o = redisTemplate.opsForValue().get("comment_" + id);
        if(o != null){  //从缓存中查询到了数据
            return (Comment) o;
        }else{   //缓存中没有，从数据库查询
            Optional<Comment> optional = commentRepository.findById(id);
            if(optional.isPresent()){
                Comment comment = optional.get();
                //将查询结果存到缓存中，同时还可以设置有效期 (此处设置为1天)
                redisTemplate.opsForValue().set("comment_"+id,comment,1, TimeUnit.DAYS);
                return comment;
            }
        }
        return null;
    }


    //更新方法
    @CachePut(cacheNames = "comment",key = "#result.id")
    public Comment updateComment(Comment comment){
        commentRepository.updateAuthorById(comment.getAuthor(),comment.getId());
        //将更新数据进行缓存更新
        redisTemplate.opsForValue().set("comment_"+comment.getId(),comment);
        return comment;
    }


    //删除方法
    @CacheEvict(cacheNames = "comment")
    public void deleteComment(Integer id){
        commentRepository.deleteById(id);
        //将数据从缓存中删除
        redisTemplate.delete("comment_"+id);
    }



}
