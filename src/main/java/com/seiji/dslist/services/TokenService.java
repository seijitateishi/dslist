package com.seiji.dslist.services;

import com.auth0.jwt.algorithms.Algorithm;
import com.seiji.dslist.entities.User;
import org.springframework.stereotype.Service;

@Service
public class TokenService {
    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256()
        }
    }

}
