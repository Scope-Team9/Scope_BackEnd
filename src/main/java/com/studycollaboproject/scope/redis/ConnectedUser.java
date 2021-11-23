package com.studycollaboproject.scope.redis;

import com.studycollaboproject.scope.util.Timestamped;
import lombok.Getter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;

@Getter
@RedisHash(value = "connected", timeToLive = 86400)
public class ConnectedUser extends Timestamped {

    @Id
    private String snsId;
    private String userUuid;

    public ConnectedUser(String snsId, String userUuid) {
        this.snsId = snsId;
        this.userUuid = userUuid;
    }
}