package com.company;

public class Main {

    public static void main(String[] args) {
        HttpClient hc = new HttpClient();
        hc.doGet("http://127.0.0.1:8080");
    }
}
