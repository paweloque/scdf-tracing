package io.spring.dataflow.sample.usagecostprocessor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class MyConfigController {

    @GetMapping("/configs")
    public List<String> cachedConfig() {
        ArrayList<String> configs = new ArrayList<>();
        configs.add("hello");
        configs.add("nesko");
        return configs;
    }

}
