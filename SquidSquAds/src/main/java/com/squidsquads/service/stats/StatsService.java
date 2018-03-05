package com.squidsquads.service.stats;

import com.squidsquads.form.stats.response.StatsResponse;
import org.springframework.stereotype.Service;

@Service
public class StatsService {

    public StatsResponse getWebStatsForUser(Long userid) {
        // TODO implement service method
        return new StatsResponse().todo();
    }
}
