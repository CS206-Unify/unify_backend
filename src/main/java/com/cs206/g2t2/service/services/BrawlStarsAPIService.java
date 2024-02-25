package com.cs206.g2t2.service.services;

import com.cs206.g2t2.data.response.Response;
import org.springframework.stereotype.Service;

@Service
public interface BrawlStarsAPIService {

    Response getBsStats(String username);

    Response updateBsStats(String username);
}
