package brainacadorg.lab_4.Task_5;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public class DataLoader {
    private static List<Company> companies;

    public static synchronized List<Company> getCompanies() {
        if (companies == null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                InputStream is = DataLoader.class.getClassLoader().getResourceAsStream("Task_5/data.json");
                if (is != null) {
                    companies = mapper.readValue(is, new TypeReference<List<Company>>() {});
                } else {
                    companies = Collections.emptyList();
                }
            } catch (Exception e) {
                e.printStackTrace();
                companies = Collections.emptyList();
            }
        }
        return companies;
    }

    public static Company getCompanyById(String id) {
        if (id == null || id.isEmpty()) {
            return getCompanies().isEmpty() ? null : getCompanies().get(0);
        }
        return getCompanies().stream()
                .filter(c -> c.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(getCompanies().isEmpty() ? null : getCompanies().get(0));
    }
}