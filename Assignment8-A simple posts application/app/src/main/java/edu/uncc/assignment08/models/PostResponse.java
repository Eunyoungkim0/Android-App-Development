package edu.uncc.assignment08.models;

import java.util.ArrayList;

public class PostResponse {
    public String status;
    public ArrayList<Post> posts = new ArrayList<>();
    public String page;
    public int pageSize;
    public int totalCount;
}
