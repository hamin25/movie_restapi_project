package org.example.file.week05_assignment.domain;

public class Movie {
    private Long id;
    private String title;
    private String director;
    private String genre;
    private Long runningTime;
    private Long score;

    public Movie() {}
    public Movie(Long id, String title, String director, String genre, Long runningTime, Long score){
        this.id = id;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.runningTime = runningTime;
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Long getRunningTime() {
        return runningTime;
    }

    public void setRunningTime(Long runningTime) {
        this.runningTime = runningTime;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
