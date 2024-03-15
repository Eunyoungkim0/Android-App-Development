package edu.uncc.assignment08.models;

import java.io.Serializable;

public class AuthResponse implements Serializable {
    public String user_id;
    public String user_fullname;
    public String token;

    /*

    {
    "status": "ok",
    "token": "wcUJRckNsODlsbkgyZUJS",
    "user_id": 1,
    "user_fullname": "Alice Smith"
}
     */
}
