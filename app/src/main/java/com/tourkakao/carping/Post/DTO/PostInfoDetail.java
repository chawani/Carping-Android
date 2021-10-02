package com.tourkakao.carping.Post.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PostInfoDetail implements Serializable {
    private int id;
    private int userpost_id;
    private String author_name;
    private String author_profile;
    private String author_comment;
    private String title;
    private String thumbnail;
    private int point;
    private String info;
    private String recommend_to;
    private List<Review> review;
    private float star1_avg;
    private float star2_avg;
    private float star3_avg;
    private float star4_avg;
    private float my_star_avg;
    private float total_star_avg;
    private int my_review_count;
    private int review_count;
    private String is_liked;
    private String preview_image1;
    private String preview_image2;
    private String preview_image3;
    private int like_count;
    private int contents_count;

    public int getUserpost_id() {
        return userpost_id;
    }

    public void setUserpost_id(int userpost_id) {
        this.userpost_id = userpost_id;
    }

    public int getContents_count() {
        return contents_count;
    }

    public void setContents_count(int contents_count) {
        this.contents_count = contents_count;
    }

    public String getAuthor_comment() {
        return author_comment;
    }

    public void setAuthor_comment(String author_comment) {
        this.author_comment = author_comment;
    }

    public int getLike_count() {
        return like_count;
    }

    public void setLike_count(int like_count) {
        this.like_count = like_count;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_profile() {
        return author_profile;
    }

    public void setAuthor_profile(String author_profile) {
        this.author_profile = author_profile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getRecommend_to() {
        return recommend_to;
    }

    public void setRecommend_to(String recommend_to) {
        this.recommend_to = recommend_to;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public float getStar1_avg() {
        return star1_avg;
    }

    public void setStar1_avg(float star1_avg) {
        this.star1_avg = star1_avg;
    }

    public float getStar2_avg() {
        return star2_avg;
    }

    public void setStar2_avg(float star2_avg) {
        this.star2_avg = star2_avg;
    }

    public float getStar3_avg() {
        return star3_avg;
    }

    public void setStar3_avg(float star3_avg) {
        this.star3_avg = star3_avg;
    }

    public float getStar4_avg() {
        return star4_avg;
    }

    public void setStar4_avg(float star4_avg) {
        this.star4_avg = star4_avg;
    }

    public float getMy_star_avg() {
        return my_star_avg;
    }

    public void setMy_star_avg(float my_star_avg) {
        this.my_star_avg = my_star_avg;
    }

    public float getTotal_star_avg() {
        return total_star_avg;
    }

    public void setTotal_star_avg(float total_star_avg) {
        this.total_star_avg = total_star_avg;
    }

    public int getMy_review_count() {
        return my_review_count;
    }

    public void setMy_review_count(int my_review_count) {
        this.my_review_count = my_review_count;
    }

    public int getReview_count() {
        return review_count;
    }

    public void setReview_count(int review_count) {
        this.review_count = review_count;
    }

    public String getIs_liked() {
        return is_liked;
    }

    public void setIs_liked(String is_liked) {
        this.is_liked = is_liked;
    }

    public String getPreview_image1() {
        return preview_image1;
    }

    public void setPreview_image1(String preview_image1) {
        this.preview_image1 = preview_image1;
    }

    public String getPreview_image2() {
        return preview_image2;
    }

    public void setPreview_image2(String preview_image2) {
        this.preview_image2 = preview_image2;
    }

    public String getPreview_image3() {
        return preview_image3;
    }

    public void setPreview_image3(String preview_image3) {
        this.preview_image3 = preview_image3;
    }
}
