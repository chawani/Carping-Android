package com.tourkakao.carping.theme.Dataclass;

import java.util.List;

public class DaumBlog {
    private List documents;
    public List getDocuments(){
        return documents;
    }
    public class Blog{
        private String title;
        private String contents;
        private String url;
        private String blogname;
        private String thumbnail;
        private String datetime;

        public String getTitle() {
            return title;
        }

        public String getContents() {
            return contents;
        }

        public String getUrl() {
            return url;
        }

        public String getBlogname() {
            return blogname;
        }

        public String getThumbnail() {
            return thumbnail;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }
    }

}
