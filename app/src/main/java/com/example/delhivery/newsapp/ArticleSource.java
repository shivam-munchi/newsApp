package com.example.delhivery.newsapp;

public class ArticleSource{
        private String id;
        private String name;

        public ArticleSource(String id, String name) {
            this.id = id;
            this.name = name;
        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
}
