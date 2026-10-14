package brainacadorg.lab_4.Task_5;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Company {
    private String id;
    private String name;
    private String country;
    private String logo;
    private int employees;
    private String description;
    private List<NewsItem> news;
    private List<LaptopModel> models;
}
