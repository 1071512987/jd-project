package com.hzau.college.common.repository;//package com.hzau.college.common.repository;
//
//import com.hzau.college.pojo.es.EsPost;
//import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
///**
// * @Author 半夜里咳嗽的狼
// * @Date 2023/2/7 10:15
// * @Description: TODO
// */
//public interface EsPostRepository extends ElasticsearchRepository<EsPost, Long> {
//
//    Page<EsPost> findByPostTitle(String postTitle, Pageable pageable);
//
//    Page<EsPost> findByPostTitleOrPostContent(String postTitle, String postContent, Pageable pageable);
//
//    List<EsPost> findFirst10ByOrderByCreateTimeDesc();
//}
