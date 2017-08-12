package com.philvr.jacksonxmltest.NYTimesBooks;

import java.util.List;

/**
 * @author Philip Van Raalte
 * @date 2017-08-12
 */

public class Book {
    private String title;
    private String description;
    private String contributor;
    private String author;
    private String contributor_note;
    private int price;
    private String age_group;
    private String publishers;
    private String[] isbns;
    private List<RankHistory> ranks_history;
    private List<Review> reviews;
}
