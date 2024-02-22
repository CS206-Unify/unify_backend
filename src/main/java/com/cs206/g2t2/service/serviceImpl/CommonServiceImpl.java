package com.cs206.g2t2.service.serviceImpl;

import com.cs206.g2t2.data.response.Response;
import com.cs206.g2t2.service.services.CommonService;
import com.cs206.g2t2.service.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    //    This defaultResponse is on purpose so that it @Autowired DefaultResponse class instead of Response implementation
    private final Response defaultResponse;
    private final JwtService jwtService;

    public String returnOldUsername(String token) {
        String jwt = token.substring(7);
        return jwtService.extractUsername(jwt);
    }
}
